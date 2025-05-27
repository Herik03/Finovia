package org.vaadin.example.application.views.register;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.NutzerService;



@Route("register")
@PageTitle("Registrieren")
@AnonymousAllowed // Diese Annotation erlaubt anonymen Zugriff
public class RegisterView extends VerticalLayout {

    private final NutzerService nutzerService;

    private final TextField username = new TextField("Benutzername");
    private final TextField vorname = new TextField("Vorname");
    private final TextField nachname = new TextField("Nachname");
    private final TextField steuerId = new TextField("Steuer-ID");
    private final EmailField email = new EmailField("E-Mail");
    private final PasswordField password = new PasswordField("Passwort");
    private final PasswordField confirmPassword = new PasswordField("Passwort bestätigen");
    private final Button registerButton = new Button("Registrieren");
    private final Button cancelButton = new Button("Abbrechen");

    /**
     * Binder for form validation.
     */
    private Binder<Nutzer> binder = new BeanValidationBinder<>(Nutzer.class);

    /**
     * Constructor for the RegisterView.
     * 
     * @param nutzerService The service for user management operations
     */
    @Autowired
    public RegisterView(NutzerService nutzerService) {
        this.nutzerService = nutzerService;

        configurePage();
        configureFields();
        buildForm();
    }
    
    /**
     * Configures the page layout and styling.
     */
    private void configurePage() {
        setSpacing(false);
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.JustifyContent.CENTER, 
                LumoUtility.AlignItems.CENTER, LumoUtility.Padding.MEDIUM);
        setSizeFull();
        
        // Zentrale Ausrichtung des Hauptlayouts
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
    
    /**
     * Configures the form fields with validation rules and helper texts.
     */
    private void configureFields() {
        // Einheitliche Feldkonfiguration
        username.setRequired(true);
        username.setHelperText("Mindestens 6 Zeichen");
        username.setMinLength(3);
        
        vorname.setRequired(true);
        nachname.setRequired(true);

        steuerId.setRequired(true);
        steuerId.setHelperText("Bitte geben Sie ihre 11-stellige Steuer-ID ein");
        
        email.setRequired(true);
        email.setHelperText("Bitte geben Sie eine gültige E-Mail-Adresse ein");
        
        password.setRequired(true);
        password.setHelperText("Mindestens 8 Zeichen mit Zahlen und Buchstaben");
        password.setMinLength(8);
        
        confirmPassword.setRequired(true);
        confirmPassword.addValueChangeListener(event -> {
            if (!event.getValue().equals(password.getValue())) {
                confirmPassword.setErrorMessage("Passwörter stimmen nicht überein");
                confirmPassword.setInvalid(true);
            } else {
                confirmPassword.setInvalid(false);
            }
        });
        
        // Button-Konfiguration
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.setMinWidth("150px");
        
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.addClickListener(e -> UI.getCurrent().navigate("login"));
    }
    
    /**
     * Builds the form layout and adds all components to the view.
     */
    private void buildForm() {
        // Container für Formular und alle anderen Elemente mit begrenzter Breite
        VerticalLayout contentContainer = new VerticalLayout();
        contentContainer.setPadding(true);
        contentContainer.setSpacing(true);
        contentContainer.setAlignItems(Alignment.CENTER);
        contentContainer.setWidth("100%");
        contentContainer.setMaxWidth("600px");
        
        // Überschrift als separate Komponente
        H1 title = new H1("Registrierung");
        title.getStyle().set("margin-bottom", "1rem");
        
        // Erstellen eines FormLayouts für die Eingabefelder
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                vorname,
                nachname,
                username,
                steuerId,
                email,
                password,
                confirmPassword
        );
        
        // Verbesserte responsive Einstellungen für das Web
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 2)
        );
        
        // Formularlayout auf volle Breite des Containers setzen
        formLayout.setWidth("100%");
        
        // Horizontales Layout für Buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, registerButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setMargin(true);
        buttonLayout.setSpacing(true);
        
        // Komponenten zum Container hinzufügen
        contentContainer.add(title, formLayout, buttonLayout);
        
        // Container zum Hauptlayout hinzufügen
        add(contentContainer);
        
        registerButton.addClickListener(event -> registerUser());
    }

    
    /**
     * Checks if a password contains both numbers and letters.
     * 
     * @param password The password to check
     * @return True if the password contains both numbers and letters, false otherwise
     */
    private boolean containsNumbersAndLetters(String password) {
        return password.matches(".*\\d.*") && password.matches(".*[a-zA-Z].*");
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
        binder.forField(steuerId).bind(Nutzer::getSteuerId, Nutzer::setSteuerId);
        binder.forField(email).bind(Nutzer::getEmail, Nutzer::setEmail);
        
        // Alle gebundenen Felder in den Nutzer schreiben
        binder.writeBean(nutzer);
        
        // Passwort manuell setzen (da es nicht über den Binder gebunden ist)
        nutzer.setPasswort(password.getValue());

        // Prüfen, ob Benutzername bereits existiert
        if (nutzerService.usernameExists(nutzer.getUsername())) {
            Notification.show("Benutzername bereits vergeben")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Prüfen, ob Steuer-ID gültig ist
        if (!nutzer.istSteuerIdGueltig()) {
            Notification.show("Die angegebene Steuer-ID ist ungültig. Bitte geben Sie eine 11-stellige Nummer ein.")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Prüfen, ob E-Mail bereits existiert
        if (nutzerService.emailExists(nutzer.getEmail())) {
            Notification.show("E-Mail bereits registriert")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Prüfen, ob Passwort gültig ist
        if (!containsNumbersAndLetters(password.getValue())) {
            Notification.show("Passwort muss mindestens 8 Zeichen lang sein und Zahlen und Buchstaben enthalten")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Prüfen, ob Passwörter übereinstimmen
        if (!password.getValue().equals(confirmPassword.getValue())) {
            Notification.show("Passwörter stimmen nicht überein")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        nutzerService.speichereNutzer(nutzer);

        Notification.show("Registrierung erfolgreich. Sie können sich jetzt anmelden.")
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        getUI().ifPresent(ui -> ui.navigate("login"));

    } catch (ValidationException e) {
        Notification.show("Ungültige Eingaben. Bitte überprüfen Sie Ihre Daten.")
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
}