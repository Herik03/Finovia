package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Abstrakte Oberklasse für verschiedene Formen von Ausschüttungen, wie {@link Dividende}, {@link Zinszahlung}
 * oder {@link ETFDividende}.
 *
 * Eine Ausschüttung stellt eine finanzielle Zahlung an den Inhaber eines {@link Wertpapier} dar – typischerweise
 * in Form von Dividenden, Zinsen oder ETF-basierten Erträgen.
 *
 * Diese Klasse kapselt gemeinsame Attribute aller Ausschüttungstypen, darunter Betrag, Datum,
 * Steuerabzug und die Referenz auf das zugrunde liegende Wertpapier.
 *
 * Sie wird per JPA mit Vererbung über die {@code JOINED}-Strategie gespeichert, sodass jede konkrete
 * Ausschüttungsklasse ihre eigenen Datenbankspalten erhält.
 *
 * @author Jan, Sören
 */
@Entity
@Table(name = "Ausschuettung")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public abstract class Ausschuettung {

    /**
     * Eindeutige ID der Ausschüttung (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ausschüttungId;

    /**
     * Referenz auf das Wertpapier, dem diese Ausschüttung zugeordnet ist.
     */
    @ManyToOne
    @JoinColumn(name = "wertpapier_id")
    private Wertpapier wertpapier;

    /**
     * Der Bruttobetrag der Ausschüttung (vor Steuern).
     */
    private double betrag;

    /**
     * Das Datum, an dem die Ausschüttung erfolgt ist.
     */
    private LocalDate datum;

    /**
     * Die Höhe der abgezogenen Kapitalertragsteuer.
     */
    private double steuern;

    /**
     * Konstruktor zur Initialisierung einer allgemeinen Ausschüttung.
     *
     * @param betrag Der Bruttobetrag der Ausschüttung
     * @param datum Das Datum der Auszahlung
     * @param steuern Die abgezogene Steuer
     * @param wertpapier Das zugehörige Wertpapier, auf das sich die Ausschüttung bezieht
     */
    public Ausschuettung(double betrag, LocalDate datum, double steuern, Wertpapier wertpapier) {
        this.wertpapier = wertpapier;
        this.betrag = betrag;
        this.datum = datum;
        this.steuern = steuern;
    }
}

