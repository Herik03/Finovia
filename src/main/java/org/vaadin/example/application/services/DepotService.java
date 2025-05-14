package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.NutzerRepository;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service-Klasse für die Verwaltung von Depots.
 * Diese Klasse verwendet eine PostgreSQL-Datenbank für die Speicherung von Depots.
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
     * Initialisiert den Service mit Demo-Daten, falls die Datenbank leer ist.
     */
    @PostConstruct
    public void init() {
        if (depotRepository.count() == 0) {
            // Suche nach dem Demo-Nutzer
            Optional<Nutzer> demoNutzerOpt = nutzerRepository.findByUsername("maxmuster");

            if (demoNutzerOpt.isPresent()) {
                Nutzer demoNutzer = demoNutzerOpt.get();

                // Erstelle ein Aktiendepot
                Depot aktiendepot = new Depot("1", "Aktiendepot von Max", demoNutzer);

                // Erstelle ein ETF-Depot
                Depot etfDepot = new Depot("2", "ETF-Depot von Max", demoNutzer);

                // Speichere die Depots in der Datenbank
                depotRepository.save(aktiendepot);
                depotRepository.save(etfDepot);
            }
        }
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
    public List<Depot> getDepotsByNutzerId(int nutzerId) {
        return depotRepository.findByBesitzerId(nutzerId);
    }

    /**
     * Gibt ein Depot anhand seiner ID zurück
     * 
     * @param depotId ID des Depots
     * @return Das gefundene Depot oder null, wenn kein Depot mit dieser ID existiert
     */
    public Depot getDepotById(String depotId) {
        return depotRepository.findById(depotId).orElse(null);
    }

    /**
     * Speichert ein neues Depot in der Datenbank
     * 
     * @param depot Das zu speichernde Depot
     * @return Das gespeicherte Depot mit generierter ID
     */
    public Depot saveDepot(Depot depot) {
        if (depot.getDepotId() == null || depot.getDepotId().isEmpty()) {
            // Generiere eine neue ID für das Depot
            depot.setDepotId(UUID.randomUUID().toString());
        }
        return depotRepository.save(depot);
    }

    /**
     * Löscht ein Depot aus der Datenbank
     * 
     * @param depotId ID des zu löschenden Depots
     */
    public void deleteDepot(String depotId) {
        depotRepository.deleteById(depotId);
    }
}
