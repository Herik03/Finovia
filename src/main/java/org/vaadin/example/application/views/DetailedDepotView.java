package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.ColumnTextAlign;
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
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.classes.Transaktion;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.services.DepotService;

import java.util.Comparator;
import java.util.List;

/**
 * Die `DetailedDepotView`-Klasse stellt eine detaillierte Ansicht eines Depots dar.
 * Sie zeigt Informationen über das Depot und eine Liste der enthaltenen Wertpapiere an.
 */
@Route(value = "depot-details")
@PageTitle("Depot Details")
@PermitAll
public class DetailedDepotView extends AbstractSideNav implements HasUrlParameter<String> {

    private final DepotService depotService;
    private Depot currentDepot;
    private final H2 title = new H2("Depot Details");
    private final VerticalLayout depotInfoLayout = new VerticalLayout();
    private final Grid<Wertpapier> wertpapierGrid = new Grid<>(Wertpapier.class);
    private final VerticalLayout contentLayout = new VerticalLayout();
    private final Span gesamtwertSpan = new Span("0.00 €");

    /**
     * Konstruktor für die `DetailedDepotView`-Klasse.
     * Initialisiert die Ansicht mit einem DepotService.
     * 
     * @param depotService Der Service für Depot-Operationen
     */
    @Autowired
    public DetailedDepotView(DepotService depotService) {
        super();
        this.depotService = depotService;

        // Layout-Einstellungen
        contentLayout.setWidthFull();
        contentLayout.setSpacing(true);
        contentLayout.setPadding(true);

        // Zurück-Button
        Button backButton = new Button("Zurück zur Übersicht", VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        RouterLink routerLink = new RouterLink("", DepotView.class);
        routerLink.add(backButton);

        // Wertpapier-Grid konfigurieren
        configureWertpapierGrid();

        // Gesamtwert-Anzeige erstellen
        H3 gesamtwertTitle = new H3("Gesamtwert des Depots");
        gesamtwertSpan.getElement().getStyle().set("font-size", "24px");
        gesamtwertSpan.getElement().getStyle().set("font-weight", "bold");
        gesamtwertSpan.getElement().getStyle().set("color", "var(--lumo-primary-color)");
        gesamtwertSpan.setId("gesamtwert-span");

        VerticalLayout gesamtwertLayout = new VerticalLayout(gesamtwertTitle, gesamtwertSpan);
        gesamtwertLayout.setSpacing(false);
        gesamtwertLayout.setPadding(true);
        gesamtwertLayout.getStyle().set("background-color", "var(--lumo-contrast-5pct)");
        gesamtwertLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        gesamtwertLayout.getStyle().set("margin-bottom", "var(--lumo-space-m)");

        // Komponenten zum Layout hinzufügen
        contentLayout.add(routerLink, title, depotInfoLayout, gesamtwertLayout, new H3("Enthaltene Wertpapiere"), wertpapierGrid);

        // Content-Layout zum Hauptinhalt hinzufügen
        addToMainContent(contentLayout);
    }

    /**
     * Konfiguriert das Grid für die Anzeige von Wertpapieren.
     */
    private void configureWertpapierGrid() {
        wertpapierGrid.setColumns("name", "isin");

        // Stückzahl-Spalte hinzufügen
        wertpapierGrid.addColumn(wertpapier -> {
            // Summe aller Kauf-Transaktionen für dieses Wertpapier
            int stueckzahl = 0;
            for (Transaktion transaktion : wertpapier.getTransaktionen()) {
                if (transaktion instanceof Kauf) {
                    stueckzahl += transaktion.getStückzahl();
                }
            }
            return stueckzahl;
        }).setHeader("Stückzahl").setTextAlign(ColumnTextAlign.END);

        // Aktueller Kurs-Spalte hinzufügen
        wertpapierGrid.addColumn(wertpapier -> {
            if (wertpapier.getKurse() != null && !wertpapier.getKurse().isEmpty()) {
                // Sortiere Kurse nach Datum (neueste zuerst)
                List<Kurs> sortedKurse = wertpapier.getKurse().stream()
                        .sorted((k1, k2) -> k2.getDatum().compareTo(k1.getDatum()))
                        .toList();

                if (!sortedKurse.isEmpty()) {
                    return String.format("%.2f €", sortedKurse.get(0).getSchlusskurs());
                }
            }
            return "N/A";
        }).setHeader("Aktueller Kurs").setTextAlign(ColumnTextAlign.END);

        // Wert-Spalte hinzufügen
        wertpapierGrid.addColumn(wertpapier -> {
            // Stückzahl ermitteln
            int stueckzahl = 0;
            for (Transaktion transaktion : wertpapier.getTransaktionen()) {
                if (transaktion instanceof Kauf) {
                    stueckzahl += transaktion.getStückzahl();
                }
            }

            // Aktuellen Kurs ermitteln
            double kurs = 0.0;
            if (wertpapier.getKurse() != null && !wertpapier.getKurse().isEmpty()) {
                List<Kurs> sortedKurse = wertpapier.getKurse().stream()
                        .sorted((k1, k2) -> k2.getDatum().compareTo(k1.getDatum()))
                        .toList();

                if (!sortedKurse.isEmpty()) {
                    kurs = sortedKurse.get(0).getSchlusskurs();
                }
            }

            // Wert berechnen
            double wert = stueckzahl * kurs;
            return String.format("%.2f €", wert);
        }).setHeader("Wert").setTextAlign(ColumnTextAlign.END);

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
            contentLayout.removeAll();
            contentLayout.add(new Span("Depot nicht gefunden"));
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
     * Aktualisiert die Anzeige der Depot-Informationen und berechnet den Gesamtwert.
     */
    private void updateDepotInfo() {
        depotInfoLayout.removeAll();

        // Depot-Informationen anzeigen
        HorizontalLayout ownerLayout = new HorizontalLayout(
                new Span("Besitzer: " + currentDepot.getBesitzer().getVollerName())
        );

        // Depot-ID anzeigen
        HorizontalLayout valueLayout = new HorizontalLayout(
                new Span("Depot-ID: " + currentDepot.getDepotId())
        );

        depotInfoLayout.add(ownerLayout, valueLayout);

        // Gesamtwert des Depots berechnen
        calculateDepotGesamtwert();
    }

    /**
     * Berechnet den Gesamtwert des Depots und aktualisiert die Anzeige.
     */
    private void calculateDepotGesamtwert() {
        double gesamtwert = 0.0;

        for (Wertpapier wertpapier : currentDepot.getWertpapiere()) {
            // Stückzahl ermitteln
            int stueckzahl = 0;
            for (Transaktion transaktion : wertpapier.getTransaktionen()) {
                if (transaktion instanceof Kauf) {
                    stueckzahl += transaktion.getStückzahl();
                }
            }

            // Aktuellen Kurs ermitteln
            double kurs = 0.0;
            if (wertpapier.getKurse() != null && !wertpapier.getKurse().isEmpty()) {
                List<Kurs> sortedKurse = wertpapier.getKurse().stream()
                        .sorted((k1, k2) -> k2.getDatum().compareTo(k1.getDatum()))
                        .toList();

                if (!sortedKurse.isEmpty()) {
                    kurs = sortedKurse.get(0).getSchlusskurs();
                }
            }

            // Wert berechnen und zum Gesamtwert addieren
            double wert = stueckzahl * kurs;
            gesamtwert += wert;
        }

        // Gesamtwert anzeigen
        gesamtwertSpan.setText(String.format("%.2f €", gesamtwert));
    }
}
//TODO:Einbinden der Funktionalität zum Kaufen und Verkaufen von Wertpapieren
//TODO:Wertpapiere in die Depot-Übersicht einfügen
