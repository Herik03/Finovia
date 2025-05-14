package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Supportanfrage;
import org.vaadin.example.application.classes.Watchlist;
import org.vaadin.example.application.repositories.NutzerRepository;
import org.vaadin.example.application.repositories.WatchlistRepository;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * Service-Klasse für die Verwaltung von Nutzern.
 * <p>
 * Diese Klasse stellt Methoden zum Erstellen, Lesen, Aktualisieren und Löschen von Nutzern
 * zur Verfügung und verwendet dafür eine PostgreSQL-Datenbank.
 */
@Service
public class NutzerService {

    private final NutzerRepository nutzerRepository;
    private final WatchlistRepository watchlistRepository;

    @Autowired
    public NutzerService(NutzerRepository nutzerRepository, WatchlistRepository watchlistRepository) {
        this.nutzerRepository = nutzerRepository;
        this.watchlistRepository = watchlistRepository;
    }

    /**
     * Initialisiert den Service mit Demo-Daten, falls die Datenbank leer ist.
     */
    @PostConstruct
    public void init() {
        if (nutzerRepository.count() == 0) {
            // Demo-Nutzer erstellen
            Nutzer demo = new Nutzer("Max", "Mustermann", "max@example.com", "password123", "maxmuster");

            // Watchlist für den Demo-Nutzer
            Watchlist watchlist = new Watchlist("Demo-Watchlist", demo);
            watchlist.setName("Meine Watchlist");
            demo.setWatchlist(watchlist);

            // Demo-Depot erstellen
            demo.erstelleNeuesDepot("D001", "Hauptdepot");

            // Demo-Benachrichtigungen hinzufügen
            demo.aktualisieren("ANTWORT_AKTUALISIERT", 
                    new Supportanfrage("Wie kaufe ich Aktien?", demo));
            demo.aktualisieren("STATUS_GEÄNDERT",
                    new Supportanfrage("Wie kaufe ich Aktien?", demo));

            // Nutzer in die Datenbank einfügen
            nutzerRepository.save(demo);
        }
    }

    /**
     * Gibt einen Nutzer anhand seiner ID zurück.
     *
     * @param id Die ID des Nutzers
     * @return Der gefundene Nutzer oder null, wenn keiner gefunden wurde
     */
    public Nutzer getNutzerById(int id) {
        return nutzerRepository.findById(id).orElse(null);
    }

    /**
     * Gibt einen Nutzer anhand seines Benutzernamens zurück.
     *
     * @param username Der Benutzername des Nutzers
     * @return Der gefundene Nutzer oder null, wenn keiner gefunden wurde
     */
    public Nutzer getNutzerByUsername(String username) {
        return nutzerRepository.findByUsername(username).orElse(null);
    }

    /**
     * Gibt alle Nutzer zurück.
     *
     * @return Eine Liste aller Nutzer
     */
    public List<Nutzer> getAlleNutzer() {
        return nutzerRepository.findAll();
    }

    /**
     * Speichert einen Nutzer.
     * <p>
     * Wenn der Nutzer noch keine ID hat, wird eine neue vergeben.
     * Ansonsten wird der bestehende Nutzer aktualisiert.
     *
     * @param nutzer Der zu speichernde Nutzer
     */
    public void speichereNutzer(Nutzer nutzer) {
        nutzerRepository.save(nutzer);
    }

    /**
     * Setzt ein neues Passwort für einen Nutzer.
     * <p>
     * In einer echten Anwendung würde hier das Passwort gehasht werden.
     *
     * @param nutzer Der Nutzer
     * @param neuesPasswort Das neue Passwort
     */
    public void setzePasswort(Nutzer nutzer, String neuesPasswort) {
        // In einer echten Anwendung würde hier das Passwort gehasht werden
        nutzer.setPasswort(neuesPasswort);
        nutzerRepository.save(nutzer);
    }

    /**
     * Löscht einen Nutzer anhand seiner ID.
     *
     * @param id Die ID des zu löschenden Nutzers
     * @return true wenn der Nutzer gelöscht wurde, false wenn kein Nutzer gefunden wurde
     */
    public boolean loescheNutzer(int id) {
        Optional<Nutzer> nutzerOpt = nutzerRepository.findById(id);
        if (nutzerOpt.isPresent()) {
            nutzerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
