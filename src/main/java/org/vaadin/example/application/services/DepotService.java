package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.classes.Transaktion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service-Klasse für die Verwaltung von Depots.
 * Diese Klasse simuliert eine Datenbankanbindung für Depots.
 */
@Service
public class DepotService {
    // Simuliert eine Datenbank mit Depots
    private final Map<String, Depot> depotDatabase = new HashMap<>();

    public DepotService() {
        // Initialisiere einige Beispiel-Depots für Demonstrationszwecke
        initializeExampleDepots();
    }

    /**
     * Initialisiert einige Beispiel-Depots für Demonstrationszwecke
     */
    private void initializeExampleDepots() {
        // Erstelle einen Beispiel-Nutzer
        Nutzer exampleUser = new Nutzer("Max", "Mustermann", "max@example.com", "password", "maxmuster");
        exampleUser.setId(1);

        // Erstelle ein Aktiendepot
        Depot aktiendepot = new Depot("1", "Aktiendepot von Max", exampleUser);

        // Erstelle ein ETF-Depot
        Depot etfDepot = new Depot("2", "ETF-Depot von Max", exampleUser);

        // Füge die Depots zur "Datenbank" hinzu
        depotDatabase.put(aktiendepot.getDepotId(), aktiendepot);
        depotDatabase.put(etfDepot.getDepotId(), etfDepot);
    }

    /**
     * Gibt alle Depots zurück
     * 
     * @return Liste aller Depots
     */
    public List<Depot> getAllDepots() {
        return new ArrayList<>(depotDatabase.values());
    }

    /**
     * Gibt alle Depots eines bestimmten Nutzers zurück
     * 
     * @param nutzerId ID des Nutzers
     * @return Liste der Depots des Nutzers
     */
    public List<Depot> getDepotsByNutzerId(int nutzerId) {
        return depotDatabase.values().stream()
                .filter(depot -> depot.getBesitzer().getId() == nutzerId)
                .collect(Collectors.toList());
    }

    /**
     * Gibt ein Depot anhand seiner ID zurück
     * 
     * @param depotId ID des Depots
     * @return Das gefundene Depot oder null, wenn kein Depot mit dieser ID existiert
     */
    public Depot getDepotById(String depotId) {
        return depotDatabase.get(depotId);
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
        depotDatabase.put(depot.getDepotId(), depot);
        return depot;
    }

    /**
     * Löscht ein Depot aus der Datenbank
     * 
     * @param depotId ID des zu löschenden Depots
     */
    public void deleteDepot(String depotId) {
        depotDatabase.remove(depotId);
    }
}
