package org.vaadin.example.application.classes;

import java.sql.Date;

public class Ausschuettung {
    private double ausschuettung;
    private double betrag;
    private double steueren;
    private Date datum;
    public Ausschuettung(double ausschuettung, double betrag, double steueren, Date datum)
    {
        this.ausschuettung=ausschuettung;
        this.betrag=betrag;
        this.steueren=steueren;
        this.datum=datum;
    }

    public double getAusschuettung() {
        return ausschuettung;
    }

    public void setAusschuettung(double ausschuettung) {
        this.ausschuettung = ausschuettung;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public double getSteueren() {
        return steueren;
    }

    public void setSteueren(double steueren) {
        this.steueren = steueren;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
