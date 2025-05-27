package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Watchlist;

import java.util.Optional;

/**
 * Repository-Interface für den Datenbankzugriff auf Watchlists.
 */
@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    Optional<Watchlist> findByNutzerId(Long nutzerId);
}
