package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.repositories.ETFRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service-Klasse für den Verkauf von ETF-Anteilen aus einem Depot.
 *
 * Diese Klasse kapselt die Logik für den Verkauf von ETFs, einschließlich Kursabfrage,
 * Validierung der Stückzahl, Erstellung und Speicherung der Verkaufs-Transaktion sowie
 * Aktualisierung des Depots.
 *
 * @author Batuhan Güvercin
 */
@Service
public class ETFVerkaufService {

    private final DepotService depotService;
    private final TransaktionRepository transaktionRepository;
    private final ETFRepository etfRepository;

    /**
     * Konstruktor für ETFVerkaufService.
     *
     * @param depotService             Service für Depots
     * @param transaktionRepository    Repository für Transaktionen
     * @param etfRepository            Repository für ETFs
     */
    public ETFVerkaufService(DepotService depotService,
                             TransaktionRepository transaktionRepository,
                             ETFRepository etfRepository) {
        this.depotService = depotService;
        this.transaktionRepository = transaktionRepository;
        this.etfRepository = etfRepository;
    }

    /**
     * Verkauft ETF-Anteile aus einem Depot.
     *
     * Prüft, ob das Symbol, die Stückzahl und das Depot gültig sind, holt den aktuellen Kurs,
     * prüft die vorhandene Stückzahl im Depot, erstellt und speichert die Verkaufs-Transaktion,
     * entfernt die ETF-Anteile aus dem Depot und speichert das aktualisierte Depot.
     *
     * @param symbol     Das ETF-Symbol
     * @param stueckzahl Anzahl der zu verkaufenden ETF-Anteile
     * @param depot      Das Depot, aus dem verkauft wird
     * @param nutzer     Aktuell angemeldeter Nutzer
     * @return Das ETF, wenn Verkauf erfolgreich, sonst null
     */
    @Transactional
    public ETF verkaufeETF(String symbol, int stueckzahl, Depot depot, Nutzer nutzer) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
            return null;
        }

        Optional<ETF> optionalETF = etfRepository.findBySymbolIgnoreCase(symbol);
        if (optionalETF.isEmpty()) {
            return null; // ETF nicht gefunden
        }

        ETF etf = optionalETF.get();

        int vorhandeneStueckzahl = 0;
        ETF depotETF = null;

        for (DepotWertpapier dw : depot.getDepotWertpapiere()) {
            if (dw.getWertpapier().getSymbol().equalsIgnoreCase(symbol)
                    && dw.getWertpapier() instanceof ETF) {
                vorhandeneStueckzahl = dw.getAnzahl();
                depotETF = (ETF) dw.getWertpapier();
                break;
            }
        }

        if (depotETF == null || vorhandeneStueckzahl < stueckzahl) {
            return null; // Kein passender ETF oder nicht genug Anteile
        }

        double kurs;
        try {
            kurs = depotETF.getAktuellerKurs();
        } catch (IllegalStateException e) {
            return null; // Kein Kurs verfügbar
        }

        double gebuehren = 2.50;
        double steuern = 0.0;

        Verkauf verkauf = new Verkauf(
                steuern,
                LocalDate.now(),
                gebuehren,
                kurs,
                stueckzahl,
                depotETF,
                null
        );
        verkauf.setNutzer(nutzer);

        depotETF.getTransaktionen().add(verkauf);
        transaktionRepository.save(verkauf);

        depotService.wertpapierAusDepotEntfernen(depot, depotETF, stueckzahl);

        return depotETF;
    }

    /**
     * Gibt den aktuellen Kurs für das angegebene Symbol zurück.
     *
     * @param symbol Das ETF-Symbol
     * @return Der aktuelle Kurswert
     * @throws IllegalArgumentException wenn das Symbol leer ist
     * @throws RuntimeException wenn kein Kurs gefunden wird
     */
    public double getKursFürSymbol(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol darf nicht leer sein.");
        }

        Optional<ETF> optionalETF = etfRepository.findBySymbolIgnoreCase(symbol);
        if (optionalETF.isEmpty()) {
            throw new RuntimeException("Kein Kurs für Symbol gefunden: " + symbol);
        }

        return optionalETF.get().getAktuellerKurs();
    }
}
