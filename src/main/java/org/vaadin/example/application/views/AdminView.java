package org.vaadin.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.services.Support;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.SupportRequest;
import org.vaadin.example.application.services.EmailService;
import org.vaadin.example.application.services.NutzerService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.VaadinSession;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin-Bereich der Anwendung.
 * Die Ansicht besteht aus mehreren Tabs, die verschiedene Verwaltungsfunktionen bieten.
 * Die Hauptfunktionen sind die Verwaltung von Support-Anfragen und die Nutzerverwaltung.
 */
@Route("admin")
@PageTitle("Admin - Finovia")
@RolesAllowed("ROLE_ADMIN")
public class AdminView extends AbstractSideNav {

    private final Support supportService;
    private final EmailService emailService;
    private final NutzerService nutzerService;
    private final Map<Tab, Component> tabsToPages = new HashMap<>();
    private final VerticalLayout contentLayout = new VerticalLayout();
    private final Div mainContentDiv = new Div();
    private Grid<SupportRequest> supportGrid;
    private Grid<Nutzer> nutzerGrid;
    private VerticalLayout detailLayout;
    private SupportRequest selectedRequest;

    /**
     * Konstruktor für die AdminView.
     *
     * @param supportService  Service für Support-Anfragen
     * @param emailService    Service für E-Mail-Kommunikation
     * @param nutzerService   Service für Nutzerverwaltung
     * @param securityService Service für Sicherheitsfunktionen
     */
    @Autowired
    public AdminView(Support supportService, EmailService emailService, NutzerService nutzerService, SecurityService securityService) {
        super(securityService);
        this.supportService = supportService;
        this.emailService = emailService;
        this.nutzerService = nutzerService;


        initializeView();
    }

    /**
     * Initialisiert die Ansicht mit Tabs und Inhalten.
     */
    private void initializeView() {
        // Haupttitel
        H2 title = new H2("Admin-Bereich");
        title.addClassNames(LumoUtility.Margin.Bottom.MEDIUM);

        // Tabs für verschiedene Verwaltungsbereiche
        Tab supportTab = new Tab(createTabContent(VaadinIcon.QUESTION_CIRCLE, "Support-Anfragen"));
        Tab usersTab = new Tab(createTabContent(VaadinIcon.USERS, "Benutzerverwaltung"));

        Tabs tabs = new Tabs(supportTab, usersTab);
        tabs.setWidthFull();
        tabs.setFlexGrowForEnclosedTabs(1);

        // Tab-Inhalte erstellen
        tabsToPages.put(supportTab, createSupportManagementContent());
        tabsToPages.put(usersTab, createUserManagementContent());

        // Tab-Wechsel-Event
        tabs.addSelectedChangeListener(event -> {
            mainContentDiv.removeAll();
            mainContentDiv.add(tabsToPages.get(tabs.getSelectedTab()));
        });

        // Standardtab anzeigen
        mainContentDiv.add(tabsToPages.get(supportTab));

        // Styling für den Inhaltsbereich
        mainContentDiv.addClassNames(
                LumoUtility.Background.BASE,
                LumoUtility.BoxShadow.SMALL,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Padding.LARGE
        );
        mainContentDiv.setWidthFull();

        // Layout zusammenbauen
        contentLayout.setAlignItems(FlexComponent.Alignment.START);
        contentLayout.setPadding(true);
        contentLayout.setSpacing(true);
        contentLayout.setWidthFull();
        contentLayout.add(title, tabs, mainContentDiv);

        // ContentLayout zum Hauptinhaltsbereich hinzufügen
        addToMainContent(contentLayout);
    }

    /**
     * Erstellt den Inhalt eines Tabs mit Icon und Text.
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
     * Erstellt den Inhalt für die Support-Anfragenverwaltung.
     */
    private Component createSupportManagementContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setWidthFull();

        // Zweispaltiges Layout: Links die Grid mit allen Anfragen, rechts die Details
        HorizontalLayout splitLayout = new HorizontalLayout();
        splitLayout.setWidthFull();
        splitLayout.setHeight("700px");

