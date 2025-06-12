package org.vaadin.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.services.DepotService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit-Test für {@link DepotService}.
 * Erstellt von Batuhan Güvercin.
 *
 * Testet grundlegende Funktionen wie das Abrufen, Speichern und Löschen von Depots.
 */
public class DepotServiceIT {

    @Mock
    private DepotRepository depotRepository;

    @InjectMocks
    private DepotService depotService;

    /**
     * Initialisiert die Mockito-Mocks vor jedem Testlauf.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testet: Alle vorhandenen Depots werden korrekt zurückgegeben.
     */
    @Test
    public void testGetAllDepots_returnsAllDepots() {
        List<Depot> mockDepots = List.of(new Depot(), new Depot());
        when(depotRepository.findAll()).thenReturn(mockDepots);

        List<Depot> result = depotService.getAllDepots();

        assertEquals(2, result.size(), "Es sollten zwei Depots zurückgegeben werden");
        verify(depotRepository).findAll();
    }

    /**
     * Testet: Bei leerer Datenbasis wird eine leere Liste zurückgegeben.
     */
    @Test
    public void testGetAllDepots_returnsEmptyListIfNoneExist() {
        when(depotRepository.findAll()).thenReturn(Collections.emptyList());

        List<Depot> result = depotService.getAllDepots();

        assertTrue(result.isEmpty(), "Die Liste sollte leer sein");
        verify(depotRepository).findAll();
    }

    /**
     * Testet: Gibt eine Liste der zugeordneten Depots zurück, wenn vorhanden.
     */
    @Test
    public void testGetDepotsByNutzerId_returnsDepots() {
        Long nutzerId = 1L;
        List<Depot> mockDepots = List.of(new Depot());
        when(depotRepository.findByBesitzerId(nutzerId)).thenReturn(mockDepots);

        List<Depot> result = depotService.getDepotsByNutzerId(nutzerId);

        assertEquals(1, result.size(), "Ein Depot sollte zurückgegeben werden");
        verify(depotRepository).findByBesitzerId(nutzerId);
    }

    /**
     * Testet: Gibt eine leere Liste zurück, wenn keine Depots vorhanden sind.
     */
    @Test
    public void testGetDepotsByNutzerId_returnsEmptyListIfNone() {
        when(depotRepository.findByBesitzerId(anyLong())).thenReturn(Collections.emptyList());

        List<Depot> result = depotService.getDepotsByNutzerId(99L);

        assertTrue(result.isEmpty(), "Die Liste sollte leer sein");
        verify(depotRepository).findByBesitzerId(99L);
    }

    /**
     * Testet: Gibt ein konkretes Depot bei gültiger ID zurück.
     */
    @Test
    public void testGetDepotById_validId_returnsDepot() {
        Depot depot = new Depot();
        when(depotRepository.findById(1L)).thenReturn(Optional.of(depot));

        Depot result = depotService.getDepotById(1L);

        assertNotNull(result, "Depot sollte nicht null sein");
        assertEquals(depot, result, "Das zurückgegebene Depot sollte dem gespeicherten entsprechen");
        verify(depotRepository).findById(1L);
    }

    /**
     * Testet: Gibt null zurück, wenn die ID ungültig ist.
     */
    @Test
    public void testGetDepotById_invalidId_returnsNull() {
        when(depotRepository.findById(anyLong())).thenReturn(Optional.empty());

        Depot result = depotService.getDepotById(999L);

        assertNull(result, "Es sollte null zurückgegeben werden");
        verify(depotRepository).findById(999L);
    }

    /**
     * Testet: Speichert ein Depot korrekt über das Repository.
     */
    @Test
    public void testSaveDepot_savesDepot() {
        Depot depot = new Depot();

        depotService.saveDepot(depot);

        verify(depotRepository).save(depot);
    }

    /**
     * Testet: Löscht ein vorhandenes Depot anhand der ID.
     */
    @Test
    public void testDeleteDepot_validId_deletesDepot() {
        Long id = 1L;
        when(depotRepository.existsById(id)).thenReturn(true);

        depotService.deleteDepot(id);

        verify(depotRepository).deleteById(id);
    }

    /**
     * Testet: Depot wird **nicht** gelöscht, wenn es nicht existiert (ID ungültig).
     */
    @Test
    public void testDeleteDepot_invalidId_doesNothing() {
        Long id = 999L;
        when(depotRepository.existsById(id)).thenReturn(false);

        depotService.deleteDepot(id);

        verify(depotRepository, never()).deleteById(any());
    }
}
