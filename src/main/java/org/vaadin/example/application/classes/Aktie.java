package org.vaadin.example.application.classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

@Entity
@Table(name = "Aktie")
@Getter
@Setter
@NoArgsConstructor
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

    public Aktie(String symbol, String unternehmensname, String description, String exchange, String currency, String country,
                 String sector, String industry, long marketCap, long ebitda, double pegRatio, double bookValue,
                 double dividendPerShare, double dividendYield, double eps, double forwardPE, double beta,
                 double yearHigh, double yearLow, LocalDate dividendDate) {

        super(symbol, new ArrayList<Transaktion>(), new ArrayList<Kurs>());

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
