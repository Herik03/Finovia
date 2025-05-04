package org.vaadin.example.application.wertpapier;

import java.util.List;

public class ETF extends Wertpapier{
    private String ausschüttung;
    private String emittent;
    private String fondsname;
    private String index;

    public ETF(String ausschüttung, String emittent, String fondsname, String index, String isin, String name, int wertpapierId, List<Transaktion> transaktionen, List<Kurs> kurse) {
        super(isin, name, wertpapierId, transaktionen, kurse);
        this.ausschüttung = ausschüttung;
        this.emittent = emittent;
        this.fondsname = fondsname;
        this.index = index;
    }

    public String getAusschüttung() {
        return ausschüttung;
    }

    public void setAusschüttung(String ausschüttung) {
        this.ausschüttung = ausschüttung;
    }

    public String getEmittent() {
        return emittent;
    }

    public void setEmittent(String emittent) {
        this.emittent = emittent;
    }
    public String getFondsname() {
        return fondsname;
    }

    public void setFondsname(String fondsname) {
        this.fondsname = fondsname;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
