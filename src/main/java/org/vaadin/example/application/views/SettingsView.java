package org.vaadin.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Support;
import org.vaadin.example.application.services.EmailService;
import org.vaadin.example.application.services.NutzerService;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Die SettingsView-Klasse stellt die Einstellungsseite der Anwendung dar.
 * Sie enthält verschiedene Tabs für unterschiedliche Einstellungsbereiche.
 */
@Route("settings")
@PageTitle("Einstellungen - Finovia")
@PermitAll
public class SettingsView extends AbstractSideNav {

    /** Layout für den Hauptinhalt */
    private final VerticalLayout contentLayout = new VerticalLayout();
    
    /** Container für den aktuell ausgewählten Einstellungsbereich */
    private final Div settingsContent = new Div();
    
    /** Zuordnung von Tabs zu Inhaltskomponenten */
    private final Map<Tab, Component> tabsToPages = new HashMap<>();

    /** Der Service für Support-Anfragen */
    private final Support supportService;
    
    /** Der Service für E-Mail-Kommunikation */
    private final EmailService emailService;
    
    /** Der Service für Nutzer-Verwaltung */
    private final NutzerService nutzerService;
    
    /** Der Security-Service für Authentifizierung */
    private final SecurityService securityService;

    /**
     * Konstruktor für die SettingsView-Klasse.
     * Initialisiert die Ansicht mit Tabs und deren Inhalten.
     * 
     * @param supportService Der Service für Support-Anfragen
     * @param emailService Der Service für E-Mail-Kommunikation
     * @param nutzerService Der Service für Nutzer-Verwaltung
     * @param securityService Der Security-Service für Authentifizierung
     */
    @Autowired
    public SettingsView(Support supportService, EmailService emailService, 
                        NutzerService nutzerService, SecurityService securityService) {
        super(); // Ruft den Konstruktor der Basisklasse auf
        this.supportService = supportService;
        this.emailService = emailService;
        this.nutzerService = nutzerService;
        this.securityService = securityService;
        
        // Haupttitel
        H1 title = new H1("Einstellungen");

        // Tabs für verschiedene Einstellungsbereiche erstellen
        Tab allgemeinTab = new Tab(createTabContent(VaadinIcon.COG, "Allgemein"));
        Tab anzeigeTab = new Tab(createTabContent(VaadinIcon.DESKTOP, "Benutzerverwaltung"));
        Tab benachrichtigungenTab = new Tab(createTabContent(VaadinIcon.BELL, "Benachrichtigungen"));
        Tab datenschutzTab = new Tab(createTabContent(VaadinIcon.LOCK, "Datenschutz & Sicherheit"));
        Tab supportTab = new Tab(createTabContent(VaadinIcon.QUESTION_CIRCLE, "Support"));

        Tabs tabs = new Tabs(allgemeinTab, anzeigeTab, benachrichtigungenTab, datenschutzTab, supportTab);
        tabs.setWidthFull();
        tabs.setFlexGrowForEnclosedTabs(1);

        // Seiteninhalte für jeden Tab erstellen
        tabsToPages.put(allgemeinTab, createAllgemeinSettingsContent());
        tabsToPages.put(anzeigeTab, createBenutzerSettingsContent());
        tabsToPages.put(benachrichtigungenTab, createBenachrichtigungenSettingsContent());
        tabsToPages.put(datenschutzTab, createDatenschutzSettingsContent());
        
        // SupportView als eigenständige Komponente einbinden
        tabsToPages.put(supportTab, new SupportView(supportService, emailService));

        // Tab-Wechsel-Event-Handler
        tabs.addSelectedChangeListener(event -> {
            settingsContent.removeAll();
            settingsContent.add(tabsToPages.get(tabs.getSelectedTab()));
        });

        // Standard-Tab anzeigen
        settingsContent.add(tabsToPages.get(allgemeinTab));

        // Styling und Layout für den Inhaltsbereich
        settingsContent.addClassNames(
                LumoUtility.Background.BASE,
                LumoUtility.BoxShadow.SMALL,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Padding.LARGE
        );
        settingsContent.setWidthFull();

        // Komponenten zum Content-Layout hinzufügen
        contentLayout.setAlignItems(FlexComponent.Alignment.START);
        contentLayout.setPadding(true);
        contentLayout.setSpacing(true);
        contentLayout.setWidthFull();
        contentLayout.add(title, tabs, settingsContent);

        // Content-Layout zum Hauptinhaltsbereich hinzufügen
        addToMainContent(contentLayout);
    }

