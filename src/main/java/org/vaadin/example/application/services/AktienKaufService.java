package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.classes.Transaktion;
import org.vaadin.example.application.classes.StockQuote;
import org.vaadin.example.application.repositories.AktieRepository;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AktienKaufService {

    private final AlphaVantageService alphaVantageService;
    private final DepotRepository depotRepository;
    private final TransaktionRepository transaktionRepository;
    private final AktieRepository aktieRepository;

    public AktienKaufService(AlphaVantageService alphaVantageService,
                             DepotRepository depotRepository,
                             TransaktionRepository transaktionRepository,
                             AktieRepository aktieRepository) {
        this.alphaVantageService = alphaVantageService;
        this.depotRepository = depotRepository;
        this.transaktionRepository = transaktionRepository;
        this.aktieRepository = aktieRepository;
    }

    /**
     * Führt den Kauf einer Aktie durch, speichert die Kauf-Transaktion und fügt die Aktie dem Depot hinzu.
     * Stellt sicher, dass die Aktiendetails in der Datenbank persistent gespeichert werden.
     *
     * @param symbol       Das Tickersymbol der Aktie (z.B. AAPL)
     * @param stueckzahl   Anzahl der zu kaufenden Aktien
     * @param handelsplatz Börsenplatz des Kaufs
     * @param depot        Depot, dem die Aktie hinzugefügt werden soll
     * @return Das gekaufte Aktie-Objekt oder null bei Fehler
     */
    @Transactional
    public Aktie kaufeAktie(String symbol, int stueckzahl, String handelsplatz, Depot depot) {
        // Grundlegende Validierung der Eingabeparameter
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
            System.err.println("Fehler: Ungültige Eingabeparameter für den Aktienkauf.");
            return null;
        }

        // 1. Aktuellen Kurs abrufen (für die Kauf-Transaktion)
        StockQuote currentQuote = alphaVantageService.getCurrentStockQuote(symbol);
        if (currentQuote == null) {
            System.err.println("Fehler: Konnte keinen aktuellen Kurs für Symbol " + symbol + " abrufen.");
            return null;
        }
        double kurs = currentQuote.getPrice();
        double gebuehren = 2.50; // Feste Gebühren

        // 2. Vollständige Aktiendetails abrufen
        Aktie fetchedAktie = alphaVantageService.getFundamentalData(symbol);
        if (fetchedAktie == null) {
            System.err.println("Fehler: Konnte keine fundamentalen Daten für Symbol " + symbol + " abrufen.");
            return null;
        }

        // 3. Prüfen, ob die Aktie bereits in der Datenbank existiert
        Optional<Aktie> existingAktieOptional = Optional.ofNullable(aktieRepository.findBySymbol(symbol));
        Aktie aktieToPersist;

        if (existingAktieOptional.isPresent()) {
            // Aktie existiert bereits, aktualisiere ihre Details
            aktieToPersist = existingAktieOptional.get();
            // Aktualisiere alle Felder mit den neuesten Daten von Alpha Vantage
            aktieToPersist.setName(fetchedAktie.getName());
            aktieToPersist.setSymbol(fetchedAktie.getSymbol());
            aktieToPersist.setUnternehmensname(fetchedAktie.getUnternehmensname());
            aktieToPersist.setDescription(fetchedAktie.getDescription());
            aktieToPersist.setExchange(fetchedAktie.getExchange());
            aktieToPersist.setCurrency(fetchedAktie.getCurrency());
            aktieToPersist.setCountry(fetchedAktie.getCountry());
            aktieToPersist.setSector(fetchedAktie.getSector());
            aktieToPersist.setIndustry(fetchedAktie.getIndustry());
            aktieToPersist.setMarketCap(fetchedAktie.getMarketCap());
            aktieToPersist.setEbitda(fetchedAktie.getEbitda());
            aktieToPersist.setPegRatio(fetchedAktie.getPegRatio());
            aktieToPersist.setBookValue(fetchedAktie.getBookValue());
            aktieToPersist.setDividendPerShare(fetchedAktie.getDividendPerShare());
            aktieToPersist.setDividendYield(fetchedAktie.getDividendYield());
            aktieToPersist.setEps(fetchedAktie.getEps());
            aktieToPersist.setForwardPE(fetchedAktie.getForwardPE());
            aktieToPersist.setBeta(fetchedAktie.getBeta());
            aktieToPersist.setYearHigh(fetchedAktie.getYearHigh());
            aktieToPersist.setYearLow(fetchedAktie.getYearLow());
            aktieToPersist.setDividendDate(fetchedAktie.getDividendDate());

            // Save the updated Aktie
            aktieRepository.save(aktieToPersist);
        } else {
            // Aktie existiert nicht, speichere die neue Aktie
            aktieToPersist = fetchedAktie;
            aktieRepository.save(aktieToPersist);
        }

        // 4. Kauf-Transaktion erstellen
        // Der Kauf wird mit der persistenten Aktie verknüpft
        Kauf kauf = new Kauf(
                handelsplatz,
                LocalDateTime.now().toLocalDate(), // Use toLocalDate() to match Kauf constructor
                gebuehren,
                kurs,
                stueckzahl,
                aktieToPersist,
                null // Assuming no Ausschuettung for a purchase
        );

        // 5. Kauf-Transaktion speichern
        transaktionRepository.save(kauf);

        // 6. Aktie dem Depot hinzufügen (dies verwaltet die Stückzahl im Depot)
        depot.wertpapierHinzufuegen(aktieToPersist, stueckzahl);

        // 7. Depot speichern (um die Änderungen am Depot zu persistieren)
        depotRepository.save(depot);

        System.out.println("Aktie " + symbol + " erfolgreich gekauft und Details gespeichert.");
        return aktieToPersist;
    }

    /**
     * Ruft den aktuellen Kurs für ein gegebenes Tickersymbol ab.
     *
     * @param symbol Das Tickersymbol der Aktie.
     * @return Der aktuelle Kurs der Aktie.
     * @throws IllegalArgumentException wenn das Symbol leer ist.
     * @throws RuntimeException wenn kein Kurs gefunden werden konnte.
     */
    public double getKursFürSymbol(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol darf nicht leer sein.");
        }

        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            throw new RuntimeException("Kein Kurs für Symbol gefunden: " + symbol);
        }

        return quote.getPrice();
    }

    /**
     * Ruft alle Transaktionen aus der Datenbank ab.
     *
     * @return Eine Liste aller Transaktionen.
     */
    public List<Transaktion> getAllTransaktionen() {
        return transaktionRepository.findAll();
    }
}