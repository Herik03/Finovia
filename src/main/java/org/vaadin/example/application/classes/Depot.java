package org.vaadin.example.application.classes;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    }

    /**
     * Fügt eine Wertpapierposition zum Depot hinzu
     *
     * @param wertpapier Die hinzuzufügende Wertpapierposition
     */
    public void wertpapierHinzufuegen(Wertpapier wertpapier) {
        wertpapiere.add(wertpapier);
    }

    /**
     * Entfernt eine Wertpapierposition aus dem Depot
     *
     * @param wertpapier Die zu entfernende Wertpapierposition
     * @return true wenn die Position entfernt wurde, false wenn sie nicht gefunden wurde
     */
    public boolean wertpapierEntfernen(Wertpapier wertpapier) {
        return wertpapiere.remove(wertpapier);
    }

    public List<Wertpapier> getWertpapiere() {
        return new ArrayList<>(wertpapiere); // Kopie zurückgeben für Kapselung
    }
}
