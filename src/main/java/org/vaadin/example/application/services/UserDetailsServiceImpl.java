package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.repositories.NutzerRepository;

import java.util.stream.Collectors;

/**
 * Implementierung des {@link UserDetailsService} für die Authentifizierung.
 *
 * Diese Service-Klasse lädt Benutzerdetails aus der Datenbank anhand des Benutzernamens.
 * Sie wird von Spring Security für die Authentifizierung verwendet.
 *
 * @author Sören Heß
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final NutzerRepository nutzerRepository;

    /**
     * Konstruktor für UserDetailsServiceImpl.
     *
     * @param nutzerRepository Repository zur Abfrage von Nutzer-Entitäten
     */
    @Autowired
    public UserDetailsServiceImpl(NutzerRepository nutzerRepository) {
        this.nutzerRepository = nutzerRepository;
    }

    /**
     * Lädt die Benutzerdetails anhand des Benutzernamens.
     *
     * @param username Der Benutzername
     * @return UserDetails-Objekt für die Authentifizierung
     * @throws UsernameNotFoundException wenn der Benutzer nicht gefunden wurde
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Nutzer nutzer = nutzerRepository.findByUsername(username);
        if (nutzer == null) {
            throw new UsernameNotFoundException("Benutzer nicht gefunden: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                nutzer.getUsername(),
                nutzer.getPasswort(),
                true, true, true, true,
                nutzer.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList())
        );
    }
}
