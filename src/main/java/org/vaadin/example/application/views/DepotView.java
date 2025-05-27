package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.factory.DepotAnlegenViewFactory;
import org.vaadin.example.application.services.DepotService;

import java.util.List;

/**
 * Die `DepotView`-Klasse stellt die Hauptansicht für die Verwaltung von Depots dar.
 * Sie zeigt eine Liste der vorhandenen Depots an und bietet die Möglichkeit,
 * neue Depots zu erstellen.
 */
@Route(value = "depot")
@PageTitle("Depot")
@PermitAll
public class DepotView extends AbstractSideNav {

    private final DepotService depotService;
    private final SecurityService securityService;
    private final DepotAnlegenViewFactory depotAnlegenViewFactory;

    /**
     * Konstruktor für die `DepotView`-Klasse.
     * Initialisiert die Ansicht mit den notwendigen Services und baut die Benutzeroberfläche auf.
     *
     * @param depotService Der Service für Depot-Operationen, um Depots zu laden.
     * @param depotAnlegenViewFactory Die Factory zum Erstellen der DepotAnlegenView als Dialog.
     */
    @Autowired
    public DepotView(DepotService depotService, DepotAnlegenViewFactory depotAnlegenViewFactory, SecurityService securityService) {
        super();
        this.depotService = depotService;
        this.depotAnlegenViewFactory = depotAnlegenViewFactory;
        this.securityService = securityService;

        // Überschrift der Ansicht
        H2 title = new H2("Meine Depots");

        // Button zum Öffnen des Dialogs zum Anlegen eines neuen Depots
        Button depotAnlegenButton = new Button("Neues Depot anlegen", VaadinIcon.PLUS.create());
        depotAnlegenButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        // Öffnet den Dialog, wenn der Button geklickt wird
        depotAnlegenButton.addClickListener(event -> openDepotAnlegenDialog());

        // Layout für die Überschrift und den Button
        VerticalLayout depotHeader = new VerticalLayout(title, depotAnlegenButton);
        depotHeader.setSpacing(true);
        depotHeader.setPadding(false);
        depotHeader.setWidthFull();

        // Depots aus der Datenbank laden
        UserDetails userDetails = securityService.getAuthenticatedUser();
        Long id = userDetails != null ? securityService.getUserIdFromUserDetails(userDetails) : null;

        List<Depot> depots = depotService.getDepotsByNutzerId(id);

        // Layout für die Liste der Depots
        VerticalLayout depotList = new VerticalLayout();
        depotList.setSpacing(true);
        depotList.setPadding(false);
        depotList.setWidthFull();

        // Prüfen, ob Depots vorhanden sind und diese anzeigen
        if (depots.isEmpty()) {
            depotList.add(new Span("Keine Depots vorhanden. Erstellen Sie ein neues Depot."));
        } else {
            // Depots in einzelnen Boxen anzeigen
            for (Depot depot : depots) {
                Div depotBox = createDepotBox(depot);
                depotList.add(depotBox);
            }
        }

        // Hauptinhaltsbereich der Seite konfigurieren (aus AbstractSideNav)
        addToMainContent(depotHeader, depotList);
    }

    /**
     * Öffnet den Dialog zum Anlegen eines neuen Depots.
     * Registriert einen Listener, um die Depotliste nach dem Schließen des Dialogs zu aktualisieren.
     */
    private void openDepotAnlegenDialog() {
        // Erstellt eine Instanz von DepotAnlegenView über die Factory
        DepotAnlegenView depotAnlegenDialog = depotAnlegenViewFactory.createDepotAnlegenView();

        // Fügt einen Listener hinzu, um die Ansicht zu aktualisieren, wenn der Dialog geschlossen wird
        // (z.B. nach dem Speichern eines neuen Depots)
        depotAnlegenDialog.addOpenedChangeListener(event -> {
            if (!event.isOpened()) {
                // Dialog wurde geschlossen, Depotliste aktualisieren
                // Dies bewirkt, dass die Daten neu geladen und die Ansicht neu aufgebaut wird.
                getUI().ifPresent(ui -> ui.navigate(DepotView.class));
            }
        });
        depotAnlegenDialog.open(); // Öffnet den Dialog
    }

    /**
     * Erstellt eine visuelle Box zur Darstellung eines einzelnen Depots.
     * Diese Box zeigt grundlegende Informationen an und ist klickbar,
     * um zur Detailansicht des Depots zu navigieren.
     *
     * @param depot Das anzuzeigende {@link Depot}-Objekt.
     * @return Eine {@link Div}-Komponente, die das Depot darstellt.
     */
    private Div createDepotBox(Depot depot) {
        Div depotBox = new Div();
        depotBox.addClassName("depot-box");

        // Depot-Name als Überschrift in der Box
        depotBox.add(new H3(depot.getName()));

        // Weitere Depot-Informationen
        depotBox.add(new Div(new Span("Besitzer: " + depot.getBesitzer().getVollerName())));
        depotBox.add(new Div(new Span("Anzahl Wertpapiere: " + depot.getDepotWertpapiere().size())));

        // CSS-Styling für die Depot-Box
        depotBox.getStyle().set("border", "1px solid #ccc");
        depotBox.getStyle().set("padding", "10px");
        depotBox.getStyle().set("border-radius", "5px");
        depotBox.getStyle().set("cursor", "pointer"); // Zeigt an, dass die Box klickbar ist
        depotBox.getStyle().set("margin-bottom", "10px");
        depotBox.getStyle().set("background-color", "#f8f8f8");
        depotBox.setWidth("400px");

        // Klick-Handler für die Navigation zur Detailansicht des Depots
        depotBox.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(DetailedDepotView.class, depot.getDepotId()));
        });

        return depotBox;
    }
}