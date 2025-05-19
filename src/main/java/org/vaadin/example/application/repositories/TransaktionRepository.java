package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.application.classes.Transaktion;

public interface TransaktionRepository extends JpaRepository<Transaktion, Long> {
}