        // Grid für die Support-Anfragen
        supportGrid = new Grid<>();
        supportGrid.setWidthFull();
        supportGrid.setHeight("100%");

        supportGrid.addColumn(SupportRequest::getTicketId).setHeader("Ticket-ID").setAutoWidth(true);
        supportGrid.addColumn(SupportRequest::getCategory).setHeader("Kategorie").setAutoWidth(true);
        supportGrid.addColumn(SupportRequest::getStatus).setHeader("Status").setAutoWidth(true).setKey("status");
        supportGrid.addColumn(SupportRequest::getCreationDate).setHeader("Erstellt am").setAutoWidth(true);

        // Status-Column mit Farbe basierend auf Status
        supportGrid.getColumnByKey("status").setRenderer(
                new com.vaadin.flow.data.renderer.ComponentRenderer<>(request -> {
                    Span statusBadge = new Span(request.getStatus());
                    statusBadge.getElement().getThemeList().add("badge");

                    switch (request.getStatus().toLowerCase()) {
                        case "offen":
                            statusBadge.getElement().getThemeList().add("error");
                            break;
                        case "in bearbeitung":
                            statusBadge.getElement().getThemeList().add("warning");
                            break;
                        case "beantwortet":
                            statusBadge.getElement().getThemeList().add("success");
                            break;
                        case "geschlossen":
                            statusBadge.getElement().getThemeList().add("contrast");
                            break;
                        default:
                            statusBadge.getElement().getThemeList().add("primary");
                    }

                    return statusBadge;
                })
        );

        // Daten für die Grid laden
        updateSupportGrid();

        // Detail-Bereich für die ausgewählte Anfrage
        detailLayout = new VerticalLayout();
        detailLayout.setWidth("50%");
        detailLayout.setHeight("100%");
        detailLayout.setPadding(true);
        detailLayout.setSpacing(true);
        detailLayout.addClassName("support-detail-layout");
        detailLayout.getStyle().set("overflow", "auto");
        detailLayout.getStyle().set("border-left", "1px solid var(--lumo-contrast-10pct)");

        // Anfrage-Auswahl-Listener
        supportGrid.addSelectionListener(event -> {
            event.getFirstSelectedItem().ifPresent(this::showRequestDetails);
        });

        // Komponenten zum Layout hinzufügen
        splitLayout.add(supportGrid, detailLayout);
        splitLayout.setFlexGrow(1, supportGrid);
        splitLayout.setFlexGrow(1, detailLayout);

        // Aktualisieren-Button
        Button refreshButton = new Button("Aktualisieren", VaadinIcon.REFRESH.create());
        refreshButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshButton.addClickListener(e -> updateSupportGrid());

        layout.add(
                new H3("Support-Anfragen verwalten"),
                refreshButton,
                splitLayout
        );

