package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert ein festverzinsliches Wertpapier vom Typ Anleihe.
 *
 * Eine Anleihe wird typischerweise von einem Emittenten herausgegeben,
 * hat eine festgelegte Laufzeit, einen Nominalwert (Nennwert) und zahlt
 * regelmäßig Zinsen in Form von {@link Zinszahlung}.
 *
 * Diese Klasse erweitert die allgemeine {@link Wertpapier}-Klasse um
 * anleihenspezifische Eigenschaften.
 *
 * @author Jan, Sören
 */

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Anleihe extends Wertpapier {
    private String emittent;
    private double kupon;
    private LocalDate laufzeit;
    private double nennwert;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zinszahlung> zinszahlungen = new ArrayList<>();
/**
 * Konstruktor zum Erzeugen einer vollständigen Anleihe-Instanz.
 */
    public Anleihe(String emittent, double kupon, LocalDate laufzeit, double nennwert,
               String name, String symbol, List<Transaktion> transaktionen, List<Kurs> kurse) {

        super(name, symbol, transaktionen, kurse);
        this.emittent = emittent;
        this.kupon = kupon;
        this.laufzeit = laufzeit;
        this.nennwert = nennwert;
}

    public void addZinszahlung(Zinszahlung zinszahlung) {
        this.zinszahlungen.add(zinszahlung);
    }

    public void removeZinszahlung(Zinszahlung zinszahlung) {
        this.zinszahlungen.remove(zinszahlung);
    }

    public List<Zinszahlung> getZinszahlungen() {
        return new ArrayList<>(zinszahlungen);
    }

    public double getLetzterKurs() {
        if (getKurse() != null && !getKurse().isEmpty()) {
            return getKurse().get(getKurse().size() - 1).getKurswert(); // letzter Kurswert (z.B. Schlusskurs)
        }
        return nennwert; // Fallback falls keine Kurse vorhanden
    }

}
