package org.vaadin.example.application.classes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Eine Watchlist ermöglicht Nutzern, Wertpapiere zu beobachten, ohne sie zu kaufen.
 * Sie enthält eine Liste von Wertpapieren, die der Nutzer verfolgen möchte.
 */
//        von Ben
@Getter
public class Watchlist {

    // Getter und Setter
    @Setter
    private String name;
    private LocalDate erstellungsdatum;
    @Setter
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

}
