package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
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

    public DetailedDepotView(DepotService depotService, AlphaVantageService alphaVantageService) {
        super();
        this.depotService = depotService;
        this.alphaVantageService = alphaVantageService;

        contentLayout.setWidthFull();
        contentLayout.setSpacing(true);
        contentLayout.setPadding(true);

        // Back Button in RouterLink
        Button backButton = new Button("Zurück zur Übersicht", VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        RouterLink routerLink = new RouterLink("", DepotView.class);
        routerLink.add(backButton);

        configureWertpapierGrid();

        contentLayout.add(
                routerLink,
                title,
                depotInfoLayout,
                new H3("Enthaltene Wertpapiere"),
                wertpapierGrid,
                gesamtwertSpan
        );

        addToMainContent(contentLayout);
    }

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

        wertpapierGrid.addColumn(new ComponentRenderer<>(dw -> {
            if (dw.getWertpapier() instanceof Aktie) {
                Button verkaufenButton = new Button("Verkaufen", new Icon(VaadinIcon.MONEY_WITHDRAW));
                verkaufenButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);

                verkaufenButton.addClickListener(e -> {
                    Long depotId = currentDepot.getDepotId(); // Falls du es brauchst
                    String symbol = dw.getWertpapier().getSymbol(); // Hole das Symbol der Aktie
                    getUI().ifPresent(ui -> ui.navigate("verkaufen/" + symbol));
                });

                return verkaufenButton;
            } else {
                return new Span(""); // Leerer Platzhalter für Nicht-Aktien
            }
        })).setHeader("Aktion").setAutoWidth(true);

        wertpapierGrid.setWidthFull();
    }

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
    }

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