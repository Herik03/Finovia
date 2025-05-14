package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Wertpapier;

import java.util.List;
import java.util.Optional;

/**
 * Repository-Interface für die Wertpapier-Entität.
 * Stellt Methoden zum Speichern, Lesen, Aktualisieren und Löschen von Wertpapieren bereit.
 */
@Repository
public interface WertpapierRepository extends JpaRepository<Wertpapier, Integer> {
    
    /**
     * Findet ein Wertpapier anhand seiner ISIN.
     * 
     * @param isin Die ISIN des gesuchten Wertpapiers
     * @return Ein Optional mit dem gefundenen Wertpapier oder ein leeres Optional, wenn kein Wertpapier gefunden wurde
     */
    Optional<Wertpapier> findByIsin(String isin);
    
    /**
     * Findet Wertpapiere anhand ihres Namens (teilweise Übereinstimmung).
     * 
     * @param name Der Name oder Teil des Namens des gesuchten Wertpapiers
     * @return Eine Liste der gefundenen Wertpapiere
     */
    List<Wertpapier> findByNameContainingIgnoreCase(String name);
}