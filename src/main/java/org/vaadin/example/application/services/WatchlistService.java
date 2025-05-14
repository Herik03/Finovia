package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Watchlist;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.repositories.NutzerRepository;
import org.vaadin.example.application.repositories.WatchlistRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service-Klasse für die Verwaltung von Watchlists.
 * Diese Klasse verwendet eine PostgreSQL-Datenbank für die Speicherung von Watchlists.
 */
@Service
public class WatchlistService {
    private final WatchlistRepository watchlistRepository;
    private final NutzerRepository nutzerRepository;
    private final WertpapierRepository wertpapierRepository;
    private final NutzerService nutzerService;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository, 
                           NutzerRepository nutzerRepository,
                           WertpapierRepository wertpapierRepository,
                           NutzerService nutzerService) {
        this.watchlistRepository = watchlistRepository;
        this.nutzerRepository = nutzerRepository;
        this.wertpapierRepository = wertpapierRepository;
        this.nutzerService = nutzerService;
    }

    /**
     * Gibt die Watchlist eines bestimmten Nutzers zurück
     * 
     * @param nutzerId ID des Nutzers
     * @return Die Watchlist des Nutzers oder null, wenn keine Watchlist existiert
     */
    public Watchlist getWatchlistByNutzerId(int nutzerId) {
        Optional<Watchlist> watchlistOpt = watchlistRepository.findByBesitzerId(nutzerId);
        return watchlistOpt.orElse(null);
    }

    /**
     * Gibt die Watchlist eines bestimmten Nutzers zurück
     * 
     * @param username Benutzername des Nutzers
     * @return Die Watchlist des Nutzers oder null, wenn keine Watchlist existiert
     */
    public Watchlist getWatchlistByUsername(String username) {
        Nutzer nutzer = nutzerService.getNutzerByUsername(username);
        if (nutzer != null) {
            Optional<Watchlist> watchlistOpt = watchlistRepository.findByBesitzer(nutzer);
            return watchlistOpt.orElse(null);
        }
        return null;
    }

    /**
     * Erstellt eine neue Watchlist für einen Nutzer
     * 
     * @param nutzer Der Nutzer, für den die Watchlist erstellt werden soll
     * @param name Der Name der Watchlist
     * @return Die erstellte Watchlist
     */
    public Watchlist createWatchlist(Nutzer nutzer, String name) {
        Watchlist watchlist = new Watchlist(name, nutzer);
        nutzer.setWatchlist(watchlist);
        watchlist = watchlistRepository.save(watchlist);
        nutzerRepository.save(nutzer);
        return watchlist;
    }

    /**
     * Fügt ein Wertpapier zur Watchlist eines Nutzers hinzu
     * 
     * @param nutzerId ID des Nutzers
     * @param wertpapier Das hinzuzufügende Wertpapier
     * @return true wenn das Wertpapier hinzugefügt wurde, false wenn keine Watchlist existiert
     */
    public boolean addWertpapierToWatchlist(int nutzerId, Wertpapier wertpapier) {
        Watchlist watchlist = getWatchlistByNutzerId(nutzerId);
        if (watchlist != null) {
            // Speichere das Wertpapier, falls es noch nicht in der Datenbank ist
            wertpapier = wertpapierRepository.save(wertpapier);

            watchlist.wertpapierHinzufuegen(wertpapier);
            watchlistRepository.save(watchlist);
            return true;
        }
        return false;
    }

    /**
     * Fügt ein Wertpapier zur Watchlist eines Nutzers hinzu
     * 
     * @param username Benutzername des Nutzers
     * @param wertpapier Das hinzuzufügende Wertpapier
     * @return true wenn das Wertpapier hinzugefügt wurde, false wenn keine Watchlist existiert
     */
    public boolean addWertpapierToWatchlist(String username, Wertpapier wertpapier) {
        Watchlist watchlist = getWatchlistByUsername(username);
        if (watchlist != null) {
            // Speichere das Wertpapier, falls es noch nicht in der Datenbank ist
            wertpapier = wertpapierRepository.save(wertpapier);

            watchlist.wertpapierHinzufuegen(wertpapier);
            watchlistRepository.save(watchlist);
            return true;
        }
        return false;
    }

    /**
     * Entfernt ein Wertpapier aus der Watchlist eines Nutzers
     * 
     * @param nutzerId ID des Nutzers
     * @param wertpapier Das zu entfernende Wertpapier
     * @return true wenn das Wertpapier entfernt wurde, false wenn keine Watchlist existiert oder das Wertpapier nicht in der Watchlist ist
     */
    public boolean removeWertpapierFromWatchlist(int nutzerId, Wertpapier wertpapier) {
        Watchlist watchlist = getWatchlistByNutzerId(nutzerId);
        if (watchlist != null) {
            boolean removed = watchlist.wertpapierEntfernen(wertpapier);
            if (removed) {
                watchlistRepository.save(watchlist);
            }
            return removed;
        }
        return false;
    }

    /**
     * Entfernt ein Wertpapier aus der Watchlist eines Nutzers
     * 
     * @param username Benutzername des Nutzers
     * @param wertpapier Das zu entfernende Wertpapier
     * @return true wenn das Wertpapier entfernt wurde, false wenn keine Watchlist existiert oder das Wertpapier nicht in der Watchlist ist
     */
    public boolean removeWertpapierFromWatchlist(String username, Wertpapier wertpapier) {
        Watchlist watchlist = getWatchlistByUsername(username);
        if (watchlist != null) {
            boolean removed = watchlist.wertpapierEntfernen(wertpapier);
            if (removed) {
                watchlistRepository.save(watchlist);
            }
            return removed;
        }
        return false;
    }

    /**
     * Gibt alle Wertpapiere in der Watchlist eines Nutzers zurück
     * 
     * @param nutzerId ID des Nutzers
     * @return Liste der Wertpapiere in der Watchlist oder eine leere Liste, wenn keine Watchlist existiert
     */
    public List<Wertpapier> getWertpapiereInWatchlist(int nutzerId) {
        Watchlist watchlist = getWatchlistByNutzerId(nutzerId);
        if (watchlist != null) {
            return watchlist.getWertpapiere();
        }
        return new ArrayList<>();
    }

    /**
     * Gibt alle Wertpapiere in der Watchlist eines Nutzers zurück
     * 
     * @param username Benutzername des Nutzers
     * @return Liste der Wertpapiere in der Watchlist oder eine leere Liste, wenn keine Watchlist existiert
     */
    public List<Wertpapier> getWertpapiereInWatchlist(String username) {
        Watchlist watchlist = getWatchlistByUsername(username);
        if (watchlist != null) {
            return watchlist.getWertpapiere();
        }
        return new ArrayList<>();
    }
}
