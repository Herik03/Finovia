package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Depot;

import java.util.List;

/**
 * Repository-Interface für {@link Depot}-Entitäten.
 *
 * Bietet CRUD-Operationen sowie eine benutzerdefinierte Methode zur Suche
 * von Depots anhand der Besitzer-ID.
 *
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Sören Heß
 */
@Repository
public interface DepotRepository extends JpaRepository<Depot, Long> {
    /**
     * Sucht alle Depots, die einem bestimmten Nutzer gehören.
     *
     * @param nutzerId Die ID des Besitzers (Nutzer)
     * @return Liste der zugehörigen {@link Depot}-Objekte
     */
    List<Depot> findByBesitzerId(Long nutzerId);
}
