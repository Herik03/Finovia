package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.NutzerService;

/**
 * Die  DetailedDepotView-Klasse stellt eine detaillierte Ansicht eines Depots dar.
 * Sie zeigt Informationen über das Depot und eine Liste der enthaltenen Wertpapiere an.
 */
@Route(value = "depot-details")
@PageTitle("Depot Details")
@PermitAll
public class DetailedDepotView extends AbstractSideNav implements HasUrlParameter<Long> {

    private final DepotService depotService;
    private final NutzerService nutzerService;
    private final SecurityService securityService;
    private final AlphaVantageService alphaVantageService;
    private Depot currentDepot;
    private final H2 title = new H2("Depot Details");
    private final VerticalLayout depotInfoLayout = new VerticalLayout();
    private final Grid<DepotWertpapier> wertpapierGrid = new Grid<>(DepotWertpapier.class);
    private final VerticalLayout contentLayout = new VerticalLayout();
    private final Span gesamtwertSpan = new Span();

    /**
     * Konstruktor für die DetailedDepotView-Klasse.
     * Initialisiert die Ansicht mit einem DepotService.
     *
     * @param depotService Der Service für Depot-Operationen
     * @param nutzerService Der Service für Nutzer-Operationen
     * @param securityService Der Service für Security-Operationen
     */
    @Autowired
    public DetailedDepotView(DepotService depotService, NutzerService nutzerService, AlphaVantageService alphaVantageService, SecurityService securityService) {
        super(securityService);
        this.depotService = depotService;
        this.nutzerService = nutzerService;
        this.alphaVantageService = alphaVantageService;
        this.securityService = securityService;



        contentLayout.setWidthFull();
        contentLayout.setSpacing(true);
        contentLayout.setPadding(true);

        // Back Button in RouterLink
        Button backButton = new Button("Zurück zur Übersicht", VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        RouterLink routerLink = new RouterLink("", DepotView.class);
        routerLink.add(backButton);

        configureWertpapierGrid();
        // Komponenten zum Layout hinzufügen
        contentLayout.add(routerLink, title, depotInfoLayout, new H3("Enthaltene Wertpapiere"), wertpapierGrid);


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
            DepotService.BestandUndBuchwert bk = depotService.berechneBestandUndKosten(dw);
            if (bk.anzahl == 0) return "Keine Position";

            double kurs = 240.0; // Beispielwert, hier sollte der aktuelle Kurs des Wertpapiers stehen
//            if (dw.getWertpapier() instanceof Aktie aktie) {
//                kurs = alphaVantageService.getAktuellerKurs(aktie.getName());
//            }

            double wertAktuell = kurs * bk.anzahl;
            double gewinnVerlust = wertAktuell - bk.buchwert;

            //Depotwert = (Marktwert * Anzahl Anteile) - Buchwert
            // Buchwert = Summe der Kurse der vorhandenen Käufe

            return String.format("%.2f € (%s)", gewinnVerlust, gewinnVerlust >= 0 ? "Gewinn" : "Verlust");
        }).setHeader("Gewinn / Verlust").setAutoWidth(true);

