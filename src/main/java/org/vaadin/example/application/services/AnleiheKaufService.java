package org.vaadin.example.application.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.DepotWertpapierRepository;
import org.vaadin.example.application.repositories.KursRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnleiheKaufService {

    @Autowired
    private DepotWertpapierRepository depotWertpapierRepository;

    @Autowired
    private TransaktionRepository transaktionRepository;

    @Autowired
    private KursRepository kursRepository;

    @Autowired
    private WertpapierRepository wertpapierRepository;

    public double getKursFürSymbol(String symbol) {
        List<Kurs> kurse = kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol);
        if (kurse.isEmpty()) {
            throw new RuntimeException("Kein Kurs für Symbol " + symbol + " gefunden.");
        }
        return kurse.get(0).getSchlusskurs();
    }
        @Transactional
        public Anleihe kaufeAnleihe(String symbol, int stueckzahl, String handelsplatz, Depot depot) {
            Anleihe anleihe = (Anleihe) wertpapierRepository.findBySymbol(symbol)
                    .orElseThrow(() -> new RuntimeException("Anleihe mit Symbol " + symbol + " nicht gefunden."));

            List<Kurs> kurse = kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol);
            if (kurse.isEmpty()) {
                throw new RuntimeException("Kein Kurs für Symbol " + symbol + " gefunden.");
            }
            double letzterKurs = kurse.get(0).getSchlusskurs();


            DepotWertpapier dwp = depotWertpapierRepository
                    .findByDepotAndWertpapier(depot, anleihe)
                    .orElse(new DepotWertpapier(depot, anleihe, 0, 0.0));

            dwp.setAnzahl(dwp.getAnzahl() + stueckzahl);
            dwp.setEinstandspreis(letzterKurs);
            depotWertpapierRepository.save(dwp);

            Kauf kauf = new Kauf(handelsplatz, LocalDate.now(), 0.0, letzterKurs, stueckzahl, anleihe, null);
            transaktionRepository.save(kauf);

            return anleihe;
        }
}
