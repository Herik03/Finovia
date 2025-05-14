package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Watchlist;

import java.util.Optional;

/**
 * Repository-Interface für die Watchlist-Entität.
 * Stellt Methoden zum Speichern, Lesen, Aktualisieren und Löschen von Watchlists bereit.
 */
@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    
    /**
     * Findet eine Watchlist anhand ihres Besitzers.
     * 
     * @param besitzer Der Besitzer der gesuchten Watchlist
     * @return Ein Optional mit der gefundenen Watchlist oder ein leeres Optional, wenn keine Watchlist gefunden wurde
     */
    Optional<Watchlist> findByBesitzer(Nutzer besitzer);
    
    /**
     * Findet eine Watchlist anhand der ID des Besitzers.
     * 
     * @param besitzerId Die ID des Besitzers der gesuchten Watchlist
     * @return Ein Optional mit der gefundenen Watchlist oder ein leeres Optional, wenn keine Watchlist gefunden wurde
     */
    Optional<Watchlist> findByBesitzerId(int besitzerId);
}