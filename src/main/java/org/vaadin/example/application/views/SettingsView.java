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
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Support;
import org.vaadin.example.application.models.SupportRequest;
import org.vaadin.example.application.services.EmailService;
import com.vaadin.flow.component.UI;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("settings")
@PageTitle("Einstellungen - Finovia")
@PermitAll
public class SettingsView extends AbstractSideNav {

    /** Der Service für Support-Anfragen */
    @Autowired
private final Support supportService;

    private final VerticalLayout contentLayout = new VerticalLayout();
    private final Div settingsContent = new Div();
    private final Map<Tab, Component> tabsToPages = new HashMap<>();

    public SettingsView(Support supportService) {
        super(); // Ruft den Konstruktor der Basisklasse auf
        this.supportService = supportService;
        
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
    
    content.add(sectionTitle, infoText, navigateToUserViewButton);
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

@Autowired
private EmailService emailService;

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
    
    // Container für die Anfragen
    Div requestsContainer = new Div();
    requestsContainer.addClassNames(
            LumoUtility.Border.ALL,
            LumoUtility.BorderRadius.MEDIUM,
            LumoUtility.Padding.MEDIUM,
            LumoUtility.Margin.Top.MEDIUM
    );
    
    // Handler für das Absenden der Anfrage
    // Im submitButton-Listener in der createSupportContent-Methode:
    submitButton.addClickListener(e -> {
        if (descriptionArea.getValue().isEmpty()) {
            Notification.show("Bitte geben Sie eine Beschreibung ein",
                    3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Support-Anfrage erstellen
        SupportRequest newRequest = supportService.createRequest(
                categorySelect.getValue(),
                descriptionArea.getValue()
        );

        // E-Mail senden
        boolean emailSent = emailService.sendSupportRequest(newRequest, "benutzer@example.com");

        // Notification anzeigen
        if (emailSent) {
            Notification.show("Ihre Anfrage wurde erfolgreich gesendet",
                    3000, Notification.Position.BOTTOM_START)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Ihre Anfrage wurde gespeichert, konnte aber nicht per E-Mail gesendet werden",
                    3000, Notification.Position.BOTTOM_START)
                    .addThemeVariants(NotificationVariant.LUMO_WARNING);
        }

        // Anfragen-Liste aktualisieren
        updateRequestsContainer(requestsContainer);

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

    // Initiale Anzeige der Anfragen
    updateRequestsContainer(requestsContainer);

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
 * Aktualisiert den Container mit den Support-Anfragen
 */
private void updateRequestsContainer(Div requestsContainer) {
    requestsContainer.removeAll();
    
    List<SupportRequest> requests = supportService.getAllRequests();
    
    if (requests.isEmpty()) {
        Span noRequestsSpan = new Span("Keine Anfragen vorhanden");
        noRequestsSpan.addClassNames(
                LumoUtility.Padding.MEDIUM,
                LumoUtility.TextColor.SECONDARY
        );
        requestsContainer.add(noRequestsSpan);
    } else {
        for (int i = 0; i < requests.size(); i++) {
            SupportRequest request = requests.get(i);

            Div requestItem = createSupportRequestItem(
                    request.getCategory(),
                    request.getDescription(),
                    request.getStatus(),
                    request.getCreationDate(),
                    i
            );
            
            requestsContainer.add(requestItem);
        }
    }
}

/**
 * Erstellt ein Element für eine Support-Anfrage in der Liste
 */
private Div createSupportRequestItem(String category, String description, String status, String date, int requestIndex) {
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
    
    // Status-Änderung ermöglichen
    Select<String> statusSelect = new Select<>();
    statusSelect.setItems("Offen", "In Bearbeitung", "Geschlossen");
    statusSelect.setValue(status);
    statusSelect.addValueChangeListener(event -> {
        if (supportService.updateRequestStatus(requestIndex, event.getValue())) {
            Notification.show("Status aktualisiert", 
                    2000, Notification.Position.BOTTOM_START)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    });
    
    footerLayout.add(dateSpan, statusSelect, detailsButton);
    
    // Kommentare anzeigen, falls vorhanden
    SupportRequest request = supportService.getAllRequests().get(requestIndex);
    if (request.getComments() != null && !request.getComments().isEmpty()) {
        Div commentsDiv = new Div();
        commentsDiv.addClassNames(
                LumoUtility.Background.CONTRAST_10,
                LumoUtility.BorderRadius.SMALL,
                LumoUtility.Padding.SMALL,
                LumoUtility.Margin.Top.SMALL
        );
        
        H5 commentsTitle = new H5("Kommentare");
        commentsTitle.getStyle().set("margin-top", "0");
        commentsDiv.add(commentsTitle);
        
        for (String comment : request.getComments()) {
            Paragraph commentParagraph = new Paragraph(comment);
            commentParagraph.addClassNames(
                    LumoUtility.Padding.XSMALL,
                    LumoUtility.Border.BOTTOM
            );
            commentsDiv.add(commentParagraph);
        }
        
        requestItem.add(commentsDiv);
    }
    
    return requestItem;
}
}