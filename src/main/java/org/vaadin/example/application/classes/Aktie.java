package org.vaadin.example.application.classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
/**
 * Repräsentiert ein Wertpapier vom Typ Aktie.
 *
 * Eine Aktie ist ein Anteilsschein an einem Unternehmen, der typischerweise mit einer
 * bestimmten Anzahl an Anteilen (Stückzahl), dem Unternehmensnamen sowie einer Liste von
 * Dividendenzahlungen assoziiert ist.
 *
 * Diese Klasse erbt von {@link Wertpapier} und erweitert sie um aktienspezifische Eigenschaften.
 *
 * @author Sören
 */

@Getter
@Setter
public class Aktie extends Wertpapier {
    private String unternehmensname;
    private String description;
    private String exchange;
    private String currency;
    private String country;
    private String sector;
    private String industry;
    private long marketCap;
    private long ebitda;
    private double pegRatio;
    private double bookValue;
    private double dividendPerShare;
    private double dividendYield;
    private double eps;
    private double forwardPE;
    private double beta;
    private double yearHigh;
    private double yearLow;
    private LocalDate dividendDate;

    public Aktie(String unternehmensname, String description, String exchange, String currency, String country,
                 String sector, String industry, long marketCap, long ebitda, double pegRatio, double bookValue,
                 double dividendPerShare, double dividendYield, double eps, double forwardPE, double beta,
                 double yearHigh, double yearLow, LocalDate dividendDate) {


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
    public Aktie(int stueckzahl,
                 String unternehmensname,
                 List<Kurs> kurse,
                 String isin,
                 String symbol,
                 int wertpapierId,
                 List<Transaktion> transaktionen,
                 List<Kurs> placeholderList2) {

        // korrektes Mapping auf den vorhandenen Konstruktor in Wertpapier
        super(isin, symbol, wertpapierId, transaktionen, placeholderList2);
        this.unternehmensname = unternehmensname;

        // Initialisierung der optionalen Felder
        this.description = null;
        this.exchange = null;
        this.currency = null;
        this.country = null;
        this.sector = null;
        this.industry = null;
        this.marketCap = 0L;
        this.ebitda = 0L;
        this.pegRatio = 0.0;
        this.bookValue = 0.0;
        this.dividendPerShare = 0.0;
        this.dividendYield = 0.0;
        this.eps = 0.0;
        this.forwardPE = 0.0;
        this.beta = 0.0;
        this.yearHigh = 0.0;
        this.yearLow = 0.0;
        this.dividendDate = null;
    }
}
