package org.vaadin.example.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Nutzer-Klasse, die die Eigenschaften und Methoden eines Nutzers repräsentiert.
 * Diese Klasse implementiert das Beobachter-Interface, um Benachrichtigungen
 * über Änderungen an Supportanfragen zu erhalten.
 */
//        von Ben
public class Depot {
    private String depotId;
    private String name;
    private Nutzer besitzer;
    private List<Wertpapierposition> positionen;

    /**
     * Konstruktor für ein neues Depot
     *
     * @param depotId Die eindeutige ID des Depots
     * @param name Der Name des Depots
     * @param besitzer Der Besitzer des Depots
     */
    public Depot(String depotId, String name, Nutzer besitzer) {
        this.depotId = depotId;
        this.name = name;
        this.besitzer = besitzer;
        this.positionen = new ArrayList<>();
    }

    /**
     * Fügt eine Wertpapierposition zum Depot hinzu
     *
     * @param position Die hinzuzufügende Wertpapierposition
     */
    public void positionHinzufuegen(Wertpapierposition position) {
        positionen.add(position);
    }

    /**
     * Entfernt eine Wertpapierposition aus dem Depot
     *
     * @param position Die zu entfernende Wertpapierposition
     * @return true wenn die Position entfernt wurde, false wenn sie nicht gefunden wurde
     */
    public boolean positionEntfernen(Wertpapierposition position) {
        return positionen.remove(position);
    }

    /**
     * Erstellt eine Auswertung des aktuellen Depotstands
     *
     * @return Ein Depotauswertung-Objekt mit aktuellen Werten
     */
    public Depotauswertung erstelleAuswertung() {
        double gesamtWert = 0.0;
        double gesamtEinstandswert = 0.0;

        for (Wertpapierposition position : positionen) {
            gesamtWert += position.getAktuellerWert();
            gesamtEinstandswert += position.getEinstandswert();
        }

        double gewinnVerlust = gesamtWert - gesamtEinstandswert;
        double performanceProzent = 0.0;

        // Vermeiden von Division durch Null
        if (gesamtEinstandswert > 0) {
            performanceProzent = (gewinnVerlust / gesamtEinstandswert) * 100.0;
        }

        return new Depotauswertung(gesamtWert, gewinnVerlust, performanceProzent);
    }

    // Getter und Setter
    public String getDepotId() {
        return depotId;
    }

    public void setDepotId(String depotId) {
        this.depotId = depotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nutzer getBesitzer() {
        return besitzer;
    }

    public void setBesitzer(Nutzer besitzer) {
        this.besitzer = besitzer;
    }

    public List<Wertpapierposition> getPositionen() {
        return new ArrayList<>(positionen); // Kopie zurückgeben für Kapselung
    }
}
