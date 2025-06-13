package org.vaadin.example.application.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vaadin.example.application.classes.enums.Zinsfrequenz;

import java.time.LocalDate;

/**
 * Die Klasse {@code Zinszahlung} repräsentiert eine konkrete Form der {@link Ausschuettung},
 * die typischerweise mit festverzinslichen Wertpapieren wie {@link Anleihe} verknüpft ist.
 *
 * Sie enthält Informationen über:
 * <ul>
 *   <li>die Anzahl gehaltener Anleihen,</li>
 *   <li>den Zinssatz,</li>
 *   <li>die Zinsfrequenz (z. B. jährlich, halbjährlich).</li>
 * </ul>
 *
 * Gemeinsame Ausschüttungsinformationen wie Betrag, Datum, Steuer und Referenz auf das Wertpapier
 * werden über die Oberklasse {@link Ausschuettung} verwaltet.
 *
 * Diese Klasse wird ausschließlich in Verbindung mit {@link Anleihe} verwendet.
 *
 * @author Jan Schwarzer
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Zinszahlung extends Ausschuettung {

    /**
     * Der Zinssatz, der für diese Zinszahlung angewendet wurde (z. B. 1.5 für 1,5 %).
     */
    private double zinssatz;

    /**
     * Die Frequenz, in der Zinszahlungen erfolgen (z. B. jährlich, halbjährlich).
     */
    @Enumerated(EnumType.STRING)
    private Zinsfrequenz frequenz;

    /**
     * Die Anzahl der Anleihen, auf die sich diese Zinszahlung bezieht.
     */
    private int anleihenAnzahl;

    /**
     * Konstruktor zur Initialisierung aller Attribute der Zinszahlung.
     *
     * @param anleihenAnzahl Anzahl der gehaltenen Anleihen
     * @param zinssatz Der angewendete Zinssatz
     * @param betrag Der ausgezahlte Bruttobetrag
     * @param datum Datum der Zinszahlung
     * @param steuern Abgezogene Kapitalertragsteuer
     * @param wertpapier Referenz auf das zugehörige {@link Anleihe}-Wertpapier
     * @param frequenz Die Frequenz der Zinszahlung (z. B. JAEHRLICH)
     */
    public Zinszahlung(int anleihenAnzahl, double zinssatz, double betrag, LocalDate datum,
                       double steuern, Wertpapier wertpapier, Zinsfrequenz frequenz) {
        super(betrag, datum, steuern, wertpapier);
        this.anleihenAnzahl = anleihenAnzahl;
        this.zinssatz = zinssatz;
        this.frequenz = frequenz;
    }
}
