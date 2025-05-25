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
 * @author Sören
 */

@Entity
@Table(name = "Wertpapier")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Wertpapier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wertpapierId;

    private String name; // Dieser Parameter ist wichtig für die Suche

    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Transaktion> transaktionen;

    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ausschuettung> ausschuettungen = new ArrayList<>();

    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Kurs> kurse;

    public Wertpapier(String name, List<Transaktion> transaktionen, List<Kurs> kurse) {
        this.name = name;
        this.transaktionen = transaktionen;
        this.kurse = kurse;
    }

    public void addTransaktion(Transaktion transaktion) {
        transaktionen.add(transaktion);
    }

    public void addKurs(Kurs kurs) {
        kurse.add(kurs);
    }


    public void addAusschuettung(Ausschuettung ausschuettung) {
        ausschuettungen.add(ausschuettung);
    }


}