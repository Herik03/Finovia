package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;

public class Dividende extends Ausschuettung{
    private int aktienAnzahl;
    private double dividendenRendite;

    public Dividende(int aktienAnzahl, double dividendenRendite, int ausschüttungId, double betrag, LocalDate datum, double steuern, Transaktion transaktion) {
        super(ausschüttungId, betrag, datum, steuern, transaktion);
        this.aktienAnzahl = aktienAnzahl;
        this.dividendenRendite = dividendenRendite;
    }

    public int getAktienAnzahl() {
        return aktienAnzahl;
    }

    public void setAktienAnzahl(int aktienAnzahl) {
        this.aktienAnzahl = aktienAnzahl;
    }

    public double getDividendenRendite() {
        return dividendenRendite;
    }

    public void setDividendenRendite(double dividendenRendite) {
        this.dividendenRendite = dividendenRendite;
    }
}
