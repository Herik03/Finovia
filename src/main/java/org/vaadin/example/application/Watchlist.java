package org.vaadin.example.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Eine Watchlist ermöglicht Nutzern, Wertpapiere zu beobachten, ohne sie zu kaufen.
 * Sie enthält eine Liste von Wertpapieren, die der Nutzer verfolgen möchte.
 */
//        von Ben
public class Watchlist {

    private String name;
    private LocalDate erstellungsdatum;
    private Nutzer besitzer;
    private List<Wertpapier> wertpapiere;

    /**
     * Konstruktor für eine neue Watchlist
     *
     * @param name Der Name der Watchlist
     * @param besitzer Der Besitzer der Watchlist
     */
    public Watchlist(String name, Nutzer besitzer) {
        this.name = name;
        this.besitzer = besitzer;
        this.erstellungsdatum = LocalDate.now();
        this.wertpapiere = new ArrayList<>();
    }

    /**
     * Fügt ein Wertpapier zur Watchlist hinzu
     *
     * @param wertpapier Das hinzuzufügende Wertpapier
     */
    public void wertpapierHinzufuegen(Wertpapier wertpapier) {
        wertpapiere.add(wertpapier);
    }

    /**
     * Entfernt ein Wertpapier aus der Watchlist
     *
     * @param wertpapier Das zu entfernende Wertpapier
     * @return true wenn das Wertpapier entfernt wurde, false wenn es nicht gefunden wurde
     */
    public boolean wertpapierEntfernen(Wertpapier wertpapier) {
        return wertpapiere.remove(wertpapier);
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getErstellungsdatum() {
        return erstellungsdatum;
    }

    public Nutzer getBesitzer() {
        return besitzer;
    }

    public void setBesitzer(Nutzer besitzer) {
        this.besitzer = besitzer;
    }

    public List<Wertpapier> getWertpapiere() {
        return wertpapiere;
    }
}
