package org.vaadin.example.application.views.register;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("passwortvergessen")
@PageTitle("Passwort zurücksetzen")
@AnonymousAllowed
public class PasswortVergessen extends VerticalLayout {
    private final TextField username = new TextField("Benutzername");
    private final PasswordField password = new PasswordField("Passwort");
    private final PasswordField confirmPassword = new PasswordField("Passwort bestätigen");
    private final Button sendButton = new Button("Senden");
    private final Button cancelButton = new Button("Abbrechen");

    public PasswortVergessen() {
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.JustifyContent.CENTER, LumoUtility.AlignItems.CENTER);
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Passwort zurücksetzen");
        title.addClassNames(LumoUtility.Margin.Top.MEDIUM, LumoUtility.Margin.Bottom.NONE);
        
        Paragraph explanation = new Paragraph("Bitte geben Sie Ihren Benutzernamen und ein neues Passwort ein, um Ihr Passwort zurückzusetzen.");
        explanation.addClassNames(LumoUtility.Margin.Top.SMALL, LumoUtility.Margin.Bottom.MEDIUM);
        
        configureFields();
        configureButtons();
        
        // Horizontal-Layout für Buttons
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(cancelButton, sendButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setSpacing(true);
        buttonLayout.addClassNames(LumoUtility.Margin.Top.MEDIUM);

        sendButton.addClickListener(event -> {
            if (username.isEmpty()) {
                showErrorMessage("Bitte geben Sie Ihren Benutzernamen ein");
            } else if (password.isEmpty()) {
                showErrorMessage("Bitte geben Sie ein Passwort ein");
            } else if (password.getValue().length() < 8) {
                showErrorMessage("Das Passwort muss mindestens 8 Zeichen enthalten");
            } else if (!containsNumbersAndLetters(password.getValue())) {
                showErrorMessage("Das Passwort muss Zahlen und Buchstaben enthalten");
            } else if (!password.getValue().equals(confirmPassword.getValue())) {
                showErrorMessage("Die Passwörter stimmen nicht überein");
            } else {
                // Hier würde die eigentliche Logik zum Zurücksetzen des Passworts stehen
                showSuccessMessage();
                UI.getCurrent().navigate("login");
            }
        });

        add(
                title,
                explanation,
                username,
                password,
                confirmPassword,
                buttonLayout
        );
        
        addClassName("passwort-vergessen-view");
    }
    
    private void configureFields() {
        // Einheitliche Feldkonfiguration
        username.setRequired(true);
        username.setHelperText("Geben Sie Ihren Benutzernamen ein");
        username.setWidth("300px");
        
        password.setRequired(true);
        password.setHelperText("Mindestens 8 Zeichen mit Zahlen und Buchstaben");
        password.setWidth("300px");
        password.setMinLength(8);
        
        confirmPassword.setRequired(true);
        confirmPassword.setHelperText("Wiederholen Sie Ihr neues Passwort");
        confirmPassword.setWidth("300px");
    }
    
    private void configureButtons() {
        // Senden-Button Konfiguration
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        // Abbrechen-Button Konfiguration
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.addClickListener(e -> UI.getCurrent().navigate("login"));
    }
    
    private boolean containsNumbersAndLetters(String password) {
        return password.matches(".*\\d.*") && password.matches(".*[a-zA-Z].*");
    }
    
    private void showErrorMessage(String message) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.TOP_CENTER);
    }
    
    private void showSuccessMessage() {
        Notification notification = Notification.show("Ihr Passwort wurde erfolgreich zurückgesetzt");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.TOP_CENTER);
    }
}