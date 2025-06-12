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
 * @author Jan Schwarzer
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
public class Dividende extends Ausschuettung {
    /**
     * Anzahl der Aktien, für die die Dividende gezahlt wird.
     */
    private int aktienAnzahl;

    /**
     * Dividendenrendite zum Zeitpunkt der Ausschüttung.
     */
    private double dividendenRendite;

    /**
     * Konstruktor zum Erstellen einer Dividendenzahlung mit allen relevanten Attributen.
     *
     * @param aktienAnzahl Anzahl der Aktien
     * @param dividendenRendite Dividendenrendite
     * @param betrag Ausgezahlter Betrag
     * @param datum Datum der Ausschüttung
     * @param steuern Abgeführte Steuern
     * @param wertpapier Zugehöriges Wertpapier (Aktie)
     */
    public Dividende(int aktienAnzahl, double dividendenRendite, double betrag, LocalDate datum, double steuern, Wertpapier wertpapier) {
        super(betrag, datum, steuern, wertpapier);

        this.aktienAnzahl = aktienAnzahl;
        this.dividendenRendite = dividendenRendite;
    }
}