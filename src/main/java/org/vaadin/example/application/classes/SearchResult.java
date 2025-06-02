package org.vaadin.example.application.classes;

import lombok.Getter;
import org.vaadin.example.application.classes.enums.SearchResultTypeEnum;

@Getter
public class SearchResult {
    private String symbol;
    private String name;
    private String region;
    private String currency;
    private SearchResultTypeEnum type;

    public SearchResult(String symbol, String name, String region, String currency, SearchResultTypeEnum type) {
        this.symbol = symbol;
        this.name = name;
        this.region = region;
        this.currency = currency;
        this.type = type;
    }

}
