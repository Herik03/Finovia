package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;

public abstract class Transaktion {
    private LocalDate datum;
    private double gebühren;
    private double kurs;
    private int stückzahl;
    private Wertpapier wertpapier;
    private Ausschuettung ausschüttung;

    public Transaktion(LocalDate datum, double gebühren, double kurs, int stückzahl, Wertpapier wertpapier, Ausschuettung ausschüttung) {
        this.datum = datum;
        this.gebühren = gebühren;
        this.kurs = kurs;
        this.stückzahl = stückzahl;
        this.wertpapier = wertpapier;
        this.ausschüttung = ausschüttung;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public double getGebühren() {
        return gebühren;
    }

    public void setGebühren(double gebühren) {
        this.gebühren = gebühren;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public int getStückzahl() {
        return stückzahl;
    }

    public void setStückzahl(int stückzahl) {
        this.stückzahl = stückzahl;
    }

    public Wertpapier getWertpapier() {
        return wertpapier;
    }

    public void setWertpapier(Wertpapier wertpapier) {
        this.wertpapier = wertpapier;
    }

    public Ausschuettung getAusschüttung() {
        return ausschüttung;
    }

    public void setAusschüttung(Ausschuettung ausschüttung) {
        this.ausschüttung = ausschüttung;
    }
}
