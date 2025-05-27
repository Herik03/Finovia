package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Nutzer;

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
 * @version 1.0
 */
@Service
public class NutzerService {

    private final NutzerRepository nutzerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Konstruktor für den NutzerService.
     * 
     * @param nutzerRepository Repository für den Zugriff auf Nutzerdaten
     * @param passwordEncoder  Encoder für sichere Passwort-Verschlüsselung
     */
    @Autowired
    public NutzerService(NutzerRepository nutzerRepository, PasswordEncoder passwordEncoder) {
        this.nutzerRepository = nutzerRepository;
        this.passwordEncoder = passwordEncoder;
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
    public Nutzer getNutzerByUsername(String username) {
        return nutzerRepository.findByUsername(username);
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

    public Nutzer findByUsername(String username) {
        return nutzerRepository.findByUsername(username); // kann null sein
    }

    //TODO: Passwort ändern
}