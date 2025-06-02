package org.vaadin.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.*;
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

    /**
     * Layout für den Hauptinhalt
     */
    private final VerticalLayout contentLayout = new VerticalLayout();

    /**
     * Container für den aktuell ausgewählten Einstellungsbereich
     */
    private final Div settingsContent = new Div();

    /**
     * Zuordnung von Tabs zu Inhaltskomponenten
     */
    private final Map<Tab, Component> tabsToPages = new HashMap<>();

    /**
     * Der Service für Support-Anfragen
     */
    private final Support supportService;

    /**
     * Der Service für E-Mail-Kommunikation
     */
    private final EmailService emailService;

    /**
     * Der Service für Nutzer-Verwaltung
     */
    private final NutzerService nutzerService;

    /**
     * Der Security-Service für Authentifizierung
     */
    private final SecurityService securityService;

    /**
     * Konstruktor für die SettingsView-Klasse.
     * Initialisiert die Ansicht mit Tabs und deren Inhalten.
     *
     * @param supportService  Der Service für Support-Anfragen
     * @param emailService    Der Service für E-Mail-Kommunikation
     * @param nutzerService   Der Service für Nutzer-Verwaltung
     * @param securityService Der Security-Service für Authentifizierung
     */
    @Autowired
    public SettingsView(Support supportService, EmailService emailService,
                        NutzerService nutzerService, SecurityService securityService) {
        super(securityService);
        // Ruft den Konstruktor der Basisklasse auf
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
        Tab supportTab = new Tab(createTabContent(VaadinIcon.QUESTION_CIRCLE, "Support"));

        Tabs tabs = new Tabs(allgemeinTab, anzeigeTab, benachrichtigungenTab, supportTab);
        tabs.setWidthFull();
        tabs.setFlexGrowForEnclosedTabs(1);

        // Seiteninhalte für jeden Tab erstellen
        tabsToPages.put(allgemeinTab, createAllgemeinSettingsContent());
        tabsToPages.put(anzeigeTab, createBenutzerSettingsContent());
        tabsToPages.put(benachrichtigungenTab, createBenachrichtigungenSettingsContent());
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

        // Trennlinie für den Admin-Login-Bereich
        Div adminDivider = new Div();
        adminDivider.addClassNames(
                LumoUtility.Background.CONTRAST_10,
                LumoUtility.Margin.Vertical.MEDIUM
        );
        adminDivider.setHeight("1px");
        adminDivider.setWidthFull();

        // Bereich für Admin-Login hinzufügen
        H3 adminLoginTitle = new H3("Admin Login");

        Paragraph adminLoginInfo = new Paragraph(
                "Hier können Sie sich als Administrator anmelden, um erweiterte Funktionen zu nutzen."
        );

        // Admin-Status-Anzeige und Abmelde-Button
        HorizontalLayout adminStatusLayout = new HorizontalLayout();
        adminStatusLayout.setSpacing(true);
        adminStatusLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        adminStatusLayout.setMargin(true);

        // Admin-Status-Anzeige
        Span adminStatusLabel = new Span("Admin: ");
        adminStatusLabel.getStyle().set("font-weight", "bold");

        Span adminStatusValue = new Span("Nein");
        adminStatusValue.getStyle().set("color", "var(--lumo-error-color)");

        // Admin-Abmelde-Button
        Button adminLogoutBtn = new Button("Admin abmelden", VaadinIcon.USER_CARD.create());
        adminLogoutBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
        adminLogoutBtn.setVisible(false);
        adminLogoutBtn.addClickListener(e -> {
            if (securityService.removeAdminRole()) {
                adminStatusValue.setText("Nein");
                adminStatusValue.getStyle().set("color", "var(--lumo-error-color)");
                adminLogoutBtn.setVisible(false);
                Notification.show("Admin-Rechte wurden entfernt.",
                                3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
        });

        // Überprüfen, ob der Benutzer Admin-Rechte hat
        if (securityService.isAdmin()) {
            adminStatusValue.setText("Ja");
            adminStatusValue.getStyle().set("color", "var(--lumo-success-color)");
            adminLogoutBtn.setVisible(true);
        }

        adminStatusLayout.add(adminStatusLabel, adminStatusValue, adminLogoutBtn);

        Button adminLoginButton = new Button("Anmelden", event -> {
            openAdminLoginDialog();
        });
        adminLoginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Trennlinie für den Konto-Löschbereich
        Div deleteDivider = new Div();
        deleteDivider.addClassNames(
                LumoUtility.Background.CONTRAST_10,
                LumoUtility.Margin.Vertical.MEDIUM
        );
        deleteDivider.setHeight("1px");
        deleteDivider.setWidthFull();

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
                adminDivider,
                adminLoginTitle,
                adminLoginInfo,
                adminStatusLayout,
                adminLoginButton,
                deleteDivider,
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
                            securityService.logout();
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
     * Öffnet einen Dialog für den Admin-Login.
     * Nach Eingabe des korrekten Admin-Passworts wird dem Nutzer die Admin-Rolle hinzugefügt
     * und ein Logout erzwungen, damit die Änderungen wirksam werden.
     */
    private void openAdminLoginDialog() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Admin Login");
        dialog.setText("Bitte geben Sie das Admin-Passwort ein, um Admin-Rechte zu erhalten.");

        // Passwortfeld für Admin-Passwort
        PasswordField passwordField = new PasswordField("Admin-Passwort");
        passwordField.setWidthFull();

        dialog.add(passwordField);

        dialog.setCancelable(true);
        dialog.setCancelText("Abbrechen");

        dialog.setConfirmText("Login");
        dialog.setConfirmButtonTheme("primary");

        dialog.addConfirmListener(event -> {
            // Überprüfen, ob das eingegebene Passwort korrekt ist
            if ("administrator1".equals(passwordField.getValue())) {
                // Aktuellen Benutzer abrufen
                UserDetails userDetails = securityService.getAuthenticatedUser();
                if (userDetails != null) {
                    String username = userDetails.getUsername();
                    Nutzer nutzer = nutzerService.findByUsername(username);

                    if (nutzer != null) {
                        // Prüfen, ob der Nutzer bereits die Admin-Rolle hat
                        if (nutzer.getRoles() != null && nutzer.getRoles().contains("ADMIN")) {
                            Notification.show("Sie haben bereits Admin-Rechte.",
                                            3000, Notification.Position.MIDDLE)
                                    .addThemeVariants(NotificationVariant.LUMO_CONTRAST);
                        } else {
                            // Admin-Rolle hinzufügen
                            java.util.List<String> roles = new java.util.ArrayList<>(nutzer.getRoles());
                            roles.add("ADMIN");
                            nutzer.setRoles(roles);
                            nutzerService.speichereNutzer(nutzer);

                            // Erfolgsmeldung mit Hinweis auf neues Login
                            Notification notification = new Notification();
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                            VerticalLayout notificationContent = new VerticalLayout();
                            notificationContent.setSpacing(false);
                            notificationContent.setPadding(false);

                            H4 title = new H4("Admin-Rechte hinzugefügt");
                            title.getStyle().set("margin", "0");
                            Paragraph message = new Paragraph("Bitte melden Sie sich erneut an, um die Admin-Funktionen zu aktivieren.");

                            Button logoutButton = new Button("Jetzt abmelden", e -> {
                                notification.close();
                                // Abmeldung erzwingen
                                securityService.logout();
                            });
                            logoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                            notificationContent.add(title, message, logoutButton);
                            notification.add(notificationContent);

                            notification.setPosition(Notification.Position.MIDDLE);
                            notification.setDuration(0); // Nicht automatisch schließen
                            notification.open();

                            // Alternative: Automatisch nach 5 Sekunden abmelden
                            UI.getCurrent().getPage().executeJs(
                                    "setTimeout(function() { window.location.href = '/logout'; }, 5000);"
                            );
                        }
                    }
                }
            } else {
                Notification.show("Falsches Admin-Passwort.",
                                3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
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
}