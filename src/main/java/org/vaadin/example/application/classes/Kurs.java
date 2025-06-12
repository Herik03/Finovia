package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/*
 * Repräsentiert einen historischen Kurs eines {@link Wertpapier} an einem bestimmten Datum.
 *
 * Die Klasse speichert neben dem Kurswert auch Eröffnungs- und Schlusskurse,
 * um Kursverläufe analysieren zu können.
 * Jeder Kurs ist eindeutig einem Wertpapier zugeordnet.
 *
 * @author Jan Schwarzer, Sören Heß
*/

@Getter
@Setter
@Entity
@Table(name = "Kurs")
@NoArgsConstructor
public class Kurs {
    /**
     * Eindeutige ID des Kurses (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long kursId;

    /**
     * Datum und Uhrzeit des Kurses.
     */
    private LocalDateTime datum;

    /**
     * Eröffnungskurs des Wertpapiers an diesem Tag.
     */
    private double eröffnungskurs;

    /**
     * Schlusskurs des Wertpapiers an diesem Tag.
     */
    private double schlusskurs;

    /**
     * Höchster Kurs des Wertpapiers an diesem Tag.
     */
    private double high;

    /**
     * Niedrigster Kurs des Wertpapiers an diesem Tag.
     */
    private double low;

    /**
     * Zugehöriges Wertpapier.
     */
    @ManyToOne
    @JoinColumn(name = "wertpapier_id")
    private Wertpapier wertpapier;

    /**
     * Börsensymbol des Wertpapiers.
     */
    private String symbol;

    /**
     * Konstruktor zum Erzeugen eines Kurs-Objekts mit allen Werten.
     *
     * @param datum           Datum und Uhrzeit des Kurses
     * @param eröffnungskurs  Eröffnungskurs des Wertpapiers
     * @param schlusskurs     Schlusskurs des Wertpapiers
     * @param high            Tageshoch des Wertpapiers
     * @param low             Tagestief des Wertpapiers
     * @param wertpapier      Zugehöriges Wertpapier
     */
    public Kurs(LocalDateTime datum, double eröffnungskurs, double schlusskurs, double high, double low, Wertpapier wertpapier) {
        this.datum = datum;
        this.eröffnungskurs = eröffnungskurs;
        this.high = high;
        this.low = low;
        this.schlusskurs = schlusskurs;
        this.wertpapier = wertpapier;
    }

    /**
     * Konstruktor zum Erzeugen eines Kurs-Objekts ohne Wertpapier-Referenz.
     *
     * @param symbol         Das Börsensymbol des Wertpapiers
     * @param datum          Das Datum des Kurses
     * @param eröffnungskurs Der Eröffnungskurs des Wertpapiers an diesem Tag
     * @param schlusskurs    Der Schlusskurs des Wertpapiers an diesem Tag
     * @param high           Der höchste Kurs des Wertpapiers an diesem Tag
     * @param low            Der niedrigste Kurs des Wertpapiers an diesem Tag
     */
    public Kurs(String symbol, LocalDateTime datum, double eröffnungskurs, double schlusskurs, double high, double low) {
        this.symbol = symbol;
        this.datum = datum;
        this.eröffnungskurs = eröffnungskurs;
        this.schlusskurs = schlusskurs;
        this.high = high;
        this.low = low;
    }

    /**
     * Gibt den Schlusskurs als Kurswert zurück.
     *
     * @return Schlusskurs des Wertpapiers
     */
    public double getKurswert() {
        return schlusskurs;
    }
}
