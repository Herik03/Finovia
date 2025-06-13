package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.ETF;

import java.util.Optional;

/**
 * Repository-Interface für {@link ETF}-Entitäten.
 *
 * Bietet CRUD-Operationen für ETFs.
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Batuhan Güvercin
 */
@Repository
public interface ETFRepository extends JpaRepository<ETF, Long> {
    Optional<ETF> findBySymbolIgnoreCase(String symbol);
}