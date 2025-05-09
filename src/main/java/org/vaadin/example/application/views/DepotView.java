package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.html.Div;
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
public class DepotView extends HorizontalLayout {

    private final DepotService depotService;

    /**
     * Konstruktor für die `DepotView`-Klasse.
     * Initialisiert die Ansicht mit einer Überschrift, einem Button und den Depots aus der Datenbank.
     * 
     * @param depotService Der Service für Depot-Operationen
     */
    @Autowired
    public DepotView(DepotService depotService) {
        this.depotService = depotService;

        // Überschrift
        H2 title = new H2("Meine Depots");

        // Button für neues Depot
        Button depotAnlegenButton = new Button("Neues Depot anlegen", VaadinIcon.PLUS.create());
        depotAnlegenButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        RouterLink routerLink = new RouterLink("", DepotAnlegenView.class);
        routerLink.add(depotAnlegenButton);

        // Linker Bereich (Überschrift und Button)
        VerticalLayout leftLayout = new VerticalLayout(title, routerLink);
        leftLayout.setSpacing(true);
        leftLayout.setPadding(true);

        // Depots aus der Datenbank laden und anzeigen
        List<Depot> depots = depotService.getAllDepots();

        if (depots.isEmpty()) {
            leftLayout.add(new Span("Keine Depots vorhanden. Erstellen Sie ein neues Depot."));
        } else {
            // Depots anzeigen
            for (Depot depot : depots) {
                Div depotBox = createDepotBox(depot);
                leftLayout.add(depotBox);
            }
        }

        // Layout-Einstellungen
        add(leftLayout);
        setWidthFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.START);
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
        depotBox.add(new Div(new Span("Anzahl Wertpapiere: " + depot.getWertpapiere().size())));

        // Styling
        depotBox.getStyle().set("border", "1px solid #ccc");
        depotBox.getStyle().set("padding", "10px");
        depotBox.getStyle().set("border-radius", "5px");
        depotBox.getStyle().set("cursor", "pointer");
        depotBox.getStyle().set("margin-bottom", "10px");
        depotBox.getStyle().set("background-color", "#f8f8f8");

        // Klick-Handler für Navigation zur Detailansicht
        depotBox.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(DetailedDepotView.class, depot.getDepotId()));
        });

        return depotBox;
    }
}
