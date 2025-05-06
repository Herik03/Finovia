package org.vaadin.example.application.models;

import lombok.Getter;

@Getter
public class StockQuote {

    private String symbol;
    private double price;
    private double percentChange;

    public StockQuote(String symbol, double price, double percentChange) {
        this.symbol = symbol;
        this.price = price;
        this.percentChange = percentChange;
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f (%s)", symbol, price, percentChange);
    }

}
