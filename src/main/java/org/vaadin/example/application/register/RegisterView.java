package org.vaadin.example.application.register;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("register")
@PageTitle("Registrieren")
@AnonymousAllowed // Diese Annotation erlaubt anonymen Zugriff
public class RegisterView extends FormLayout {

    private final TextField username = new TextField("Benutzername");
    private final PasswordField password = new PasswordField("Passwort");
    private final PasswordField confirmPassword = new PasswordField("Passwort bestätigen");
    private final Button registerButton = new Button("Registrieren");

    public RegisterView() {
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.JustifyContent.CENTER, LumoUtility.AlignItems.CENTER);
        setSizeFull();

        add(
                new H1("Registrierung"),
                username,
                password,
                confirmPassword,
                registerButton
        );

        registerButton.addClickListener(event -> registerUser());
    }

    private void registerUser() {
        if (password.getValue().equals(confirmPassword.getValue())) {
            String enteredUsername = username.getValue();
            String enteredPassword = password.getValue();

            //Datenbank registrieren

            boolean registrationSuccessful = true; // Platzhalter für erfolgreiche Registrierung

            if (registrationSuccessful) {
                Notification.show("Registrierung erfolgreich!");
                UI.getCurrent().navigate("login"); // Nach der Registrierung zur Login-Seite weiterleiten
            } else {
                Notification.show("Registrierung fehlgeschlagen. Benutzername möglicherweise bereits vergeben.");
            }
        } else {
            Notification.show("Passwörter stimmen nicht überein.");
        }
    }
}