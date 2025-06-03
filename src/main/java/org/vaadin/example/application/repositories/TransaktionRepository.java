package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Transaktion;

import java.util.List;

@Repository
public interface TransaktionRepository extends JpaRepository<Transaktion, Long> {

    @Query("SELECT t FROM Transaktion t WHERE t.wertpapier IN " +
           "(SELECT dw.wertpapier FROM DepotWertpapier dw WHERE dw.depot IN " +
           "(SELECT d FROM Depot d WHERE d.besitzer.username = :username))")
    List<Transaktion> findTransaktionenByNutzerUsername(@Param("username") String username);
}
