package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service-Klasse für den Kauf von Aktien.
 *
 * Stellt Methoden bereit, um Aktien zu kaufen, Transaktionen zu speichern,
 * Aktieninformationen zu aktualisieren und Kurse abzufragen.
 *
 * @author Batuhan Güvercin, Henrik Dollmann
 */
@Service
public class AktienKaufService {

    private final AlphaVantageService alphaVantageService;
    private final DepotRepository depotRepository;
    private final TransaktionRepository transaktionRepository;
    private final AktieRepository aktieRepository;

    /**
     * Konstruktor für den AktienKaufService.
     *
     * @param alphaVantageService Service zur Abfrage von Aktienkursen und Fundamentaldaten
     * @param depotRepository Repository für Depots
     * @param transaktionRepository Repository für Transaktionen
     * @param aktieRepository Repository für Aktien
     */
    @Autowired
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
     *
     * @param symbol       Das Tickersymbol der Aktie (z.B. AAPL)
     * @param stueckzahl   Anzahl der zu kaufenden Aktien
     * @param handelsplatz Börsenplatz des Kaufs
     * @param depot        Depot, dem die Aktie hinzugefügt werden soll
     * @return Das gekaufte Aktie-Objekt oder null bei Fehler
     */
    @Transactional
    public Aktie kaufeAktie(String symbol, int stueckzahl, String handelsplatz, Depot depot) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
            return null;
        }

        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            return null;
        }

        double kurs = quote.getPrice();
        double gebuehren = 2.50;

        Kauf kauf = new Kauf(handelsplatz, LocalDate.now(), gebuehren, kurs, stueckzahl, null, null);

        List<Transaktion> transaktionen = new ArrayList<>();
        transaktionen.add(kauf);

        // 2. Vollständige Aktiendetails abrufen
        Aktie fetchedAktie = alphaVantageService.getFundamentalData(symbol);
        if (fetchedAktie == null) {
            System.err.println("Fehler: Konnte keine fundamentalen Daten für Symbol " + symbol + " abrufen.");
            return null;
        }
        // 3. Prüfen, ob die Aktie bereits in der Datenbank existiert
        Aktie aktieToPersist = aktieRepository.findBySymbol(symbol);
        if (aktieToPersist != null) {
            // Aktie existiert bereits, aktualisiere ihre Details
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
            aktieToPersist.setTransaktionen(new ArrayList<>());
            aktieToPersist.setKurse(new ArrayList<>());
            aktieRepository.save(aktieToPersist);
        } else {
            aktieToPersist = fetchedAktie;
            aktieToPersist.setTransaktionen(new ArrayList<>());
            aktieToPersist.setKurse(new ArrayList<>());
            aktieRepository.save(aktieToPersist);
        }

        // 2. Verknüpfung Kauf -> Aktie
        kauf.setWertpapier(aktieToPersist);

        // 3. Kauf-Transaktion speichern
        transaktionRepository.save(kauf);

        // 4. Aktie dem Depot hinzufügen
        depot.wertpapierHinzufuegen(aktieToPersist, stueckzahl, kurs);

        // 6. Depot speichern
        depotRepository.save(depot);

        return aktieToPersist;
    }

    /**
     * Gibt den aktuellen Kurs für das angegebene Symbol zurück.
     *
     * @param symbol Das Tickersymbol der Aktie
     * @return Der aktuelle Kurs oder 0.0, falls nicht verfügbar
     */
    public double getKursFürSymbol(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            return 0.0;
        }

        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            return 0.0; // Kein Kurs verfügbar
        }

        return quote.getPrice();
    }
}


