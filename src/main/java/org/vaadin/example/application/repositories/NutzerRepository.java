package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Nutzer;

import java.util.Optional;

/**
 * Repository-Interface für die Nutzer-Entität.
 * Stellt Methoden zum Speichern, Lesen, Aktualisieren und Löschen von Nutzern bereit.
 */
@Repository
public interface NutzerRepository extends JpaRepository<Nutzer, Integer> {
    
    /**
     * Findet einen Nutzer anhand seines Benutzernamens.
     * 
     * @param username Der Benutzername des gesuchten Nutzers
     * @return Ein Optional mit dem gefundenen Nutzer oder ein leeres Optional, wenn kein Nutzer gefunden wurde
     */
    Optional<Nutzer> findByUsername(String username);
    
    /**
     * Findet einen Nutzer anhand seiner E-Mail-Adresse.
     * 
     * @param email Die E-Mail-Adresse des gesuchten Nutzers
     * @return Ein Optional mit dem gefundenen Nutzer oder ein leeres Optional, wenn kein Nutzer gefunden wurde
     */
    Optional<Nutzer> findByEmail(String email);
}