package org.vaadin.example.application.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vaadin.example.application.enums.Zinsfrequenz;

import java.time.LocalDate;

/**
 * Die Klasse {@code ETFDividende} repräsentiert eine konkrete Ausschüttung,
 * die im Zusammenhang mit einem Exchange Traded Fund (ETF) erfolgt.
 *
 * Diese Ausschüttung basiert auf der Anzahl gehaltener ETF-Anteile im Depot
 * und enthält neben dem Bruttobetrag auch Angaben zu Steuern und Auszahlungsdatum.
 *
 * Gemeinsame Ausschüttungsinformationen wie Betrag, Datum, Steuer und zugehöriges Wertpapier
 * werden von der Oberklasse {@link Ausschuettung} verwaltet.
 *
 * Diese Klasse wird ausschließlich für {@link ETF} verwendet.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ETFDividende extends Ausschuettung {

    /**
     * Die Anzahl der ETF-Anteile, auf die sich diese Dividendenzahlung bezieht.
     */
    private int etfAnteile;

    /**
     * Die Frequenz der Dividendenausschüttung (z. B. JAEHRLICH, HALBJAEHRLICH).
     */
    @Enumerated(EnumType.STRING)
    private Zinsfrequenz frequenz;

    /**
     * Vollständiger Konstruktor zum Erzeugen einer ETF-Dividende mit allen notwendigen Daten.
     *
     * @param etfAnteile Anzahl der ETF-Anteile im Depot
     * @param betrag Bruttobetrag der Ausschüttung (Gesamtbetrag, nicht je Anteil)
     * @param nettoAuszahlung Auszahlungsbetrag nach Abzug der Kapitalertragsteuer
     * @param datum Ausschüttungsdatum
     * @param steuern Höhe der einbehaltenen Kapitalertragsteuer
     * @param etf Referenz auf das zugehörige ETF-Wertpapier
     * @param frequenz Häufigkeit der Ausschüttung (z. B. JAEHRLICH, HALBJAEHRLICH)
     */
    public ETFDividende(int etfAnteile, double betrag, double nettoAuszahlung,
                        LocalDate datum, double steuern, Wertpapier etf, Zinsfrequenz frequenz) {
        super(betrag, datum, steuern, etf);
        this.etfAnteile = etfAnteile;
        this.frequenz = frequenz;
    }
}
