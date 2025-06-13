package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Aktie;

/**
 * Repository-Interface für {@link Aktie}-Entitäten.
 *
 * Bietet CRUD-Operationen sowie eine benutzerdefinierte Methode zur Suche
 * einer Aktie anhand ihres Symbols.
 *
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Batuhan Güvercin
 */
@Repository
public interface AktieRepository extends JpaRepository<Aktie, Long> {
    /**
     * Sucht eine Aktie anhand ihres Symbols.
     *
     * @param symbol Das Symbol der Aktie (z. B. ISIN oder Ticker)
     * @return Die gefundene {@link Aktie} oder null, falls keine existiert
     */
    Aktie findBySymbol(String symbol);
}