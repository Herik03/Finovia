package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Repräsentiert einen historischen Kurs eines {@link Wertpapier} an einem bestimmten Datum.
 *
 * Die Klasse speichert neben dem Kurswert auch Eröffnungs- und Schlusskurse,
 * um Kursverläufe analysieren zu können.
 * Jeder Kurs ist eindeutig einem Wertpapier zugeordnet.
 *
 * @author Jan, Sören
 */

@Getter
@Setter
@Entity
@Table(name = "Kurs")
@NoArgsConstructor
public class Kurs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kurs_seq")
    @SequenceGenerator(name = "kurs_seq", sequenceName = "kurs_seq", allocationSize = 1)
    private Long kursId;

    private LocalDateTime datum;
    private double eröffnungskurs;
    private double schlusskurs;
    private double high;
    private double low;

    @ManyToOne
    @JoinColumn(name = "wertpapier_id")
    private Wertpapier wertpapier;
    private String symbol;

    /**
     * Konstruktor zum Erzeugen eines Kurs-Objekts mit allen Werten.
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
     * @param datum           Das Datum des Kurses
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

    //public String getKurswert() {
    //    return "";
    //}

    public double getKurswert() {
        return schlusskurs;
    }

}
