package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Aktie;

@Repository
public interface AktieRepository extends JpaRepository<Aktie, Long> {
    Aktie findBySymbol(String symbol);
}