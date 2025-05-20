package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.application.classes.Wertpapier;

import java.util.Optional;

public interface WertpapierRepository extends JpaRepository<Wertpapier, Long> {
    Optional<Wertpapier> findByNameIgnoreCase(String symbol);
    // Hier können benutzerdefinierte Abfragen hinzugefügt werden, wenn nötig
    // Zum Beispiel: List<Wertpapier> findByName(String name);
}
