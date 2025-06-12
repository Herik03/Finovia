package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Kurs;

import java.util.List;

/**
 * Repository-Interface für {@link Kurs}-Entitäten.
 *
 * Bietet CRUD-Operationen sowie benutzerdefinierte Methoden zur Suche
 * von Kursen anhand des Wertpapier-Symbols, sortiert nach Datum aufsteigend.
 *
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Jan Schwarzer
 */
@Repository
public interface KursRepository extends JpaRepository<Kurs, Long> {
    /**
     * Sucht alle Kurse eines Wertpapiers anhand des Symbols und sortiert sie aufsteigend nach Datum.
     *
     * @param symbol Das Symbol des Wertpapiers
     * @return Liste der zugehörigen {@link Kurs}-Objekte, sortiert nach Datum (aufsteigend)
     */
    List<Kurs> findByWertpapierSymbolOrderByDatumAsc(String symbol);

    /**
     * Sucht alle Kurse eines Wertpapiers anhand des Symbols (per Property Path) und sortiert sie aufsteigend nach Datum.
     *
     * @param symbol Das Symbol des Wertpapiers
     * @return Liste der zugehörigen {@link Kurs}-Objekte, sortiert nach Datum (aufsteigend)
     */
    List<Kurs> findByWertpapier_SymbolOrderByDatumAsc(String symbol);

    // Diese Methode NEU hinzufügen:
    Kurs findTopByWertpapier_SymbolOrderByDatumDesc(String symbol);
}