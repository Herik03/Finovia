package org.vaadin.example.application.classes;

import java.util.List;

/**
 * Die abstrakte Basisklasse {@code Wertpapier} repräsentiert ein generisches Finanzinstrument.
 * Sie enthält grundlegende Attribute und Beziehungen, die für spezifische Finanzprodukte wie
 * Aktien, Anleihen oder ETFs gemeinsam sind.
 *
 * Zu den Attributen gehören die eindeutige ISIN, ein Name, eine interne Wertpapier-ID,
 * eine Liste von zugehörigen Transaktionen sowie eine Historie von Kurswerten.
 *
 * Diese Klasse ist als abstrakt definiert und wird von konkreten Unterklassen wie {@link Aktie},
 * {@link Anleihe} oder {@link ETF} erweitert.
 *
 * @author Jan
 */
public abstract class Wertpapier {
    private String isin;
    private String name;
    private int wertpapierId;
    private List<Transaktion> transaktionen;
    private List<Kurs> kurse;
/**
 * Konstruktor zur Initialisierung aller Attribute eines Wertpapiers.
 */
    public Wertpapier(String isin, String name, int wertpapierId, List<Transaktion> transaktionen, List<Kurs> kurse) {
        this.isin = isin;
        this.name = name;
        this.wertpapierId = wertpapierId;
        this.transaktionen = transaktionen;
        this.kurse = kurse;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWertpapierId() {
        return wertpapierId;
    }

    public void setWertpapierId(int wertpapierId) {
        this.wertpapierId = wertpapierId;
    }

    public List<Transaktion> getTransaktionen() {
        return transaktionen;
    }

    public void setTransaktionen(List<Transaktion> transaktionen) {
        this.transaktionen = transaktionen;
    }

    public List<Kurs> getKurse() {
        return kurse;
    }

    public void setKurse(List<Kurs> kurse) {
        this.kurse = kurse;
    }
}
