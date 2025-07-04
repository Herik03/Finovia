package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.AnleiheRepository;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.repositories.KursRepository;

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

    private final DepotRepository depotRepository;
    private final DepotService depotService;
    private final TransaktionRepository transaktionRepository;
    private final AnleiheRepository anleiheRepository;
    private final KursRepository kursRepository;

    /**
     * Konstruktor für AnleiheVerkaufService.
     *
     * @param depotService           Service zur Depotverwaltung
     * @param depotRepository        Repository für Depots
     * @param transaktionRepository  Repository für Transaktionen
     * @param anleiheRepository      Repository für Anleihen
     */
    public AnleiheVerkaufService(DepotRepository depotRepository,
                                 TransaktionRepository transaktionRepository,
                                 AnleiheRepository anleiheRepository,
                                 KursRepository kursRepository, DepotService depotService) {
        this.depotRepository = depotRepository;
        this.transaktionRepository = transaktionRepository;
        this.anleiheRepository = anleiheRepository;
        this.kursRepository = kursRepository;
        this.depotService = depotService;
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
    public Anleihe verkaufeAnleihe(String symbol, int stueckzahl, Depot depot, Nutzer nutzer) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
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
            if (dw.getWertpapier().getSymbol().equalsIgnoreCase(symbol)) {
                vorhandeneStueckzahl = dw.getAnzahl();
                if (!(dw.getWertpapier() instanceof Anleihe)) {
                    return null; // kein Verkauf möglich, da kein Anleihe-Wertpapier
                }
                anleihe = (Anleihe) dw.getWertpapier();
                break;
            }
        }

        if (vorhandeneStueckzahl < stueckzahl) {
            return null; // Nicht genug Anleihen im Depot
        }

        Kurs letzterKurs = kursRepository.findTopByWertpapier_SymbolOrderByDatumDesc(symbol);
        if (letzterKurs == null) {
            return null; // Kein Kurs gefunden
        }

        double kurs = letzterKurs.getSchlusskurs();
        double gebuehren = 2.50;
        double steuern = 0.0;

        Verkauf verkauf = new Verkauf(
                steuern,
                LocalDate.now(),
                gebuehren,
                kurs,
                stueckzahl,
                anleihe,
                null
        );
        verkauf.setNutzer(nutzer);

        anleihe.getTransaktionen().add(verkauf);
        transaktionRepository.save(verkauf);

        depotService.wertpapierAusDepotEntfernen(depot, anleihe, stueckzahl);

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
        Kurs letzterKurs = kursRepository.findTopByWertpapier_SymbolOrderByDatumDesc(symbol);
        if (letzterKurs != null) {
            return letzterKurs.getSchlusskurs();
        } else {
            throw new IllegalArgumentException("Kein Kurs für Symbol " + symbol + " gefunden.");
        }
    }
}
