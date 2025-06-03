package org.vaadin.example.application.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vaadin.example.application.enums.Zinsfrequenz;


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
    @Enumerated(EnumType.STRING)
    private Zinsfrequenz  frequenz;
    private int anleihenAnzahl;

    /**
     * Konstruktor zur Initialisierung aller Attribute der Zinszahlung.
     */
    public Zinszahlung(int anleihenAnzahl, double zinssatz, double betrag, LocalDate datum, double steuern, Wertpapier wertpapier, Zinsfrequenz frequenz) {
        super(betrag, datum, steuern, wertpapier);
        this.anleihenAnzahl = anleihenAnzahl;
        this.zinssatz = zinssatz;
        this.frequenz = frequenz;
    }

}
