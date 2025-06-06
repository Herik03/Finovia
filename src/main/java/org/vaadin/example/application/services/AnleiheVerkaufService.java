package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.Anleihe;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.DepotWertpapier;
import org.vaadin.example.application.classes.Verkauf;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.repositories.AnleiheRepository;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.repositories.KursRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AnleiheVerkaufService {

    private final DepotRepository depotRepository;
    private final TransaktionRepository transaktionRepository;
    private final AnleiheRepository anleiheRepository;
    private final KursRepository kursRepository;

    public AnleiheVerkaufService(DepotRepository depotRepository,
                                 TransaktionRepository transaktionRepository,
                                 AnleiheRepository anleiheRepository,
                                 KursRepository kursRepository) {
        this.depotRepository = depotRepository;
        this.transaktionRepository = transaktionRepository;
        this.anleiheRepository = anleiheRepository;
        this.kursRepository = kursRepository;
    }

    @Transactional
    public Anleihe verkaufeAnleihe(String symbol, int stueckzahl, Depot depot) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
            return null;
        }

        Optional<Anleihe> optionalAnleihe = anleiheRepository.findAll()
                .stream()
                .filter(a -> symbol.equalsIgnoreCase(a.getSymbol()))
                .findFirst();

        if (optionalAnleihe.isEmpty()) {
            return null; // Anleihe nicht gefunden
        }

        Anleihe anleihe = optionalAnleihe.get();

        int vorhandeneStueckzahl = 0;
        for (DepotWertpapier dw : depot.getDepotWertpapiere()) {
            if (dw.getWertpapier().getSymbol().equalsIgnoreCase(symbol)) {
                vorhandeneStueckzahl = dw.getAnzahl();
                if (!(dw.getWertpapier() instanceof Anleihe)) {
                    return null; // kein Verkauf möglich, da kein Anleihe-Wertpapier
                }
                anleihe = (Anleihe) dw.getWertpapier();
                break;
            }
        }

        if (vorhandeneStueckzahl < stueckzahl) {
            return null; // Nicht genug Anleihen im Depot
        }

        Kurs letzterKurs = kursRepository.findTopByWertpapier_SymbolOrderByDatumDesc(symbol);
        if (letzterKurs == null) {
            return null; // Kein Kurs gefunden
        }

        double kurs = letzterKurs.getSchlusskurs();
        double gebuehren = 2.50;
        double steuern = 0.0;

        Verkauf verkauf = new Verkauf(
                steuern,
                LocalDate.now(),
                gebuehren,
                kurs,
                stueckzahl,
                anleihe,
                null
        );

        anleihe.getTransaktionen().add(verkauf);
        transaktionRepository.save(verkauf);

        depot.wertpapierEntfernen(anleihe, stueckzahl);
        depotRepository.save(depot);

        // Verkauf bleibt in der Transaktionshistorie!

        return anleihe;
    }

    public double getKursFürSymbol(String symbol) {
        Kurs letzterKurs = kursRepository.findTopByWertpapier_SymbolOrderByDatumDesc(symbol);
        if (letzterKurs != null) {
            return letzterKurs.getSchlusskurs();
        } else {
            throw new IllegalArgumentException("Kein Kurs für Symbol " + symbol + " gefunden.");
        }
    }
}
