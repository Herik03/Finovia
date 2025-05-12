package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Supportanfrage;
import org.vaadin.example.application.classes.Watchlist;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service-Klasse für die Verwaltung von Nutzern.
 * <p>
 * Diese Klasse stellt Methoden zum Erstellen, Lesen, Aktualisieren und Löschen von Nutzern
 * zur Verfügung. In einer produktiven Umgebung würde dieser Service mit einer Datenbank
 * kommunizieren.
 */
@Service
public class NutzerService {

    // In-Memory-Speicher für Nutzer (für Demo-Zwecke)
    private final Map<Integer, Nutzer> nutzerMap = new HashMap<>();
    private final Map<String, Nutzer> nutzerByUsername = new HashMap<>();
    private int nextId = 1;

    /**
     * Initialisiert den Service mit Demo-Daten.
     */
    @PostConstruct
    public void init() {
        // Demo-Nutzer erstellen
        Nutzer demo = new Nutzer("Max", "Mustermann", "max@example.com", "password123", "maxmuster");
        demo.setId(nextId++);
        
        // Watchlist für den Demo-Nutzer
        Watchlist watchlist = new Watchlist( "Demo-Watchlist", demo);
        watchlist.setName("Meine Watchlist");
        demo.setWatchlist(watchlist);
        
        // Demo-Depot erstellen
        demo.erstelleNeuesDepot("D001", "Hauptdepot");
        
        // Demo-Benachrichtigungen hinzufügen
        demo.aktualisieren("ANTWORT_AKTUALISIERT", 
                new Supportanfrage("Wie kaufe ich Aktien?", demo));
        demo.aktualisieren("STATUS_GEÄNDERT",
                new Supportanfrage("Wie kaufe ich Aktien?", demo));
        
        // Nutzer in die Maps einfügen
        nutzerMap.put(demo.getId(), demo);
        nutzerByUsername.put(demo.getUsername(), demo);
    }

    /**
     * Gibt einen Nutzer anhand seiner ID zurück.
     *
     * @param id Die ID des Nutzers
     * @return Der gefundene Nutzer oder null, wenn keiner gefunden wurde
     */
    public Nutzer getNutzerById(int id) {
        return nutzerMap.get(id);
    }

    /**
     * Gibt einen Nutzer anhand seines Benutzernamens zurück.
     *
     * @param username Der Benutzername des Nutzers
     * @return Der gefundene Nutzer oder null, wenn keiner gefunden wurde
     */
    public Nutzer getNutzerByUsername(String username) {
        return nutzerByUsername.get(username);
    }

    /**
     * Gibt alle Nutzer zurück.
     *
     * @return Eine Liste aller Nutzer
     */
    public List<Nutzer> getAlleNutzer() {
        return new ArrayList<>(nutzerMap.values());
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
        if (nutzer.getId() == 0) {
            // Neuer Nutzer
            nutzer.setId(nextId++);
        }
        
        // Nutzer in beide Maps einfügen/aktualisieren
        nutzerMap.put(nutzer.getId(), nutzer);
        nutzerByUsername.put(nutzer.getUsername(), nutzer);

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
    }

    /**
     * Löscht einen Nutzer anhand seiner ID.
     *
     * @param id Die ID des zu löschenden Nutzers
     * @return true wenn der Nutzer gelöscht wurde, false wenn kein Nutzer gefunden wurde
     */
    public boolean loescheNutzer(int id) {
        Nutzer nutzer = nutzerMap.get(id);
        if (nutzer != null) {
            nutzerByUsername.remove(nutzer.getUsername());
            nutzerMap.remove(id);
            return true;
        }
        return false;
    }
}