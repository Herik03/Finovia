package org.vaadin.example.application.classes;

import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Repräsentiert eine Aktie als spezielles Wertpapier.
 * Enthält zusätzliche Informationen wie Unternehmensname, Börse, Branche, Marktkapitalisierung,
 * Dividendeninformationen und weitere finanzielle Kennzahlen.
 *
 * @author Sören Heß
 */
@Entity
@Table(name = "Aktie")
@Getter
@Setter
@NoArgsConstructor
public class Aktie extends Wertpapier {
    /**
     * Name des Unternehmens, das die Aktie herausgibt.
     */
    private String unternehmensname;

    /**
     * Beschreibung des Unternehmens oder der Aktie.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Börse, an der die Aktie gehandelt wird.
     */
    private String exchange;

    /**
     * Währung, in der die Aktie notiert ist.
     */
    private String currency;

    /**
     * Land des Unternehmens.
     */
    private String country;

    /**
     * Sektor, dem das Unternehmen angehört.
     */
    private String sector;

    /**
     * Branche des Unternehmens.
     */
    private String industry;

    /**
     * Marktkapitalisierung des Unternehmens.
     */
    private long marketCap;

    /**
     * EBITDA (Gewinn vor Zinsen, Steuern und Abschreibungen).
     */
    private long ebitda;

    /**
     * PEG-Ratio (Kurs-Gewinn-Wachstums-Verhältnis).
     */
    private double pegRatio;

    /**
     * Buchwert je Aktie.
     */
    private double bookValue;

    /**
     * Dividende je Aktie.
     */
    private double dividendPerShare;

    /**
     * Dividendenrendite.
     */
    private double dividendYield;

    /**
     * Gewinn je Aktie (Earnings per Share).
     */
    private double eps;

    /**
     * Erwartetes Kurs-Gewinn-Verhältnis (Forward P/E).
     */
    private double forwardPE;

    /**
     * Beta-Faktor der Aktie.
     */
    private double beta;

    /**
     * Höchstkurs des letzten Jahres.
     */
    private double yearHigh;

    /**
     * Tiefstkurs des letzten Jahres.
     */
    private double yearLow;

    /**
     * Datum der nächsten Dividendenzahlung.
     */
    private LocalDate dividendDate;

    /**
     * Konstruktor zum Erstellen einer Aktie mit allen relevanten Attributen.
     *
     * @param symbol Symbol der Aktie
     * @param unternehmensname Name des Unternehmens
     * @param description Beschreibung der Aktie
     * @param exchange Börse
     * @param currency Währung
     * @param country Land
     * @param sector Sektor
     * @param industry Branche
     * @param marketCap Marktkapitalisierung
     * @param ebitda EBITDA
     * @param pegRatio PEG-Ratio
     * @param bookValue Buchwert je Aktie
     * @param dividendPerShare Dividende je Aktie
     * @param dividendYield Dividendenrendite
     * @param eps Gewinn je Aktie
     * @param forwardPE Erwartetes KGV
     * @param beta Beta-Faktor
     * @param yearHigh Höchstkurs des Jahres
     * @param yearLow Tiefstkurs des Jahres
     * @param dividendDate Datum der Dividendenzahlung
     */
    public Aktie(String symbol, String unternehmensname, String description, String exchange, String currency, String country,
                 String sector, String industry, long marketCap, long ebitda, double pegRatio, double bookValue,
                 double dividendPerShare, double dividendYield, double eps, double forwardPE, double beta,
                 double yearHigh, double yearLow, LocalDate dividendDate) {

        super(symbol, symbol, new ArrayList<>(), new ArrayList<>());  // Name und Symbol setzen

        this.unternehmensname = unternehmensname;
        this.description = description;
        this.exchange = exchange;
        this.currency = currency;
        this.country = country;
        this.sector = sector;
        this.industry = industry;
        this.marketCap = marketCap;
        this.ebitda = ebitda;
        this.pegRatio = pegRatio;
        this.bookValue = bookValue;
        this.dividendPerShare = dividendPerShare;
        this.dividendYield = dividendYield;
        this.eps = eps;
        this.forwardPE = forwardPE;
        this.beta = beta;
        this.yearHigh = yearHigh;
        this.yearLow = yearLow;
        this.dividendDate = dividendDate;
    }
}
