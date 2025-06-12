package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Die Klasse {@code ETF} repräsentiert ein Exchange Traded Fund, das eine spezialisierte Form von {@link Wertpapier} ist.
 *
 * ETFs sind börsengehandelte Fonds, die typischerweise einen Index abbilden (z. B. DAX, MSCI World)
 * und regelmäßig Dividenden ausschütten können. Diese Ausschüttungen werden als {@link ETFDividende} verwaltet.
 *
 * @author Jan Schwarzer
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ETF extends Wertpapier {

    /**
     * Emittent des ETFs (z. B. iShares, Lyxor).
     */
    private String emittent;

    /**
     * Der abgebildete Index (z. B. MSCI World, DAX).
     */
    private String index;

    /**
     * Liste aller erfassten ETF-Dividenden, die diesem ETF zugeordnet sind.
     */
    @OneToMany(mappedBy = "wertpapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ETFDividende> etfDividenden = new ArrayList<>();

    /**
     * Konstruktor zur vollständigen Initialisierung eines ETFs inklusive Dividendenhistorie.
     *
     * @param emittent Der Emittent des ETFs
     * @param index Der abgebildete Index
     * @param name Der Name des ETFs
     * @param symbol Das Handelssymbol des ETFs
     * @param transaktionen Liste der zugehörigen Transaktionen
     * @param kurse Liste historischer Kursdaten
     * @param etfDividenden Historische Ausschüttungen dieses ETFs
     */
    public ETF(String emittent, String index, String name, String symbol,
               List<Transaktion> transaktionen, List<Kurs> kurse, List<ETFDividende> etfDividenden) {

        super(name, symbol, transaktionen, kurse);
        this.emittent = emittent;
        this.index = index;
        this.etfDividenden = new ArrayList<>();

        for (ETFDividende dividende : etfDividenden) {
            dividende.setWertpapier(this);
            this.etfDividenden.add(dividende);
        }
    }

    /**
     * Liefert den aktuellsten Kurs (basierend auf dem neuesten Datum) des ETFs aus der Kursliste.
     * @return aktueller Kurs als double
     * @throws IllegalStateException wenn keine Kursdaten vorhanden sind
     */
    public double getAktuellerKurs() {
        if (getKurse() == null || getKurse().isEmpty()) {
            throw new IllegalStateException("Keine Kursdaten vorhanden für ETF " + getSymbol());
        }

        return getKurse()
                .stream()
                .max(Comparator.comparing(Kurs::getDatum))
                .map(Kurs::getSchlusskurs)
                .orElseThrow(() -> new IllegalStateException("Keine Kursdaten vorhanden für ETF " + getSymbol()));
    }
}
