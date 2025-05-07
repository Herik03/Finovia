package org.vaadin.example.application.classes;

import java.time.LocalDate;
/**
 * Repräsentiert eine Verkaufs-Transaktion eines {@link Wertpapier}.
 * Erbt von {@link Transaktion} und ergänzt diese um die beim Verkauf
 * angefallenen Steuern.
 *
 * Diese Klasse ist ein konkreter Typ einer Transaktion und wird
 * typischerweise im Rahmen von Veräußerungsprozessen verwendet.
 *
 * @author Jan
 */
public class Verkauf extends Transaktion{
    private double steuern;

/**
 * Konstruktor zur Initialisierung aller Felder der Verkaufs-Transaktion.
 */
    public Verkauf(double steuern, LocalDate datum, double gebühren, double kurs, int stückzahl, Wertpapier wertpapier, Ausschuettung ausschüttung) {
        super(datum, gebühren, kurs, stückzahl, wertpapier, ausschüttung);
        this.steuern = steuern;
    }

    public double getSteuern() {
        return steuern;
    }

    public void setSteuern(double steuern) {
        this.steuern = steuern;
    }
}
