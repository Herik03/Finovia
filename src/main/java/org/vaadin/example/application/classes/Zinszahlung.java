package org.vaadin.example.application.classes;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
/**
 * Die Klasse {@code Zinszahlung} repräsentiert eine konkrete Form der {@link Ausschuettung},
 * die typischerweise mit festverzinslichen Wertpapieren wie Anleihen verknüpft ist.
 *
 * Eine Zinszahlung enthält Informationen über den angewendeten Zinssatz und
 * erbt gemeinsame Ausschüttungsattribute wie Betrag, Datum, Steuern und zugehörige Transaktion.
 *
 * Diese Klasse wird ausschließlich in Verbindung mit {@link Anleihe} verwendet.
 *
 * @author Jan
 */
@Entity
@NoArgsConstructor
@Getter @Setter
public class Zinszahlung extends Ausschuettung{
    private double zinssatz;
/**
 * Konstruktor zur Initialisierung aller Attribute der Zinszahlung.
 */
    public Zinszahlung(double zinssatz, double betrag, LocalDate datum, double steuern, Wertpapier wertpapier) {
        super(betrag, datum, steuern, wertpapier);
        this.zinssatz = zinssatz;
    }

}
