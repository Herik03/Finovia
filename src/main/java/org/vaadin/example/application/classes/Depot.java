package org.vaadin.example.application.classes;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Nutzer-Klasse, die die Eigenschaften und Methoden eines Nutzers repräsentiert.
 * Diese Klasse implementiert das Beobachter-Interface, um Benachrichtigungen
 * über Änderungen an Supportanfragen zu erhalten.
 */
//        von Ben
public class Depot {
    @Setter
    @Getter
    private String depotId;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private Nutzer besitzer;

    private List<Wertpapier> wertpapiere;

    private final Map<Wertpapier, Double> papierBestand;

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
        this.wertpapiere = new ArrayList<>();
        this.papierBestand  = new HashMap<>();
    }

    /**
     * Fügt eine Wertpapierposition zum Depot hinzu
     *
     * @param wertpapier Die hinzuzufügende Wertpapierposition
     */
    public void wertpapierHinzufuegen(Wertpapier wertpapier) {
        if(!wertpapiere.contains(wertpapier)) {
            wertpapiere.add(wertpapier);
        }
    }

    /**
     * Entfernt eine Wertpapierposition aus dem Depot
     *
     * @param wertpapier Die zu entfernende Wertpapierposition
     * @return true wenn die Position entfernt wurde, false wenn sie nicht gefunden wurde
     */
    public boolean wertpapierEntfernen(Wertpapier wertpapier) {
        papierBestand.remove(wertpapier);
        return wertpapiere.remove(wertpapier);
    }

    public List<Wertpapier> getWertpapiere() {
        return new ArrayList<>(wertpapiere); // Kopie zurückgeben für Kapselung
    }

    // Batuhan Guevercin
    public void fuegeWertpapierHinzu(Wertpapier papier, double menge) {
        if (papierBestand.containsKey(papier)) {
            double aktuell = papierBestand.get(papier);
            papierBestand.put(papier, aktuell + menge);
        } else {
            papierBestand.put(papier, menge);
            wertpapierHinzufuegen(papier);
        }
    }

    public double getBestandVon(Wertpapier papier) {
        return papierBestand.getOrDefault(papier, 0.0);
    }

    public Map<Wertpapier, Double> getPapierBestand() {
        return new HashMap<>(papierBestand);
    }
}