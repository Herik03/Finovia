package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;

public class Kurs {
    private LocalDate datum;
    private double eröffnungskurs;
    private double kurswert;
    private double schlusskurs;
    private Wertpapier wertpapier;

    public Kurs(LocalDate datum, double eröffnungskurs, double kurswert, double schlusskurs, Wertpapier wertpapier) {
        this.datum = datum;
        this.eröffnungskurs = eröffnungskurs;
        this.kurswert = kurswert;
        this.schlusskurs = schlusskurs;
        this.wertpapier = wertpapier;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public double getEröffnungskurs() {
        return eröffnungskurs;
    }

    public void setEröffnungskurs(double eröffnungskurs) {
        this.eröffnungskurs = eröffnungskurs;
    }

    public double getKurswert() {
        return kurswert;
    }

    public void setKurswert(double kurswert) {
        this.kurswert = kurswert;
    }

    public double getSchlusskurs() {
        return schlusskurs;
    }

    public void setSchlusskurs(double schlusskurs) {
        this.schlusskurs = schlusskurs;
    }

    public Wertpapier getWertpapier() {
        return wertpapier;
    }

    public void setWertpapier(Wertpapier wertpapier) {
        this.wertpapier = wertpapier;
    }
}
