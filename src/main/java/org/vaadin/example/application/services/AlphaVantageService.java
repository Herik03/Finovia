package org.vaadin.example.application.services;

import com.crazzyghost.alphavantage.AlphaVantage;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.models.StockQuote;

@Service
public class AlphaVantageService {

    public StockQuote getStockQuote(String symbol) {
        var response = AlphaVantage.api().timeSeries().quote().forSymbol(symbol).fetchSync();

        if (response == null || response.getErrorMessage() != null) return null;

        return new StockQuote(response.getSymbol(), response.getPrice(), response.getChangePercent());
    }
}
