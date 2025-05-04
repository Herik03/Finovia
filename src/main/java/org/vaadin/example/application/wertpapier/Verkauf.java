package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;

public class Verkauf extends Transaktion{
    private double steuern;

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
