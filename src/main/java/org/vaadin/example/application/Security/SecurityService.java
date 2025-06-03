package org.vaadin.example.application.Security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.Nutzer;

@Component
public class SecurityService {
    private static final String LOGOUT_SUCCESS_URL = "/";

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

    public Long getUserIdFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof Nutzer nutzer) {
            return nutzer.getId();
        }
        return null;
    }

}
