package org.vaadin.example.application.views.register;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.NutzerService;



@Route("register")
@PageTitle("Registrieren")
@AnonymousAllowed // Diese Annotation erlaubt anonymen Zugriff
public class RegisterView extends VerticalLayout {

    private final NutzerService nutzerService;

    private TextField username;
    private TextField vorname;
    private TextField nachname;
    private EmailField email;
    private TextField steuerID;
    private PasswordField password;
    private PasswordField confirmPassword;
    private Button registerButton;
    private Button cancelButton;


    /**
     * Binder for form validation.
     */
    private final Binder<Nutzer> binder = new BeanValidationBinder<>(Nutzer.class);

    /**
     * Constructor for the RegisterView.
     *
     * @param nutzerService The service for user management operations
     */
    @Autowired
    public RegisterView(NutzerService nutzerService) {
        this.nutzerService = nutzerService;

        configurePage();
        initUI();
    }

    /**
     * Configures the page layout and styling.
     */
    private void configurePage() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        addClassName("passwort-vergessen-view");
    }

    /**
     * Initializes the UI components and layout.
     */
    private void initUI() {
        Div container = new Div();
        container.addClassName("passwort-vergessen-container");

        H1 title = new H1("Registrierung");
        title.addClassName("passwort-vergessen-title");

        // Vorname
        Span vornameLabel = new Span("Vorname:");
        vornameLabel.addClassName("passwort-vergessen-label");
        vorname = new TextField();
        vorname.setPlaceholder("Vorname eingeben");
        vorname.setRequiredIndicatorVisible(true);
        vorname.setRequired(true);
        vorname.setWidthFull();
        vorname.addClassName("vergessen-input-field");

        // Nachname
        Span nachnameLabel = new Span("Nachname:");
        nachnameLabel.addClassName("passwort-vergessen-label");
        nachname = new TextField();
        nachname.setPlaceholder("Nachname eingeben");
        nachname.setRequiredIndicatorVisible(true);
        nachname.setRequired(true);
        nachname.setWidthFull();
        nachname.addClassName("vergessen-input-field");

        // Benutzername
        Span usernameLabel = new Span("Benutzername:");
        usernameLabel.addClassName("passwort-vergessen-label");
        username = new TextField();
        username.setPlaceholder("Benutzername eingeben (mindestens 3 Zeichen)");
        username.setRequiredIndicatorVisible(true);
        username.setRequired(true);
        username.setMinLength(3);
        username.setWidthFull();
        username.addClassName("vergessen-input-field");

        username.addValueChangeListener(e -> {
            if(e.getValue().length() < 3) {
                username.setErrorMessage("Benutzername muss mindestens 3 Zeichen lang sein");
                username.setInvalid(true);
            } else {
                username.setInvalid(false);
            }
        });

        //Steuer-ID
        Span steuerLabel = new Span("Steuer-ID:");
        steuerLabel.addClassName("passwort-vergessen-label");
        steuerID = new TextField();
        steuerID.setPlaceholder("11-stellige Steuer-ID eingeben");
        steuerID.setRequiredIndicatorVisible(true);
        steuerID.setRequired(true);
        steuerID.setMinLength(3);
        steuerID.setWidthFull();
        steuerID.addClassName("vergessen-input-field");
        steuerID.addValueChangeListener(e -> {
            steuerID.setInvalid(!e.getValue().matches("^\\d{11}$")); //11 Stellen und nur Ziffern
        });

        // E-Mail
        Span emailLabel = new Span("E-Mail:");
        emailLabel.addClassName("passwort-vergessen-label");
        email = new EmailField();
        email.setPlaceholder("E-Mail-Adresse eingeben");
        email.setRequiredIndicatorVisible(true);
        email.setRequired(true);
        email.setWidthFull();
        email.addClassName("vergessen-input-field");
        email.addValueChangeListener(e -> {
            email.setInvalid(!isValidEmail(e.getValue()));
        });

        // Passwort
        Span passwordLabel = new Span("Passwort:");
        passwordLabel.addClassName("passwort-vergessen-label");
        password = new PasswordField();
        password.setPlaceholder("Passwort eingeben");
        password.setRequiredIndicatorVisible(true);
        password.setRequired(true);
        password.setMinLength(8);
        password.setWidthFull();
        password.addClassName("vergessen-input-field");

        password.addValueChangeListener(e -> {
            //TODO: Validierung einfügen
        });

        // Passwort bestätigen
        Span confirmPasswordLabel = new Span("Passwort bestätigen:");
        confirmPasswordLabel.addClassName("passwort-vergessen-label");
        confirmPassword = new PasswordField();
        confirmPassword.setPlaceholder("Passwort bestätigen");
        confirmPassword.setRequiredIndicatorVisible(true);
        confirmPassword.setRequired(true);
        confirmPassword.setWidthFull();
        confirmPassword.addClassName("vergessen-input-field");

        // Passwort-Validierung bei Eingabe
        confirmPassword.addValueChangeListener(event -> {
            if (!event.getValue().equals(password.getValue())) {
                confirmPassword.setErrorMessage("Passwörter stimmen nicht überein");
                confirmPassword.setInvalid(true);
                confirmPassword.getStyle().set("margin-bottom", "0.35rem");
            } else {
                confirmPassword.setInvalid(false);
                confirmPassword.getStyle().set("margin-bottom", "1.25rem");
            }
        });

        // Passwort-Anforderungen
        Span passwordRequirements = new Span("Das Passwort muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben, einen Kleinbuchstaben, eine Zahl und ein Sonderzeichen enthalten.");
        passwordRequirements.addClassName("register-subtitle");

        // Buttons
        registerButton = new Button("Registrieren", e -> registerUser());
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.setWidth("48%");
        registerButton.addClassName("vergessen-send-button");
        registerButton.addClickShortcut(com.vaadin.flow.component.Key.ENTER);
        registerButton.getStyle().set("margin-top", "0.75rem");

        cancelButton = new Button("Abbrechen", e -> UI.getCurrent().navigate("login"));
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.setWidth("48%");
        cancelButton.addClassName("vergessen-cancel-button");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        buttonLayout.add(cancelButton, registerButton);

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(false);
        formLayout.setPadding(false);
        formLayout.add(
                title,
                vornameLabel, vorname,
                nachnameLabel, nachname,
                usernameLabel, username,
                steuerLabel, steuerID,
                emailLabel, email,
                passwordLabel, password,
                confirmPasswordLabel, confirmPassword,
                passwordRequirements,
                buttonLayout
        );

        container.add(formLayout);
        //container.addClassNames(LumoUtility.Padding.MEDIUM);
        add(container);

    }

    /**
     * Handles the user registration process.
     * Validates the form data, checks for existing usernames and emails,
     * and creates a new user if all validations pass.
     */
    private void registerUser() {
        try {
            Nutzer nutzer = new Nutzer();

            // Nur die anderen Felder an den Binder binden (ohne Passwort)
            binder.forField(username).bind(Nutzer::getUsername, Nutzer::setUsername);
            binder.forField(vorname).bind(Nutzer::getVorname, Nutzer::setVorname);
            binder.forField(nachname).bind(Nutzer::getNachname, Nutzer::setNachname);
            binder.forField(email).bind(Nutzer::getEmail, Nutzer::setEmail);

            // Alle gebundenen Felder in den Nutzer schreiben
            binder.writeBean(nutzer);

            // Passwort manuell setzen (da es nicht über den Binder gebunden ist)
            nutzer.setPasswort(password.getValue());

            // Prüfen, ob Benutzername bereits existiert
            if (nutzerService.usernameExists(nutzer.getUsername())) {
                Notification notification = Notification.show("Benutzername bereits vergeben");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setDuration(5000);
                return;
            }

            // Prüfen, ob E-Mail bereits existiert
            if (nutzerService.emailExists(nutzer.getEmail())) {
                Notification notification = Notification.show("E-Mail bereits registriert");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setDuration(5000);
                return;
            }

            //Prüfen ob Steuer-ID gültig ist
            if (!steuerID.getValue().matches("^\\d{11}$")) {
                Notification notification = Notification.show("Steuer-ID muss 11-stellig sein und nur Ziffern enthalten.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setDuration(5000);
                return;
            }

            // Prüfen, ob Passwort gültig ist
            if (!isValidPassword(password.getValue())) {
                Notification notification = Notification.show("Passwort muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben, einen Kleinbuchstaben, eine Zahl und ein Sonderzeichen enthalten.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setDuration(5000);
                return;
            }

            // Prüfen, ob Passwörter übereinstimmen
            if (!password.getValue().equals(confirmPassword.getValue())) {
                Notification notification = Notification.show("Passwörter stimmen nicht überein");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setDuration(5000);
                return;
            }

            nutzerService.speichereNutzer(nutzer);

            Notification notification = Notification.show("Registrierung erfolgreich. Sie können sich jetzt anmelden.");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setDuration(3000);

            getUI().ifPresent(ui -> ui.navigate("login"));

        } catch (ValidationException e) {
            Notification notification = Notification.show("Ungültige Eingaben. Bitte überprüfen Sie Ihre Daten.");
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

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(regex);
    }
}
