package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Nutzer;
import java.util.Optional;


import java.util.Optional;

@Repository
public interface NutzerRepository extends JpaRepository<Nutzer, Long> {
    Nutzer findByUsername(String username);
    Optional<Nutzer> findByEmail(String email);
    Optional<Nutzer> findByResetToken(String resetToken);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
