package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Die abstrakte Basisklasse {@code Wertpapier} repräsentiert ein generisches Finanzinstrument.
 * Sie enthält grundlegende Attribute und Beziehungen, die für spezifische Finanzprodukte wie
 * Aktien, Anleihen oder ETFs gemeinsam sind.
 *
 * Diese Klasse ist als abstrakt definiert und wird von konkreten Unterklassen wie {@link Aktie},
 * {@link Anleihe} oder {@link ETF} erweitert.
 *
 * @author Sören Heß
 */

@Entity
@Table(name = "Wertpapier")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Wertpapier {
    /**
     * Eindeutige ID des Wertpapiers (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wertpapier_seq")
    @SequenceGenerator(name = "wertpapier_seq", sequenceName = "wertpapier_seq", allocationSize = 1)
    private Long wertpapierId;

    /**
     * Name des Wertpapiers (wichtig für die Suche).
     */
    private String name;

    /**
     * Symbol/Kürzel des Wertpapiers (z. B. ISIN, Ticker).
     */
    private String symbol;

    /**
     * Liste der zugehörigen Transaktionen.
     */
    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Transaktion> transaktionen;

    /**
     * Liste der zugehörigen Ausschüttungen.
     */
    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ausschuettung> ausschuettungen = new ArrayList<>();

    /**
     * Historische Kurse des Wertpapiers.
     */
    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Kurs> kurse;

    /**
     * Konstruktor zur Initialisierung eines Wertpapiers mit Name, Symbol, Transaktionen und Kursen.
     *
     * @param name          Name des Wertpapiers
     * @param symbol        Symbol/Kürzel des Wertpapiers
     * @param transaktionen Liste der Transaktionen
     * @param kurse         Liste der Kurse
     */
    public Wertpapier(String name, String symbol, List<Transaktion> transaktionen, List<Kurs> kurse) {
        this.name = name;
        this.symbol = symbol;
        this.transaktionen = transaktionen;
        this.kurse = kurse;
    }

    /**
     * Fügt eine Ausschüttung zur Liste der Ausschüttungen hinzu.
     *
     * @param ausschuettung Die hinzuzufügende Ausschüttung
     */
    public void addAusschuettung(Ausschuettung ausschuettung) {
        ausschuettungen.add(ausschuettung);
    }

    /**
     * Gibt den Typ des Wertpapiers als String zurück, basierend auf der konkreten Unterklasse.
     *
     * @return "Aktie", "Anleihe", "ETF" oder "Unbekannt"
     */
    @Transient
    public String getTyp() {
        return switch (this) {
            case Aktie ignored -> "Aktie";
            case Anleihe ignored -> "Anleihe";
            case ETF ignored -> "ETF";
            default -> "Unbekannt";
        };
    }
}