package org.vaadin.example.application.classes;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
@NoArgsConstructor
public class Kauf extends Transaktion{

    @Getter @Setter
    private String handelsplatz;
/**
 * Konstruktor zur Initialisierung eines Kauf-Objekts mit allen Attributen.
 */
    public Kauf(String handelsplatz, LocalDate datum, double gebühren, double kurs, int stückzahl, Wertpapier wertpapier, Ausschuettung ausschüttung) {
        super(datum, gebühren, kurs, stückzahl, wertpapier, ausschüttung);
        this.handelsplatz = handelsplatz;
    }

}
