package org.vaadin.example.application.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.NutzerRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class DepotService {

    private final DepotRepository depotRepository;
    private final NutzerRepository nutzerRepository;
    private final WertpapierRepository wertpapierRepository;

    @Autowired
    public DepotService(DepotRepository depotRepository,
                        NutzerRepository nutzerRepository,
                        WertpapierRepository wertpapierRepository) {
        this.depotRepository = depotRepository;
        this.nutzerRepository = nutzerRepository;
        this.wertpapierRepository = wertpapierRepository;
    }

    @jakarta.transaction.Transactional
    public List<Depot> getAllDepots() {
        List<Depot> depots = depotRepository.findAll();
        // Initialize the lazy-loaded collections to prevent LazyInitializationException
        for (Depot depot : depots) {
            depot.getDepotWertpapiere().size(); // Force initialization
        }
        return depots;
    }

    @jakarta.transaction.Transactional
    public List<Depot> getDepotsByNutzerId(Long nutzerId) {
        List<Depot> depots = depotRepository.findByBesitzerId(nutzerId);
        // Initialize the lazy-loaded collections to prevent LazyInitializationException
        for (Depot depot : depots) {
            depot.getDepotWertpapiere().size(); // Force initialization
        }
        return depots;
    }

    @jakarta.transaction.Transactional
    public Depot getDepotById(Long depotId) {
        Depot depot = depotRepository.findById(depotId).orElse(null);
        if (depot != null) {
            // Initialize the lazy-loaded collections to prevent LazyInitializationException
            depot.getDepotWertpapiere().size(); // Force initialization
        }
        return depot;
    }

    @jakarta.transaction.Transactional
    public Wertpapier getWertpapierById(Long id) {
        Wertpapier wertpapier = wertpapierRepository.findById(id).orElse(null);
        if (wertpapier != null) {
            // Force initialization of any lazy-loaded collections if needed
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
            // Force initialization of the lazy-loaded collections
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
        @Getter @Setter
        private int stückzahl;
        @Getter @Setter
        private double kurs;
        @Getter @Setter
        private double gebühren;

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

        // Force initialization of the lazy-loaded collections
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
                        fifoKaeufe.poll(); // Kauf vollständig verbraucht
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

    /**
     * Führt den Verkauf von Aktien durch.
     * Prüft ob genug Aktien vorhanden sind, erstellt eine Verkaufs-Transaktion,
     * zieht die Aktien vom Bestand ab und erhöht die Cash-Balance im Depot.
     *
     * @param depot      Das Depot, aus dem verkauft wird.
     * @param wertpapier Das Wertpapier, das verkauft wird.
     * @param stückzahl  Anzahl der zu verkaufenden Aktien.
     * @param kurs       Verkaufskurs pro Aktie.
     * @param gebühren   Gebühren für den Verkauf.
     * @param steuern    Steuern auf den Verkauf.
     * @return true, wenn Verkauf erfolgreich war, sonst false.
     */
    @jakarta.transaction.Transactional
    public boolean verkaufen(Depot depot, Wertpapier wertpapier, int stückzahl, double kurs, double gebühren, double steuern) {
        if (depot == null || wertpapier == null || stückzahl <= 0) {
            return false;
        }

        // Force initialization of the lazy-loaded collections
        depot.getDepotWertpapiere().size();

        DepotWertpapier dw = depot.getDepotWertpapierFor(wertpapier);
        if (dw == null) {
            return false; // Wertpapier nicht im Depot
        }

        BestandUndBuchwert bestandUndBuchwert = berechneBestandUndKosten(dw);
        if (bestandUndBuchwert.anzahl < stückzahl) {
            return false; // Nicht genug Aktien zum Verkaufen
        }

        // Verkaufs-Transaktion erstellen
        Verkauf verkauf = new Verkauf(steuern, LocalDate.now(), gebühren, kurs, stückzahl, wertpapier, null);

        // Transaktion dem Wertpapier hinzufügen
        wertpapier.getTransaktionen().add(verkauf);

        // Bestand im DepotWertpapier reduzieren
        dw.setAnzahl(dw.getAnzahl() - stückzahl);

        // Cash-Balance im Depot aktualisieren (Verkaufserlös minus Gebühren und Steuern)
        double nettoErlös = stückzahl * kurs - gebühren - steuern;
        depot.setSaldo(depot.getSaldo() + nettoErlös);

        // Depot speichern (Cascade speichert Wertpapiere und Transaktionen)
        saveDepot(depot);

        return true;
    }
}
