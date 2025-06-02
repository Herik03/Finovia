package org.vaadin.example.application.classes;

import lombok.Getter;

/**
 * Repräsentiert ein Aktienpreiszitat mit grundlegenden Informationen über einen Aktienkurs.
 * <p>
 * Diese Klasse kapselt die wesentlichen Bestandteile eines Aktienkurses:
 * <ul>
 *     <li>Das Börsensymbol der Aktie (z.B. "AAPL" für Apple Inc.)</li>
 *     <li>Den aktuellen Preis der Aktie</li>
 *     <li>Die prozentuale Änderung des Aktienkurses</li>
 * </ul>
 * <p>
 * Die Klasse verwendet Lombok's {@code @Getter} Annotation, um automatisch 
 * Getter-Methoden für alle Felder zu generieren.
 * 
 * @author Sören Heß
 * @version 1.0
 * @see org.vaadin.example.application.services.AlphaVantageService
 */
@Getter
public class StockQuote {

    /** Das Börsensymbol der Aktie (z.B. "AAPL" für Apple Inc.) */
    private String symbol;
    
    /** Der aktuelle Preis der Aktie in USD */
    private double price;
    
    /** Die prozentuale Änderung des Aktienkurses im Vergleich zum vorherigen Schlusskurs */
    private double percentChange;

    /**
     * Konstruktor für ein StockQuote-Objekt.
     * 
     * @param symbol Das Börsensymbol der Aktie
     * @param price Der aktuelle Preis der Aktie
     * @param percentChange Die prozentuale Änderung des Aktienkurses
     */
    public StockQuote(String symbol, double price, double percentChange) {
        this.symbol = symbol;
        this.price = price;
        this.percentChange = percentChange;
    }

    /**
     * Gibt eine formatierte String-Repräsentation des StockQuote-Objekts zurück.
     * <p>
     * Das Format ist: "Symbol: $Preis (Prozentänderung)"
     * Beispiel: "AAPL: $150.50 (2.3)"
     * 
     * @return Eine formatierte String-Darstellung des Aktienkurses
     */
    @Override
    public String toString() {
        return String.format("%s: $%.2f (%s)", symbol, price, percentChange);
    }

}