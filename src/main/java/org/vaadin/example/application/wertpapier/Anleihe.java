package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;
import java.util.List;

public class Anleihe extends Wertpapier {
    private String emittent;
    private double kupon;
    private LocalDate laufzeit;
    private double nennwert;
    private List<Zinszahlung> zinszahlungen;

    public Anleihe(String emittent, double kupon, LocalDate laufzeit, double nennwert, List<Zinszahlung> zinszahlungen, String isin, String name, int wertpapierId, List<Transaktion> transaktionen, List<Kurs> kurse) {
        super(isin, name, wertpapierId, transaktionen, kurse);
        this.emittent = emittent;
        this.kupon = kupon;
        this.laufzeit = laufzeit;
        this.nennwert = nennwert;
        this.zinszahlungen = zinszahlungen;
    }

    public String getEmittent() {
        return emittent;
    }

    public void setEmittent(String emittent) {
        this.emittent = emittent;
    }

    public double getKupon() {
        return kupon;
    }

    public void setKupon(double kupon) {
        this.kupon = kupon;
    }

    public LocalDate getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(LocalDate laufzeit) {
        this.laufzeit = laufzeit;
    }

    public double getNennwert() {
        return nennwert;
    }

    public void setNennwert(double nennwert) {
        this.nennwert = nennwert;
    }

    public List<Zinszahlung> getZinszahlungen() {
        return zinszahlungen;
    }

    public void setZinszahlungen(List<Zinszahlung> zinszahlungen) {
        this.zinszahlungen = zinszahlungen;
    }
}
