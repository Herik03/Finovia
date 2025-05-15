package org.vaadin.example.application.classes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Repräsentiert einen historischen Kurs eines {@link Wertpapier} an einem bestimmten Datum.
 *
 * Die Klasse speichert neben dem Kurswert auch Eröffnungs- und Schlusskurse,
 * um Kursverläufe analysieren zu können.
 * Jeder Kurs ist eindeutig einem Wertpapier zugeordnet.
 *
 * @author Jan, Sören
 */

@Getter
@Setter
public class Kurs {
    private LocalDate datum;
    private double eröffnungskurs;
    private double schlusskurs;
    private double high;
    private double low;
    private Wertpapier wertpapier;
    private String symbol;

    /**
     * Konstruktor zum Erzeugen eines Kurs-Objekts mit allen Werten.
    */
    public Kurs(LocalDate datum, double eröffnungskurs, double schlusskurs, double high, double low, Wertpapier wertpapier) {
        this.datum = datum;
        this.eröffnungskurs = eröffnungskurs;
        this.high = high;
        this.low = low;
        this.schlusskurs = schlusskurs;
        this.wertpapier = wertpapier;
    }

    /**
     * Konstruktor zum Erzeugen eines Kurs-Objekts ohne Wertpapier-Referenz.
     *
     * @param symbol         Das Börsensymbol des Wertpapiers
     * @param datum           Das Datum des Kurses
     * @param eröffnungskurs Der Eröffnungskurs des Wertpapiers an diesem Tag
     * @param schlusskurs    Der Schlusskurs des Wertpapiers an diesem Tag
     * @param high           Der höchste Kurs des Wertpapiers an diesem Tag
     * @param low            Der niedrigste Kurs des Wertpapiers an diesem Tag
     */
    public Kurs(String symbol, LocalDate datum, double eröffnungskurs, double schlusskurs, double high, double low) {
        this.symbol = symbol;
        this.datum = datum;
        this.eröffnungskurs = eröffnungskurs;
        this.schlusskurs = schlusskurs;
        this.high = high;
        this.low = low;
    }

    //public String getKurswert() {
    //    return "";
    //}

    public double getKurswert() {
        return schlusskurs;
    }
}
