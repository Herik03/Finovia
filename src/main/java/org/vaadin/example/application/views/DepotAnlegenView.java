package org.vaadin.example.application.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.NutzerService;

/**
 * Die `DepotAnlegenView`-Klasse stellt einen Dialog zur Erstellung eines neuen Depots dar.
 * Dieser Dialog wird als Pop-up von der DepotView geöffnet.
 */
@Component // Macht es zu einer Spring-Komponente
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // Stellt sicher, dass für jeden Dialog eine neue Instanz erstellt wird
public class DepotAnlegenView extends Dialog { // Erbt von Dialog für Pop-up-Verhalten

    private final DepotService depotService;
    private final SecurityService securityService;
    private final NutzerService nutzerService;

    private Nutzer currentUser; // Der aktuell eingeloggte Nutzer

    /**
     * Konstruktor für die `DepotAnlegenView`-Klasse.
     * Initialisiert den Dialog und seine Komponenten zur Eingabe der Depotdetails.
     *
     * @param depotService Der Service für Depot-Operationen.
     * @param securityService Der Service für Sicherheitsoperationen, um den aktuellen Nutzer zu ermitteln.
     * @param nutzerService Der Service für Nutzer-Operationen, um Nutzerdaten zu laden.
     */
    @Autowired
    public DepotAnlegenView(DepotService depotService, SecurityService securityService, NutzerService nutzerService) {
        this.depotService = depotService;
        this.securityService = securityService;
        this.nutzerService = nutzerService;

        // Setzt den Titel des Dialogs
        setHeaderTitle("Neues Depot anlegen");

        // Erlaubt das Schließen des Dialogs mit der Esc-Taste und durch Klicken außerhalb (für bessere Benutzerfreundlichkeit)
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        // Fügt einen Schließen-Button (X-Symbol) in der Kopfzeile des Dialogs hinzu
        Button closeButtonInHeader = new Button(VaadinIcon.CLOSE_SMALL.create());
        // Fügt Theme-Varianten hinzu: LUMO_TERTIARY für einen unauffälligen Stil und LUMO_ERROR für rote Farbe
        closeButtonInHeader.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
        closeButtonInHeader.getElement().setAttribute("aria-label", "Schließen"); // Zugänglichkeitsattribut
        closeButtonInHeader.addClickListener(event -> close()); // Schließt den Dialog beim Klick

        getHeader().add(closeButtonInHeader); // Fügt den Button der Kopfzeile des Dialogs hinzu

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        contentLayout.setWidthFull();

        setupDepotForm(contentLayout);

        // Fügt den Inhalt zum Dialog hinzu
        add(contentLayout);
    }

    /**
     * Konfiguriert das Formular zur Eingabe der Depotdetails.
     *
     * @param contentLayout Das Layout, zu dem das Formular hinzugefügt wird.
     */
    private void setupDepotForm(VerticalLayout contentLayout) {
        VerticalLayout depotForm = new VerticalLayout();
        depotForm.setMaxWidth("600px");
        depotForm.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Textfeld für den Depot-Namen
        TextField depotName = new TextField("Depot-Name");
        depotName.setRequiredIndicatorVisible(true); // Feld ist Pflichtfeld
        depotName.setPrefixComponent(VaadinIcon.PIGGY_BANK.create()); // Icon für das Textfeld

        // Textfeld für die IBAN
        TextField iban = new TextField("IBAN");
        iban.setRequiredIndicatorVisible(true); // Feld ist Pflichtfeld

        // Button zum Erstellen des Depots
        Button speichernButton = new Button("Depot erstellen", VaadinIcon.CHECK.create());
        speichernButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        speichernButton.addClickListener(e -> {
            // Überprüfen, ob alle Pflichtfelder ausgefüllt sind
            if (!depotName.isEmpty() && !iban.isEmpty()) {
                getAktuellenNutzer(); // Ermittelt den aktuellen Nutzer

                // Erstellt ein neues Depot-Objekt
                String name = depotName.getValue();
                Depot neuesDepot = new Depot(name, currentUser);
                assert currentUser != null; // Sicherstellen, dass ein Nutzer gefunden wurde

                // Nutzer und Depot in einer Transaktion speichern, um LazyInitializationException zu vermeiden
                boolean success = nutzerService.depotZuNutzerHinzufuegen(currentUser.getId(), neuesDepot);

                if (success) {
                    // Zeigt eine Erfolgsbenachrichtigung an
                    Notification.show("Depot erfolgreich erstellt!", 3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                    this.close(); // Schließt den Dialog nach erfolgreicher Erstellung
                } else {
                    // Zeigt eine Fehlermeldung an, wenn das Depot nicht erstellt werden konnte
                    Notification.show("Fehler beim Erstellen des Depots. Bitte versuchen Sie es erneut.", 
                            3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }

            } else {
                // Zeigt eine Fehlermeldung an, wenn Pflichtfelder fehlen
                Notification.show("Bitte alle Pflichtfelder ausfüllen!", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        speichernButton.addClickShortcut(Key.ENTER); // Ermöglicht das Speichern mit der Enter-Taste

        // Layout für die Buttons (hier nur der Speichern-Button, da der Schließen-Button in der Kopfzeile ist)
        HorizontalLayout buttonLayout = new HorizontalLayout(speichernButton);
        buttonLayout.setSpacing(true);

        // Fügt die Komponenten zum Formular hinzu
        depotForm.add(new H2("Neues Depot anlegen"), depotName, iban, buttonLayout);
        contentLayout.add(depotForm);
    }

    /**
     * Ermittelt den aktuell eingeloggten Nutzer und speichert ihn in der `currentUser`-Variable.
     */
    private void getAktuellenNutzer() {
        currentUser = securityService.getAuthenticatedUser() != null ?
                nutzerService.getNutzerByUsername(securityService.getAuthenticatedUser().getUsername()) :
                null;
    }
}
