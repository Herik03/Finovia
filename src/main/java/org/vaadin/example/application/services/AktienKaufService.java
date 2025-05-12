package org.vaadin.example.application.services;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.classes.Transaktion;
import org.vaadin.example.application.models.StockQuote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Batuhan Güvercin
@Service
public class AktienKaufService {
    private final AlphaVantageService alphaVantageService;

    public AktienKaufService(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

    public Aktie kaufeAktie(String symbol, int stueckzahl, String handelsplatz){
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0) {
            return null;
        }

        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if(quote == null){
            return null;
        }

        double kurs = quote.getPrice();
        double gebühren = 2.50;

        Kauf kauf = new Kauf(
                handelsplatz,
                LocalDate.now(),
                gebühren,
                kurs,
                stueckzahl,
                null,
                null
        );

        List<Transaktion> transaktionen = new ArrayList<>();
        transaktionen.add(kauf);

        Aktie aktie = new Aktie(
                stueckzahl,
                "Unternehmen: " + quote.getSymbol(),
                new ArrayList<>(),
                "ISIN: " + quote.getSymbol(),
                quote.getSymbol(),
                quote.getSymbol().hashCode(),
                transaktionen,
                new ArrayList<>()
        );

        kauf.setWertpapier(aktie);

        return aktie;
    }
}
