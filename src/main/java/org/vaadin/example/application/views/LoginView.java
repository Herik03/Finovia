package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.List;

/**
 * Die Login-Ansicht der Finovia-Anwendung.
 * <p>
 * Diese Klasse stellt die Login-Seite der Anwendung dar und enthält:
 * <ul>
 *   <li>Ein Login-Formular für die Benutzerauthentifizierung</li>
 *   <li>Einen Button zur Registrierung neuer Benutzer</li>
 *   <li>Einen Button für das Zurücksetzen vergessener Passwörter</li>
 * </ul>
 * <p>
 * Die Klasse implementiert das BeforeEnterListener-Interface, um Fehler bei der Anmeldung zu verarbeiten.
 *
 * @author Finovia Team
 * @version 2.0
 */
@Route("login")
@PageTitle("Login | Finovia")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private final AuthenticationManager authenticationManager;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Span forgotPasswordLink;

    /**
     * Konstruktor der LoginView-Klasse.
     * Initialisiert die Ansicht und richtet das Layout sowie die Komponenten ein.
     *
     * @param authenticationManager Der AuthenticationManager für die Benutzerauthentifizierung
     */
    @Autowired
    public LoginView(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        setupBackgroundAndForm();
    }

    /**
     * Konfiguriert den Hintergrund und das Formular der Login-Ansicht.
     * <p>
     * Diese Methode fügt dem Layout einen Hintergrund hinzu und erstellt das Login-Formular.
     */
    private void setupBackgroundAndForm() {
        // Background styling mit Bild
        addClassName("login-view");
        getStyle()
                .set("background", "linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.7))")
                .set("background-size", "cover")
                .set("background-position", "center")
                .set("background-attachment", "fixed")
                .set("min-height", "100vh");

        createLoginForm();
    }

    /**
     * Erstellt das Login-Formular mit allen erforderlichen Feldern und Buttons.
     * <p>
     * Das Formular enthält:
     * <ul>
     *   <li>Benutzername-Feld</li>
     *   <li>Passwort-Feld</li>
     *   <li>Anmelde-Button</li>
     *   <li>Registrierungs-Button</li>
     *   <li>Link zum Zurücksetzen des Passworts</li>
     * </ul>
     */
    private void createLoginForm() {
        // Main container mit Glassmorphism-Effekt
        Div loginContainer = new Div();
        loginContainer.addClassName("login-container");
        loginContainer.getStyle()
                .set("background", "var(--lumo-base-color)") // Angepasst an Ihr Farbschema
                .set("backdrop-filter", "blur(15px)")
                .set("border", "1px solid rgba(68, 207, 108, 0.2)") // Verwendet Ihre Primärfarbe
                .set("border-radius", "16px")
                .set("box-shadow", "0 25px 50px -12px rgba(0, 0, 0, 0.6)")
                .set("padding", "3rem")
                .set("width", "420px")
                .set("max-width", "90vw");

        // Title
        H1 title = new H1("Finovia");
        title.getStyle()
                .set("margin", "0 0 0.5rem 0")
                .set("color", "var(--lumo-header-text-color)")
                .set("font-size", "2.5rem")
                .set("font-weight", "700")
                .set("text-align", "center")
                .set("font-family", "var(--lumo-heading-font-family)");

        // Subtitle
        Span subtitle = new Span("Bitte melden Sie sich mit Ihren Zugangsdaten an");
        subtitle.getStyle()
                .set("color", "var(--lumo-body-text-color)") // Ihre Sekundärfarbe
                .set("font-size", "1rem")
                .set("text-align", "center")
                .set("display", "block")
                .set("margin-bottom", "2rem")
                .set("opacity", "0.9");

        // Username field mit Label
        Span usernameLabel = createFieldLabel("Benutzername");
        usernameField = new TextField();
        usernameField.setPlaceholder("Benutzername eingeben");
        usernameField.setWidthFull();
        customizeField(usernameField);

        // Password field mit Label
        Span passwordLabel = createFieldLabel("Passwort");
        passwordField = new PasswordField();
        passwordField.setPlaceholder("Passwort eingeben");
        passwordField.setWidthFull();
        customizePasswordField(passwordField);

        // Login button
        loginButton = new Button("Anmelden");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.setWidthFull();
        loginButton.addClassName("login-button");
        loginButton.getStyle()
                .set("background", "linear-gradient(135deg, var(--lumo-primary-color), var(--lumo-primary-color-shade))")
                .set("border", "none")
                .set("padding", "0.875rem")
                .set("font-weight", "600")
                .set("font-size", "1rem")
                .set("border-radius", "var(--lumo-border-radius)")
                .set("margin-top", "1.5rem")
                .set("cursor", "pointer")
                .set("transition", "all 0.3s ease")
                .set("color", "white")
                .set("text-transform", "none");

        // Register button
        registerButton = new Button("Registrieren");
        registerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        registerButton.setWidthFull();
        registerButton.addClassName("register-button");
        registerButton.getStyle()
                .set("color", "var(--lumo-primary-color)")
                .set("border", "1px solid rgba(68, 207, 108, 0.4)")
                .set("background", "transparent")
                .set("padding", "0.875rem")
                .set("font-weight", "500")
                .set("font-size", "1rem")
                .set("border-radius", "var(--lumo-border-radius)")
                .set("margin-top", "0.75rem")
                .set("cursor", "pointer")
                .set("transition", "all 0.3s ease")
                .set("text-transform", "none");

        // Forgot password link
        forgotPasswordLink = new Span("Passwort vergessen?");
        forgotPasswordLink.addClassName("forgot-link");
        forgotPasswordLink.getStyle()
                .set("color", "var(--lumo-primary-color)")
                .set("font-size", "0.875rem")
                .set("cursor", "pointer")
                .set("text-align", "center")
                .set("margin-top", "1.5rem")
                .set("display", "block")
                .set("transition", "color 0.3s ease")
                .set("text-decoration", "underline")
                .set("text-decoration-color", "rgba(68, 207, 108, 0.4)");

        // Event listeners
        loginButton.addClickListener(e -> handleLogin());
        registerButton.addClickListener(e -> handleRegister());
        forgotPasswordLink.getElement().addEventListener("click", e -> handleForgotPassword());

        // Hover effects mit JavaScript
        addHoverEffects();

        // Enter key support
        usernameField.addKeyPressListener(com.vaadin.flow.component.Key.ENTER, e -> handleLogin());
        passwordField.addKeyPressListener(com.vaadin.flow.component.Key.ENTER, e -> handleLogin());

        // Add components to container
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(false);
        formLayout.setPadding(false);
        formLayout.add(
                title,
                subtitle,
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                loginButton,
                registerButton,
                forgotPasswordLink
        );

        loginContainer.add(formLayout);
        add(loginContainer);
    }

    /**
     * Erstellt ein Label für die Eingabefelder mit dem angegebenen Text.
     *
     * @param text Der Text des Labels
     * @return Ein Span-Element, das als Label dient
     */
    private Span createFieldLabel(String text) {
        Span label = new Span(text);
        label.getStyle()
                .set("color", "var(--lumo-primary-color-shade)")
                .set("font-size", "0.875rem")
                .set("font-weight", "500")
                .set("margin-bottom", "0.5rem")
                .set("display", "block")
                .set("font-family", "var(--lumo-font-family)");
        return label;
    }

    /**
     * Passt das Styling des TextField an, um es an das Design anzupassen.
     *
     * @param field Das TextField, das angepasst werden soll
     */
    private void customizeField(TextField field) {
        field.addClassName("input-field");
        field.getStyle()
                .set("margin-bottom", "1.25rem");

        // Custom styling für die Input-Felder
        field.getElement().executeJs(
                "const field = this;" +
                        "const inputField = this.shadowRoot.querySelector('[part=\"input-field\"]');" +
                        "const input = this.shadowRoot.querySelector('input');" +

                        "if (inputField) {" +
                        "inputField.style.background = 'var(--lumo-shade-20pct)';" +
                        "inputField.style.border = '1px solid rgba(68, 207, 108, 0.3)';" +
                        "inputField.style.borderRadius = 'var(--lumo-border-radius)';" +
                        "inputField.style.color = 'white';" +
                        "inputField.style.padding = '0.875rem';" +
                        "inputField.style.transition = 'all 0.3s ease';" +
                        "}" +

                        "if (input) {" +
                        "input.style.background = 'transparent';" +
                        "input.style.color = 'white';" +
                        "input.style.fontFamily = 'var(--lumo-font-family)';" +
                        "}" +

                        // Focus-Effekt
                        "this.addEventListener('focus', () => {" +
                        "if (inputField) {" +
                        "inputField.style.borderColor = 'var(--lumo-primary-color)';" +
                        "inputField.style.boxShadow = '0 0 0 3px rgba(68, 207, 108, 0.2)';" +
                        "inputField.style.background = 'var(--lumo-shade-30pct)';" +
                        "}" +
                        "});" +

                        "this.addEventListener('blur', () => {" +
                        "if (inputField) {" +
                        "inputField.style.borderColor = 'rgba(68, 207, 108, 0.3)';" +
                        "inputField.style.boxShadow = 'none';" +
                        "inputField.style.background = 'var(--lumo-shade-20pct)';" +
                        "}" +
                        "});"
        );
    }

    // Separate Methode für PasswordField
    private void customizePasswordField(PasswordField field) {
        field.addClassName("input-field");
        field.getStyle()
                .set("margin-bottom", "1.25rem");

        // PasswordField hat eine etwas andere Struktur
        field.getElement().executeJs(
                "const field = this;" +
                        "const inputField = this.shadowRoot.querySelector('[part=\"input-field\"]');" +
                        "const input = this.shadowRoot.querySelector('input');" +
                        "const toggleButton = this.shadowRoot.querySelector('[part=\"toggle-button\"]');" +

                        "if (inputField) {" +
                        "inputField.style.background = 'var(--lumo-shade-20pct)';" +
                        "inputField.style.border = '1px solid rgba(68, 207, 108, 0.3)';" +
                        "inputField.style.borderRadius = 'var(--lumo-border-radius)';" +
                        "inputField.style.color = 'white';" +
                        "inputField.style.padding = '0.875rem';" +
                        "inputField.style.transition = 'all 0.3s ease';" +
                        "}" +

                        "if (input) {" +
                        "input.style.background = 'transparent';" +
                        "input.style.color = 'white';" +
                        "input.style.fontFamily = 'var(--lumo-font-family)';" +
                        "}" +

                        // Passwort-Toggle Button styling
                        "if (toggleButton) {" +
                        "toggleButton.style.color = 'rgba(192, 239, 216, 0.8)';" +
                        "}" +

                        // Focus-Effekt
                        "this.addEventListener('focus', () => {" +
                        "if (inputField) {" +
                        "inputField.style.borderColor = 'var(--lumo-primary-color)';" +
                        "inputField.style.boxShadow = '0 0 0 3px rgba(68, 207, 108, 0.2)';" +
                        "inputField.style.background = 'var(--lumo-shade-30pct)';" +
                        "}" +
                        "});" +

                        "this.addEventListener('blur', () => {" +
                        "if (inputField) {" +
                        "inputField.style.borderColor = 'rgba(68, 207, 108, 0.3)';" +
                        "inputField.style.boxShadow = 'none';" +
                        "inputField.style.background = 'var(--lumo-shade-20pct)';" +
                        "}" +
                        "});"
        );
    }

    /**
     * Fügt Hover-Effekte für die Buttons und Links hinzu.
     * <p>
     * Diese Methode registriert JavaScript-Event-Listener für Hover-Effekte
     * auf den Login-Button, den Registrierungs-Button und den "Passwort vergessen"-Link.
     */
    private void addHoverEffects() {
        // Login Button Hover
        loginButton.getElement().addEventListener("mouseenter", e -> {
            loginButton.getStyle()
                    .set("transform", "translateY(-2px)")
                    .set("box-shadow", "0 8px 25px rgba(68, 207, 108, 0.4)");
        });

        loginButton.getElement().addEventListener("mouseleave", e -> {
            loginButton.getStyle()
                    .set("transform", "translateY(0)")
                    .set("box-shadow", "none");
        });

        // Register Button Hover
        registerButton.getElement().addEventListener("mouseenter", e -> {
            registerButton.getStyle()
                    .set("border-color", "var(--lumo-primary-color)")
                    .set("color", "white")
                    .set("background", "rgba(68, 207, 108, 0.1)");
        });

        registerButton.getElement().addEventListener("mouseleave", e -> {
            registerButton.getStyle()
                    .set("border-color", "rgba(68, 207, 108, 0.4)")
                    .set("color", "#C0EFD8")
                    .set("background", "transparent");
        });

        // Forgot Password Link Hover
        forgotPasswordLink.getElement().addEventListener("mouseenter", e -> {
            forgotPasswordLink.getStyle()
                    .set("color", "var(--lumo-primary-color)")
                    .set("text-decoration-color", "var(--lumo-primary-color)");
        });

        forgotPasswordLink.getElement().addEventListener("mouseleave", e -> {
        forgotPasswordLink.getStyle().set("color", "rgba(68, 207, 108, 0.8)").set("text-decoration-color", "rgba(68, 207, 108, 0.4)").setWidth("100%");
        });
    }

    /**
     * Behandelt die Anmeldung des Benutzers.
     * <p>
     * Diese Methode überprüft die Eingaben des Benutzers und führt die Authentifizierung durch.
     * Bei erfolgreicher Anmeldung wird der Benutzer zur Hauptseite weitergeleitet.
     */
    private void handleLogin() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            showErrorNotification("Bitte füllen Sie alle Felder aus");
            return;
        }

        try {
            // Authentifizierung durchführen
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authToken);

            // Authentifizierung im SecurityContext UND in der Session speichern
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            // Session-Integration für Vaadin
            VaadinSession.getCurrent().getSession().setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    securityContext
            );

            // Bei erfolgreicher Anmeldung zur Hauptseite weiterleiten
            showSuccessNotification("Anmeldung erfolgreich");
            UI.getCurrent().navigate("uebersicht");

        } catch (AuthenticationException e) {
            showErrorNotification("Anmeldung fehlgeschlagen. Bitte überprüfen Sie Ihre Eingaben.");
        }
    }

    /**
     * Behandelt die Registrierung eines neuen Benutzers.
     * <p>
     * Diese Methode navigiert den Benutzer zur Registrierungsseite.
     */
    private void handleRegister() {
        UI.getCurrent().navigate("register");
    }

    /**
     * Behandelt das Zurücksetzen des Passworts.
     * <p>
     * Diese Methode navigiert den Benutzer zur Seite für das Zurücksetzen des Passworts.
     */
    private void handleForgotPassword() {
        UI.getCurrent().navigate("passwortvergessen");
    }

    /**
     * Zeigt eine Fehlermeldung als Benachrichtigung an.
     *
     * @param message Die Fehlermeldung, die angezeigt werden soll
     */
    private void showErrorNotification(String message) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.setDuration(4000);
    }

    /**
     * Zeigt eine Erfolgsbenachrichtigung an.
     *
     * @param message Die Erfolgsnachricht, die angezeigt werden soll
     */
    private void showSuccessNotification(String message) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.setDuration(3000);
    }

    /**
     * Diese Methode wird aufgerufen, bevor die Ansicht betreten wird.
     * Sie prüft, ob der Benutzer bereits angemeldet ist und leitet ihn gegebenenfalls weiter.
     * Außerdem zeigt sie eine Erfolgsmeldung an, wenn der Benutzer sich erfolgreich abgemeldet hat.
     *
     * @param beforeEnterEvent Das Ereignis, das vor dem Betreten der Ansicht ausgelöst wird
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Prüfe auf Logout-Parameter
        String logoutParam = beforeEnterEvent.getLocation().getQueryParameters()
                .getParameters()
                .getOrDefault("logout", List.of(""))
                .getFirst();

        if ("true".equals(logoutParam)) {
            showSuccessNotification("Sie wurden erfolgreich abgemeldet.");
        }

        // Prüfen ob Benutzer bereits angemeldet ist
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getName().equals("anonymousUser")) {
            // Bereits angemeldet - zur Hauptseite weiterleiten
            beforeEnterEvent.forwardTo("uebersicht");
        }
    }
}
