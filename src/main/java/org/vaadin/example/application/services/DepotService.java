package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.NutzerRepository;
import org.vaadin.example.application.classes.DepotWertpapier;
import org.vaadin.example.application.classes.Transaktion;
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.classes.Verkauf;

import java.util.List;

@Service
public class DepotService {

    private final DepotRepository depotRepository;
    private final NutzerRepository nutzerRepository;

    @Autowired
    public DepotService(DepotRepository depotRepository, NutzerRepository nutzerRepository) {
        this.depotRepository = depotRepository;
        this.nutzerRepository = nutzerRepository;
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
                gesamtKosten -= t.getStückzahl() * t.getKurs(); // Verkaufspreis wird abgezogen
            }
        }

        if (gesamtStueck <= 0) {
            return new BestandUndKosten(0, 0.0);
        }

        return new BestandUndKosten(gesamtStueck, gesamtKosten);
    }
}
