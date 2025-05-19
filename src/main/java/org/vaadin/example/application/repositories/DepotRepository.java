package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;

import java.util.List;
import java.util.Optional;

public interface DepotRepository extends JpaRepository<Depot, Long> {
    List<Depot> findByBesitzerId(Long nutzerId);
    List<Depot> findByBesitzer(Nutzer nutzer);
    Optional<Depot> findByNameAndBesitzer(String name, Nutzer besitzer);
}
