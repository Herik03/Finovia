package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Wertpapier;
import java.util.List;
import java.util.Optional;


/**
 * Repository-Interface für {@link Wertpapier}-Entitäten.
 *
 * Bietet CRUD-Operationen sowie benutzerdefinierte Methoden zur Suche
 * von Wertpapieren anhand vom Namen oder Symbol, inklusive unscharfer Suche.
 *
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Henrik Dollmann, Jan Schwarzer
 */
@Repository
public interface WertpapierRepository extends JpaRepository<Wertpapier, Long> {
    /**
     * Sucht ein Wertpapier anhand des Namens (Groß-/Kleinschreibung wird ignoriert).
     *
     * @param symbol Der Name des Wertpapiers
     * @return Optional mit dem gefundenen {@link Wertpapier}, oder leer, falls nicht vorhanden
     */
    Optional<Wertpapier> findByNameIgnoreCase(String symbol);

    /**
     * Sucht ein Wertpapier anhand des Symbols.
     *
     * @param symbol Das Symbol des Wertpapiers
     * @return Optional mit dem gefundenen {@link Wertpapier}, oder leer, falls nicht vorhanden
     */
    Optional<Wertpapier> findBySymbol(String symbol);

    /**
     * Sucht Wertpapiere, deren Name oder Symbol das angegebene Stichwort (unscharf) enthält.
     *
     * @param keyword Das Suchstichwort für Name oder Symbol
     * @return Liste der passenden {@link Wertpapier}-Objekte
     */
    @Query("SELECT w FROM Wertpapier w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(w.symbol) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Wertpapier> searchByNameOrSymbol(@Param("keyword") String keyword);
}