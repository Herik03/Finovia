package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert ein festverzinsliches Wertpapier vom Typ Anleihe.
 * Eine Anleihe wird typischerweise von einem Emittenten herausgegeben,
 * hat eine festgelegte Laufzeit, einen Nominalwert (Nennwert) und zahlt
 * regelmäßig Zinsen in Form von {@link Zinszahlung}.
 * Diese Klasse erweitert die allgemeine {@link Wertpapier}-Klasse um
 * Anleihen spezifische Eigenschaften.
 *
 * @author Jan Schwarzer, Sören Heß
 */

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Anleihe extends Wertpapier {
    /**
     * Name des Emittenten der Anleihe.
     */
    private String emittent;

    /**
     * Kuponzinssatz der Anleihe (in Prozent).
     */
    private double kupon;

    /**
     * Fälligkeit bzw. Laufzeitende der Anleihe.
     */
    private LocalDate laufzeit;

    /**
     * Nennwert (Nominalwert) der Anleihe.
     */
    private double nennwert;

    /**
     * Liste der zugehörigen Zinszahlungen.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zinszahlung> zinszahlungen = new ArrayList<>();

    /**
     * Konstruktor zum Erstellen einer Anleihe mit allen relevanten Attributen.
     *
     * @param emittent Name des Emittenten
     * @param kupon Kuponzinssatz
     * @param laufzeit Laufzeitende
     * @param nennwert Nennwert der Anleihe
     * @param name Name des Wertpapiers
     * @param symbol Symbol des Wertpapiers
     * @param transaktionen Liste der Transaktionen
     * @param kurse Liste der Kurse
     */
    public Anleihe(String emittent, double kupon, LocalDate laufzeit, double nennwert,
                   String name, String symbol, List<Transaktion> transaktionen, List<Kurs> kurse) {

        super(name, symbol, transaktionen, kurse);
        this.emittent = emittent;
        this.kupon = kupon;
        this.laufzeit = laufzeit;
        this.nennwert = nennwert;
    }
}
