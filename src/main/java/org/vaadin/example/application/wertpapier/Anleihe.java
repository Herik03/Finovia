package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;
import java.util.List;

/**
 * Repräsentiert ein festverzinsliches Wertpapier vom Typ Anleihe.
 *
 * Eine Anleihe wird typischerweise von einem Emittenten herausgegeben,
 * hat eine festgelegte Laufzeit, einen Nominalwert (Nennwert) und zahlt
 * regelmäßig Zinsen in Form von {@link Zinszahlung}.
 *
 * Diese Klasse erweitert die allgemeine {@link Wertpapier}-Klasse um
 * anleihenspezifische Eigenschaften.
 *
 * @author Jan
 */
public class Anleihe extends Wertpapier {
    private String emittent;
    private double kupon;
    private LocalDate laufzeit;
    private double nennwert;
    private List<Zinszahlung> zinszahlungen;
/**
 * Konstruktor zum Erzeugen einer vollständigen Anleihe-Instanz.
 */
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
