package org.vaadin.example.application.classes;

import java.util.List;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

/**
 * Repräsentiert ein Exchange Traded Fund (ETF), das eine Sonderform des Wertpapiers darstellt.
 *
 * Ein ETF ist ein börsengehandelter Fonds, der in der Regel einen Index abbildet
 * und sowohl thesaurierend als auch ausschüttend sein kann.
 *
 * Diese Klasse erweitert {@link Wertpapier} und ergänzt spezifische Eigenschaften
 * wie Emittent, Fondsname, Index und Ausschüttungsform.
 *
 * @author Jan
 */
@Entity
@Table(name = "etf")
@NoArgsConstructor
public class ETF extends Wertpapier {
    private String ausschüttung;
    private String emittent;
    private String fondsname;
    private String index;
/**
 * Konstruktor zur Initialisierung eines ETF-Objekts mit allen Attributen.
 */
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
