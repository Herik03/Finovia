package org.vaadin.example.application.services;

import com.crazzyghost.alphavantage.AlphaVantage;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.models.StockQuote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlphaVantageService {

    public StockQuote getStockQuote(String symbol) {
        var response = AlphaVantage.api().timeSeries().quote().forSymbol(symbol).fetchSync();

        if (response == null || response.getErrorMessage() != null) return null;

        return new StockQuote(response.getSymbol(), response.getPrice(), response.getChangePercent());
    }


    /**
     * Ruft Beispiel-Daten für ein Wertpapier basierend auf dem angegebenen Symbol ab.
     *
     * Diese Methode simuliert das Abrufen von Wertpapierdaten anhand eines Symbols.
     * Es werden derzeit statische Beispielwerte für Aktien, Anleihen und ETFs zurückgegeben.
     * Später kann diese Methode durch eine API-Anbindung ersetzt werden, um dynamische Daten zu laden.
     *
     * @param symbol Das Symbol des Wertpapiers. Gültige Werte sind "aktie", "anleihe" und "etf".
     * @return Ein {@link Wertpapier}-Objekt, das die entsprechenden Wertpapierdaten enthält.
     *         Gibt {@code null} zurück, wenn ein ungültiges Symbol übergeben wird.
     *
     * @author Jan Schwarzer
     * @see Aktie
     * @see Anleihe
     * @see ETF
     */
    public Wertpapier fetchWertpapierData(String symbol) {
        String isin = "US6700661040";
        String name = "NVIDIA";
        int wertpapierId = 1;

        List<Transaktion> transaktionen = new ArrayList<>();
        List<Kurs> kurse = new ArrayList<>();

        Transaktion beispielTransaktion = new Kauf("NYSE", LocalDate.now(), 2.0, 250.0, 10, null, null);
        transaktionen.add(beispielTransaktion);

        switch (symbol.toLowerCase()) {
            case "aktie":
                int anzahl = 10;
                String unternehmensname = "NVIDIA Corporation";
                List<Dividende> dividenden = new ArrayList<>();

                Dividende dividende = new Dividende(100, 2.5, 1, 1.5, LocalDate.of(2025, 1, 1), 0.2, beispielTransaktion);
                dividenden.add(dividende);

                return new Aktie(anzahl, unternehmensname, dividenden, isin, name, wertpapierId, transaktionen, kurse);

            case "anleihe":
                String emittent = "Deutsche Bank";
                double kupon = 5.0;
                LocalDate laufzeit = LocalDate.of(2026, 12, 31);
                double nennwert = 1000.0;
                List<Zinszahlung> zinszahlungen = new ArrayList<>();

                Zinszahlung zinszahlung = new Zinszahlung(5.0, 2, 50.0, LocalDate.of(2025, 6, 1), 0.15, beispielTransaktion);
                zinszahlungen.add(zinszahlung);

                return new Anleihe(emittent, kupon, laufzeit, nennwert, zinszahlungen, isin, name, wertpapierId, transaktionen, kurse);

            case "etf":
                String ausschüttung = "Jährlich";
                String etfEmittent = "iShares";
                String fondsname = "iShares MSCI World";
                String index = "MSCI World";

                return new ETF(ausschüttung, etfEmittent, fondsname, index, isin, name, wertpapierId, transaktionen, kurse);

            default:
                System.out.println("Ungültiges Symbol: " + symbol);
                return null;
        }
    }

}
