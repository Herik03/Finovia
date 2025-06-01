package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vaadin.example.application.classes.Wertpapier;
import java.util.List;
import java.util.Optional;

public interface WertpapierRepository extends JpaRepository<Wertpapier, Long> {
    Optional<Wertpapier> findByNameIgnoreCase(String symbol);
    Optional<Wertpapier> findBySymbol(String symbol);
    @Query("SELECT w FROM Wertpapier w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(w.symbol) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Wertpapier> searchByNameOrSymbol(@Param("keyword") String keyword);


}
