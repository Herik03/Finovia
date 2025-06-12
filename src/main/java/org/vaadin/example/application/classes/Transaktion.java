package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
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
 * @author Jan
 */

@Getter
@Setter
@Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
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

    @ManyToOne
    @JoinColumn(name = "nutzer_id")
    private Nutzer nutzer;


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
