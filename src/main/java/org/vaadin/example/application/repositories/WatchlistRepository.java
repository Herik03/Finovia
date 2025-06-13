package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Watchlist;

import java.util.Optional;

/**
 * Repository-Interface für {@link Watchlist}-Entitäten.
 *
 * Bietet CRUD-Operationen sowie benutzerdefinierte Methoden zur Suche
 * von Watchlists anhand der Nutzer-ID, mit und ohne zugehörige Wertpapiere.
 *
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Henrik Dollmann
 */
@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    /**
     * Sucht eine Watchlist anhand der Nutzer-ID.
     *
     * @param nutzerId Die ID des Nutzers
     * @return Optional mit der gefundenen {@link Watchlist}, oder leer, falls nicht vorhanden
     */
    Optional<Watchlist> findByNutzerId(Long nutzerId);

    /**
     * Sucht eine Watchlist anhand der Nutzer-ID und lädt die zugehörigen Wertpapiere mit.
     *
     * @param nutzerId Die ID des Nutzers
     * @return Optional mit der gefundenen {@link Watchlist} inklusive Wertpapiere, oder leer, falls nicht vorhanden
     */
    @Query("SELECT w FROM Watchlist w LEFT JOIN FETCH w.wertpapiere WHERE w.nutzer.id = :nutzerId")
    Optional<Watchlist> findByNutzerIdWithWertpapiere(@Param("nutzerId") Long nutzerId);
}