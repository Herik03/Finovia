package org.vaadin.example.application.Security;

import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.NutzerService;
import java.util.ArrayList;
import java.util.List;

/**
 * Service zur Verwaltung von Sicherheitsoperationen wie Authentifizierung und Autorisierung.
 * Bietet Methoden zum Abrufen des aktuell angemeldeten Benutzers, Überprüfen von Rollen
 * und Entfernen von Admin-Rechten.
 */
@Component
public class SecurityService {
    private static final String LOGOUT_SUCCESS_URL = "/";

    @Autowired
    private NutzerService nutzerService;

    /**
     * Gibt den aktuell angemeldeten Benutzer zurück.
     *
     * @return UserDetails des aktuell angemeldeten Benutzers oder null, wenn kein Benutzer angemeldet ist
     */
    public UserDetails getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            System.out.println("Aktuell angemeldeter Benutzer: " + userDetails.getUsername());
            return userDetails;
        }
        System.out.println("Kein Benutzer angemeldet oder Principal ist nicht vom Typ UserDetails");
        return null;
    }

    /**
     * Prüft, ob der aktuell angemeldete Benutzer Admin-Rechte hat.
     *
     * @return true, wenn der Benutzer Admin-Rechte hat, sonst false
     */
    public boolean isAdmin() {
        UserDetails userDetails = getAuthenticatedUser();
        if (userDetails != null) {
            return userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(authority -> authority.equals("ROLE_ADMIN"));
        }
        return false;
    }

    /**
     * Entfernt die Admin-Rolle vom aktuell angemeldeten Benutzer.
     *
     * @return true, wenn die Admin-Rolle erfolgreich entfernt wurde, sonst false
     */
    public boolean removeAdminRole() {
        UserDetails userDetails = getAuthenticatedUser();
        if (userDetails != null && isAdmin()) {
            String username = userDetails.getUsername();
            Nutzer nutzer = nutzerService.findByUsername(username);

            if (nutzer != null) {
                // Admin-Rolle aus der Datenbank entfernen
                List<String> roles = new ArrayList<>(nutzer.getRoles());
                roles.remove("ADMIN");
                nutzer.setRoles(roles);
                nutzerService.speichereNutzer(nutzer);
                logout();
                return true;

            }
        }
        return false;
    }
    /**
     * Meldet den aktuellen Benutzer ab und leitet zur Login-Seite weiter.
     */
    public void logout() {
        SecurityContextHolder.clearContext();
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
    }

}
