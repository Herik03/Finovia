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
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.services.DepotService;

import java.util.List;

/**
 * Die `DepotView`-Klasse stellt die Hauptansicht für die Verwaltung von Depots dar.
 * Sie enthält eine Überschrift, einen Button zum Erstellen eines neuen Depots
 * und eine Liste der Depots des Nutzers aus der Datenbank.
 */
@Route(value = "depot")
@PageTitle("Depot")
@PermitAll
public class DepotView extends AbstractSideNav {

    private final DepotService depotService;

    /**
     * Konstruktor für die `DepotView`-Klasse.
     * Initialisiert die Ansicht mit einer Überschrift, einem Button und den Depots aus der Datenbank.
     * 
     * @param depotService Der Service für Depot-Operationen
     */
    @Autowired
    public DepotView(DepotService depotService) {
        super(); // Ruft den Konstruktor der AbstractSideNav auf
        this.depotService = depotService;
        
        // Überschrift
        H2 title = new H2("Meine Depots");

        // Button für neues Depot
        Button depotAnlegenButton = new Button("Neues Depot anlegen", VaadinIcon.PLUS.create());
        depotAnlegenButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        RouterLink routerLink = new RouterLink("", DepotAnlegenView.class);
        routerLink.add(depotAnlegenButton);

        // Depot-Header-Layout erstellen
        VerticalLayout depotHeader = new VerticalLayout(title, routerLink);
        depotHeader.setSpacing(true);
        depotHeader.setPadding(false);
        depotHeader.setWidthFull();

        // Depots aus der Datenbank laden und anzeigen
        List<Depot> depots = depotService.getAllDepots();

        // Depot-Liste-Layout erstellen
        VerticalLayout depotList = new VerticalLayout();
        depotList.setSpacing(true);
        depotList.setPadding(false);
        depotList.setWidthFull();

        if (depots.isEmpty()) {
            depotList.add(new Span("Keine Depots vorhanden. Erstellen Sie ein neues Depot."));
        } else {
            // Depots anzeigen
            for (Depot depot : depots) {
                Div depotBox = createDepotBox(depot);
                depotList.add(depotBox);
            }
        }

        // Den Hauptinhaltsbereich konfigurieren
        // Diese überschreibt die Methode configureMainContent aus AbstractSideNav
        // Wir fügen stattdessen unsere eigenen Komponenten hinzu
        addToMainContent(depotHeader, depotList);
    }

    /**
     * Erstellt eine Box für die Anzeige eines Depots.
     * 
     * @param depot Das anzuzeigende Depot
     * @return Eine Div-Komponente, die das Depot darstellt
     */
    private Div createDepotBox(Depot depot) {
        Div depotBox = new Div();
        depotBox.addClassName("depot-box");

        // Depot-Name als Überschrift
        depotBox.add(new H3(depot.getName()));

        // Depot-Informationen
        depotBox.add(new Div(new Span("Besitzer: " + depot.getBesitzer().getVollerName())));
        depotBox.add(new Div(new Span("Anzahl Wertpapiere: " + depot.getDepotWertpapiere().size())));

        // Styling
        depotBox.getStyle().set("border", "1px solid #ccc");
        depotBox.getStyle().set("padding", "10px");
        depotBox.getStyle().set("border-radius", "5px");
        depotBox.getStyle().set("cursor", "pointer");
        depotBox.getStyle().set("margin-bottom", "10px");
        depotBox.getStyle().set("background-color", "#f8f8f8");
        depotBox.setWidth("400px");

        // Klick-Handler für Navigation zur Detailansicht
        depotBox.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(DetailedDepotView.class, depot.getDepotId()));
        });

        return depotBox;
    }
}