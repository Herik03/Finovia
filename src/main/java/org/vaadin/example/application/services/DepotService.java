package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.NutzerRepository;

import java.util.List;

/**
 * Service-Klasse für die Verwaltung von Depots.
 * Diese Klasse simuliert eine Datenbankanbindung für Depots.
 */
@Service
public class DepotService {

    private final DepotRepository depotRepository;
    private final NutzerRepository nutzerRepository;

    @Autowired
    public DepotService(DepotRepository depotRepository, NutzerRepository nutzerRepository) {
        this.depotRepository = depotRepository;
        this.nutzerRepository = nutzerRepository;
    }



    /**
     * Gibt alle Depots zurück
     * 
     * @return Liste aller Depots
     */
    public List<Depot> getAllDepots() {
        return depotRepository.findAll();
    }

    /**
     * Gibt alle Depots eines bestimmten Nutzers zurück
     * 
     * @param nutzerId ID des Nutzers
     * @return Liste der Depots des Nutzers
     */
    public List<Depot> getDepotsByNutzerId(Long nutzerId) {
        return depotRepository.findByBesitzerId(nutzerId);
    }

    /**
     * Gibt ein Depot anhand seiner ID zurück
     * 
     * @param depotId ID des Depots
     * @return Das gefundene Depot oder null, wenn kein Depot mit dieser ID existiert
     */
    public Depot getDepotById(Long depotId) {
        return depotRepository.findById(depotId).orElse(null);
    }

    /**
     * Speichert ein neues Depot in der Datenbank
     *
     * @param depot Das zu speichernde Depot
     */
    public void saveDepot(Depot depot) {
        depotRepository.save(depot);
    }

    /**
     * Löscht ein Depot aus der Datenbank
     * 
     * @param depotId ID des zu löschenden Depots
     */
    public void deleteDepot(Long depotId) {
        depotRepository.deleteById(depotId);
    }
}