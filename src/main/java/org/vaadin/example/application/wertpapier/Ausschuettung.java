package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;

public abstract class Ausschuettung{
    private int ausschüttungId;
    private double betrag;
    private LocalDate datum;
    private double steuern;
    private Transaktion transaktion;

    public Ausschuettung(int ausschüttungId, double betrag, LocalDate datum, double steuern, Transaktion transaktion) {
        this.ausschüttungId = ausschüttungId;
        this.betrag = betrag;
        this.datum = datum;
        this.steuern = steuern;
        this.transaktion = transaktion;
    }

    public int getAusschüttungId() {
        return ausschüttungId;
    }

    public void setAusschüttungId(int ausschüttungId) {
        this.ausschüttungId = ausschüttungId;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public double getSteuern() {
        return steuern;
    }

    public void setSteuern(double steuern) {
        this.steuern = steuern;
    }

    public Transaktion getTransaktion() {
        return transaktion;
    }

    public void setTransaktion(Transaktion transaktion) {
        this.transaktion = transaktion;
    }
}
