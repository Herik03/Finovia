package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;
/**
 * Die Klasse {@code Zinszahlung} repräsentiert eine konkrete Form der {@link Ausschuettung},
 * die typischerweise mit festverzinslichen Wertpapieren wie Anleihen verknüpft ist.
 *
 * Eine Zinszahlung enthält Informationen über den angewendeten Zinssatz und
 * erbt gemeinsame Ausschüttungsattribute wie Betrag, Datum, Steuern und zugehörige Transaktion.
 *
 * Diese Klasse wird ausschließlich in Verbindung mit {@link Anleihe} verwendet.
 *
 * @author Jan
 */
public class Zinszahlung extends Ausschuettung{
    private double zinssatz;
/**
 * Konstruktor zur Initialisierung aller Attribute der Zinszahlung.
 */
    public Zinszahlung(double zinssatz, int ausschüttungId, double betrag, LocalDate datum, double steuern, Transaktion transaktion) {
        super(ausschüttungId, betrag, datum, steuern, transaktion);
        this.zinssatz = zinssatz;
    }

    public double getZinssatz() {
        return zinssatz;
    }

    public void setZinssatz(double zinssatz) {
        this.zinssatz = zinssatz;
    }
}
