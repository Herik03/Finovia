package org.vaadin.example.application.classes;

import jakarta.persistence.*;
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
@Table(name = "wertpapier")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Wertpapier {
    @Column(unique = true)
    private String isin;

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wertpapierId;

    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Transaktion> transaktionen = new ArrayList<>();

    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Kurs> kurse = new ArrayList<>();
/**
 * Konstruktor zur Initialisierung aller Attribute eines Wertpapiers.
 */
    public Wertpapier(String isin, String name, int wertpapierId, List<Transaktion> transaktionen, List<Kurs> kurse) {
        this.isin = isin;
        this.name = name;
        this.wertpapierId = wertpapierId;
        this.transaktionen = transaktionen;
        this.kurse = kurse;
    }

    public Wertpapier(){}

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWertpapierId() {
        return wertpapierId;
    }

    public void setWertpapierId(int wertpapierId) {
        this.wertpapierId = wertpapierId;
    }

    public List<Transaktion> getTransaktionen() {
        return transaktionen;
    }

    public void setTransaktionen(List<Transaktion> transaktionen) {
        this.transaktionen = transaktionen;
    }

    public List<Kurs> getKurse() {
        return kurse;
    }

    public void setKurse(List<Kurs> kurse) {
        this.kurse = kurse;
    }
}
