package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.ETF;
import org.vaadin.example.application.classes.DepotWertpapier;
import org.vaadin.example.application.classes.Verkauf;
import org.vaadin.example.application.classes.StockQuote;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.repositories.ETFRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ETFVerkaufService {

    private final AlphaVantageService alphaVantageService;
    private final DepotRepository depotRepository;
    private final TransaktionRepository transaktionRepository;
    private final ETFRepository etfRepository;

    public ETFVerkaufService(AlphaVantageService alphaVantageService,
                             DepotRepository depotRepository,
                             TransaktionRepository transaktionRepository,
                             ETFRepository etfRepository) {
        this.alphaVantageService = alphaVantageService;
        this.depotRepository = depotRepository;
        this.transaktionRepository = transaktionRepository;
        this.etfRepository = etfRepository;
    }

    /**
     * Verkauft ETF-Anteile aus einem Depot.
     *
     * @param symbol    Das ETF-Symbol
     * @param stueckzahl Anzahl der zu verkaufenden ETF-Anteile
     * @param depot     Das Depot, aus dem verkauft wird
     * @return Das ETF, wenn Verkauf erfolgreich, sonst null
     */
    @Transactional
    public ETF verkaufeETF(String symbol, int stueckzahl, Depot depot) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
            return null;
        }

        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            return null;
        }

        Optional<ETF> optionalETF = etfRepository.findAll()
                .stream()
                .filter(e -> symbol.equalsIgnoreCase(e.getSymbol()))
                .findFirst();

        if (optionalETF.isEmpty()) {
            return null; // ETF nicht gefunden
        }

        ETF etf = optionalETF.get();

        int vorhandeneStueckzahl = 0;
        for (DepotWertpapier dw : depot.getDepotWertpapiere()) {
            if (dw.getWertpapier().equals(etf)) {
                vorhandeneStueckzahl = dw.getAnzahl();
                break;
            }
        }

        if (vorhandeneStueckzahl < stueckzahl) {
            return null; // Nicht genug ETF-Anteile zum Verkaufen
        }

        double kurs = quote.getPrice();
        double gebuehren = 2.50;
        double steuern = 0.0; // Beispiel: Steuerberechnung kann später ergänzt werden

        Verkauf verkauf = new Verkauf(
                steuern,
                LocalDate.now(),
                gebuehren,
                kurs,
                stueckzahl,
                etf,
                null
        );

        etf.getTransaktionen().add(verkauf);
        transaktionRepository.save(verkauf);

        depot.wertpapierEntfernen(etf, stueckzahl);
        depotRepository.save(depot);

        return etf;
    }

    public double getKursFürSymbol(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol darf nicht leer sein.");
        }
        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            throw new RuntimeException("Kein Kurs für Symbol gefunden: " + symbol);
        }
        return quote.getPrice();
    }
}
