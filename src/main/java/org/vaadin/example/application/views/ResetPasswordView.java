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

/**
 * Ansicht zum Zurücksetzen des Passworts.
 * Diese Klasse ermöglicht es Benutzern, ihr Passwort zurückzusetzen,
 * wenn sie es vergessen haben. Sie ist für anonyme Benutzer zugänglich.
 */
@Route("reset-password")
@PageTitle("Passwort zurücksetzen")
@AnonymousAllowed
public class ResetPasswordView extends VerticalLayout implements BeforeEnterObserver {

    private final NutzerService nutzerService;
    private String token;

    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;
    private Button resetButton;

    /**
     * Konstruktor zur Initialisierung der ResetPasswordView.
     * Erfordert den NutzerService, um das Zurücksetzen des Passworts zu ermöglichen.
     *
     * @param nutzerService Service zur Verwaltung von Nutzeroperationen
     */
    @Autowired
    public ResetPasswordView(NutzerService nutzerService) {
        this.nutzerService = nutzerService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    /**
     * Initialisiert die Benutzeroberfläche der ResetPasswordView.
     * Erstellt die erforderlichen UI-Komponenten und fügt sie zur Ansicht hinzu.
     *
     * @param event Das BeforeEnterEvent, das Informationen über die Navigation enthält
     */
    private void initUI(BeforeEnterEvent event){
        addClassName("passwort-vergessen-view");

        Div container = new Div();
        container.addClassName("passwort-vergessen-container");

        // Titel und Untertitel
        H1 title = new H1("Passwort zurücksetzen");
        title.addClassName("passwort-vergessen-title");
        Span subtitle = new Span("Das Passwort muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben, einen Kleinbuchstaben, eine Zahl und ein Sonderzeichen enthalten.");
        subtitle.addClassName("passwort-vergessen-subtitle");

        // Eingabefelder für neues Passwort und Bestätigung
        Span newPasswordLabel = new Span("Neues Passwort:");
        newPasswordField = new PasswordField();
        newPasswordLabel.addClassName("passwort-vergessen-label");
        newPasswordField.setPlaceholder("Neues Passwort eingeben");
        newPasswordField.setRequiredIndicatorVisible(true);
        newPasswordField.setRequired(true);
        newPasswordField.setWidthFull();
        newPasswordField.addClassName("vergessen-input-field");

        // Eingabefeld für die Bestätigung des neuen Passworts
        Span confirmPasswordLabel = new Span("Passwort bestätigen:");
        confirmPasswordField = new PasswordField();
        confirmPasswordLabel.addClassName("passwort-vergessen-label");
        confirmPasswordField.setPlaceholder("Passwort bestätigen");
        confirmPasswordField.setRequiredIndicatorVisible(true);
        confirmPasswordField.setRequired(true);
        confirmPasswordField.setWidthFull();
        confirmPasswordField.addClassName("vergessen-input-field");

        // Button zum Zurücksetzen des Passworts
        resetButton = new Button("Passwort zurücksetzen", e -> handlePasswordReset(event));
        resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetButton.setWidthFull();
        resetButton.addClassName("vergessen-send-button");
        resetButton.addClickShortcut(com.vaadin.flow.component.Key.ENTER);

        // Layout für das Formular
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(false);
        formLayout.setPadding(false);
        formLayout.add(title, subtitle, newPasswordLabel, newPasswordField, confirmPasswordLabel, confirmPasswordField, resetButton);

        container.add(formLayout);
        add(container);
    }

    /**
     * Behandelt das Zurücksetzen des Passworts.
     * Überprüft die Eingaben und führt das Zurücksetzen durch, wenn die Bedingungen erfüllt sind.
     *
     * @param event Das BeforeEnterEvent, das Informationen über die Navigation enthält
     */
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

    /**
     * Überprüft, ob das eingegebene Passwort den Sicherheitsanforderungen entspricht.
     * Das Passwort muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben,
     * einen Kleinbuchstaben, eine Zahl und ein Sonderzeichen enthalten.
     *
     * @param password Das zu überprüfende Passwort
     * @return true, wenn das Passwort gültig ist, sonst false
     */
    private boolean isValidPassword(String password) {
        /*
        * Passwort muss mindestens 8 Zeichen lang sein, mindestens einen Großbuchstaben, einen Kleinbuchstaben, eine Zahl und ein Sonderzeichen enthalten.
        */
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9])\\S{8,}$";
        return password != null && password.matches(regex);
    }

    /**
     * Wird aufgerufen, bevor die Ansicht betreten wird.
     * Extrahiert den Token aus den Query-Parametern und initialisiert die UI.
     *
     * @param event Das BeforeEnterEvent, das Informationen über die Navigation enthält
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event){
        token = event.getLocation().getQueryParameters().getParameters().getOrDefault("token", List.of("")).getFirst();
        initUI(event);
    }
}
