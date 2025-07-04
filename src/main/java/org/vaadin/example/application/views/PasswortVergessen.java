package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.services.NutzerService;

/**
 * View zum Zurücksetzen des Passworts.
 * Der Nutzer gibt seine E-Mail Adresse ein, um eine E-Mail zum Zurücksetzen des Passworts zu erhalten.
 */
@Route("passwortvergessen")
@PageTitle("Passwort zurücksetzen")
@AnonymousAllowed
public class PasswortVergessen extends VerticalLayout {

    private final NutzerService nutzerService;
    private EmailField emailField;
    private Button sendButton;
    private Button cancelButton;

    @Autowired
    public PasswortVergessen(NutzerService nutzerService) {
        this.nutzerService = nutzerService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        createView();
    }

    /**
     * Erstellt die View-Komponenten für die Passwort-Zurücksetzen-Seite.
     */
    private void createView() {
        addClassName("passwort-vergessen-view");

        Div container = new Div();
        container.addClassName("passwort-vergessen-container");

        H1 title = new H1("Passwort zurücksetzen");
        title.addClassName("passwort-vergessen-title");
        Span subtitle = new Span("Bitte geben Sie ihre E-Mail Adresse ein, wir melden und dann bei Ihnen");
        subtitle.addClassName("passwort-vergessen-subtitle");

        Span emailLabel = new Span("E-Mail:");
        emailField = new EmailField();
        emailLabel.addClassName("passwort-vergessen-label");
        emailField.setPlaceholder("E-Mail Adresse eingeben");
        emailField.setWidthFull();
        emailField.setRequired(true);
        emailField.setRequiredIndicatorVisible(true);
        emailField.addClassName("vergessen-input-field");

        sendButton = new Button("Senden", e -> handleSend());
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.setWidthFull();
        sendButton.addClassName("vergessen-send-button");

        cancelButton = new Button("Abbrechen", e -> UI.getCurrent().navigate("login"));
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.setWidthFull();
        cancelButton.addClassName("vergessen-cancel-button");

        //Key support for Enter key
        emailField.addKeyDownListener(com.vaadin.flow.component.Key.ENTER, e -> handleSend());

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(false);
        formLayout.setPadding(false);
        formLayout.add(title, subtitle, emailLabel, emailField, sendButton, cancelButton);

        container.add(formLayout);
        add(container);
    }

    /**
     * Behandelt das Senden der E-Mail zum Zurücksetzen des Passworts.
     * Überprüft die E-Mail-Adresse und sendet eine Benachrichtigung.
     */
    private void handleSend() {
        if (isValidEmail(emailField.getValue())){
            nutzerService.sendPasswordResetEmail(emailField.getValue());
            showSuccessMessage();
            UI.getCurrent().navigate("login");
        } else {
            showErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse ein.");
            return;
        }
    }

    /**
     * Validiert die E-Mail-Adresse anhand eines regulären Ausdrucks.
     * @param email Die zu prüfende E-Mail-Adresse.
     * @return true, wenn die E-Mail-Adresse gültig ist, sonst false.
     */
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(regex);
    }

    /**
     * Zeigt eine Fehlermeldung an.
     * @param message Die Fehlermeldung, die angezeigt werden soll.
     */
    private void showErrorMessage(String message) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.TOP_CENTER);
    }

    /**
     * Zeigt eine Erfolgsmeldung an, wenn die E-Mail zum Zurücksetzen des Passworts gesendet wurde.
     */
    private void showSuccessMessage() {
        Notification notification = Notification.show("Bitte überprüfen Sie Ihre E-Mails für weitere Anweisungen.");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.TOP_CENTER);
    }
}