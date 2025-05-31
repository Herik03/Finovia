package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.DepotWertpapier;
import org.vaadin.example.application.classes.Wertpapier;

import java.util.Optional;

public interface DepotWertpapierRepository extends JpaRepository<DepotWertpapier, Long> {
    Optional<DepotWertpapier> findByDepotAndWertpapier(Depot depot, Wertpapier wertpapier);
}
