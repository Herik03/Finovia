package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;

public class Zinszahlung extends Ausschuettung{
    private double zinssatz;

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
