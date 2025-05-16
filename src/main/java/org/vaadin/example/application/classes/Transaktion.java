package org.vaadin.example.application.classes;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
@Entity
public abstract class Transaktion {
    private LocalDate datum;
    private double gebühren;
    private double kurs;
    private int stückzahl;
    private Wertpapier wertpapier;
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

}
