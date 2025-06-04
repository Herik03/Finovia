package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Anleihe;


@Repository
public interface AnleiheRepository extends JpaRepository<Anleihe, Long> {
    Anleihe findBySymbol(String symbol);
}
