package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
 * @author Jan Schwarzer
 */

@Getter
@Setter
@Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
@NoArgsConstructor
public abstract class Transaktion {

    /**
     * Eindeutige ID der Transaktion (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Datum der Transaktion.
     */
    private LocalDate datum;

    /**
     * Angefallene Gebühren bei der Transaktion.
     */
    private double gebühren;

    /**
     * Kurs des Wertpapiers zum Zeitpunkt der Transaktion.
     */
    private double kurs;

    /**
     * Anzahl der gehandelten Wertpapiere.
     */
    private int stückzahl;

    /**
     * Zugehöriges Wertpapier zur Transaktion.
     */
    @ManyToOne
    @JoinColumn(name = "wertpapier_id")
    private Wertpapier wertpapier;

    /**
     * Zugehörige Ausschüttung (optional).
     */
    @OneToOne
    @JoinColumn(name = "ausschuettung_id")
    private Ausschuettung ausschüttung;

    /**
     * Konstruktor zur Initialisierung aller Felder einer Transaktion.
     *
     * @param datum        Datum der Transaktion
     * @param gebühren     Gebühren der Transaktion
     * @param kurs         Kurs des Wertpapiers
     * @param stückzahl    Anzahl der Wertpapiere
     * @param wertpapier   Zugehöriges Wertpapier
     * @param ausschüttung Zugehörige Ausschüttung (optional)
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
