package org.vaadin.example.application.services;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class DepotService {

    private final DepotRepository depotRepository;
    private final DepotWertpapierRepository depotWertpapierRepository;
    private final NutzerRepository nutzerRepository;
    private final WertpapierRepository wertpapierRepository;
    private final KursRepository kursRepository;
    private final AlphaVantageService alphaVantageService;

    @Autowired
    public DepotService(AlphaVantageService alphaVantageService, DepotRepository depotRepository,
                        NutzerRepository nutzerRepository,
                        WertpapierRepository wertpapierRepository,
                        KursRepository kursRepository, DepotWertpapierRepository depotWertpapierRepository) {
        this.depotRepository = depotRepository;
        this.nutzerRepository = nutzerRepository;
        this.wertpapierRepository = wertpapierRepository;
        this.kursRepository = kursRepository;
        this.alphaVantageService = alphaVantageService;
        this.depotWertpapierRepository = depotWertpapierRepository;
    }

    @Transactional
    public void wertpapierAusDepotEntfernen(Depot depot, Wertpapier wertpapier, int anzahl) {
        DepotWertpapier zuEntfernendesWertpapier = null;
        boolean erfolgreich = false;

        for (DepotWertpapier dw : depot.getDepotWertpapiere()) {
            if (dw.getWertpapier().equals(wertpapier)) {
                if (dw.getAnzahl() > anzahl) {
                    // Nur die Anzahl reduzieren
                    dw.setAnzahl(dw.getAnzahl() - anzahl);
                    depotWertpapierRepository.save(dw);
                    erfolgreich = true;
                    break;
                } else if (dw.getAnzahl() == anzahl) {
                    // Komplett entfernen
                    zuEntfernendesWertpapier = dw;
                    erfolgreich = true;
                    break;
                }
            }
        }

        if (erfolgreich && zuEntfernendesWertpapier != null) {
            // Manuelle Entfernung aus der Collection, um Kaskadierung zu vermeiden
            depot.getDepotWertpapiere().remove(zuEntfernendesWertpapier);

            // Wichtig: Referenz auf das Depot nullen, um die bidirektionale Beziehung zu trennen
            // Dies verhindert die automatische Kaskadierung beim Löschen
            zuEntfernendesWertpapier.setDepot(null);
            zuEntfernendesWertpapier.setWertpapier(null);

            // Änderungen speichern
            depotRepository.save(depot);

            // Jetzt erst das DepotWertpapier löschen
            depotWertpapierRepository.delete(zuEntfernendesWertpapier);
        }

    }

    @jakarta.transaction.Transactional
    public List<Depot> getAllDepots() {
        List<Depot> depots = depotRepository.findAll();
        for (Depot depot : depots) {
            depot.getDepotWertpapiere().size();
        }
        return depots;
    }

    @jakarta.transaction.Transactional
    public List<Depot> getDepotsByNutzerId(Long nutzerId) {
        List<Depot> depots = depotRepository.findByBesitzerId(nutzerId);
        for (Depot depot : depots) {
            depot.getDepotWertpapiere().size();
        }
        return depots;
    }

    @jakarta.transaction.Transactional
    public Depot getDepotById(Long depotId) {
        Depot depot = depotRepository.findById(depotId).orElse(null);
        if (depot != null) {
            depot.getDepotWertpapiere().size();
        }
        return depot;
    }

    @jakarta.transaction.Transactional
    public Wertpapier getWertpapierById(Long id) {
        Wertpapier wertpapier = wertpapierRepository.findById(id).orElse(null);
        if (wertpapier != null) {
            wertpapier.getTransaktionen().size();
        }
        return wertpapier;
    }

    @jakarta.transaction.Transactional
    public void saveDepot(Depot depot) {
        depotRepository.save(depot);
    }

    @jakarta.transaction.Transactional
    public void deleteDepot(Long depotId) {
        Depot depot = depotRepository.findById(depotId).orElse(null);
        if (depot != null && depot.getBesitzer() != null) {
            depot.getDepotWertpapiere().size();
            Nutzer besitzer = depot.getBesitzer();
            besitzer.depotEntfernen(depot);
            nutzerRepository.save(besitzer);
        }
        depotRepository.deleteById(depotId);
    }

    public static class BestandUndBuchwert {
        public long anzahl;
        public double buchwert;

        public BestandUndBuchwert(long anzahl, double buchwert) {
            this.anzahl = anzahl;
            this.buchwert = buchwert;
        }
    }

    private static class Kauf {
        @Getter @Setter private int stückzahl;
        @Getter @Setter private double kurs;
        @Getter @Setter private double gebühren;

        public Kauf(int stückzahl, double kurs, double gebühren) {
            this.stückzahl = stückzahl;
            this.kurs = kurs;
            this.gebühren = gebühren;
        }
    }

    @jakarta.transaction.Transactional
    public BestandUndBuchwert berechneBestandUndKosten(DepotWertpapier dw) {
        if (dw == null || dw.getWertpapier() == null || dw.getWertpapier().getTransaktionen() == null) {
            return new BestandUndBuchwert(0, 0.0);
        }

        dw.getWertpapier().getTransaktionen().size();

        Queue<Kauf> fifoKaeufe = new LinkedList<>();
        double buchwert = 0.0;
        long gesamtStueck = 0;

        for (Transaktion t : dw.getWertpapier().getTransaktionen()) {
            if (t instanceof org.vaadin.example.application.classes.Kauf kauf) {
                fifoKaeufe.add(new Kauf(kauf.getStückzahl(), kauf.getKurs(), kauf.getGebühren()));
                gesamtStueck += kauf.getStückzahl();
                buchwert += kauf.getStückzahl() * kauf.getKurs() + kauf.getGebühren();
            } else if (t instanceof Verkauf verkauf) {
                int zuVerkaufen = verkauf.getStückzahl();
                gesamtStueck -= zuVerkaufen;

                while (zuVerkaufen > 0 && !fifoKaeufe.isEmpty()) {
                    Kauf fifoKauf = fifoKaeufe.peek();
                    int stk = fifoKauf.getStückzahl();

                    int abzug = Math.min(zuVerkaufen, stk);
                    double anteiligeGebuehr = (fifoKauf.getGebühren() / fifoKauf.getStückzahl()) * abzug;
                    double abzugBuchwert = abzug * fifoKauf.getKurs() + anteiligeGebuehr;

                    buchwert -= abzugBuchwert;
                    fifoKauf.setStückzahl(stk - abzug);

                    if (fifoKauf.getStückzahl() == 0) {
                        fifoKaeufe.poll();
                    }

                    zuVerkaufen -= abzug;
                }
            }
        }

        if (gesamtStueck <= 0) {
            return new BestandUndBuchwert(0, 0.0);
        }

        return new BestandUndBuchwert(gesamtStueck, buchwert);
    }

    public double getAktuellerKurs(Wertpapier wertpapier) {
        if (wertpapier == null) {
            return 0.0;
        }

        if (wertpapier instanceof Aktie aktie) {
            // alphaVantageService liefert direkt double zurück
            return alphaVantageService.getAktuellerKurs(aktie.getSymbol());
        } else if (wertpapier instanceof ETF etf) {
            Kurs kurs = kursRepository.findTopByWertpapier_SymbolOrderByDatumDesc(etf.getSymbol());
            return kurs != null ? kurs.getSchlusskurs() : 0.0;  // Kein doubleValue() nötig!
        } else if (wertpapier instanceof Anleihe anleihe) {
            Kurs kurs = kursRepository.findTopByWertpapier_SymbolOrderByDatumDesc(anleihe.getSymbol());
            return kurs != null ? kurs.getSchlusskurs() : 0.0;  // Auch hier
        }

        return 0.0;
    }
}
