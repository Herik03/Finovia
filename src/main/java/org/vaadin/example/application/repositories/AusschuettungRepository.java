package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.application.classes.Ausschuettung;

public interface AusschuettungRepository extends JpaRepository<Ausschuettung, Long> {
}
