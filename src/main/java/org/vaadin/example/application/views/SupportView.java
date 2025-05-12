package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Supportanfrage;

@Route("support")
@PageTitle("Support - Finovia")
@PermitAll
public class SupportView extends VerticalLayout {

    private final TextArea nachrichtField = new TextArea("Ihre Anfrage");
    private final TextField betreffField = new TextField("Betreff");
    private final Select<String> kategorieSelect = new Select<>();
    private final Button sendenButton = new Button("Senden");
    private final Button abbrechenButton = new Button("Abbrechen");

    /**
     * Konstruktor für die SupportView.
     * Erstellt eine Benutzeroberfläche für die Erstellung von Supportanfragen.
     */
    public SupportView() {
        configureView();
        configureForm();
        setupButtons();
    }

    /**
     * Konfiguriert die grundlegenden Eigenschaften der View.
     */
    private void configureView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        setPadding(true);
        setSpacing(true);
        addClassNames(LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Horizontal.AUTO);
    }

    /**
     * Konfiguriert das Formular für die Supportanfrage.
     */
    private void configureForm() {
        // Header
        H1 title = new H1("Support-Anfrage erstellen");
        Paragraph description = new Paragraph("Beschreiben Sie Ihr Anliegen, und unser Support-Team wird sich so schnell wie möglich bei Ihnen melden.");

        // Formular-Komponenten
        betreffField.setPlaceholder("Kurze Beschreibung Ihres Anliegens");
        betreffField.setRequired(true);
        betreffField.setWidth("100%");
        betreffField.setMaxLength(100);
        betreffField.setHelperText("Max. 100 Zeichen");

        kategorieSelect.setLabel("Kategorie");
        kategorieSelect.setItems("Technisches Problem", "Finanzfrage", "Depotproblem", "Kontoproblem", "Sonstiges");
        kategorieSelect.setValue("Sonstiges");
        kategorieSelect.getElement().setAttribute("required", "true");
        kategorieSelect.setWidth("100%");

        nachrichtField.setPlaceholder("Beschreiben Sie Ihr Problem oder Anliegen detailliert...");
        nachrichtField.setRequired(true);
        nachrichtField.setMinHeight("200px");
        nachrichtField.setWidth("100%");
        nachrichtField.setMaxLength(2000);
        nachrichtField.setHelperText("Max. 2000 Zeichen");

        VerticalLayout formLayout = new VerticalLayout(
                title,
                description,
                betreffField,
                kategorieSelect,
                nachrichtField
        );
        formLayout.setPadding(false);
        formLayout.setSpacing(true);
        formLayout.setMaxWidth("800px");
        formLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        add(formLayout);
    }

    /**
     * Richtet die Buttons zum Senden und Abbrechen ein.
     */
    private void setupButtons() {
        // Buttons konfigurieren
        sendenButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendenButton.setMinWidth("150px");

        abbrechenButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        abbrechenButton.setMinWidth("150px");

        // Button-Container
        HorizontalLayout buttonLayout = new HorizontalLayout(abbrechenButton, sendenButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        buttonLayout.setPadding(false);
        buttonLayout.addClassNames(LumoUtility.Margin.Top.MEDIUM);

        // Click-Listener
        sendenButton.addClickListener(event -> sendeAnfrage());
        abbrechenButton.addClickListener(event -> UI.getCurrent().navigate(""));

        add(buttonLayout);
    }

    /**
     * Verarbeitet die Supportanfrage nach dem Absenden.
     */
    private void sendeAnfrage() {
        if (formIsValid()) {
            try {
                // Testdaten für einen Nutzer erstellen
                Nutzer aktuellerNutzer = new Nutzer(
                        "testuser",
                        "Test",
                        "Nutzer",
                        "test.nutzer@example.com",
                        "password123"
                );

                String nachricht = "Kategorie: " + kategorieSelect.getValue() +
                        "\nBetreff: " + betreffField.getValue() +
                        "\n\n" + nachrichtField.getValue();

                // Supportanfrage erstellen
                Supportanfrage anfrage = new Supportanfrage(nachricht, aktuellerNutzer);

                showSuccessMessage();
                clearForm();

                // Optional: Nach kurzer Verzögerung zurück zur Hauptseite navigieren
                UI.getCurrent().getPage().executeJs("setTimeout(() => {$0.navigate('');}, 2000)", UI.getCurrent());

            } catch (Exception e) {
                Notification.show("Fehler beim Senden: " + e.getMessage(),
                                3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        }
    }


    /**
     * Überprüft, ob alle Pflichtfelder ausgefüllt sind.
     */
    private boolean formIsValid() {
        boolean valid = true;

        if (betreffField.isEmpty()) {
            betreffField.setInvalid(true);
            betreffField.setErrorMessage("Bitte geben Sie einen Betreff ein");
            valid = false;
        }

        if (nachrichtField.isEmpty()) {
            nachrichtField.setInvalid(true);
            nachrichtField.setErrorMessage("Bitte beschreiben Sie Ihr Anliegen");
            valid = false;
        }

        if (kategorieSelect.isEmpty()) {
            kategorieSelect.setInvalid(true);
            kategorieSelect.setErrorMessage("Bitte wählen Sie eine Kategorie");
            valid = false;
        }

        return valid;
    }

    /**
     * Zeigt eine Erfolgsmeldung nach dem erfolgreichen Senden der Anfrage.
     */
    private void showSuccessMessage() {
        Notification notification = Notification.show(
                "Ihre Supportanfrage wurde erfolgreich übermittelt. Wir werden uns in Kürze bei Ihnen melden.",
                5000,
                Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    /**
     * Setzt das Formular zurück.
     */
    private void clearForm() {
        betreffField.clear();
        nachrichtField.clear();
        kategorieSelect.setValue("Sonstiges");
    }
}