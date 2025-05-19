package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Nutzer;

@Repository
public interface NutzerRepository extends JpaRepository<Nutzer, Long> {
    Nutzer findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
