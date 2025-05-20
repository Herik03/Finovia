package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.NutzerRepository;

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
}
