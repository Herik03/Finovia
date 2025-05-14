package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Abstrakte Basisklasse für finanzielle Transaktionen eines {@link Wertpapier},
 * z.B. {@link Kauf} oder {@link Verkauf}.
 *
 * Enthält gemeinsame Attribute wie Datum, Kurs, Stückzahl, Gebühren sowie
 * Referenzen zum zugehörigen Wertpapier und ggf. einer {@link Ausschuettung}.
 * Diese Klasse dient als Grundlage für konkrete Transaktionstypen.
 *
 * @author Jan
 */
@Entity
@Table(name = "transaktion")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class Transaktion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datum;
    private double gebühren;
    private double kurs;
    private int stückzahl;

    @ManyToOne
    @JoinColumn(name = "wertpapier_id")
    private Wertpapier wertpapier;

    @OneToOne
    @JoinColumn(name = "ausschuettung_id")
    private Ausschuettung ausschüttung;

/**
 * Konstruktor zur Initialisierung aller Felder einer Transaktion.
 */
    public Transaktion(LocalDate datum, double gebühren, double kurs, int stückzahl, Wertpapier wertpapier, Ausschuettung ausschüttung) {
        this.datum = datum;
        this.gebühren = gebühren;
        this.kurs = kurs;
        this.stückzahl = stückzahl;
        this.wertpapier = wertpapier;
        this.ausschüttung = ausschüttung;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public double getGebühren() {
        return gebühren;
    }

    public void setGebühren(double gebühren) {
        this.gebühren = gebühren;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public int getStückzahl() {
        return stückzahl;
    }

    public void setStückzahl(int stückzahl) {
        this.stückzahl = stückzahl;
    }

    public Wertpapier getWertpapier() {
        return wertpapier;
    }

    public void setWertpapier(Wertpapier wertpapier) {
        this.wertpapier = wertpapier;
    }

    public Ausschuettung getAusschüttung() {
        return ausschüttung;
    }

    public void setAusschüttung(Ausschuettung ausschüttung) {
        this.ausschüttung = ausschüttung;
    }
}
