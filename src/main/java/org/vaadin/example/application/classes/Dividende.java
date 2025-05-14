package org.vaadin.example.application.classes;

import java.time.LocalDate;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

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
@Entity
@Table(name = "dividende")
@NoArgsConstructor
public class Dividende extends Ausschuettung {
    private int aktienAnzahl;
    private double dividendenRendite;
/**
 * Konstruktor für eine Dividendenzahlung.
 */
    public Dividende(int aktienAnzahl, double dividendenRendite, int ausschüttungId, double betrag, LocalDate datum, double steuern, Transaktion transaktion) {
        super(ausschüttungId, betrag, datum, steuern, transaktion);
        this.aktienAnzahl = aktienAnzahl;
        this.dividendenRendite = dividendenRendite;
    }

    public int getAktienAnzahl() {
        return aktienAnzahl;
    }

    public void setAktienAnzahl(int aktienAnzahl) {
        this.aktienAnzahl = aktienAnzahl;
    }

    public double getDividendenRendite() {
        return dividendenRendite;
    }

    public void setDividendenRendite(double dividendenRendite) {
        this.dividendenRendite = dividendenRendite;
    }
}
