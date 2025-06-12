package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Transaktion;

import java.util.List;

/**
 * Repository-Interface für {@link Transaktion}-Entitäten.
 *
 * Bietet CRUD-Operationen sowie eine benutzerdefinierte Methode zur Suche
 * von Transaktionen anhand des Usernames des Depotbesitzers.
 *
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Ben Hübert
 */
@Repository
public interface TransaktionRepository extends JpaRepository<Transaktion, Long> {

    /**
     * Sucht alle Transaktionen, die mit Wertpapieren verknüpft sind,
     * die sich in Depots eines bestimmten Nutzers befinden.
     *
     * @param username Der Username des Depotbesitzers
     * @return Liste der zugehörigen {@link Transaktion}-Objekte
     */
    @Query("SELECT t FROM Transaktion t WHERE t.wertpapier IN " +
            "(SELECT dw.wertpapier FROM DepotWertpapier dw WHERE dw.depot IN " +
            "(SELECT d FROM Depot d WHERE d.besitzer.username = :username))")
    List<Transaktion> findTransaktionenByNutzerUsername(@Param("username") String username);
}
