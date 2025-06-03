package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Aktie;

import java.util.Optional;

@Repository
public interface AktieRepository extends JpaRepository<Aktie, Long> {
    Optional<Aktie> findBySymbolIgnoreCase(String symbol);
}