package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Watchlist;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.repositories.NutzerRepository;
import org.vaadin.example.application.repositories.WatchlistRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service-Klasse für die Verwaltung von Watchlists im System.
 * Bietet Funktionen zum Erstellen, Abrufen und Aktualisieren von Watchlists.
 */
@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final NutzerRepository nutzerRepository;
    private final WertpapierRepository wertpapierRepository;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository, NutzerRepository nutzerRepository, WertpapierRepository wertpapierRepository) {
        this.watchlistRepository = watchlistRepository;
        this.nutzerRepository = nutzerRepository;
        this.wertpapierRepository = wertpapierRepository;
    }

    /**
     * Erstellt eine neue leere Watchlist für einen Nutzer.
     *
     * @param nutzerId Die ID des Nutzers
     * @param name Name der Watchlist
     * @return Die erstellte Watchlist
     * @throws IllegalArgumentException wenn der Nutzer nicht existiert
     */
    @Transactional
    public Watchlist createWatchlistForUser(Long nutzerId, String name) {
        Nutzer nutzer = nutzerRepository.findById(nutzerId)
                .orElseThrow(() -> new IllegalArgumentException("Nutzer mit ID " + nutzerId + " nicht gefunden"));

        if (nutzer.getWatchlist() != null) {
            throw new IllegalArgumentException("Nutzer hat bereits eine Watchlist");
        }

        Watchlist watchlist = new Watchlist(name);
        watchlist = watchlistRepository.save(watchlist);

        nutzer.setWatchlist(watchlist);
        watchlist.setNutzer(nutzer);

        nutzerRepository.save(nutzer);

        return watchlist;
    }

    /**
     * Ruft die Watchlist eines Nutzers ab.
     *
     * @param nutzerId Die ID des Nutzers
     * @return Optional mit der Watchlist des Nutzers oder leer, falls keine existiert
     */
    public Optional<Watchlist> getWatchlistForUser(Long nutzerId) {
        return watchlistRepository.findByNutzerId(nutzerId);
    }

    /**
     * Fügt ein Wertpapier zur Watchlist eines Nutzers hinzu.
     *
     * @param nutzerId Die ID des Nutzers
     * @param wertpapierId Die ID des hinzuzufügenden Wertpapiers
     * @return Die aktualisierte Watchlist
     * @throws IllegalArgumentException wenn der Nutzer oder das Wertpapier nicht existiert
     */
    @Transactional
    public Watchlist addWertpapierToUserWatchlist(Long nutzerId, Long wertpapierId) {
        Nutzer nutzer = nutzerRepository.findById(nutzerId)
                .orElseThrow(() -> new IllegalArgumentException("Nutzer mit ID " + nutzerId + " nicht gefunden"));

        Wertpapier wertpapier = wertpapierRepository.findById(wertpapierId)
                .orElseThrow(() -> new IllegalArgumentException("Wertpapier mit ID " + wertpapierId + " nicht gefunden"));

        Watchlist watchlist = nutzer.getWatchlist();
        if (watchlist == null) {
            // Erstelle automatisch eine Watchlist wenn keine existiert
            watchlist = createWatchlistForUser(nutzerId, "Meine Watchlist");
        }

        if (watchlist.addWertpapier(wertpapier)) {
            return watchlistRepository.save(watchlist);
        } else {
            throw new IllegalArgumentException("Wertpapier ist bereits in der Watchlist enthalten");
        }
    }

    /**
     * Entfernt ein Wertpapier aus der Watchlist eines Nutzers.
     *
     * @param nutzerId Die ID des Nutzers
     * @param wertpapierId Die ID des zu entfernenden Wertpapiers
     * @return Die aktualisierte Watchlist
     * @throws IllegalArgumentException wenn der Nutzer oder die Watchlist nicht existiert
     */
    @Transactional
    public Watchlist removeWertpapierFromUserWatchlist(Long nutzerId, Long wertpapierId) {
        Watchlist watchlist = watchlistRepository.findByNutzerId(nutzerId)
                .orElseThrow(() -> new IllegalArgumentException("Keine Watchlist für Nutzer mit ID " + nutzerId + " gefunden"));

        Wertpapier wertpapier = wertpapierRepository.findById(wertpapierId)
                .orElseThrow(() -> new IllegalArgumentException("Wertpapier mit ID " + wertpapierId + " nicht gefunden"));

        if (watchlist.removeWertpapier(wertpapier)) {
            return watchlistRepository.save(watchlist);
        } else {
            throw new IllegalArgumentException("Wertpapier ist nicht in der Watchlist enthalten");
        }
    }

    /**
     * Ruft alle Wertpapiere in der Watchlist eines Nutzers ab.
     *
     * @param nutzerId Die ID des Nutzers
     * @return Liste der Wertpapiere in der Watchlist
     * @throws IllegalArgumentException wenn der Nutzer keine Watchlist hat
     */
  /*  public List<Wertpapier> getWertpapiereInUserWatchlist(Long nutzerId) {
        Watchlist watchlist = watchlistRepository.findByNutzerId(nutzerId)
                .orElseThrow(() -> new IllegalArgumentException("Keine Watchlist für Nutzer mit ID " + nutzerId + " gefunden"));

        return watchlist.getWertpapiere();
    } */

    public List<Wertpapier> getWertpapiereInUserWatchlist(Long nutzerId) {
        Watchlist watchlist = watchlistRepository.findByNutzerIdWithWertpapiere(nutzerId)
                .orElseThrow(() -> new IllegalArgumentException("Keine Watchlist für Nutzer mit ID " + nutzerId + " gefunden"));

        return watchlist.getWertpapiere();
    }
}
