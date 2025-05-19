package org.vaadin.example.application.classes;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


/**
 * Repräsentiert eine Dividendenzahlung, die aus einer Aktie resultiert.
 *
 * Eine Dividende ist eine Form der Ausschüttung an Aktionäre,
 * die basierend auf der Anzahl gehaltener Aktien erfolgt.
 *
 * Diese Klasse erweitert {@link Ausschuettung} und fügt spezifische Attribute
 * wie die Anzahl der Aktien und die Dividendenrendite hinzu.
 *
 * @author Jan
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
public class Dividende extends Ausschuettung{
    private int aktienAnzahl;
    private double dividendenRendite;
/**
 * Konstruktor für eine Dividendenzahlung.
 */
    public Dividende(int aktienAnzahl, double dividendenRendite, double betrag, LocalDate datum, double steuern, Wertpapier wertpapier) {
        super(betrag, datum, steuern, wertpapier);

        this.aktienAnzahl = aktienAnzahl;
        this.dividendenRendite = dividendenRendite;
    }

}
