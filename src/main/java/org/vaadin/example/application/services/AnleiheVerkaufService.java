package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.Anleihe;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.DepotWertpapier;
import org.vaadin.example.application.classes.Verkauf;
import org.vaadin.example.application.classes.StockQuote;
import org.vaadin.example.application.repositories.AnleiheRepository;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service-Klasse für den Verkauf von Anleihen aus einem Depot.
 *
 * Diese Klasse kapselt die Logik für den Verkauf von Anleihen, einschließlich Kursabfrage,
 * Validierung der Stückzahl, Erstellung und Speicherung der Verkaufs-Transaktion sowie
 * Aktualisierung des Depots.
 *
 * @author Batuhan Güvercin
 */
@Service
public class AnleiheVerkaufService {

    private final AlphaVantageService alphaVantageService;
    private final DepotRepository depotRepository;
    private final TransaktionRepository transaktionRepository;
    private final AnleiheRepository anleiheRepository;

    /**
     * Konstruktor für AnleiheVerkaufService.
     *
     * @param alphaVantageService    Service zur Kursabfrage
     * @param depotRepository        Repository für Depots
     * @param transaktionRepository  Repository für Transaktionen
     * @param anleiheRepository      Repository für Anleihen
     */
    public AnleiheVerkaufService(AlphaVantageService alphaVantageService,
                                 DepotRepository depotRepository,
                                 TransaktionRepository transaktionRepository,
                                 AnleiheRepository anleiheRepository) {
        this.alphaVantageService = alphaVantageService;
        this.depotRepository = depotRepository;
        this.transaktionRepository = transaktionRepository;
        this.anleiheRepository = anleiheRepository;
    }

    /**
     * Verkauft Anleihen aus einem Depot.
     *
     * Prüft, ob das Symbol, die Stückzahl und das Depot gültig sind, holt den aktuellen Kurs,
     * prüft die vorhandene Stückzahl im Depot, erstellt und speichert die Verkaufs-Transaktion,
     * entfernt die Anleihen aus dem Depot und speichert das aktualisierte Depot.
     *
     * @param symbol     Das Anleihesymbol
     * @param stueckzahl Anzahl der zu verkaufenden Anleihen
     * @param depot      Das Depot, aus dem verkauft wird
     * @return Die Anleihe, wenn Verkauf erfolgreich, sonst null
     */
    @Transactional
    public Anleihe verkaufeAnleihe(String symbol, int stueckzahl, Depot depot) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
            return null;
        }

        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            return null;
        }

        Optional<Anleihe> optionalAnleihe = anleiheRepository.findAll()
                .stream()
                .filter(a -> symbol.equalsIgnoreCase(a.getSymbol()))
                .findFirst();

        if (optionalAnleihe.isEmpty()) {
            return null; // Anleihe nicht gefunden
        }

        Anleihe anleihe = optionalAnleihe.get();

        int vorhandeneStueckzahl = 0;
        for (DepotWertpapier dw : depot.getDepotWertpapiere()) {
            if (dw.getWertpapier().equals(anleihe)) {
                vorhandeneStueckzahl = dw.getAnzahl();
                break;
            }
        }

        if (vorhandeneStueckzahl < stueckzahl) {
            return null; // Nicht genug Anleihen zum Verkaufen
        }

        double kurs = quote.getPrice();
        double gebuehren = 2.50;
        double steuern = 0.0; // Steuerberechnung kann später ergänzt werden

        Verkauf verkauf = new Verkauf(
                steuern,
                LocalDate.now(),
                gebuehren,
                kurs,
                stueckzahl,
                anleihe,
                null
        );

        anleihe.getTransaktionen().add(verkauf);
        transaktionRepository.save(verkauf);

        depot.wertpapierEntfernen(anleihe, stueckzahl);
        depotRepository.save(depot);

        return anleihe;
    }

    /**
     * Gibt den aktuellen Kurs für das angegebene Symbol zurück.
     *
     * @param symbol Das Anleihesymbol
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