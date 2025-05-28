package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.services.NutzerService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

import java.util.List;

@Route("reset-password")
@PageTitle("Passwort zurücksetzen")
@AnonymousAllowed
public class ResetPasswordView extends VerticalLayout implements BeforeEnterObserver {

    private final NutzerService nutzerService;
    private String token;

    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;
    private Button resetButton;

    @Autowired
    public ResetPasswordView(NutzerService nutzerService) {
        this.nutzerService = nutzerService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void initUI(BeforeEnterEvent event){
        addClassName("passwort-vergessen-view");

        Div container = new Div();
        container.addClassName("passwort-vergessen-container");

        H1 title = new H1("Passwort zurücksetzen");
        title.addClassName("passwort-vergessen-title");
        Span subtitle = new Span("Das Passwort muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben, einen Kleinbuchstaben, eine Zahl und ein Sonderzeichen enthalten.");
        subtitle.addClassName("passwort-vergessen-subtitle");

        Span newPasswordLabel = new Span("Neues Passwort:");
        newPasswordField = new PasswordField();
        newPasswordLabel.addClassName("passwort-vergessen-label");
        newPasswordField.setPlaceholder("Neues Passwort eingeben");
        newPasswordField.setRequiredIndicatorVisible(true);
        newPasswordField.setRequired(true);
        newPasswordField.setWidthFull();
        newPasswordField.addClassName("vergessen-input-field");

        Span confirmPasswordLabel = new Span("Passwort bestätigen:");
        confirmPasswordField = new PasswordField();
        confirmPasswordLabel.addClassName("passwort-vergessen-label");
        confirmPasswordField.setPlaceholder("Passwort bestätigen");
        confirmPasswordField.setRequiredIndicatorVisible(true);
        confirmPasswordField.setRequired(true);
        confirmPasswordField.setWidthFull();
        confirmPasswordField.addClassName("vergessen-input-field");

        resetButton = new Button("Passwort zurücksetzen", e -> handlePasswordReset(event));
        resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetButton.setWidthFull();
        resetButton.addClassName("vergessen-send-button");
        resetButton.addClickShortcut(com.vaadin.flow.component.Key.ENTER);

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(false);
        formLayout.setPadding(false);
        formLayout.add(title, subtitle, newPasswordLabel, newPasswordField, confirmPasswordLabel, confirmPasswordField, resetButton);

        container.add(formLayout);
        add(container);
    }

    private void handlePasswordReset(BeforeEnterEvent event) {
        if (!newPasswordField.isEmpty() && !confirmPasswordField.isEmpty() && newPasswordField.getValue().equals(confirmPasswordField.getValue()) && isValidPassword(newPasswordField.getValue()) ) {
            boolean success = nutzerService.resetPassword(token, newPasswordField.getValue());
            if (success) {
                Notification notification = Notification.show("Passwort erfolgreich zurückgesetzt.");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setDuration(3000);
                event.forwardTo("login");
                UI.getCurrent().navigate("login");
            } else {
                Notification notification = Notification.show("Fehler beim Zurücksetzen des Passworts.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setDuration(5000);
            }
        } else {
            Notification notification = Notification.show("Bitte geben Sie ein gültiges Passwort ein!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setDuration(5000);
        }
    }

    private boolean isValidPassword(String password) {
        /*
        * Passwort muss mindestens 8 Zeichen lang sein, mindestens einen Großbuchstaben, einen Kleinbuchstaben, eine Zahl und ein Sonderzeichen enthalten.
        */
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9])\\S{8,}$";
        return password != null && password.matches(regex);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event){
        token = event.getLocation().getQueryParameters().getParameters().getOrDefault("token", List.of("")).getFirst();
        initUI(event);
    }
}
