package org.vaadin.example.application.classes;

import java.sql.Date;

public class Verkauf extends Transaktion {
    private double steueren;
    public Verkauf(Date datum, double gebuehren, double kurs, int stueckzahl,double steueren)
    {
        super(null,0,0,0);
        this.steueren=steueren;
    }

    public double getSteueren() {
        return steueren;
    }

    public void setSteueren(double steueren) {
        this.steueren = steueren;
    }
}
