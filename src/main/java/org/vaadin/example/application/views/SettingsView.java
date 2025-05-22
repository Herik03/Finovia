package org.vaadin.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Route("settings")
@PageTitle("Einstellungen - Finovia")
@PermitAll
public class SettingsView extends AbstractSideNav {

    private final VerticalLayout contentLayout = new VerticalLayout();
    private final Div settingsContent = new Div();
    private final Map<Tab, Component> tabsToPages = new HashMap<>();

    public SettingsView() {
        super(); // Ruft den Konstruktor der Basisklasse auf

        // Haupttitel
        H1 title = new H1("Einstellungen");

        // Tabs für verschiedene Einstellungsbereiche erstellen
        Tab allgemeinTab = new Tab(createTabContent(VaadinIcon.COG, "Allgemein"));
        Tab anzeigeTab = new Tab(createTabContent(VaadinIcon.DESKTOP, "Anzeige"));
        Tab benachrichtigungenTab = new Tab(createTabContent(VaadinIcon.BELL, "Benachrichtigungen"));
        Tab datenschutzTab = new Tab(createTabContent(VaadinIcon.LOCK, "Datenschutz & Sicherheit"));
        Tab supportTab = new Tab(createTabContent(VaadinIcon.QUESTION_CIRCLE, "Support"));

        Tabs tabs = new Tabs(allgemeinTab, anzeigeTab, benachrichtigungenTab, datenschutzTab, supportTab);
        tabs.setWidthFull();
        tabs.setFlexGrowForEnclosedTabs(1);

        // Seiteninhalte für jeden Tab erstellen
        tabsToPages.put(allgemeinTab, createAllgemeinSettingsContent());
        tabsToPages.put(anzeigeTab, createAnzeigeSettingsContent());
        tabsToPages.put(benachrichtigungenTab, createBenachrichtigungenSettingsContent());
        tabsToPages.put(datenschutzTab, createDatenschutzSettingsContent());
        tabsToPages.put(supportTab, createSupportContent());

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

    private Component createTabContent(VaadinIcon icon, String text) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);

        Icon tabIcon = new Icon(icon);

        layout.add(tabIcon, new Span(text));

