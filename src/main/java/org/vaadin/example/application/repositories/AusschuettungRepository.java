package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Ausschuettung;

/**
 * Repository für {@link Ausschuettung}-Entitäten.
 *
 * Bietet CRUD-Operation für Ausschuettungen.
 *
 * @author Sören Heß
 */
@Repository
public interface AusschuettungRepository extends JpaRepository<Ausschuettung, Long> {
}
