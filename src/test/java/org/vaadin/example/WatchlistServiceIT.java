package org.vaadin.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.NutzerRepository;
import org.vaadin.example.application.repositories.WatchlistRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.vaadin.example.application.services.WatchlistService;

/**
 * Integrationstests für die WatchlistService-Klasse.
 * Hier wird überprüft, ob die Geschäftslogik rund um Watchlists korrekt funktioniert,
 * insbesondere das Anlegen, Abfragen und Verwalten von Wertpapieren in einer Watchlist.
 */
public class WatchlistServiceIT {

    // Mock-Objekte für Repositories, die von WatchlistService genutzt werden
    @Mock
    private NutzerRepository nutzerRepository;

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private WertpapierRepository wertpapierRepository;

    // Die Klasse unter Test, in die die Mock-Objekte injiziert werden
    @InjectMocks
    private WatchlistService watchlistService;

    // Beispielnutzer und Wertpapier, die in den Tests verwendet werden
    private Nutzer testNutzer;
    private Wertpapier testWertpapier;

    /**
     * Setup-Methode, die vor jedem Test ausgeführt wird.
     * Initialisiert Mockito-Mocks und legt Beispielobjekte an.
     */
    @BeforeEach
    public void setup() {
        // Mockito-Annotationen initialisieren (Mock-Objekte aktivieren)
        MockitoAnnotations.openMocks(this);

        // Mock eines Nutzers, der in Tests verwendet wird
        testNutzer = mock(Nutzer.class);
        when(testNutzer.getId()).thenReturn(1L);
        when(testNutzer.getUsernameOrEmpty()).thenReturn("MaxMustermann");
        when(testNutzer.getVornameOrEmpty()).thenReturn("Max");
        when(testNutzer.getNachnameOrEmpty()).thenReturn("Mustermann");

        // Beispiel-Wertpapier vom Typ Aktie erstellen
        testWertpapier = new Aktie();
        setId(testWertpapier, 1L);
        testWertpapier.setSymbol("ABC");
    }

    /**
     * Hilfsmethode, um die private ID-Feld eines Objekts via Reflection zu setzen.
     * Wird benötigt, da ID oft private und final ist und keinen Setter hat.
     *
     * @param entity Das Objekt, bei dem die ID gesetzt wird
     * @param id     Die zu setzende ID
     */
    private void setId(Object entity, Long id) {
        try {
            java.lang.reflect.Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Setzen der ID via Reflection", e);
        }
    }

    /**
     * Testet das erfolgreiche Erstellen einer Watchlist für einen existierenden Nutzer.
     * Erwartet wird, dass die Watchlist gespeichert wird.
     */
    @Test
    public void createWatchlistForUser_Success() {
        when(nutzerRepository.findById(1L)).thenReturn(Optional.of(testNutzer));
        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.empty());

        watchlistService.createWatchlistForUser(1L, "Meine Watchlist");

