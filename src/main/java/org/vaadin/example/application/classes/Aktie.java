package org.vaadin.example.application.classes;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
/**
 * Repräsentiert ein Wertpapier vom Typ Aktie.
 *
 * Eine Aktie ist ein Anteilsschein an einem Unternehmen, der typischerweise mit einer
 * bestimmten Anzahl an Anteilen (Stückzahl), dem Unternehmensnamen sowie einer Liste von
 * Dividendenzahlungen assoziiert ist.
 *
 * Diese Klasse erbt von {@link Wertpapier} und erweitert sie um aktienspezifische Eigenschaften.
 *
 * @author Jan
 */

@Getter
@Setter
public class Aktie extends Wertpapier {
    int anzahl;
    private String unternehmensname;
    private List<Dividende> dividenden;
/**
 * Konstruktor zum Erzeugen einer Aktie mit vollständigen Informationen.
 *
 */
 public Aktie(int anzahl, String unternehmensname, List<Dividende> dividenden, String isin, String name, int wertpapierId, List<Transaktion> transaktionen, List<Kurs> kurse) {
        super( isin, name, wertpapierId, transaktionen, kurse);
        this.unternehmensname = unternehmensname;
        this.dividenden = dividenden;
        this.anzahl = anzahl;
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
