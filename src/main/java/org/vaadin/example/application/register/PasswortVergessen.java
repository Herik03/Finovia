package org.vaadin.example.application.register;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("passwortvergessen")
@PageTitle("PasswortVergessen")
@AnonymousAllowed
public class PasswortVergessen extends HorizontalLayout {
    private final TextField username = new TextField("Benutzername");
    private final PasswordField password = new PasswordField("Passwort");
    private final PasswordField confirmPassword = new PasswordField("Passwort bestätigen");
    private final Button sendButton = new Button("Senden");

    public PasswortVergessen() {

        addClassNames(LumoUtility.Display.FLEX, LumoUtility.JustifyContent.CENTER, LumoUtility.AlignItems.CENTER);
        setSizeFull();

        sendButton.addClickListener(event -> {
            if(password.getValue().equals(confirmPassword.getValue())==false){
                Notification.show("Passwörter stimmen nicht überein");
            }
        });


        add(
                new H1("Registrierung"),
                username,
                password,
                confirmPassword,
                sendButton

                );
        addClassName("passwort-vergessen-view");
    }

}
