package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.DepotWertpapierRepository;
import org.vaadin.example.application.repositories.KursRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

@Service
public class ETFKaufService {
    private final DepotWertpapierRepository depotWertpapierRepository;
    private final TransaktionRepository transaktionRepository;
    private final KursRepository kursRepository;
    private final WertpapierRepository wertpapierRepository;

    @Autowired
    public ETFKaufService(DepotWertpapierRepository depotWertpapierRepository,
                          TransaktionRepository transaktionRepository, KursRepository kursRepository,
                          WertpapierRepository wertpapierRepository) {
        this.depotWertpapierRepository = depotWertpapierRepository;
        this.transaktionRepository = transaktionRepository;
        this.kursRepository = kursRepository;
        this.wertpapierRepository = wertpapierRepository;
    }

    public double getKursFuerSymbol(String symbol) {
        return kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Kein Kurs für Symbol " + symbol + " gefunden."))
                .getSchlusskurs();
    }

    @Transactional
    public ETF kaufeETF(String symbol, int stueckzahl, String handelsplatz, Depot depot, Nutzer nutzer) {
        ETF etf = (ETF) wertpapierRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("ETF mit Symbol " + symbol + " nicht gefunden."));

        var kurse = kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol);
        if (kurse.isEmpty()) {
            throw new RuntimeException("Kein Kurs für Symbol " + symbol + " gefunden.");
        }
        double letzterKurs = kurse.get(0).getSchlusskurs();

        DepotWertpapier dwp = depotWertpapierRepository
                .findByDepotAndWertpapier(depot, etf)
                .orElse(new DepotWertpapier(depot, etf, 0, Double.valueOf(0.0)));

        dwp.setAnzahl(dwp.getAnzahl() + stueckzahl);
        dwp.setEinstandspreis(letzterKurs);
        depotWertpapierRepository.save(dwp);

        Kauf kauf = new Kauf(handelsplatz, LocalDate.now(), 2.5, letzterKurs, stueckzahl, etf, null);
        kauf.setNutzer(nutzer);
        transaktionRepository.save(kauf);

        return etf;
    }
}
