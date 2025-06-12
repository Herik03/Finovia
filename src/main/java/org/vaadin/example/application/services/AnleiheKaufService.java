package org.vaadin.example.application.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.DepotWertpapierRepository;
import org.vaadin.example.application.repositories.KursRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service-Klasse für den Kauf von Anleihen.
 * <p>
 * Diese Klasse bietet Methoden zur Abwicklung des Kaufs von Anleihen inklusive
 * Kursermittlung, Aktualisierung des Depotbestands und Verbuchung der Transaktion.
 */
@Service
public class AnleiheKaufService {

    @Autowired
    private DepotWertpapierRepository depotWertpapierRepository;

    @Autowired
    private TransaktionRepository transaktionRepository;

    @Autowired
    private KursRepository kursRepository;

    @Autowired
    private WertpapierRepository wertpapierRepository;

    /**
     * Gibt den zuletzt bekannten Kurs für das übergebene Symbol zurück.
     *
     * @param symbol Das Symbol der Anleihe.
     * @return Der Schlusskurs des ältesten Eintrags für das Symbol.
     * @throws RuntimeException Wenn kein Kurs für das Symbol gefunden wurde.
     */
    public double getKursFürSymbol(String symbol) {
        List<Kurs> kurse = kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol);
        if (kurse.isEmpty()) {
            throw new RuntimeException("Kein Kurs für Symbol " + symbol + " gefunden.");
        }
        return kurse.get(0).getSchlusskurs();
    }

    /**
     * Führt den Kauf einer Anleihe durch und aktualisiert das Depot sowie die Transaktionen.
     *
     * @param symbol       Das Symbol der Anleihe.
     * @param stueckzahl   Die Anzahl der zu kaufenden Stücke.
     * @param handelsplatz Der Handelsplatz, an dem die Anleihe gekauft wird.
     * @param depot        Das Depot, in dem die Anleihe gebucht werden soll.
     * @return Die gekaufte {@link Anleihe}.
     * @throws RuntimeException Wenn die Anleihe oder der Kurs nicht gefunden wird.
     */
    @Transactional
    public Anleihe kaufeAnleihe(String symbol, int stueckzahl, String handelsplatz, Depot depot, Nutzer nutzer) {
        Anleihe anleihe = (Anleihe) wertpapierRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Anleihe mit Symbol " + symbol + " nicht gefunden."));

        List<Kurs> kurse = kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol);
        if (kurse.isEmpty()) {
            throw new RuntimeException("Kein Kurs für Symbol " + symbol + " gefunden.");
        }
        double letzterKurs = kurse.get(0).getSchlusskurs();

        DepotWertpapier dwp = depotWertpapierRepository
                .findByDepotAndWertpapier(depot, anleihe)
                .orElse(new DepotWertpapier(depot, anleihe, 0, Double.valueOf(0.0)));

        dwp.setAnzahl(dwp.getAnzahl() + stueckzahl);
        dwp.setEinstandspreis(letzterKurs);
        depotWertpapierRepository.save(dwp);

        Kauf kauf = new Kauf(handelsplatz, LocalDate.now(), 2.5, letzterKurs, stueckzahl, anleihe, null);
        kauf.setNutzer(nutzer);
        transaktionRepository.save(kauf);

        return anleihe;
    }
}
