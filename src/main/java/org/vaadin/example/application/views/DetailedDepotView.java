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
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.DepotService;

/**
 * Die `DetailedDepotView`-Klasse stellt eine detaillierte Ansicht eines Depots dar.
 * Sie zeigt Informationen über das Depot und eine Liste der enthaltenen Wertpapiere an.
 */
@Route(value = "depot-details")
@PageTitle("Depot Details")
@PermitAll
public class DetailedDepotView extends AbstractSideNav implements HasUrlParameter<Long> {

    private final DepotService depotService;
    private final AlphaVantageService alphaVantageService;
    private Depot currentDepot;
    private final H2 title = new H2("Depot Details");
    private final VerticalLayout depotInfoLayout = new VerticalLayout();
    private final Grid<DepotWertpapier> wertpapierGrid = new Grid<>(DepotWertpapier.class);
    private final VerticalLayout contentLayout = new VerticalLayout();
    private final Span gesamtwertSpan = new Span();

    /**
     * Konstruktor für die `DetailedDepotView`-Klasse.
     * Initialisiert die Ansicht mit einem DepotService.
     *
     * @param depotService Der Service für Depot-Operationen
     */
    @Autowired
    public DetailedDepotView(DepotService depotService, AlphaVantageService alphaVantageService) {
        super();
        this.depotService = depotService;
        this.alphaVantageService = alphaVantageService;

        contentLayout.setWidthFull();
        contentLayout.setSpacing(true);
        contentLayout.setPadding(true);

        // Back Button
        Button backButton = new Button("Zurück zur Übersicht", VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        RouterLink routerLink = new RouterLink("", DepotView.class);
        routerLink.add(backButton);

        // Grid konfigurieren
        configureWertpapierGrid();

        contentLayout.add(routerLink, title, depotInfoLayout, new H3("Enthaltene Wertpapiere"), wertpapierGrid, gesamtwertSpan);
        addToMainContent(contentLayout);
    }

    /**
     * Konfiguriert das Grid für die Anzeige von Wertpapieren.
     */
    private void configureWertpapierGrid() {
        wertpapierGrid.removeAllColumns();

        wertpapierGrid.addColumn(dw -> dw.getWertpapier().getName())
                .setHeader("Name")
                .setAutoWidth(true);

        wertpapierGrid.addColumn(dw -> dw.getWertpapier().getWertpapierId())
                .setHeader("ID")
                .setAutoWidth(true);

        wertpapierGrid.addColumn(DepotWertpapier::getAnzahl)
                .setHeader("Anzahl")
                .setAutoWidth(true);

        wertpapierGrid.addColumn(dw -> {
            if (dw.getWertpapier() instanceof Aktie aktie) {
                return String.format("%.2f €", alphaVantageService.getAktuellerKurs(aktie.getName()));
            }
            return "N/A";
        }).setHeader("Aktueller Kurs").setAutoWidth(true);

        wertpapierGrid.addColumn(dw -> {
            DepotService.BestandUndKosten bk = depotService.berechneBestandUndKosten(dw);
            if (bk.anzahl == 0) return "Keine Position";

            double kurs = 0.0;
            if (dw.getWertpapier() instanceof Aktie aktie) {
                kurs = alphaVantageService.getAktuellerKurs(aktie.getName());
            }

            double wertAktuell = kurs * bk.anzahl;
            double gewinnVerlust = wertAktuell - bk.kosten;

            return String.format("%.2f € (%s)", gewinnVerlust, gewinnVerlust >= 0 ? "Gewinn" : "Verlust");
        }).setHeader("Gewinn / Verlust").setAutoWidth(true);

        wertpapierGrid.setWidthFull();
    }

    /**
     * Wird aufgerufen, bevor die View angezeigt wird.
     * Lädt das Depot anhand der übergebenen ID.
     *
     * @param event Das BeforeEvent
     * @param depotId Die ID des anzuzeigenden Depots
     */
    @Override
    public void setParameter(BeforeEvent event, Long depotId) {
        currentDepot = depotService.getDepotById(depotId);

        if (currentDepot == null) {
            contentLayout.removeAll();
            contentLayout.add(new Span("Depot nicht gefunden"));
            return;
        }

        title.setText(currentDepot.getName());
        updateDepotInfo();

        wertpapierGrid.setItems(currentDepot.getDepotWertpapiere());
        updateGesamtwert();

        DividendenPanel dividendenPanel = new DividendenPanel(currentDepot);

        HorizontalLayout mainArea = new HorizontalLayout(contentLayout, dividendenPanel);
        mainArea.setWidthFull();
        mainArea.setFlexGrow(2, contentLayout);
        mainArea.setFlexGrow(1, dividendenPanel);

        addToMainContent(mainArea);
    }

    /**
     * Aktualisiert die Anzeige der Depot-Informationen.
     */
    private void updateDepotInfo() {
        depotInfoLayout.removeAll();

        HorizontalLayout ownerLayout = new HorizontalLayout(
                new Span("Besitzer: " + currentDepot.getBesitzer().getVollerName())
        );

        HorizontalLayout valueLayout = new HorizontalLayout(
                new Span("Depot-ID: " + currentDepot.getDepotId())
        );

        depotInfoLayout.add(ownerLayout, valueLayout);
    }

    private void updateGesamtwert() {
        double gesamtwert = 0.0;

        for (DepotWertpapier dw : currentDepot.getDepotWertpapiere()) {
            if (dw.getWertpapier() instanceof Aktie aktie) {
                double kurs = alphaVantageService.getAktuellerKurs(aktie.getName());
                gesamtwert += kurs * dw.getAnzahl();
            }
        }

        gesamtwertSpan.setText("Gesamtwert des Depots: " + String.format("%.2f €", gesamtwert));
    }
}

//TODO:Einbinden der Funktionalität zum Kaufen und Verkaufen von Wertpapieren
//TODO:Wertpapiere in die Depot-Übersicht einfügen