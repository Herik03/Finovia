package org.vaadin.example.application.wertpapier;

import java.util.List;

public class Aktie extends Wertpapier {
    private int anzahl;
    private String unternehmensname;
    private List<Dividende> dividenden;

    public Aktie(int anzahl, String unternehmensname, List<Dividende> dividenden, String isin, String name, int wertpapierId, List<Transaktion> transaktionen, List<Kurs> kurse) {
        super( isin, name, wertpapierId, transaktionen, kurse);
        this.anzahl = anzahl;
        this.unternehmensname = unternehmensname;
        this.dividenden = dividenden;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public String getUnternehmensname() {
        return unternehmensname;
    }

    public void setUnternehmensname(String unternehmensname) {
        this.unternehmensname = unternehmensname;
    }

    public List<Dividende> getDividenden() {
        return dividenden;
    }

    public void setDividenden(List<Dividende> dividenden) {
        this.dividenden = dividenden;

    }
}
