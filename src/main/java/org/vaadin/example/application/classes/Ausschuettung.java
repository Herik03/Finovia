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
}
