package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.services.DepotService;

/**
 * Die `DetailedDepotView`-Klasse stellt eine detaillierte Ansicht eines Depots dar.
 * Sie zeigt Informationen über das Depot und eine Liste der enthaltenen Wertpapiere an.
 */
@Route(value = "depot-details")
@PageTitle("Depot Details")
@PermitAll
public class DetailedDepotView extends VerticalLayout implements HasUrlParameter<String> {

    private final DepotService depotService;
    private Depot currentDepot;
    private final H2 title = new H2("Depot Details");
    private final VerticalLayout depotInfoLayout = new VerticalLayout();
    private final Grid<Wertpapier> wertpapierGrid = new Grid<>(Wertpapier.class);

    /**
     * Konstruktor für die `DetailedDepotView`-Klasse.
     * Initialisiert die Ansicht mit einem DepotService.
     * 
     * @param depotService Der Service für Depot-Operationen
     */
    @Autowired
    public DetailedDepotView(DepotService depotService) {
        this.depotService = depotService;

        // Layout-Einstellungen
        setWidthFull();
        setSpacing(true);
        setPadding(true);

        // Zurück-Button
        Button backButton = new Button("Zurück zur Übersicht", VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        RouterLink routerLink = new RouterLink("", DepotView.class);
        routerLink.add(backButton);

        // Wertpapier-Grid konfigurieren
        configureWertpapierGrid();

        // Komponenten zum Layout hinzufügen
        add(routerLink, title, depotInfoLayout, new H3("Enthaltene Wertpapiere"), wertpapierGrid);
    }

    /**
     * Konfiguriert das Grid für die Anzeige von Wertpapieren.
     */
    private void configureWertpapierGrid() {
        wertpapierGrid.setColumns("name", "isin", "wertpapierId");
        wertpapierGrid.addColumn(wertpapier -> {
            // Hier könnte der aktuelle Kurs angezeigt werden
            return "N/A";
        }).setHeader("Aktueller Kurs");

        wertpapierGrid.setWidthFull();
        wertpapierGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    /**
     * Wird aufgerufen, bevor die View angezeigt wird.
     * Lädt das Depot anhand der übergebenen ID.
     * 
     * @param event Das BeforeEvent
     * @param depotId Die ID des anzuzeigenden Depots
     */
    @Override
    public void setParameter(BeforeEvent event, String depotId) {
        // Depot aus dem Service laden
        currentDepot = depotService.getDepotById(depotId);

        if (currentDepot == null) {
            // Fehlerbehandlung, wenn das Depot nicht gefunden wurde
            add(new Span("Depot nicht gefunden"));
            return;
        }

        // Titel aktualisieren
        title.setText(currentDepot.getName());

        // Depot-Informationen anzeigen
        updateDepotInfo();

        // Wertpapiere anzeigen
        wertpapierGrid.setItems(currentDepot.getWertpapiere());
    }

    /**
     * Aktualisiert die Anzeige der Depot-Informationen.
     */
    private void updateDepotInfo() {
        depotInfoLayout.removeAll();

        // Depot-Informationen anzeigen
        HorizontalLayout ownerLayout = new HorizontalLayout(
                new Span("Besitzer: " + currentDepot.getBesitzer().getVollerName())
        );

        // Hier könnten weitere Informationen wie Gesamtwert, Performance, etc. angezeigt werden
        HorizontalLayout valueLayout = new HorizontalLayout(
                new Span("Depot-ID: " + currentDepot.getDepotId())
        );

        depotInfoLayout.add(ownerLayout, valueLayout);
    }
}
//TODO:Einbinden der Funktionalität zum Kaufen und Verkaufen von Wertpapieren
//TODO:Wertpapiere in die Depot-Übersicht einfügen