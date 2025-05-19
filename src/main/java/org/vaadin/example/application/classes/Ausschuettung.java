package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
/**
 * Abstrakte Oberklasse für Ausschüttungen wie {@link Dividende} oder {@link Zinszahlung}.
 *
 * Eine Ausschüttung stellt eine finanzielle Zahlung dar, die einem Anleger
 * im Rahmen eines Wertpapiers zufließt. Dazu zählen insbesondere Dividenden (Aktien)
 * und Zinszahlungen (Anleihen).
 *
 * Diese Klasse enthält allgemeine Eigenschaften und Methoden, die allen Ausschüttungsarten gemeinsam sind.
 *
 * @author Jan, Sören
 */

@Entity
@Table(name = "Ausschuettung")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
@NoArgsConstructor
@Getter @Setter
public abstract class Ausschuettung{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ausschüttungId;

    private double betrag;
    private LocalDate datum;
    private double steuern;

/**
 * Konstruktor für eine Ausschüttung.
 */

 public Ausschuettung(double betrag, LocalDate datum, double steuern) {

        this.betrag = betrag;
        this.datum = datum;
        this.steuern = steuern;
    }

}
