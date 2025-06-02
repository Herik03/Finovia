package org.vaadin.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.example.application.classes.Support;
import org.vaadin.example.application.classes.SupportRequest;
import org.vaadin.example.application.services.EmailService;

import java.util.List;

/**
 * Komponente zur Darstellung des Support-Bereichs
 */
@org.springframework.stereotype.Component
public class SupportView extends VerticalLayout {

    private final Support supportService;
    private final EmailService emailService;

    /**
     * Erstellt eine neue SupportView-Komponente
     *
     * @param supportService Der Service für Support-Anfragen
     * @param emailService Der Service für E-Mail-Kommunikation
     */
    public SupportView(Support supportService, EmailService emailService) {
        this.supportService = supportService;
        this.emailService = emailService;

        setSpacing(true);
        setPadding(false);

        add(createSupportContent());
    }

    /**
     * Erstellt den Inhalt des Support-Bereichs
     */
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

        // Bisherige Support-Anfragen
        H2 requestHistoryTitle = new H2("Ihre bisherigen Anfragen");
        requestHistoryTitle.getStyle().set("margin-top", "2rem");

        // Button zum manuellen Aktualisieren der Anfragenliste
        Button refreshButton = new Button("Aktualisieren", new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        refreshButton.addClickListener(e -> updateRequestsContainer(requestsContainer));

        // Layout für Überschrift und Aktualisieren-Button
        HorizontalLayout requestHistoryHeader = new HorizontalLayout(requestHistoryTitle, refreshButton);
        requestHistoryHeader.setWidthFull();
        requestHistoryHeader.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        requestHistoryHeader.setAlignItems(FlexComponent.Alignment.CENTER);

        // Initiale Anzeige der Anfragen
        updateRequestsContainer(requestsContainer);

        // Starte eine periodische Aktualisierung der Anfragen, um automatische Antworten zu erfassen
        UI ui = UI.getCurrent();
        if (ui != null) {
            ui.setPollInterval(10000); // Alle 10 Sekunden aktualisieren
            ui.addPollListener(event -> {
                // Führe die Aktualisierung nur im UI-Thread durch
                ui.access(() -> updateRequestsContainer(requestsContainer));
            });
        }


        // Handler für das Absenden der Anfrage
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

            // Sofortige Aktualisierung der Anfragenliste
            updateRequestsContainer(requestsContainer);

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

            // Formular zurücksetzen
            descriptionArea.clear();
            categorySelect.setValue("Allgemeine Frage");

            // Scrolle zum Anfragenbereich, damit Benutzer die neue Anfrage sehen können
            requestsContainer.scrollIntoView();
        });

        HorizontalLayout submitLayout = new HorizontalLayout(submitButton);
        submitLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        submitLayout.setWidthFull();

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
                requestHistoryHeader,
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

        // Footer mit Datum, Status-Auswahl, Details-Button und Löschen-Button
        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.setWidthFull();
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        footerLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        Span dateSpan = new Span("Erstellt am: " + date);
        dateSpan.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);

        // Buttons-Layout für Details und Löschen
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);

        Button detailsButton = new Button("Details anzeigen");
        detailsButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        // Löschen-Button hinzufügen
        Button deleteButton = new Button("Löschen");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.getElement().getStyle().set("margin-left", "8px");

        // Löschen-Button-Funktionalität
        deleteButton.addClickListener(event -> {
            // Bestätigungsdialog anzeigen
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Anfrage löschen");
            dialog.setText("Möchten Sie diese Support-Anfrage wirklich löschen?");

            dialog.setCancelable(true);
            dialog.setCancelText("Abbrechen");

            dialog.setConfirmText("Löschen");
            dialog.setConfirmButtonTheme("error primary");

            dialog.addConfirmListener(confirmEvent -> {
                // Anfrage löschen
                supportService.deleteRequest(requestIndex);

                // Erfolgsbenachrichtigung anzeigen
                Notification.show("Anfrage wurde gelöscht",
                                3000, Notification.Position.BOTTOM_START)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                // Container aktualisieren
                Div container = (Div) requestItem.getParent().get();
                updateRequestsContainer(container);
            });

            dialog.open();
        });

        buttonsLayout.add(detailsButton, deleteButton);

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

        footerLayout.add(dateSpan, statusSelect, buttonsLayout);

        // Die UI-Elemente zum requestItem hinzufügen
        requestItem.add(headerLayout, descriptionParagraph, footerLayout);

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
