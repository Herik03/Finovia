package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.application.classes.Kurs;

public interface KursRepository extends JpaRepository<Kurs, Long> {
}
