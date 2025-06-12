package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Anleihe;

/**
 * Repository-Interface für {@link Anleihe}-Entitäten.
 *
 * Bietet CRUD-Operationen für Anleihen.
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Batuhan Güvercin
 */
@Repository
public interface AnleiheRepository extends JpaRepository<Anleihe, Long> {

}
