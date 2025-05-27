package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.NutzerRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;

import java.time.LocalDate;
import java.util.List;

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

    public List<Depot> getAllDepots() {
        return depotRepository.findAll();
    }

    public List<Depot> getDepotsByNutzerId(Long nutzerId) {
        return depotRepository.findByBesitzerId(nutzerId);
    }

    public Depot getDepotById(Long depotId) {
        return depotRepository.findById(depotId).orElse(null);
    }

    public Wertpapier getWertpapierById(Long id) {
        return wertpapierRepository.findById(id).orElse(null);
    }

    public void saveDepot(Depot depot) {
        depotRepository.save(depot);
    }

    public void deleteDepot(Long depotId) {
        depotRepository.deleteById(depotId);
    }

    public static class BestandUndKosten {
        public long anzahl;
        public double kosten;

        public BestandUndKosten(long anzahl, double kosten) {
            this.anzahl = anzahl;
            this.kosten = kosten;
        }
    }

    public BestandUndKosten berechneBestandUndKosten(DepotWertpapier dw) {
        if (dw == null || dw.getWertpapier() == null || dw.getWertpapier().getTransaktionen() == null) {
            return new BestandUndKosten(0, 0.0);
        }

        long gesamtStueck = 0;
        double gesamtKosten = 0.0;

        for (Transaktion t : dw.getWertpapier().getTransaktionen()) {
            if (t instanceof Kauf) {
                gesamtStueck += t.getStückzahl();
                gesamtKosten += t.getStückzahl() * t.getKurs() + t.getGebühren();
            } else if (t instanceof Verkauf) {
                gesamtStueck -= t.getStückzahl();
                // Verkauf mindert den Bestand, aber die Kosten bleiben bei Kauf-Transaktionen
            }
        }

        if (gesamtStueck <= 0) {
            return new BestandUndKosten(0, 0.0);
        }

        return new BestandUndKosten(gesamtStueck, gesamtKosten);
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
    public boolean verkaufen(Depot depot, Wertpapier wertpapier, int stückzahl, double kurs, double gebühren, double steuern) {
        if (depot == null || wertpapier == null || stückzahl <= 0) {
            return false;
        }

        DepotWertpapier dw = depot.getDepotWertpapierFor(wertpapier);
        if (dw == null) {
            return false; // Wertpapier nicht im Depot
        }

        BestandUndKosten bestandUndKosten = berechneBestandUndKosten(dw);
        if (bestandUndKosten.anzahl < stückzahl) {
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