        verify(watchlistRepository, times(1)).save(any(Watchlist.class));
    }

    /**
     * Testet, dass beim Versuch eine Watchlist für einen nicht vorhandenen Nutzer zu erstellen,
     * eine NoSuchElementException geworfen wird.
     */
    @Test
    public void createWatchlistForUser_NutzerNichtVorhanden() {
        when(nutzerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> watchlistService.createWatchlistForUser(1L, "Meine Watchlist"));
    }

    /**
     * Testet, dass eine IllegalArgumentException geworfen wird,
     * wenn für einen Nutzer bereits eine Watchlist existiert und versucht wird,
     * eine weitere anzulegen.
     */
    @Test
    public void createWatchlistForUser_WatchlistSchonVorhanden() {
        when(nutzerRepository.findById(1L)).thenReturn(Optional.of(testNutzer));
        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.of(new Watchlist()));

        assertThrows(IllegalArgumentException.class, () -> watchlistService.createWatchlistForUser(1L, "Meine Watchlist"));
    }

    /**
     * Testet das erfolgreiche Abrufen einer Watchlist für einen Nutzer.
     * Erwartet wird, dass Optional mit Watchlist gefüllt ist.
     */
    @Test
    public void getWatchlistForUser_Vorhanden() {
        Watchlist watchlist = new Watchlist();
        setId(watchlist, 1L);
        watchlist.setNutzer(testNutzer);

        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.of(watchlist));

        Optional<Watchlist> result = watchlistService.getWatchlistForUser(1L);
        assertTrue(result.isPresent());
        assertEquals(watchlist, result.get());
    }

    /**
     * Testet das Abrufen einer Watchlist, wenn keine vorhanden ist.
     * Erwartet wird ein leeres Optional.
     */
    @Test
    public void getWatchlistForUser_NichtVorhanden() {
        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.empty());

        Optional<Watchlist> result = watchlistService.getWatchlistForUser(1L);
        assertTrue(result.isEmpty());
    }

    /**
     * Testet das erfolgreiche Hinzufügen eines Wertpapiers zu einer Watchlist eines Nutzers.
     * Erwartet wird, dass das Wertpapier in der Watchlist-Liste enthalten ist
     * und dass die Watchlist gespeichert wird.
     */
    @Test
    public void addWertpapierToUserWatchlist_Success() {
        Watchlist watchlist = new Watchlist();
        setId(watchlist, 1L);
        watchlist.setNutzer(testNutzer);
        // Wertpapierliste ist initial leer

        when(nutzerRepository.findById(1L)).thenReturn(Optional.of(testNutzer));
        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.of(watchlist));
        when(wertpapierRepository.findById(1L)).thenReturn(Optional.of(testWertpapier));

        watchlistService.addWertpapierToUserWatchlist(1L, 1L);

        // Prüfen, ob Wertpapier zur Liste hinzugefügt wurde
        assertTrue(watchlist.getWertpapiere().contains(testWertpapier));
        verify(watchlistRepository, times(1)).save(watchlist);
    }

    /**
     * Testet verschiedene Fehlerfälle beim Hinzufügen eines Wertpapiers:
     * - Nutzer nicht vorhanden
     * - Watchlist nicht vorhanden
     * - Wertpapier nicht vorhanden
     * Erwartet werden entsprechende Exceptions.
     */
    @Test
    public void addWertpapierToUserWatchlist_NutzerOderWertpapierNichtVorhanden() {
        when(nutzerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> watchlistService.addWertpapierToUserWatchlist(1L, 1L));

        when(nutzerRepository.findById(1L)).thenReturn(Optional.of(testNutzer));
        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> watchlistService.addWertpapierToUserWatchlist(1L, 1L));

        when(wertpapierRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> watchlistService.addWertpapierToUserWatchlist(1L, 1L));
    }

    /**
     * Testet, dass eine Exception geworfen wird, wenn versucht wird,
     * ein Wertpapier hinzuzufügen, das bereits in der Watchlist enthalten ist.
     */
    @Test
    public void addWertpapierToUserWatchlist_WertpapierSchonVorhanden() {
        Watchlist watchlist = new Watchlist();
        setId(watchlist, 1L);
        watchlist.setNutzer(testNutzer);
        watchlist.getWertpapiere().add(testWertpapier);

        when(nutzerRepository.findById(1L)).thenReturn(Optional.of(testNutzer));
        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.of(watchlist));
        when(wertpapierRepository.findById(1L)).thenReturn(Optional.of(testWertpapier));

        assertThrows(IllegalArgumentException.class, () -> watchlistService.addWertpapierToUserWatchlist(1L, 1L));
    }

    /**
     * Testet das erfolgreiche Entfernen eines Wertpapiers aus der Watchlist.
     * Erwartet wird, dass das Wertpapier nicht mehr in der Liste enthalten ist
     * und die Watchlist gespeichert wird.
     */
    @Test
    public void removeWertpapierFromUserWatchlist_Success() {
        Watchlist watchlist = new Watchlist();
        setId(watchlist, 1L);
        watchlist.setNutzer(testNutzer);
        watchlist.getWertpapiere().add(testWertpapier);

        when(nutzerRepository.findById(1L)).thenReturn(Optional.of(testNutzer));
        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.of(watchlist));

        watchlistService.removeWertpapierFromUserWatchlist(1L, 1L);

        assertFalse(watchlist.getWertpapiere().contains(testWertpapier));
        verify(watchlistRepository, times(1)).save(watchlist);
    }

    /**
     * Testet Fehlerfälle beim Entfernen:
     * - Watchlist nicht vorhanden
     * - Wertpapier nicht in Watchlist enthalten
     * Erwartet werden Exceptions.
     */
    @Test
    public void removeWertpapierFromUserWatchlist_KeineWatchlistOderNichtEnthalten() {
        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> watchlistService.removeWertpapierFromUserWatchlist(1L, 1L));

        Watchlist watchlist = new Watchlist();
        setId(watchlist, 1L);
        watchlist.setNutzer(testNutzer);
        // Wertpapierliste bleibt leer

        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.of(watchlist));
        assertThrows(IllegalArgumentException.class, () -> watchlistService.removeWertpapierFromUserWatchlist(1L, 1L));
    }

    /**
     * Testet das Abrufen der Wertpapiere aus der Watchlist eines Nutzers.
     * Erwartet wird eine Liste mit den gespeicherten Wertpapieren.
     */
    @Test
    public void getWertpapiereInUserWatchlist_Success() {
        Watchlist watchlist = new Watchlist();
        setId(watchlist, 1L);
        watchlist.setNutzer(testNutzer);
        watchlist.getWertpapiere().add(testWertpapier);

        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.of(watchlist));

        List<Wertpapier> wertpapiere = watchlistService.getWertpapiereInUserWatchlist(1L);
        assertEquals(1, wertpapiere.size());
        assertTrue(wertpapiere.contains(testWertpapier));
    }

    /**
     * Testet das Abrufen der Wertpapiere, wenn keine Watchlist vorhanden ist.
     * Erwartet wird, dass eine Exception geworfen wird.
     */
    @Test
    public void getWertpapiereInUserWatchlist_KeineWatchlist() {
        when(watchlistRepository.findByNutzerId(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> watchlistService.getWertpapiereInUserWatchlist(1L));
    }
}
