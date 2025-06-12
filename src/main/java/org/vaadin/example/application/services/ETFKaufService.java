package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.DepotWertpapierRepository;
import org.vaadin.example.application.repositories.KursRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

/**
 * Service-Klasse für den Kauf von ETFs.
 *
 * Diese Klasse kapselt die Logik für den Kauf von ETFs, einschließlich Kursabfrage,
 * Erstellung und Speicherung der Kauf-Transaktion sowie Aktualisierung des Depots.
 *
 * @author Sören Heß
 */
@Service
public class ETFKaufService {
    private final DepotWertpapierRepository depotWertpapierRepository;
    private final TransaktionRepository transaktionRepository;
    private final KursRepository kursRepository;
    private final WertpapierRepository wertpapierRepository;

    /**
     * Konstruktor für ETFKaufService.
     *
     * @param depotWertpapierRepository Repository für DepotWertpapiere
     * @param transaktionRepository     Repository für Transaktionen
     * @param kursRepository            Repository für Kursdaten
     * @param wertpapierRepository      Repository für Wertpapiere
     */
    @Autowired
    public ETFKaufService(DepotWertpapierRepository depotWertpapierRepository,
                          TransaktionRepository transaktionRepository, KursRepository kursRepository,
                          WertpapierRepository wertpapierRepository) {
        this.depotWertpapierRepository = depotWertpapierRepository;
        this.transaktionRepository = transaktionRepository;
        this.kursRepository = kursRepository;
        this.wertpapierRepository = wertpapierRepository;
    }

    /**
     * Gibt den aktuellen Kurs für das angegebene Symbol zurück.
     *
     * @param symbol Das ETF-Symbol
     * @return Der aktuelle Schlusskurs
     * @throws RuntimeException wenn kein Kurs gefunden wird
     */
    public double getKursFuerSymbol(String symbol) {
        return kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Kein Kurs für Symbol " + symbol + " gefunden."))
                .getSchlusskurs();
    }

    /**
     * Kauft einen ETF und aktualisiert das Depot entsprechend.
     *
     * Prüft, ob der ETF und Kursdaten vorhanden sind, aktualisiert oder erstellt das DepotWertpapier,
     * speichert die Kauf-Transaktion und gibt den gekauften ETF zurück.
     *
     * @param symbol       Das ETF-Symbol
     * @param stueckzahl   Anzahl der zu kaufenden Anteile
     * @param handelsplatz Handelsplatz des Kaufs
     * @param depot        Das Depot, in das gekauft wird
     * @return Der gekaufte ETF
     * @throws RuntimeException wenn ETF oder Kurs nicht gefunden werden
     */
    @Transactional
    public ETF kaufeETF(String symbol, int stueckzahl, String handelsplatz, Depot depot) {
        ETF etf = (ETF) wertpapierRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("ETF mit Symbol " + symbol + " nicht gefunden."));

        var kurse = kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol);
        if (kurse.isEmpty()) {
            throw new RuntimeException("Kein Kurs für Symbol " + symbol + " gefunden.");
        }
        double letzterKurs = kurse.getFirst().getSchlusskurs();

        DepotWertpapier dwp = depotWertpapierRepository
                .findByDepotAndWertpapier(depot, etf)
                .orElse(new DepotWertpapier(depot, etf, 0, 0.0));

        dwp.setAnzahl(dwp.getAnzahl() + stueckzahl);
        dwp.setEinstandspreis(letzterKurs);
        depotWertpapierRepository.save(dwp);

        Kauf kauf = new Kauf(handelsplatz, LocalDate.now(), 2.5, letzterKurs, stueckzahl, etf, null);
        transaktionRepository.save(kauf);

        return etf;
    }
}