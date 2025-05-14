package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;

import java.util.List;
import java.util.Optional;

/**
 * Repository-Interface für die Depot-Entität.
 * Stellt Methoden zum Speichern, Lesen, Aktualisieren und Löschen von Depots bereit.
 */
@Repository
public interface DepotRepository extends JpaRepository<Depot, String> {
    
    /**
     * Findet alle Depots eines bestimmten Nutzers.
     * 
     * @param besitzer Der Besitzer der gesuchten Depots
     * @return Eine Liste der gefundenen Depots
     */
    List<Depot> findByBesitzer(Nutzer besitzer);
    
    /**
     * Findet alle Depots anhand der ID des Besitzers.
     * 
     * @param besitzerId Die ID des Besitzers der gesuchten Depots
     * @return Eine Liste der gefundenen Depots
     */
    List<Depot> findByBesitzerId(int besitzerId);
    
    /**
     * Findet ein Depot anhand seines Namens und Besitzers.
     * 
     * @param name Der Name des gesuchten Depots
     * @param besitzer Der Besitzer des gesuchten Depots
     * @return Ein Optional mit dem gefundenen Depot oder ein leeres Optional, wenn kein Depot gefunden wurde
     */
    Optional<Depot> findByNameAndBesitzer(String name, Nutzer besitzer);
}