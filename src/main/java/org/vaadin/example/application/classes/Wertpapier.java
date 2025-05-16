package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
@Getter @Setter
public abstract class Wertpapier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wertpapierId;

    private String name;
    private List<Transaktion> transaktionen;

    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Kurs> kurse;
/**
 * Konstruktor zur Initialisierung aller Attribute eines Wertpapiers.
 */
    public Wertpapier(String name, List<Transaktion> transaktionen, List<Kurs> kurse) {
        this.name = name;
//        this.transaktionen = transaktionen;
        this.kurse = kurse;
    }

    public Wertpapier(){}
}