        return layout;
    }

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

    private Component createAnzeigeSettingsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(true);

        H2 sectionTitle = new H2("Anzeige Einstellungen");
        sectionTitle.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Margin.Bottom.SMALL);

        // Theme-Auswahl
        Select<String> themeSelect = new Select<>();
        themeSelect.setLabel("Theme");
        themeSelect.setItems("Light", "Dark", "System Default");
        themeSelect.setValue("Light");
        themeSelect.setWidth("300px");

        // Schriftgrößenauswahl
        Select<String> fontSizeSelect = new Select<>();
        fontSizeSelect.setLabel("Schriftgröße");
        fontSizeSelect.setItems("Klein", "Mittel", "Groß");
        fontSizeSelect.setValue("Mittel");
        fontSizeSelect.setWidth("300px");

        // Kontrast
        NumberField contrastField = new NumberField("Kontrast");
        contrastField.setValue(100.0); // Standardwert
        contrastField.setSuffixComponent(new Span("%"));
        contrastField.setWidth("300px");

        Button saveButton = new Button("Änderungen speichern", event -> {
            Notification notification = Notification.show("Anzeige Einstellungen gespeichert");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        content.add(sectionTitle, themeSelect, fontSizeSelect, contrastField, saveButton);
        return content;
    }

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

private Component createSupportContent() {
    VerticalLayout layout = new VerticalLayout();
    layout.setSpacing(true);
    layout.setPadding(false);

    H2 sectionTitle = new H2("Support");

    // Support-Informationen
    Paragraph supportDescription = new Paragraph("Hier können Sie den Kundensupport kontaktieren und Ihre bisherigen Anfragen einsehen.");

    // Neue Supportanfrage erstellen
    H2 newRequestTitle = new H2("Neue Supportanfrage");
    newRequestTitle.getStyle().set("margin-top", "1rem");

    // Felder für die neue Anfrage
    Select<String> categorySelect = new Select<>();
    categorySelect.setLabel("Kategorie");
    categorySelect.setItems("Allgemeine Frage", "Technisches Problem", "Depotproblem", "Konto & Sicherheit", "Sonstiges");
    categorySelect.setValue("Allgemeine Frage");

    TextArea descriptionArea = new TextArea("Beschreibung");
    descriptionArea.setPlaceholder("Beschreiben Sie Ihr Anliegen...");
    descriptionArea.setMinHeight("150px");
    descriptionArea.setWidthFull();

    Upload fileUpload = new Upload();
    fileUpload.setMaxFiles(3);
    fileUpload.setDropLabel(new Span("Dateien hier ablegen (max. 3)"));
    fileUpload.setAcceptedFileTypes("image/*", ".pdf", ".docx");

    Button submitButton = new Button("Anfrage senden");
    submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    submitButton.addClickListener(e -> {
        if (descriptionArea.getValue().isEmpty()) {
            Notification.show("Bitte geben Sie eine Beschreibung ein",
                    3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        Notification.show("Ihre Anfrage wurde erfolgreich gesendet",
                3000, Notification.Position.BOTTOM_START)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        // Formular zurücksetzen
        descriptionArea.clear();
        categorySelect.setValue("Allgemeine Frage");
    });

    HorizontalLayout submitLayout = new HorizontalLayout(submitButton);
    submitLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    submitLayout.setWidthFull();

    // Bisherige Support-Anfragen
    H2 requestHistoryTitle = new H2("Ihre bisherigen Anfragen");
    requestHistoryTitle.getStyle().set("margin-top", "2rem");

    // Support-Anfragen-Liste
    Div requestsContainer = new Div();
    requestsContainer.addClassNames(
            LumoUtility.Border.ALL,
            LumoUtility.BorderRadius.MEDIUM,
            LumoUtility.Padding.MEDIUM,
            LumoUtility.Margin.Top.MEDIUM
    );

    // Beispiel-Anfragen
    requestsContainer.add(createSupportRequestItem(
            "Technisches Problem",
            "Verbindungsprobleme bei der Wertpapiersuche",
            "Offen",
            "2023-06-15"
    ));

    requestsContainer.add(createSupportRequestItem(
            "Konto & Sicherheit",
            "Passwort zurücksetzen funktioniert nicht",
            "In Bearbeitung",
            "2023-05-28"
    ));

    requestsContainer.add(createSupportRequestItem(
            "Allgemeine Frage",
            "Wie ändere ich meine Steuer-ID?",
            "Geschlossen",
            "2023-04-10"
    ));



    // Direkte Kontaktmöglichkeiten
    H2 directContactTitle = new H2("Direkter Kontakt");
    directContactTitle.getStyle().set("margin-top", "2rem");

    VerticalLayout contactInfoLayout = new VerticalLayout();
    contactInfoLayout.setSpacing(false);
    contactInfoLayout.setPadding(false);

    HorizontalLayout emailLayout = new HorizontalLayout(
            new Icon(VaadinIcon.ENVELOPE),
            new Span("support@finovia.de")
    );
    emailLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    emailLayout.setSpacing(true);

    HorizontalLayout phoneLayout = new HorizontalLayout(
            new Icon(VaadinIcon.PHONE),
            new Span("+49 (0) 123 456789")
    );
    phoneLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    phoneLayout.setSpacing(true);

    HorizontalLayout timeLayout = new HorizontalLayout(
            new Icon(VaadinIcon.CLOCK),
            new Span("Mo-Fr: 9:00 - 18:00 Uhr")
    );
    timeLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    timeLayout.setSpacing(true);

    contactInfoLayout.add(emailLayout, phoneLayout, timeLayout);

    // Komponenten zum Layout hinzufügen
    layout.add(
            sectionTitle,
            supportDescription,
            newRequestTitle,
            categorySelect,
            descriptionArea,
            fileUpload,
            submitLayout,
            requestHistoryTitle,
            requestsContainer,
            directContactTitle,
            contactInfoLayout
    );

    return layout;
}

/**
 * Erstellt ein Element für eine Support-Anfrage in der Liste
 */
private Div createSupportRequestItem(String category, String description, String status, String date) {
    Div requestItem = new Div();
    requestItem.addClassNames(
            LumoUtility.BorderRadius.SMALL,
            LumoUtility.Padding.SMALL,
            LumoUtility.Margin.Vertical.SMALL,
            LumoUtility.Background.CONTRAST_5
    );
    
    // Header mit Kategorie und Status
    HorizontalLayout headerLayout = new HorizontalLayout();
    headerLayout.setWidthFull();
    headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
    
    Span categorySpan = new Span(category);
    categorySpan.addClassNames(LumoUtility.FontWeight.BOLD);
    
    Span statusSpan = new Span(status);
    if ("Offen".equals(status)) {
        statusSpan.addClassNames(LumoUtility.TextColor.ERROR);
    } else if ("In Bearbeitung".equals(status)) {
        statusSpan.addClassNames(LumoUtility.TextColor.WARNING);
    } else {
        statusSpan.addClassNames(LumoUtility.TextColor.SUCCESS);
    }
    
    headerLayout.add(categorySpan, statusSpan);
    
    // Beschreibung
    Paragraph descriptionParagraph = new Paragraph(description);
    descriptionParagraph.getStyle().set("margin", "0.5rem 0");
    
    // Footer mit Datum und Details-Button
    HorizontalLayout footerLayout = new HorizontalLayout();
    footerLayout.setWidthFull();
    footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
    footerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    
    Span dateSpan = new Span("Erstellt am: " + date);
    dateSpan.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
    
    Button detailsButton = new Button("Details anzeigen");
    detailsButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
    
    footerLayout.add(dateSpan, detailsButton);
    
    // Alles zum Container hinzufügen
    requestItem.add(headerLayout, descriptionParagraph, footerLayout);
    
    return requestItem;
}
}