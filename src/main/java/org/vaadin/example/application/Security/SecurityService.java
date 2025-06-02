package org.vaadin.example.application.Security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.NutzerService;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityService {
    private static final String LOGOUT_SUCCESS_URL = "/";

    @Autowired
    private NutzerService nutzerService;

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

    public void logout(){
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null);
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
                    .anyMatch(authority -> authority.equals("ROLE_ADMIN") || authority.equals("ADMIN"));
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

                // Die laufende Sitzung beenden und den Benutzer neu anmelden
                // Entweder mit Logout oder durch Aktualisierung der Authentifizierung

                // Variante 1: Vollständiger Logout und Weiterleitung zur Login-Seite
                // Dies ist die sicherste Methode, erfordert aber eine erneute Anmeldung
                logout();
                return true;

            }
        }
        return false;
    }
}