        return layout;
    }

    /**
     * Aktualisiert die Grid mit den Support-Anfragen.
     */
    private void updateSupportGrid() {
        List<SupportRequest> allRequests = supportService.getAllRequests();
        supportGrid.setItems(allRequests);

        if (selectedRequest != null) {
            // Wenn eine Anfrage ausgewählt war, finden wir sie in der aktualisierten Liste
            allRequests.stream()
                    .filter(r -> r.getTicketId().equals(selectedRequest.getTicketId()))
                    .findFirst()
                    .ifPresent(updated -> {
                        selectedRequest = updated;
                        showRequestDetails(updated);
                        supportGrid.select(updated);
                    });
        }
    }

    /**
     * Zeigt die Details einer ausgewählten Support-Anfrage.
     */
    private void showRequestDetails(SupportRequest request) {
        selectedRequest = request;
        detailLayout.removeAll();

        // Header mit Ticket-ID und Status
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        H2 ticketTitle = new H2("Ticket: " + request.getTicketId());

        // Status-Badge
        Span statusBadge = new Span(request.getStatus());
        statusBadge.getElement().getThemeList().add("badge");
        switch (request.getStatus().toLowerCase()) {
            case "offen":
                statusBadge.getElement().getThemeList().add("error");
                break;
            case "in bearbeitung":
                statusBadge.getElement().getThemeList().add("warning");
                break;
            case "beantwortet":
                statusBadge.getElement().getThemeList().add("success");
                break;
            case "geschlossen":
                statusBadge.getElement().getThemeList().add("contrast");
                break;
            default:
                statusBadge.getElement().getThemeList().add("primary");
        }

        header.add(ticketTitle, statusBadge);

        // Info-Block
        Div infoBlock = new Div();
        infoBlock.addClassNames(
                LumoUtility.Background.CONTRAST_5,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Margin.Vertical.MEDIUM
        );
        infoBlock.setWidthFull();

        // Kategorie und Erstellungsdatum
        Span categoryLabel = new Span("Kategorie: ");
        categoryLabel.getStyle().set("font-weight", "bold");
        Span categoryValue = new Span(request.getCategory());

        Span dateLabel = new Span("Erstellt am: ");
        dateLabel.getStyle().set("font-weight", "bold");
        Span dateValue = new Span(request.getCreationDate());

        HorizontalLayout metaInfo = new HorizontalLayout(
                new HorizontalLayout(categoryLabel, categoryValue),
                new HorizontalLayout(dateLabel, dateValue)
        );
        metaInfo.setWidthFull();
        metaInfo.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Beschreibung der Anfrage
        Span descriptionLabel = new Span("Beschreibung: ");
        descriptionLabel.getStyle().set("font-weight", "bold");

        Div descriptionBlock = new Div();
        descriptionBlock.setText(request.getDescription());
        descriptionBlock.addClassNames(
                LumoUtility.Padding.Vertical.SMALL
        );

        infoBlock.add(metaInfo, descriptionLabel, descriptionBlock);

        // Status-Änderungsbuttons
        HorizontalLayout statusButtons = new HorizontalLayout();
        statusButtons.setWidthFull();
        statusButtons.setSpacing(true);

        Button openButton = new Button("Offen", e -> changeStatus(request, "Offen"));
        openButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button inProgressButton = new Button("In Bearbeitung", e -> changeStatus(request, "In Bearbeitung"));
        inProgressButton.addThemeVariants(ButtonVariant.LUMO_WARNING);

        Button answeredButton = new Button("Beantwortet", e -> changeStatus(request, "Beantwortet"));
        answeredButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        Button closedButton = new Button("Geschlossen", e -> changeStatus(request, "Geschlossen"));
        closedButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        statusButtons.add(openButton, inProgressButton, answeredButton, closedButton);

        // NEUE FUNKTIONALITÄT: Anhänge anzeigen, falls vorhanden
        VerticalLayout attachmentsLayout = null;
        if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
            H3 attachmentsTitle = new H3("Anhänge");

            // Container für die Anhänge
            attachmentsLayout = new VerticalLayout();
            attachmentsLayout.setPadding(false);
            attachmentsLayout.setSpacing(true);

            // Für jeden Anhang ein eigenes Layout erstellen
            for (String attachment : request.getAttachments()) {
                HorizontalLayout attachmentLayout = new HorizontalLayout();
                attachmentLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                attachmentLayout.setSpacing(true);
                attachmentLayout.addClassNames(
                        LumoUtility.Background.CONTRAST_5,
                        LumoUtility.BorderRadius.MEDIUM,
                        LumoUtility.Padding.SMALL
                );
                attachmentLayout.setWidthFull();

                // Icon basierend auf Dateityp
                Icon fileIcon;
                if (attachment.toLowerCase().endsWith(".pdf")) {
                    fileIcon = VaadinIcon.FILE_TEXT_O.create();
                    fileIcon.setColor("var(--lumo-error-color)");
                } else if (attachment.toLowerCase().endsWith(".docx") || attachment.toLowerCase().endsWith(".doc")) {
                    fileIcon = VaadinIcon.FILE_TEXT_O.create();
                    fileIcon.setColor("var(--lumo-primary-color)");
                } else if (attachment.toLowerCase().endsWith(".png") || attachment.toLowerCase().endsWith(".jpg") ||
                        attachment.toLowerCase().endsWith(".jpeg") || attachment.toLowerCase().endsWith(".gif")) {
                    fileIcon = VaadinIcon.FILE_PICTURE.create();
                    fileIcon.setColor("var(--lumo-success-color)");
                } else {
                    fileIcon = VaadinIcon.FILE_O.create();
                }

                Span fileName = new Span(attachment);
                fileName.getStyle().set("flex-grow", "1");

                // Vorschau-Button
                Button viewButton = new Button("Vorschau", VaadinIcon.EYE.create());
                viewButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
                viewButton.addClickListener(e -> showFilePreview(attachment));

                // Download-Button
                Button downloadButton = new Button("Download", VaadinIcon.DOWNLOAD.create());
                downloadButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_SUCCESS);
                downloadButton.addClickListener(e -> {
                    Notification.show("Download von '" + attachment + "' gestartet...",
                                    3000, Notification.Position.BOTTOM_END)
                            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                });

                attachmentLayout.add(fileIcon, fileName, viewButton, downloadButton);
                attachmentsLayout.add(attachmentLayout);
            }
        }

        // Kommentar-Bereich
        H3 commentsTitle = new H3("Kommentare");

        // Vorhandene Kommentare anzeigen
        VerticalLayout commentsLayout = new VerticalLayout();
        commentsLayout.setPadding(false);
        commentsLayout.setSpacing(true);

        if (request.getComments() != null && !request.getComments().isEmpty()) {
            for (String comment : request.getComments()) {
                Div commentDiv = new Div();
                commentDiv.setText(comment);
                commentDiv.addClassNames(
                        LumoUtility.Background.CONTRAST_5,
                        LumoUtility.BorderRadius.MEDIUM,
                        LumoUtility.Padding.MEDIUM,
                        LumoUtility.Margin.Bottom.SMALL
                );
                commentsLayout.add(commentDiv);
            }
        } else {
            Span noComments = new Span("Keine Kommentare vorhanden");
            noComments.getStyle().set("font-style", "italic");
            commentsLayout.add(noComments);
        }

        // Neuen Kommentar hinzufügen
        TextArea newCommentField = new TextArea("Antwort hinzufügen");
        newCommentField.setWidthFull();
        newCommentField.setMinHeight("150px");

        Button sendCommentButton = new Button("Antwort senden", e -> {
            if (!newCommentField.isEmpty()) {
                addCommentAndUpdateStatus(request, newCommentField.getValue());
                newCommentField.clear();
            }
        });
        sendCommentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Alles zum Detail-Layout hinzufügen
        detailLayout.add(header, infoBlock, statusButtons);

        // Anhänge hinzufügen, falls vorhanden
        if (attachmentsLayout != null) {
            H3 attachmentsTitle = new H3("Anhänge");
            detailLayout.add(attachmentsTitle, attachmentsLayout);
        }

        // Rest der Komponenten hinzufügen
        detailLayout.add(commentsTitle, commentsLayout, newCommentField, sendCommentButton);
    }

    /**
     * Zeigt eine Vorschau der Datei an
     */
    private void showFilePreview(String fileName) {
        // Dialog für die Dateivorschau
        Dialog previewDialog = new Dialog();
        previewDialog.setWidth("800px");
        previewDialog.setHeight("600px");
        previewDialog.setCloseOnEsc(true);
        previewDialog.setCloseOnOutsideClick(true);

        VerticalLayout dialogContent = new VerticalLayout();
        dialogContent.setSizeFull();
        dialogContent.setPadding(true);

        H2 fileTitle = new H2("Vorschau: " + fileName);

        Component previewComponent;

        // Pfad zur Datei erstellen (in einer realen Anwendung würde dies aus einer Datenbank oder einem Dateisystem kommen)
        String filePath = "src/main/resources/uploads/" + fileName;
        File file = new File(filePath);

        // Je nach Dateityp unterschiedliche Vorschau anzeigen
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") ||
                fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".gif")) {

            // Für Bilder verwenden wir einen echten Image-Container
            Div imageContainer = new Div();
            imageContainer.setWidth("100%");
            imageContainer.setHeight("400px");
            imageContainer.getStyle()
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("justify-content", "center")
                    .set("background-color", "var(--lumo-contrast-5pct)")
                    .set("border-radius", "var(--lumo-border-radius-m)");

            try {
                // Versuche, das Bild zu laden
                if (file.exists()) {
                    // StreamResource für das Bild erstellen
                    StreamResource imageResource = new StreamResource(fileName, () -> {
                        try {
                            return new FileInputStream(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new ByteArrayInputStream(new byte[0]);
                        }
                    });

                    // Bild-Komponente erstellen und konfigurieren
                    Image image = new Image(imageResource, "Bild: " + fileName);
                    image.setMaxHeight("380px");
                    image.setMaxWidth("100%");
                    image.getStyle().set("object-fit", "contain");

                    imageContainer.add(image);
                } else {
                    // Fallback, wenn die Datei nicht existiert
                    Icon imageIcon = VaadinIcon.PICTURE.create();
                    imageIcon.setSize("100px");
                    imageIcon.setColor("var(--lumo-primary-color)");
                    Paragraph errorText = new Paragraph("Bild konnte nicht geladen werden");

                    imageContainer.add(new VerticalLayout(imageIcon, errorText));
                }
            } catch (Exception e) {
                // Bei Fehler Fallback anzeigen
                Icon imageIcon = VaadinIcon.PICTURE.create();
                imageIcon.setSize("100px");
                imageIcon.setColor("var(--lumo-primary-color)");
                Paragraph errorText = new Paragraph("Fehler beim Laden des Bildes: " + e.getMessage());

                imageContainer.add(new VerticalLayout(imageIcon, errorText));
            }

            previewComponent = imageContainer;
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            // Container für PDF-Vorschau
            Div pdfContainer = new Div();
            pdfContainer.setWidth("100%");
            pdfContainer.setHeight("400px");
            pdfContainer.getStyle()
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("justify-content", "center")
                    .set("background-color", "var(--lumo-contrast-5pct)")
                    .set("border-radius", "var(--lumo-border-radius-m)");

            if (file.exists()) {
                // PDF-Icon mit Hinweis, dass es heruntergeladen werden kann
                Icon pdfIcon = VaadinIcon.FILE_TEXT_O.create();
                pdfIcon.setSize("100px");
                pdfIcon.setColor("var(--lumo-error-color)");

                Paragraph pdfText = new Paragraph("PDF-Datei kann heruntergeladen werden");

                VerticalLayout pdfLayout = new VerticalLayout(pdfIcon, pdfText);
                pdfLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                pdfLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                pdfContainer.add(pdfLayout);
            } else {
                Icon pdfIcon = VaadinIcon.FILE_TEXT_O.create();
                pdfIcon.setSize("100px");
                pdfIcon.setColor("var(--lumo-error-color)");

                Paragraph errorText = new Paragraph("PDF-Datei konnte nicht gefunden werden");

                VerticalLayout pdfLayout = new VerticalLayout(pdfIcon, errorText);
                pdfLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                pdfLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                pdfContainer.add(pdfLayout);
            }

            previewComponent = pdfContainer;

        } else if (fileName.toLowerCase().endsWith(".docx") || fileName.toLowerCase().endsWith(".doc")) {
            // Container für Word-Dokument-Vorschau
            Div docContainer = new Div();
            docContainer.setWidth("100%");
            docContainer.setHeight("400px");
            docContainer.getStyle()
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("justify-content", "center")
                    .set("background-color", "var(--lumo-contrast-5pct)")
                    .set("border-radius", "var(--lumo-border-radius-m)");

            if (file.exists()) {
                // Word-Icon mit Hinweis, dass es heruntergeladen werden kann
                Icon docIcon = VaadinIcon.FILE_TEXT_O.create();
                docIcon.setSize("100px");
                docIcon.setColor("var(--lumo-primary-color)");

                Paragraph docText = new Paragraph("Word-Dokument kann heruntergeladen werden");

                VerticalLayout docLayout = new VerticalLayout(docIcon, docText);
                docLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                docLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                docContainer.add(docLayout);
            } else {
                Icon docIcon = VaadinIcon.FILE_TEXT_O.create();
                docIcon.setSize("100px");
                docIcon.setColor("var(--lumo-primary-color)");

                Paragraph errorText = new Paragraph("Word-Dokument konnte nicht gefunden werden");

                VerticalLayout docLayout = new VerticalLayout(docIcon, errorText);
                docLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                docLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                docContainer.add(docLayout);
            }

            previewComponent = docContainer;
        } else {
            // Container für generische Dateivorschau
            Div fileContainer = new Div();
            fileContainer.setWidth("100%");
            fileContainer.setHeight("400px");
            fileContainer.getStyle()
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("justify-content", "center")
                    .set("background-color", "var(--lumo-contrast-5pct)")
                    .set("border-radius", "var(--lumo-border-radius-m)");

            if (file.exists()) {
                Icon fileIcon = VaadinIcon.FILE_O.create();
                fileIcon.setSize("100px");
                fileIcon.setColor("var(--lumo-contrast)");

                Paragraph fileText = new Paragraph("Datei kann heruntergeladen werden");

                VerticalLayout fileLayout = new VerticalLayout(fileIcon, fileText);
                fileLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                fileLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                fileContainer.add(fileLayout);
            } else {
                Icon fileIcon = VaadinIcon.FILE_O.create();
                fileIcon.setSize("100px");
                fileIcon.setColor("var(--lumo-contrast)");

                Paragraph errorText = new Paragraph("Datei konnte nicht gefunden werden");

                VerticalLayout fileLayout = new VerticalLayout(fileIcon, errorText);
                fileLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                fileLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                fileContainer.add(fileLayout);
            }

            previewComponent = fileContainer;
        }

        Button downloadButton = new Button("Herunterladen", VaadinIcon.DOWNLOAD.create());
        downloadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        downloadButton.addClickListener(e -> {
            if (file.exists()) {
                try {
                    // StreamResource für den Download erstellen
                    StreamResource resource = new StreamResource(fileName, () -> {
                        try {
                            return new FileInputStream(file);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            return new ByteArrayInputStream(new byte[0]);
                        }
                    });

                    // Registriere die Resource für den Download
                    StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry()
                            .registerResource(resource);

                    // Öffne den Download in einem neuen Tab
                    UI.getCurrent().getPage().open(registration.getResourceUri().toString(), "_blank");

                    Notification.show("Download von '" + fileName + "' gestartet...",
                            3000, Notification.Position.BOTTOM_END)
                            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } catch (Exception ex) {
                    Notification.show("Fehler beim Herunterladen: " + ex.getMessage(),
                            3000, Notification.Position.BOTTOM_END)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            } else {
                Notification.show("Datei '" + fileName + "' konnte nicht gefunden werden",
                        3000, Notification.Position.BOTTOM_END)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        Button closeButton = new Button("Schließen", VaadinIcon.CLOSE.create());
        closeButton.addClickListener(e -> previewDialog.close());

        HorizontalLayout buttonsLayout = new HorizontalLayout(downloadButton, closeButton);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setMargin(true);
        buttonsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonsLayout.setWidthFull();

        dialogContent.add(fileTitle, previewComponent, buttonsLayout);
        previewDialog.add(dialogContent);

        previewDialog.open();
    }

    /**
     * Erstellt den Inhalt für die Benutzerverwaltung.
     */
    private Component createUserManagementContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setWidthFull();

        // Überschrift
        H2 title = new H2("Benutzerverwaltung");

        // Grid für die Benutzer
        nutzerGrid = new Grid<>();
        nutzerGrid.setWidthFull();
        nutzerGrid.setHeight("700px");

        // Spalten definieren
        nutzerGrid.addColumn(Nutzer::getId).setHeader("ID").setAutoWidth(true);
        nutzerGrid.addColumn(Nutzer::getUsername).setHeader("Benutzername").setAutoWidth(true);
        nutzerGrid.addColumn(Nutzer::getEmailOrEmpty).setHeader("E-Mail").setAutoWidth(true);
        nutzerGrid.addColumn(Nutzer::getVornameOrEmpty).setHeader("Vorname").setAutoWidth(true);
        nutzerGrid.addColumn(Nutzer::getNachnameOrEmpty).setHeader("Nachname").setAutoWidth(true);
        nutzerGrid.addColumn(nutzer -> nutzer.getFormattedRegistrierungsDatum("dd.MM.yyyy HH:mm"))
                .setHeader("Registriert am").setAutoWidth(true);

        // Lösch-Button-Spalte hinzufügen
        nutzerGrid.addComponentColumn(nutzer -> {
            Button deleteButton = new Button("Löschen", VaadinIcon.TRASH.create());
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> confirmAndDeleteUser(nutzer));
            return deleteButton;
        }).setHeader("Aktionen").setAutoWidth(true);

        // Daten für die Grid laden
        updateUserGrid();

        // Aktualisieren-Button
        Button refreshButton = new Button("Aktualisieren", VaadinIcon.REFRESH.create());
        refreshButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshButton.addClickListener(e -> updateUserGrid());

        layout.add(title, refreshButton, nutzerGrid);
        return layout;
    }

    /**
     * Aktualisiert die Grid mit den Benutzern.
     */
    private void updateUserGrid() {
        List<Nutzer> allUsers = nutzerService.getAllNutzer();
        nutzerGrid.setItems(allUsers);
        nutzerGrid.getDataProvider().refreshAll();
    }

    /**
     * Zeigt einen Bestätigungsdialog an und löscht den Benutzer bei Bestätigung.
     */
    private void confirmAndDeleteUser(Nutzer nutzer) {
        Dialog confirmDialog = new Dialog();
        confirmDialog.setCloseOnEsc(true);
        confirmDialog.setCloseOnOutsideClick(false);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        H3 title = new H3("Benutzer löschen");
        Paragraph message = new Paragraph("Möchten Sie den Benutzer '" + nutzer.getUsername() + 
                "' wirklich dauerhaft löschen? Diese Aktion kann nicht rückgängig gemacht werden.");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        buttonLayout.setWidthFull();

        Button cancelButton = new Button("Abbrechen", e -> confirmDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button deleteButton = new Button("Löschen", e -> {
            boolean success = nutzerService.nutzerVollstaendigLoeschen(nutzer.getId());
            confirmDialog.close();

            if (success) {
                Notification.show("Benutzer '" + nutzer.getUsername() + "' wurde gelöscht",
                        3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                updateUserGrid();
            } else {
                Notification.show("Fehler beim Löschen des Benutzers",
                        3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        buttonLayout.add(cancelButton, deleteButton);
        dialogLayout.add(title, message, buttonLayout);
        confirmDialog.add(dialogLayout);
        confirmDialog.open();
    }

    /**
     * Ändert den Status einer Support-Anfrage.
     */
    private void changeStatus(SupportRequest request, String newStatus) {
        if (supportService.updateRequestStatus(request, newStatus)) {
            updateSupportGrid();

            Notification.show("Status auf '" + newStatus + "' geändert",
                    3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Fehler beim Ändern des Status",
                    3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    /**
     * Fügt einen Kommentar zu einer Support-Anfrage hinzu und ändert den Status auf "Beantwortet".
     */
    private void addCommentAndUpdateStatus(SupportRequest request, String comment) {
        // Kommentar mit Admin-Präfix versehen
        String adminComment = "Finovia-Support: " + comment;

        boolean commentAdded = supportService.addCommentToRequest(request, adminComment);
        boolean statusUpdated = supportService.updateRequestStatus(request, "Beantwortet");

        if (commentAdded && statusUpdated) {
            // E-Mail an den Benutzer senden (würde in einem realen System implementiert)
            // emailService.sendSupportResponseEmail(userEmail, request.getTicketId(), comment);

            Notification.show("Antwort gesendet und Status auf 'Beantwortet' gesetzt",
                    3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            updateSupportGrid();
        } else {
            Notification.show("Fehler beim Hinzufügen des Kommentars oder Aktualisieren des Status",
                    3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
