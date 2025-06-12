package org.vaadin.example.application.classes;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
/**
 * Repräsentiert eine Verkaufs-Transaktion eines {@link Wertpapier}.
 * Erbt von {@link Transaktion} und ergänzt diese um die beim Verkauf
 * angefallenen Steuern.
 *
 * Diese Klasse ist ein konkreter Typ einer Transaktion und wird
 * typischerweise im Rahmen von Veräußerungsprozessen verwendet.
 *
 * @author Jan Schwarzer
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Verkauf extends Transaktion{
    /**
     * Beim Verkauf angefallene Steuern.
     */
    private double steuern;

    /**
     * Konstruktor zur Initialisierung einer Verkaufs-Transaktion mit allen relevanten Feldern.
     *
     * @param steuern      Beim Verkauf angefallene Steuern
     * @param datum        Datum der Transaktion
     * @param gebühren     Angefallene Gebühren
     * @param kurs         Kurs des Wertpapiers zum Verkaufszeitpunkt
     * @param stückzahl    Anzahl der verkauften Wertpapiere
     * @param wertpapier   Zugehöriges Wertpapier
     * @param ausschüttung Zugehörige Ausschüttung (optional)
     */
    public Verkauf(double steuern, LocalDate datum, double gebühren, double kurs, int stückzahl, Wertpapier wertpapier, Ausschuettung ausschüttung) {
        super(datum, gebühren, kurs, stückzahl, wertpapier, ausschüttung);
        this.steuern = steuern;
    }

}