        wertpapierGrid.addColumn(new ComponentRenderer<>(dw -> {
            if (dw.getWertpapier() instanceof Aktie) {
                Button verkaufenButton = new Button("Verkaufen", new Icon(VaadinIcon.MONEY_WITHDRAW));
                verkaufenButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);

                verkaufenButton.addClickListener(e -> {
                    String symbol = dw.getWertpapier().getSymbol();
                    String typ;

                    if (dw.getWertpapier() instanceof Aktie) {
                        typ = "aktie";
                    } else if (dw.getWertpapier() instanceof ETF) {
                        typ = "etf";
                    } else {
                        typ = "wertpapier"; // Fallback
                    }

                    getUI().ifPresent(ui -> ui.navigate("verkaufen/" + typ + "/" + symbol));
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

        // Sicherheitscheck: Darf der aktuelle Benutzer dieses Depot sehen?
        if (!isCurrentUserOwnerOfDepot()) {
            // Wenn nicht, zur Depot-Übersicht umleiten
            UI.getCurrent().navigate(DepotView.class);
            Notification.show("Sie haben keine Berechtigung, dieses Depot anzusehen.",
                            3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Titel aktualisieren

        title.setText(currentDepot.getName());
        updateDepotInfo();


        // Wertpapiere anzeigen
        wertpapierGrid.setItems(currentDepot.getDepotWertpapiere());

        DividendenPanel dividendenPanel = new DividendenPanel(currentDepot);

        // Layout zusammensetzen: Hauptinhalte links, Dividenden rechts
        HorizontalLayout mainArea = new HorizontalLayout();
        mainArea.setWidthFull();
        mainArea.add(contentLayout, dividendenPanel);
        mainArea.setFlexGrow(2, contentLayout);
        mainArea.setFlexGrow(1, dividendenPanel);

        mainArea.removeAll();
        mainArea.add(contentLayout, dividendenPanel);
        addToMainContent(mainArea);
    }

    /**
     * Überprüft, ob der aktuelle angemeldete Benutzer der Besitzer des angezeigten Depots ist.
     *
     * @return true, wenn der aktuelle Benutzer der Besitzer ist, sonst false
     */
    private boolean isCurrentUserOwnerOfDepot() {
        if (currentDepot == null || currentDepot.getBesitzer() == null) {
            return false;
        }

        UserDetails userDetails = securityService.getAuthenticatedUser();
        if (userDetails == null) {
            return false;
        }

        String username = userDetails.getUsername();
        Nutzer nutzer = nutzerService.findByUsername(username);

        return nutzer != null && nutzer.getId().equals(currentDepot.getBesitzer().getId());
    }

    private void updateDepotInfo() {
        depotInfoLayout.removeAll();

        HorizontalLayout ownerLayout = new HorizontalLayout(
                new Span("Besitzer: " + currentDepot.getBesitzer().getVollerName())
        );

        HorizontalLayout valueLayout = new HorizontalLayout(
                new Span("Depot-ID: " + currentDepot.getDepotId())
        );

        // Aktionen-Layout mit Löschen-Button
        Button deleteButton = new Button("Depot löschen", new Icon(VaadinIcon.TRASH));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        deleteButton.addClickListener(e -> openDeleteDepotDialog());

        // Trennlinie für den Löschen-Bereich
        Div divider = new Div();
        divider.addClassNames(
                LumoUtility.Background.CONTRAST_10,
                LumoUtility.Margin.Vertical.MEDIUM
        );
        divider.setHeight("1px");
        divider.setWidthFull();

        // Aktionsbereich für Gefahrenzonen-Funktionen
        VerticalLayout dangerZone = new VerticalLayout();
        dangerZone.setPadding(false);
        dangerZone.setSpacing(true);

        H3 dangerZoneTitle = new H3("Depot löschen");
        dangerZoneTitle.addClassNames(LumoUtility.TextColor.ERROR);

        Paragraph warningText = new Paragraph(
                "Achtung: Das Löschen des Depots entfernt alle damit verbundenen Daten unwiderruflich " +
                        "und kann nicht rückgängig gemacht werden.");
        warningText.addClassNames(LumoUtility.TextColor.ERROR);

        HorizontalLayout buttonLayout = new HorizontalLayout(deleteButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        dangerZone.add(dangerZoneTitle, warningText, buttonLayout);

        // Komponenten zum Layout hinzufügen
        depotInfoLayout.add(ownerLayout, valueLayout);

        // Füge den Löschen-Bereich am Ende des contentLayout hinzu (ganz unten auf der Seite)
        contentLayout.add(divider, dangerZone);
    }

    /**
     * Öffnet einen Bestätigungsdialog zum Löschen des Depots.
     */
    private void openDeleteDepotDialog() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Depot löschen");
        dialog.setText("Sind Sie sicher, dass Sie das Depot '" + currentDepot.getName() +
                "' und alle enthaltenen Wertpapiere löschen möchten? Diese Aktion kann nicht rückgängig gemacht werden.");

        dialog.setCancelable(true);
        dialog.setCancelText("Abbrechen");

        dialog.setConfirmText("Depot löschen");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(event -> {
            // Depot löschen
            depotService.deleteDepot(currentDepot.getDepotId());

            // Erfolgsmeldung anzeigen
            Notification.show("Das Depot wurde erfolgreich gelöscht.",
                            3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            // Zur Depot-Übersicht navigieren
            UI.getCurrent().navigate(DepotView.class);
        });

        dialog.open();
    }
}
//TODO:Einbinden der Funktionalität zum Kaufen und Verkaufen von Wertpapieren
//TODO:Wertpapiere in die Depot-Übersicht einfügen