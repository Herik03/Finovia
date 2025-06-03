package org.vaadin.example.application.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.DepotWertpapier;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Watchlist;
import org.vaadin.example.application.repositories.NutzerRepository;

import java.util.*;

/**
 * Service-Klasse für die Verwaltung von Nutzern im System.
 * 
 * Diese Klasse bietet Funktionen zur Authentifizierung von Nutzern, Speichern von Nutzerdaten, 
 * Prüfung der Existenz von Nutzernamen/E-Mails und weitere nutzerbezogene Operationen.
 * Sie dient als Schnittstelle zwischen der Repository-Schicht und der Anwendungslogik.
 * 
 * @author Sören
 * @version 1.1
 */
@Service
public class NutzerService {

    private final NutzerRepository nutzerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * Konstruktor für den NutzerService.
     * 
     * @param nutzerRepository Repository für den Zugriff auf Nutzerdaten
     * @param passwordEncoder  Encoder für sichere Passwort-Verschlüsselung
     */
    @Autowired
    public NutzerService(NutzerRepository nutzerRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.nutzerRepository = nutzerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /**
     * Speichert einen Nutzer in der Datenbank. Das Passwort wird vor dem Speichern
     * mit dem PasswordEncoder verschlüsselt.
     *
     * @param nutzer Der zu speichernde Nutzer
     */
    public void speichereNutzer(Nutzer nutzer) {
        //Passwort verschlüsseln
        String pw = nutzer.getPasswort();
        if (pw != null && !pw.startsWith("$2a$")) {
            // Nur wenn es noch kein Hash ist
            pw = passwordEncoder.encode(pw);
            nutzer.setPasswort(pw);
        }

        // Standardrolle setzen, falls keine angegeben
        if (nutzer.getRoles() == null || nutzer.getRoles().isEmpty()) {
            nutzer.setRoles(List.of("USER"));
        }

        nutzerRepository.save(nutzer);
    }

    /**
     * Sucht einen Nutzer anhand seiner ID.
     * 
     * @param id Die ID des gesuchten Nutzers
     * @return Optional mit dem gefundenen Nutzer oder leer, falls kein Nutzer gefunden wurde
     */
    public Optional<Nutzer> getNutzerById(Long id) {
        return nutzerRepository.findById(id);
    }

    /**
     * Sucht einen Nutzer anhand seines Benutzernamens.
     * 
     * @param username Der Benutzername des gesuchten Nutzers
     * @return Der gefundene Nutzer oder null, falls kein Nutzer mit diesem Benutzernamen existiert
     */
    @Transactional
    public Nutzer getNutzerByUsername(String username) {
        Nutzer nutzer = nutzerRepository.findByUsername(username);
        if (nutzer != null) {
            // Initialize the lazy-loaded collections to prevent LazyInitializationException
            nutzer.getDepots().size(); // Force initialization
        }
        return nutzer;
    }

    /**
     * Liefert eine Liste aller im System registrierten Nutzer.
     * 
     * @return Liste aller Nutzer
     */
    public List<Nutzer> getAllNutzer() {
        return nutzerRepository.findAll();
    }

    /**
     * Prüft, ob ein Benutzername bereits vergeben ist.
     * 
     * @param username Der zu prüfende Benutzername
     * @return true, wenn der Benutzername bereits existiert, sonst false
     */
    public boolean usernameExists(String username) {
        return nutzerRepository.existsByUsername(username);
    }

    /**
     * Prüft, ob eine E-Mail-Adresse bereits von einem Nutzer verwendet wird.
     * 
     * @param email Die zu prüfende E-Mail-Adresse
     * @return true, wenn die E-Mail bereits existiert, sonst false
     */
    public boolean emailExists(String email) {
        return nutzerRepository.existsByEmail(email);
    }

    /**
     * Löscht einen Nutzer aus dem System.
     * 
     * @param nutzer Der zu löschende Nutzer
     */
    public void loescheNutzer(Nutzer nutzer) {
        nutzerRepository.delete(nutzer);
    }
    /**
     * Löscht einen Nutzer und alle zugehörigen Daten vollständig aus dem System.
     *
     * Durch die Verwendung von CascadeType.ALL und orphanRemoval=true in der Nutzer-Klasse
     * werden alle abhängigen Objekte (Depots, Watchlist usw.) automatisch gelöscht.
     *
     * Diese Methode wurde verbessert, um sicherzustellen, dass die Löschung konsistent funktioniert,
     * indem sie explizit die Beziehungen zwischen Entitäten behandelt und Fehler abfängt.
     *
     * @param nutzerId Die ID des zu löschenden Nutzers
     * @return true, wenn der Nutzer erfolgreich gelöscht wurde, sonst false
     */
    @Transactional
    public boolean nutzerVollstaendigLoeschen(Long nutzerId) {
        try {
            Optional<Nutzer> nutzerOpt = nutzerRepository.findById(nutzerId);
            if (nutzerOpt.isPresent()) {
                Nutzer nutzer = nutzerOpt.get();

                // Explizite Behandlung der Watchlist
                if (nutzer.getWatchlist() != null) {
                    Watchlist watchlist = nutzer.getWatchlist();
                    // Beziehung zwischen Nutzer und Watchlist auflösen
                    watchlist.setNutzer(null);
                    nutzer.setWatchlist(null);
                }

                // Explizite Behandlung der Depots
                for (Depot depot : new ArrayList<>(nutzer.getDepots())) {
                    // Beziehung zwischen Depot und DepotWertpapiere auflösen
                    for (DepotWertpapier dw : new ArrayList<>(depot.getDepotWertpapiere())) {
                        dw.setDepot(null);
                    }
                    // Beziehung zwischen Nutzer und Depot auflösen
                    depot.setBesitzer(null);
                }

                // Nutzer und alle verknüpften Objekte (Depots, Watchlist usw.) werden durch
                // die JPA-Cascade-Konfiguration automatisch gelöscht
                nutzerRepository.delete(nutzer);

                // Überprüfen, ob der Nutzer wirklich gelöscht wurde
                if (!nutzerRepository.existsById(nutzerId)) {
                    return true;
                } else {
                    // Wenn der Nutzer noch existiert, versuchen wir es mit einer direkten Löschung über die ID
                    nutzerRepository.deleteById(nutzerId);
                    return !nutzerRepository.existsById(nutzerId);
                }
            }
            return false;
        } catch (Exception e) {
            // Fehler protokollieren
            System.err.println("Fehler beim Löschen des Nutzers mit ID " + nutzerId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Authentifiziert einen Nutzer anhand des Benutzernamens und Passworts.
     * <p>
     * Die Methode überprüft, ob ein Nutzer mit dem angegebenen Benutzernamen existiert
     * und ob das übergebene Passwort mit dem gespeicherten, verschlüsselten Passwort übereinstimmt.
     * 
     * @param username Der Benutzername
     * @param password Das eingegebene Passwort (im Klartext)
     * @return true, wenn die Authentifizierung erfolgreich war, sonst false
     */
    public boolean authenticate(String username, String password) {
        Nutzer nutzer = nutzerRepository.findByUsername(username);
        if (nutzer != null) {
            return passwordEncoder.matches(password, nutzer.getPasswort());
        }
        return false;
    }

    @Transactional
    public Nutzer findByUsername(String username) {
        Nutzer nutzer = nutzerRepository.findByUsername(username); // kann null sein
        if (nutzer != null) {
            // Initialize the lazy-loaded collections to prevent LazyInitializationException
            nutzer.getDepots().size(); // Force initialization
        }
        return nutzer;
    }

    public void sendPasswordResetEmail(String email) {
        Optional<Nutzer> nutzer = nutzerRepository.findByEmail(email);

        if(nutzer.isPresent()) {
            Nutzer foundNutzer = nutzer.get();
            String token = UUID.randomUUID().toString();
            foundNutzer.setResetToken(token);
            foundNutzer.setResetTokenExpiration(new Date(System.currentTimeMillis() + 3600000)); // 1 Stunde gültig
            nutzerRepository.save(foundNutzer);

            String resetLink = "localhost:8080/reset-password?token=" + token;
            emailService.sendEmail(
                email,
                "Passwort zurücksetzen",
                "Klicken Sie auf den folgenden Link, um Ihr Passwort zurückzusetzen: " + resetLink
            );
        }
    }

    public boolean resetPassword(String token, String password) {
        Optional<Nutzer> nutzerOptional = nutzerRepository.findByResetToken(token);
        if (nutzerOptional.isPresent()) {
            Nutzer nutzer = nutzerOptional.get();
            if (nutzer.getResetTokenExpiration().after(new Date())) {
                nutzer.setPasswort(passwordEncoder.encode(password));
                nutzer.setResetToken(null);
                nutzer.setResetTokenExpiration(null);
                nutzerRepository.save(nutzer);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Nutzer getAngemeldeterNutzer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Kein authentifizierter Nutzer gefunden");
        }

        String username = authentication.getName();
        Nutzer nutzer = nutzerRepository.findByUsername(username);

        if (nutzer == null) {
            throw new IllegalStateException("Kein Nutzer mit Benutzernamen '" + username + "' gefunden");
        }

        // Initialize the lazy-loaded collections to prevent LazyInitializationException
        nutzer.getDepots().size(); // Force initialization

        return nutzer;
    }

    /**
     * Fügt ein Depot zu einem Nutzer hinzu und speichert beide Entitäten.
     * Diese Methode ist transaktional, um LazyInitializationExceptions zu vermeiden.
     *
     * @param nutzerId Die ID des Nutzers
     * @param depot Das hinzuzufügende Depot
     * @return true, wenn das Depot erfolgreich hinzugefügt wurde, sonst false
     */
    @Transactional
    public boolean depotZuNutzerHinzufuegen(Long nutzerId, Depot depot) {
        try {
            Optional<Nutzer> nutzerOpt = nutzerRepository.findById(nutzerId);
            if (nutzerOpt.isPresent()) {
                Nutzer nutzer = nutzerOpt.get();

                // Beziehung von beiden Seiten setzen
                nutzer.depotHinzufuegen(depot);

                // Nutzer speichern (Cascade speichert auch das Depot)
                nutzerRepository.save(nutzer);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Fehler beim Hinzufügen des Depots zum Nutzer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
