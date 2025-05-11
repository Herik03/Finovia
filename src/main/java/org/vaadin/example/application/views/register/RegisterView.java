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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Data;

@Route("register")
@PageTitle("Registrieren")
@AnonymousAllowed // Diese Annotation erlaubt anonymen Zugriff
public class RegisterView extends VerticalLayout {

    // Datenmodell für die Registrierungsdaten
    @Data
    public static class RegistrationFormData {
        private String username;
        private String vorname;
        private String nachname;
        private String email;
        private String password;
    }

    private final TextField username = new TextField("Benutzername");
    private final TextField vorname = new TextField("Vorname");
    private final TextField nachname = new TextField("Nachname");
    private final EmailField email = new EmailField("E-Mail");
    private final PasswordField password = new PasswordField("Passwort");
    private final PasswordField confirmPassword = new PasswordField("Passwort bestätigen");
    private final Button registerButton = new Button("Registrieren");
    private final Button cancelButton = new Button("Abbrechen");
    
    private final Binder<RegistrationFormData> binder = new Binder<>(RegistrationFormData.class);
    private final RegistrationFormData formData = new RegistrationFormData();

    public RegisterView() {
        configurePage();
        configureFields();
        buildForm();
        configureBinder();
    }
    
    private void configurePage() {
        setSpacing(false);
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.JustifyContent.CENTER, 
                LumoUtility.AlignItems.CENTER, LumoUtility.Padding.MEDIUM);
        setSizeFull();
        
        // Zentrale Ausrichtung des Hauptlayouts
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
    
    private void configureFields() {
        // Einheitliche Feldkonfiguration
        username.setRequired(true);
        username.setHelperText("Mindestens 6 Zeichen");
        username.setMinLength(3);
        
        vorname.setRequired(true);
        nachname.setRequired(true);
        
        email.setRequired(true);
        email.setHelperText("Bitte geben Sie eine gültige E-Mail-Adresse ein");
        
        password.setRequired(true);
        password.setHelperText("Mindestens 8 Zeichen mit Zahlen und Buchstaben");
        password.setMinLength(8);
        
        confirmPassword.setRequired(true);
        
        // Button-Konfiguration
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.setMinWidth("150px");
        
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.addClickListener(e -> UI.getCurrent().navigate("login"));
    }
    
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
    
    // Diese Methode kann angepasst werden, um die tatsächliche Login-View-Klasse zurückzugeben
    private Class<?> getLoginViewClass() {
        // Sollte die tatsächliche Login-View-Klasse sein
        return this.getClass(); // Temporärer Platzhalter
    }
    
    private void configureBinder() {
        // Binden der Felder an das Datenmodell
        binder.forField(username)
                .asRequired("Benutzername ist erforderlich")
                .withValidator(name -> name.length() >= 3, "Mindestens 3 Zeichen")
                .bind(RegistrationFormData::getUsername, RegistrationFormData::setUsername);
        
        binder.forField(vorname)
                .asRequired("Vorname ist erforderlich")
                .bind(RegistrationFormData::getVorname, RegistrationFormData::setVorname);
        
        binder.forField(nachname)
                .asRequired("Nachname ist erforderlich")
                .bind(RegistrationFormData::getNachname, RegistrationFormData::setNachname);


        binder.forField(email)
                .asRequired("E-Mail ist erforderlich")
                .withValidator(new EmailValidator("Ungültige E-Mail-Adresse"))
                .bind(RegistrationFormData::getEmail, RegistrationFormData::setEmail);
        
        binder.forField(password)
                .asRequired("Passwort ist erforderlich")
                .withValidator(pass -> pass.length() >= 8, "Mindestens 8 Zeichen")
                .withValidator(this::containsNumbersAndLetters, "Muss Zahlen und Buchstaben enthalten")
                .bind(RegistrationFormData::getPassword, RegistrationFormData::setPassword);
        
        // Bestätigungspasswort-Validierung (vergleicht mit dem Passwortfeld)
        binder.forField(confirmPassword)
                .asRequired("Passwortbestätigung ist erforderlich")
                .withValidator(confPass -> confPass.equals(password.getValue()), 
                        "Passwörter stimmen nicht überein")
                .bind(user -> "", (user, value) -> {});
    }
    
    private boolean containsNumbersAndLetters(String password) {
        return password.matches(".*\\d.*") && password.matches(".*[a-zA-Z].*");
    }
    
    private void registerUser() {
        try {
            // Validieren und in das Modellobjekt schreiben
            binder.writeBean(formData);
            
            // Eigentliche Registrierungslogik hier
            boolean registrationSuccessful = doRegistration(formData);
            
            if (registrationSuccessful) {
                showSuccessMessage();
                UI.getCurrent().navigate("login");
            } else {
                showErrorMessage("Registrierung fehlgeschlagen. Benutzername möglicherweise bereits vergeben.");
            }
        } catch (ValidationException e) {
            // Die Validierungsfehler werden bereits in der UI angezeigt
        }
    }
    
    private boolean doRegistration(RegistrationFormData data) {
        // Hier würde der tatsächliche Registrierungscode stehen
        // (Datenbankoperationen, Service-Aufrufe usw.)
        return true; // Simulierte erfolgreiche Registrierung
    }
    
    private void showSuccessMessage() {
        Notification notification = Notification.show("Registrierung erfolgreich!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.TOP_CENTER);
    }
    
    private void showErrorMessage(String message) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.TOP_CENTER);
    }
}