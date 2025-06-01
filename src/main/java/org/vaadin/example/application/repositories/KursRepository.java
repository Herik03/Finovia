package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.application.classes.Kurs;

import java.util.List;

public interface KursRepository extends JpaRepository<Kurs, Long> {
    List<Kurs> findByWertpapierSymbolOrderByDatumAsc(String symbol);
    List<Kurs> findByWertpapier_SymbolOrderByDatumAsc(String symbol);

}
