package org.vaadin.example.application.Security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component

public class SecurtyService {
public void logout(){
    UI.getCurrent().getPage().setLocation("/");
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
}
}
