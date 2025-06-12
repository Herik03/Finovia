package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.repositories.ETFRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ETFVerkaufService {

    private final DepotService depotService;
    private final TransaktionRepository transaktionRepository;
    private final ETFRepository etfRepository;

    public ETFVerkaufService(DepotService depotService,
                             TransaktionRepository transaktionRepository,
                             ETFRepository etfRepository) {
        this.depotService = depotService;
        this.transaktionRepository = transaktionRepository;
        this.etfRepository = etfRepository;
    }

    @Transactional
    public ETF verkaufeETF(String symbol, int stueckzahl, Depot depot, Nutzer nutzer) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
            return null;
        }

        Optional<ETF> optionalETF = etfRepository.findBySymbolIgnoreCase(symbol);
        if (optionalETF.isEmpty()) {
            return null; // ETF nicht gefunden
        }

        ETF etf = optionalETF.get();

        int vorhandeneStueckzahl = 0;
        ETF depotETF = null;

        for (DepotWertpapier dw : depot.getDepotWertpapiere()) {
            if (dw.getWertpapier().getSymbol().equalsIgnoreCase(symbol)
                    && dw.getWertpapier() instanceof ETF) {
                vorhandeneStueckzahl = dw.getAnzahl();
                depotETF = (ETF) dw.getWertpapier();
                break;
            }
        }

        if (depotETF == null || vorhandeneStueckzahl < stueckzahl) {
            return null; // Kein passender ETF oder nicht genug Anteile
        }

        double kurs;
        try {
            kurs = depotETF.getAktuellerKurs();
        } catch (IllegalStateException e) {
            return null; // Kein Kurs verfügbar
        }

        double gebuehren = 2.50;
        double steuern = 0.0;

        Verkauf verkauf = new Verkauf(
                steuern,
                LocalDate.now(),
                gebuehren,
                kurs,
                stueckzahl,
                depotETF,
                null
        );
        verkauf.setNutzer(nutzer);

        depotETF.getTransaktionen().add(verkauf);
        transaktionRepository.save(verkauf);

        depotService.wertpapierAusDepotEntfernen(depot, depotETF, stueckzahl);

        return depotETF;
    }

    public double getKursFürSymbol(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol darf nicht leer sein.");
        }

        Optional<ETF> optionalETF = etfRepository.findBySymbolIgnoreCase(symbol);
        if (optionalETF.isEmpty()) {
            throw new RuntimeException("Kein Kurs für Symbol gefunden: " + symbol);
        }

        return optionalETF.get().getAktuellerKurs();
    }
}
