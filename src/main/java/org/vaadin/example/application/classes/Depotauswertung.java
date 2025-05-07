package org.vaadin.example.application.classes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Klasse zur Darstellung einer Depotauswertung
 * Diese Klasse enthält Informationen über den Gesamtwert, Gewinn/Verlust,
 * Performance und den Zeitpunkt der Auswertung.
 */
//        von Ben
@Setter
@Getter
public class Depotauswertung {
    // Getter und Setter
    private double gesamtWert;
    private double gewinnVerlust;
    private double performanceProzent;
    private LocalDateTime auswertungszeitpunkt;

    /**
     * Konstruktor für eine neue Depotauswertung
     *
     * @param gesamtWert Der Gesamtwert des Depots in Euro
     * @param gewinnVerlust Der absolute Gewinn oder Verlust in Euro
     * @param performanceProzent Die prozentuale Performance des Depots
     */
    public Depotauswertung(double gesamtWert, double gewinnVerlust, double performanceProzent) {
        this.gesamtWert = gesamtWert;
        this.gewinnVerlust = gewinnVerlust;
        this.performanceProzent = performanceProzent;
        this.auswertungszeitpunkt = LocalDateTime.now();
    }

    /**
     * Erzeugt eine formatierte Zusammenfassung der Depotauswertung
     *
     * @return Einen formatierten String mit den Auswertungsdaten
     */
    public String getZusammenfassung() {
        String ergebnis = gewinnVerlust >= 0 ? "Gewinn" : "Verlust";

        return String.format(
                "Depotauswertung vom %s%n" +
                        "Gesamtwert: %.2f €%n" +
                        "%s: %.2f € (%.2f %%)",
                auswertungszeitpunkt.toString(),
                gesamtWert,
                ergebnis,
                Math.abs(gewinnVerlust),
                performanceProzent
        );
    }

}
