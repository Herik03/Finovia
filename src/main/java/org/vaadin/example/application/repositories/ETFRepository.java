package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.ETF;

@Repository
public interface ETFRepository extends JpaRepository<ETF, Long> {
    ETF findBySymbol(String symbol);
}