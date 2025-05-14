package org.vaadin.example.application.classes;

import java.time.LocalDate;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

/**
 * Repräsentiert einen Kaufvorgang eines {@link Wertpapier} auf einem bestimmten Handelsplatz.
 *
 * Diese Klasse erweitert {@link Transaktion} und ergänzt sie um spezifische Informationen
 * zum Handelsplatz, an dem der Kauf stattgefunden hat (z. B. XETRA, NYSE, etc.).
 *
 * Ein Kauf ist eine spezielle Form einer Transaktion, bei der Wertpapiere erworben werden.
 *
 * @author Jan
 */
@Entity
@Table(name = "kauf")
@NoArgsConstructor
public class Kauf extends Transaktion {
    private String handelsplatz;
/**
 * Konstruktor zur Initialisierung eines Kauf-Objekts mit allen Attributen.
 */
    public Kauf(String handelsplatz, LocalDate datum, double gebühren, double kurs, int stückzahl, Wertpapier wertpapier, Ausschuettung ausschüttung) {
        super(datum, gebühren, kurs, stückzahl, wertpapier, ausschüttung);
        this.handelsplatz = handelsplatz;
    }

    public String getHandelsplatz() {
        return handelsplatz;
    }

    public void setHandelsplatz(String handelsplatz) {
        this.handelsplatz = handelsplatz;
    }
}
