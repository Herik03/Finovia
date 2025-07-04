package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.AktieRepository;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service-Klasse für den Verkauf von Aktien aus einem Depot.
 *
 * Diese Klasse kapselt die Logik für den Verkauf von Aktien, einschließlich Kursabfrage,
 * Validierung der Stückzahl, Erstellung und Speicherung der Verkaufs-Transaktion sowie
 * Aktualisierung des Depots.
 *
 * @author Batuhan Güvercin
 */
@Service
public class AktienVerkaufService {

    private final AlphaVantageService alphaVantageService;
    private final DepotRepository depotRepository;
    private final TransaktionRepository transaktionRepository;
    private final AktieRepository aktieRepository;
    private final DepotService depotService;

    /**
     * Konstruktor für AktienVerkaufService.
     *
     * @param alphaVantageService      Service zur Kursabfrage
     * @param depotRepository         Repository für Depots
     * @param transaktionRepository   Repository für Transaktionen
     * @param aktieRepository         Repository für Aktien
     */
    public AktienVerkaufService(AlphaVantageService alphaVantageService,
                                DepotRepository depotRepository,
                                TransaktionRepository transaktionRepository,
                                AktieRepository aktieRepository, DepotService depotService) {
        this.alphaVantageService = alphaVantageService;
        this.depotRepository = depotRepository;
        this.transaktionRepository = transaktionRepository;
        this.aktieRepository = aktieRepository;
        this.depotService = depotService;
    }

    /**
     * Verkauft Aktien aus einem Depot.
     *
     * Prüft, ob das Symbol, die Stückzahl und das Depot gültig sind, holt den aktuellen Kurs,
     * prüft die vorhandene Stückzahl im Depot, erstellt und speichert die Verkaufs-Transaktion,
     * entfernt die Aktien aus dem Depot und speichert das aktualisierte Depot.
     *
     * @param symbol     Das Aktiensymbol
     * @param stueckzahl Anzahl der zu verkaufenden Aktien
     * @param depot      Das Depot, aus dem verkauft wird
     * @return Die Aktie, wenn Verkauf erfolgreich, sonst null
     */
    @Transactional
    public Aktie verkaufeAktie(String symbol, int stueckzahl, Depot depot, Nutzer nutzer) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
            return null;
        }

        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            return null;
        }

        // Aktie aus DB holen über Symbol (hier Suche manuell, da Methode fehlt)
        Optional<Aktie> optionalAktie = aktieRepository.findAll()
                .stream()
                .filter(a -> symbol.equalsIgnoreCase(a.getSymbol()))
                .findFirst();

        if (optionalAktie.isEmpty()) {
            return null; // Aktie nicht gefunden
        }

        Aktie aktie = optionalAktie.get();

        // Anzahl der Aktien im Depot prüfen
        int vorhandeneStueckzahl = 0;
        for (DepotWertpapier dw : depot.getDepotWertpapiere()) {
            if (dw.getWertpapier().getSymbol().equalsIgnoreCase(symbol)) {
                vorhandeneStueckzahl = dw.getAnzahl();
                if (dw.getWertpapier() instanceof Aktie) {
                    aktie = (Aktie) dw.getWertpapier(); // Cast auf Aktie
                } else {
                    return null; // oder Fehler werfen: kein Verkauf möglich
                }
                break;
            }
        }

        if (vorhandeneStueckzahl < stueckzahl) {
            return null; // nicht genug Aktien zum Verkaufen
        }

        double kurs = quote.getPrice();
        double gebuehren = 2.50;
        double steuern = 0.0; // z. B. berechnete Steuern, hier als Beispiel 0

        // Verkauf erstellen (korrekter Konstruktoraufruf)
        Verkauf verkauf = new Verkauf(
                steuern,
                LocalDate.now(),
                gebuehren,
                kurs,
                stueckzahl,
                aktie,
                null
        );
        verkauf.setNutzer(nutzer);

        aktie.getTransaktionen().add(verkauf);
        transaktionRepository.save(verkauf);

        depotService.wertpapierAusDepotEntfernen(depot, aktie, stueckzahl);

        return aktie;
    }

    /**
     * Gibt den aktuellen Kurs für das angegebene Symbol zurück.
     *
     * @param symbol Das Aktiensymbol
     * @return Der aktuelle Kurswert
     * @throws IllegalArgumentException wenn das Symbol leer ist
     * @throws RuntimeException wenn kein Kurs gefunden wird
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
}