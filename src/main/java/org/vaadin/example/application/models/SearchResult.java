package org.vaadin.example.application.models;

import lombok.Getter;

@Getter
public class SearchResult {
    private String symbol;
    private String name;
    private String region;
    private String currency;

    public SearchResult(String symbol, String name, String region, String currency) {
        this.symbol = symbol;
        this.name = name;
        this.region = region;
        this.currency = currency;
    }

}
