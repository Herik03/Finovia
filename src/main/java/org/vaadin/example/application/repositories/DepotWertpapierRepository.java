package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.DepotWertpapier;
import org.vaadin.example.application.classes.Wertpapier;

import java.util.Optional;

/**
 * Repository-Interface für {@link DepotWertpapier}-Entitäten.
 *
 * Bietet CRUD-Operationen sowie eine benutzerdefinierte Methode zur Suche
 * eines DepotWertpapiers anhand eines Depots und eines Wertpapiers.
 *
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Jan Schwarzer
 */
@Repository
public interface DepotWertpapierRepository extends JpaRepository<DepotWertpapier, Long> {
    /**
     * Sucht ein {@link DepotWertpapier} anhand des zugehörigen {@link Depot} und {@link Wertpapier}.
     *
     * @param depot Das Depot, zu dem das Wertpapier gehört
     * @param wertpapier Das Wertpapier, das im Depot gehalten wird
     * @return Optional mit dem gefundenen {@link DepotWertpapier}, oder leer, falls nicht vorhanden
     */
    Optional<DepotWertpapier> findByDepotAndWertpapier(Depot depot, Wertpapier wertpapier);
}