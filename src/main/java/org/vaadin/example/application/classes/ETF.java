package org.vaadin.example.application.classes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/**
 * Repräsentiert ein Exchange Traded Fund (ETF), das eine Sonderform des Wertpapiers darstellt.
 *
 * Ein ETF ist ein börsengehandelter Fonds, der in der Regel einen Index abbildet
 * und sowohl thesaurierend als auch ausschüttend sein kann.
 *
 * Diese Klasse erweitert {@link Wertpapier} und ergänzt spezifische Eigenschaften
 * wie Emittent, Fondsname, Index und Ausschüttungsform.
 *
 * @author Jan, Sören
 */
@Entity
@NoArgsConstructor
@Getter @Setter
public class ETF extends Wertpapier{
    private String emittent;
    private String index;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Dividende> dividende = new ArrayList<>();
/**
 * Konstruktor zur Initialisierung eines ETF-Objekts mit allen Attributen.
 */
    public ETF(String emittent, String index, String name, List<Transaktion> transaktionen, List<Kurs> kurse) {
        super(name, transaktionen, kurse);
        this.emittent = emittent;
        this.index = index;
    }

    public void addDividende(Dividende dividende) {
        this.dividende.add(dividende);
    }

    public void removeDividende(Dividende dividende) {
        this.dividende.remove(dividende);
    }

    public List<Dividende> getDividenden() {
        return new ArrayList<>(dividende);
    }
}