    /**
     * Erstellt den Inhalt eines Tabs mit Icon und Text.
     * 
     * @param icon Das Icon für den Tab
     * @param text Der Text für den Tab
     * @return Eine Komponente für den Tab-Inhalt
     */
    private Component createTabContent(VaadinIcon icon, String text) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);

        Icon tabIcon = new Icon(icon);

        layout.add(tabIcon, new Span(text));

        return layout;
    }

    /**
     * Erstellt den Inhalt für den Allgemein-Tab.
     * 
     * @return Eine Komponente mit allgemeinen Einstellungen
     */
    private Component createAllgemeinSettingsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(true);

        H2 sectionTitle = new H2("Allgemeine Einstellungen");
        sectionTitle.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Margin.Bottom.SMALL);

        // Sprachauswahl
        Select<String> languageSelect = new Select<>();
        languageSelect.setLabel("Sprache");
        languageSelect.setItems("Deutsch", "Englisch", "Französisch", "Spanisch");
        languageSelect.setValue("Deutsch");
        languageSelect.setWidth("300px");

        // Zeitzonenauswahl
        ComboBox<String> timeZoneSelect = new ComboBox<>("Zeitzone");
        timeZoneSelect.setItems("Europa/Berlin", "UTC", "Amerika/New_York");
        timeZoneSelect.setValue("Europa/Berlin");
        timeZoneSelect.setWidth("300px");

        // Währungsauswahl
        Select<String> currencySelect = new Select<>();
        currencySelect.setLabel("Währung");
        currencySelect.setItems("EUR", "USD", "GBP", "CHF");
        currencySelect.setValue("EUR");
        currencySelect.setWidth("300px");

        Button saveButton = new Button("Änderungen speichern", event -> {
            Notification notification = Notification.show("Allgemeine Einstellungen gespeichert");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        content.add(sectionTitle, languageSelect, timeZoneSelect, currencySelect, saveButton);
        return content;
    }

    /**
     * Erstellt den Inhalt für den Benutzerverwaltung-Tab.
     * 
     * @return Eine Komponente mit Benutzereinstellungen
     */
    private Component createBenutzerSettingsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(true);

        H2 sectionTitle = new H2("Benutzereinstellungen");
        sectionTitle.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Margin.Bottom.SMALL);
        
        // Info-Text über Benutzerprofil
        Paragraph infoText = new Paragraph("Hier können Sie Ihre persönlichen Benutzerdaten einsehen und bearbeiten.");
        
        // Button, der zur UserView navigiert
        Button navigateToUserViewButton = new Button("Zum Benutzerprofil", e -> 
            UI.getCurrent().navigate("user")
        );
        navigateToUserViewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        // Trennlinie für den Konto-Löschbereich
        Div divider = new Div();
        divider.addClassNames(
                LumoUtility.Background.CONTRAST_10, 
                LumoUtility.Margin.Vertical.MEDIUM
        );
        divider.setHeight("1px");
        divider.setWidthFull();
        
        // Bereich für Konto-Löschung hinzufügen
        H3 deleteAccountTitle = new H3("Konto löschen");
        deleteAccountTitle.addClassNames(LumoUtility.TextColor.ERROR);
        
        Paragraph deleteAccountWarning = new Paragraph(
                "Achtung: Wenn Sie Ihr Konto löschen, werden alle Ihre Daten, " +
                "einschließlich Depots, Watchlist und persönliche Einstellungen, " +
                "unwiderruflich gelöscht. Diese Aktion kann nicht rückgängig gemacht werden."
        );
        deleteAccountWarning.addClassNames(LumoUtility.TextColor.ERROR);
        
        Button deleteAccountButton = new Button("Konto löschen", event -> {
            openDeleteAccountDialog();
        });
        deleteAccountButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        
        content.add(
                sectionTitle, 
                infoText, 
                navigateToUserViewButton, 
                divider,
                deleteAccountTitle,
                deleteAccountWarning,
                deleteAccountButton
        );
        
        return content;
    }

    /**
     * Öffnet einen Bestätigungsdialog zum Löschen des Benutzerkontos.
     * Nach Bestätigung und Passworteingabe wird das Konto gelöscht.
     */
    private void openDeleteAccountDialog() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Konto löschen");
        dialog.setText("Sind Sie sicher, dass Sie Ihr Konto und alle damit verbundenen Daten löschen möchten? " +
                "Diese Aktion kann nicht rückgängig gemacht werden.");
        
        // Passwortfeld für zusätzliche Sicherheit
        PasswordField passwordField = new PasswordField("Bitte geben Sie Ihr Passwort ein, um fortzufahren");
        passwordField.setWidthFull();
        
        dialog.add(passwordField);
        
        dialog.setCancelable(true);
        dialog.setCancelText("Abbrechen");
        
        dialog.setConfirmText("Konto unwiderruflich löschen");
        dialog.setConfirmButtonTheme("error primary");
        
        dialog.addConfirmListener(event -> {
            // Aktuellen Benutzer abrufen
            UserDetails userDetails = securityService.getAuthenticatedUser();
            if (userDetails != null) {
                String username = userDetails.getUsername();
                
                // Passwort überprüfen
                if (nutzerService.authenticate(username, passwordField.getValue())) {
                    Nutzer nutzer = nutzerService.findByUsername(username);
                    if (nutzer != null) {
                        // Benutzer und alle zugehörigen Daten löschen
                        boolean deleted = nutzerService.nutzerVollstaendigLoeschen(nutzer.getId());
                        
                        if (deleted) {
                            // Erfolgsmeldung anzeigen
                            Notification.show("Ihr Konto wurde erfolgreich gelöscht.", 
                                    5000, Notification.Position.MIDDLE)
                                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            
                            // Benutzer ausloggen und zur Startseite navigieren
                           // securityService.logout();
                        } else {
                            Notification.show("Fehler beim Löschen des Kontos. Bitte kontaktieren Sie den Support.", 
                                    5000, Notification.Position.MIDDLE)
                                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
                        }
                    }
                } else {
                    Notification.show("Falsches Passwort. Das Konto wurde nicht gelöscht.", 
                            3000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        });
        
        dialog.open();
    }

    /**
     * Erstellt den Inhalt für den Benachrichtigungen-Tab.
     * 
     * @return Eine Komponente mit Benachrichtigungseinstellungen
     */
    private Component createBenachrichtigungenSettingsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(true);

        H2 sectionTitle = new H2("Benachrichtigungseinstellungen");
        sectionTitle.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Margin.Bottom.SMALL);

        // E-Mail-Benachrichtigungen aktivieren/deaktivieren
        Checkbox emailNotificationsCheckbox = new Checkbox("E-Mail Benachrichtigungen aktivieren");
        emailNotificationsCheckbox.setValue(true);

        // Push-Benachrichtigungen aktivieren/deaktivieren
        Checkbox pushNotificationsCheckbox = new Checkbox("Push Benachrichtigungen aktivieren");
        pushNotificationsCheckbox.setValue(false);

        // Benachrichtigungszeitpunkt auswählen
        TimePicker notificationTime = new TimePicker("Benachrichtigungszeitpunkt");
        notificationTime.setValue(LocalTime.of(10, 0));
        notificationTime.setStep(Duration.ofMinutes(30));
        notificationTime.setWidth("300px");

        Button saveButton = new Button("Änderungen speichern", event -> {
            Notification notification = Notification.show("Benachrichtigungseinstellungen gespeichert");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        content.add(sectionTitle, emailNotificationsCheckbox, pushNotificationsCheckbox, notificationTime, saveButton);
        return content;
    }

    /**
     * Erstellt den Inhalt für den Datenschutz-Tab.
     * 
     * @return Eine Komponente mit Datenschutzeinstellungen
     */
    private Component createDatenschutzSettingsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(true);

        H2 sectionTitle = new H2("Datenschutz & Sicherheit");
        sectionTitle.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Margin.Bottom.SMALL);

        // Zwei-Faktor-Authentifizierung aktivieren/deaktivieren
        Checkbox twoFactorAuthCheckbox = new Checkbox("Zwei-Faktor-Authentifizierung aktivieren");
        twoFactorAuthCheckbox.setValue(false);

        // Datenverschlüsselung aktivieren/deaktivieren
        Checkbox dataEncryptionCheckbox = new Checkbox("Datenverschlüsselung aktivieren");
        dataEncryptionCheckbox.setValue(true);

        // Datenlöschungsanfrage stellen
        Button deleteDataButton = new Button("Datenlöschung beantragen");
        deleteDataButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteDataButton.addClickListener(event -> {
            Notification notification = Notification.show("Anfrage zur Datenlöschung wurde gesendet");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        content.add(sectionTitle, twoFactorAuthCheckbox, dataEncryptionCheckbox, deleteDataButton);
        return content;
    }

    /**
     * Erstellt den Inhalt für den API-Tab.
     * 
     * @return Eine Komponente mit API-Einstellungen
     */
    private Component createApiSettingsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(true);

        H2 sectionTitle = new H2("API & Datenquellen");
        sectionTitle.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Margin.Bottom.SMALL);

        // API-Schlüssel anzeigen/generieren
        Paragraph apiKeyParagraph = new Paragraph("Ihr API-Schlüssel: (nicht angezeigt)");
        Button generateApiKeyButton = new Button("API-Schlüssel generieren");

        // Datenquellen verwalten (Beispiel: Google Analytics, etc.)
        Paragraph dataSourcesParagraph = new Paragraph("Verbundene Datenquellen: Keine");
        Button manageDataSourcesButton = new Button("Datenquellen verwalten");

        content.add(sectionTitle, apiKeyParagraph, generateApiKeyButton, dataSourcesParagraph, manageDataSourcesButton);
        return content;
    }
}