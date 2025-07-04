package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.details.Details;
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

    /**
     * Konfiguriert die Spalten und das Layout der Wertpapier-Gitteransicht.
     * Zeigt Name, aktuellen Kurs, Gewinn/Verlust und eine Aktion zum Verkaufen an.
     */
    private void configureWertpapierGrid() {
        wertpapierGrid.removeAllColumns();

        wertpapierGrid.addColumn(dw -> dw.getWertpapier().getName())
                .setHeader("Name")
                .setAutoWidth(true);

        // Aktueller Kurs aus DepotService holen (unabhängig vom Typ)
        wertpapierGrid.addColumn(dw -> {
            double kurs = depotService.getAktuellerKurs(dw.getWertpapier());
            return kurs > 0.0 ? String.format("%.2f €", kurs) : "N/A";
        }).setHeader("Aktueller Kurs").setAutoWidth(true);

        // Gewinn/Verlust berechnen mit aktuellem Kurs
        wertpapierGrid.addColumn(dw -> {
            DepotService.BestandUndBuchwert bk = depotService.berechneBestandUndKosten(dw);
            if (bk.anzahl == 0) return "Keine Position";

            double kurs = depotService.getAktuellerKurs(dw.getWertpapier());
            double wertAktuell = kurs * bk.anzahl;
            double gewinnVerlust = wertAktuell - bk.buchwert;

            return String.format("%.2f € (%s)", gewinnVerlust, gewinnVerlust >= 0 ? "Gewinn" : "Verlust");
        }).setHeader("Gewinn / Verlust").setAutoWidth(true);

        // Aktion zum Verkaufen von Wertpapieren
        wertpapierGrid.addColumn(dw -> {
            DepotService.BestandUndBuchwert bk = depotService.berechneBestandUndKosten(dw);
            return bk.anzahl;
        }).setHeader("Anzahl").setAutoWidth(true);

        // Verkaufen-Button für Aktien, ETFs und Anleihen anzeigen
        wertpapierGrid.addColumn(new ComponentRenderer<>(dw -> {
            Wertpapier wp = dw.getWertpapier();
            if (wp instanceof Aktie || wp instanceof ETF || wp instanceof Anleihe) {
                Button verkaufenButton = new Button("Verkaufen", new Icon(VaadinIcon.MONEY_WITHDRAW));
                verkaufenButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);

                verkaufenButton.addClickListener(e -> {
                    String symbol = wp.getSymbol();
                    String typ;
                    if (wp instanceof Aktie) {
                        typ = "aktie";
                    } else if (wp instanceof ETF) {
                        typ = "etf";
                    } else {
                        typ = "anleihe";
                    }

                    getUI().ifPresent(ui -> ui.navigate("verkaufen/" + typ + "/" + symbol));
                });

                return verkaufenButton;
            } else {
                return new Span(""); // Leerer Platzhalter für andere Wertpapiere
            }
        })).setHeader("Aktion").setAutoWidth(true);

        wertpapierGrid.setWidthFull();
    }

    /**
     * Setzt den Parameter für die Ansicht, um ein bestimmtes Depot anzuzeigen.
     * Führt Sicherheitsprüfungen durch und aktualisiert die Anzeige entsprechend.
     *
     * @param event   Das BeforeEvent, das ausgelöst wird, bevor die Ansicht gerendert wird
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

        // Gesamtwert berechnen
        double gesamtwert = currentDepot.getDepotWertpapiere().stream()
            .mapToDouble(dw -> {
                double kurs = depotService.getAktuellerKurs(dw.getWertpapier());
                return kurs * dw.getAnzahl();
            })
            .sum();
        Span gesamtwertSpan = new Span("Gesamtwert: " + String.format("%.2f €", gesamtwert));
        gesamtwertSpan.getStyle().set("color", "black").set("font-weight", "bold").set("font-size", "1.5em");

        // DividendenPanel ein-/ausklappbar machen
        DividendenPanel dividendenPanel = new DividendenPanel(currentDepot);
        Details dividendenDetails = new Details("Dividenden anzeigen/ausblenden", dividendenPanel);
        dividendenDetails.setOpened(false);

        // Layout zusammensetzen: Hauptinhalte links, Dividenden rechts
        HorizontalLayout mainArea = new HorizontalLayout();
        mainArea.setWidthFull();
        mainArea.add(contentLayout, dividendenDetails);
        mainArea.setFlexGrow(2, contentLayout);
        mainArea.setFlexGrow(1, dividendenDetails);

        mainArea.removeAll();
        mainArea.add(contentLayout, dividendenDetails);
        addToMainContent(mainArea);

        // Gesamtwert unter dem Namen anzeigen
        if (!contentLayout.getChildren().anyMatch(c -> c.equals(gesamtwertSpan))) {
            contentLayout.addComponentAtIndex(2, gesamtwertSpan);
        }
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

    /**
     * Aktualisiert die Depot-Informationen im Layout.
     * Zeigt den Besitzer des Depots an und fügt einen Löschen-Button hinzu.
     */
    private void updateDepotInfo() {
        depotInfoLayout.removeAll();

        HorizontalLayout ownerLayout = new HorizontalLayout(
                new Span("Besitzer: " + currentDepot.getBesitzer().getVollerName())
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
        depotInfoLayout.add(ownerLayout);

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
