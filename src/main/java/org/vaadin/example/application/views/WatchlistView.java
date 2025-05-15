package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Watchlist;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.KaufService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;

import java.util.List;

/**
 * Die WatchlistView stellt die Benutzeroberfläche für die Watchlist-Funktionalität der Anwendung dar.
 * Hier können Benutzer ihre beobachteten Wertpapiere einsehen, deren aktuelle Kurse und Trends verfolgen
 * und Aktionen wie das Entfernen von Wertpapieren aus der Watchlist durchführen.
 * Die Ansicht ist nur für authentifizierte Benutzer zugänglich.
 */
@Route("watchlist")
@PageTitle("Watchlist - Finovia")
@PermitAll
public class WatchlistView extends AbstractSideNav {

    /**
     * Service zur Verwaltung von Watchlists und deren Wertpapieren.
     */
    private final WatchlistService watchlistService;

    /**
     * Service zur Verwaltung von Nutzerdaten.
     */
    private final NutzerService nutzerService;

    /**
     * Service für den Zugriff auf die AlphaVantage API zur Abfrage von Finanzdaten.
     */
    private final AlphaVantageService alphaVantageService;

    /**
     * Service zur Verwaltung von Depots.
     */
    private final DepotService depotService;

    /**
     * Service zur Verwaltung von Käufen.
     */
    private final KaufService kaufService;

    /**
     * Der aktuell angemeldete Nutzer.
     */
    private Nutzer aktuellerNutzer;

    /**
     * Container für die Watchlist-Komponenten.
     */
    private final VerticalLayout watchlistContainer = new VerticalLayout();

    /**
     * Grid zur Anzeige der Wertpapiere in der Watchlist.
     */
    private Grid<Wertpapier> watchlistGrid;

    /**
     * Konstruktor für die WatchlistView.
     * Initialisiert die View und setzt die benötigten Services.
     * Lädt den aktuellen Nutzer und erstellt die Watchlist-Ansicht.
     *
     * @param watchlistService Service zur Verwaltung von Watchlists
     * @param nutzerService Service zur Verwaltung von Nutzerdaten
     * @param alphaVantageService Service für den Zugriff auf Finanzdaten
     */
    public WatchlistView(WatchlistService watchlistService, NutzerService nutzerService, 
                   AlphaVantageService alphaVantageService, DepotService depotService, KaufService kaufService) {
        super(); // Ruft den Konstruktor der Basisklasse auf
        this.watchlistService = watchlistService;
        this.nutzerService = nutzerService;
        this.alphaVantageService = alphaVantageService;
        this.depotService = depotService;
        this.kaufService = kaufService;

        // Hauptüberschrift
        H1 title = new H1("Meine Watchlist");
        title.addClassNames(LumoUtility.Margin.Bottom.MEDIUM);

        // Aktuelle Nutzerdaten laden
        ladeAktuellenNutzer();

        // Watchlist-Section erstellen
        erstelleWatchlistSection();

        // Container für den Hauptinhalt erstellen
        VerticalLayout watchlistContentLayout = new VerticalLayout();
        watchlistContentLayout.setPadding(true);
        watchlistContentLayout.setSpacing(true);

        // Inhalte zum Container hinzufügen
        watchlistContentLayout.add(title, watchlistContainer);

        // Container zum Hauptinhaltsbereich hinzufügen
        addToMainContent(watchlistContentLayout);
    }

    /**
     * Lädt den aktuell angemeldeten Nutzer aus dem Security Context.
     * Falls der Nutzer nicht gefunden wird, wird ein Fallback auf einen Standardnutzer versucht.
     * Bei Fehlern wird eine Benachrichtigung angezeigt.
     */
    private void ladeAktuellenNutzer() {
        try {
            // Aktuelle Nutzer-ID aus dem Security Context holen
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }

            // Nutzer aus dem Service laden
            aktuellerNutzer = nutzerService.getNutzerByUsername(username);

            if (aktuellerNutzer == null) {
                // Fallback für Entwicklungszwecke - Nutzer mit einer ID laden
                aktuellerNutzer = nutzerService.getNutzerById(1);

                if (aktuellerNutzer == null) {
                    Notification.show("Nutzerdaten konnten nicht geladen werden", 
                            3000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        } catch (Exception e) {
            Notification.show("Fehler beim Laden der Nutzerdaten: " + e.getMessage(), 
                    3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    /**
     * Erstellt den Watchlist-Bereich der Benutzeroberfläche.
     * Diese Methode konfiguriert das Grid zur Anzeige der Wertpapiere in der Watchlist
     * mit Spalten für Name, aktuellen Preis, Trend und Aktionen.
     * Falls die Watchlist leer ist, wird eine entsprechende Meldung angezeigt.
     */
    private void erstelleWatchlistSection() {
        watchlistContainer.removeAll();
        watchlistContainer.setPadding(false);
        watchlistContainer.setSpacing(true);

        H2 watchlistTitle = new H2("Beobachtete Wertpapiere");
        watchlistTitle.addClassNames(LumoUtility.Margin.Bottom.SMALL, LumoUtility.Margin.Top.MEDIUM);

        // Watchlist des Nutzers abrufen
        Watchlist watchlist = aktuellerNutzer.getWatchlist();

        // Wenn keine Watchlist existiert, eine erstellen
        if (watchlist == null) {
            watchlist = watchlistService.createWatchlist(aktuellerNutzer, "Meine Watchlist");
        }

        // Grid für die Wertpapiere in der Watchlist erstellen
        watchlistGrid = new Grid<>();
        watchlistGrid.addColumn(Wertpapier::getName).setHeader("Name").setSortable(true);

        // Aktueller Preis-Spalte hinzufügen
        watchlistGrid.addComponentColumn(wertpapier -> {
            if (wertpapier.getKurse() != null && !wertpapier.getKurse().isEmpty()) {
                // Sortiere Kurse nach Datum (neueste zuerst)
                List<Kurs> sortedKurse = wertpapier.getKurse().stream()
                        .sorted((k1, k2) -> k2.getDatum().compareTo(k1.getDatum()))
                        .toList();

                if (!sortedKurse.isEmpty()) {
                    return new Span(String.format("%.2f €", sortedKurse.get(0).getSchlusskurs()));
                }
            }
            return new Span("Kein Kurs verfügbar");
        }).setHeader("Aktueller Preis").setSortable(false);

        // Trend-Indikator-Spalte hinzufügen
        watchlistGrid.addComponentColumn(wertpapier -> {
            if (wertpapier.getKurse() != null && wertpapier.getKurse().size() >= 2) {
                // Sortiere Kurse nach Datum (neueste zuerst)
                List<Kurs> sortedKurse = wertpapier.getKurse().stream()
                        .sorted((k1, k2) -> k2.getDatum().compareTo(k1.getDatum()))
                        .toList();

                if (sortedKurse.size() >= 2) {
                    double currentPrice = sortedKurse.get(0).getSchlusskurs();
                    double previousPrice = sortedKurse.get(1).getSchlusskurs();

                    if (currentPrice > previousPrice) {
                        // Steigend - grüner Pfeil nach oben
                        Icon upIcon = new Icon(VaadinIcon.ARROW_UP);
                        upIcon.setColor("green");
                        return upIcon;
                    } else if (currentPrice < previousPrice) {
                        // Fallend - roter Pfeil nach unten
                        Icon downIcon = new Icon(VaadinIcon.ARROW_DOWN);
                        downIcon.setColor("red");
                        return downIcon;
                    } else {
                        // Unverändert - grauer Strich
                        Icon neutralIcon = new Icon(VaadinIcon.MINUS);
                        neutralIcon.setColor("gray");
                        return neutralIcon;
                    }
                }
            }
            // Wenn keine oder zu wenige Kursdaten vorhanden sind
            Icon unknownIcon = new Icon(VaadinIcon.QUESTION);
            unknownIcon.setColor("gray");
            return unknownIcon;
        }).setHeader("Trend").setSortable(false);

        // Aktionen-Spalte hinzufügen
        watchlistGrid.addComponentColumn(wertpapier -> {
            HorizontalLayout actions = new HorizontalLayout();

            // Button zum Anzeigen der Details
            Button detailsButton = new Button(new Icon(VaadinIcon.SEARCH), e -> {
                // Detailansicht des Wertpapiers öffnen
                WertpapierView wertpapierView = new WertpapierView(
                    alphaVantageService,
                    nutzerService,
                    watchlistService,
                    depotService

                );
                wertpapierView.displayWertpapierDetails(wertpapier.getIsin());
            });
            detailsButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

            // Button zum Entfernen aus der Watchlist
            Button removeButton = new Button(new Icon(VaadinIcon.TRASH), e -> {
                boolean removed = watchlistService.removeWertpapierFromWatchlist(aktuellerNutzer.getId(), wertpapier);
                if (removed) {
                    refreshWatchlistGrid();
                    Notification.show(wertpapier.getName() + " wurde aus der Watchlist entfernt", 
                            3000, Notification.Position.BOTTOM_START)
                            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } else {
                    Notification.show("Fehler beim Entfernen aus der Watchlist", 
                            3000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });
            removeButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);

            actions.add(detailsButton, removeButton);
            return actions;
        }).setHeader("Aktionen").setFlexGrow(0);

        // Daten für das Grid laden
        refreshWatchlistGrid();

        // Wenn keine Wertpapiere in der Watchlist sind, eine Meldung anzeigen
        if (watchlist.getWertpapiere().isEmpty()) {
            Paragraph emptyMessage = new Paragraph("Ihre Watchlist ist leer. Fügen Sie Wertpapiere hinzu, um sie zu beobachten.");
            watchlistContainer.add(watchlistTitle, emptyMessage);
        } else {
            watchlistContainer.add(watchlistTitle, watchlistGrid);
        }
    }

    /**
     * Aktualisiert das Watchlist-Grid mit den aktuellen Daten.
     * Lädt die Wertpapiere des aktuellen Nutzers aus der Datenbank
     * und aktualisiert die Anzeige im Grid.
     */
    private void refreshWatchlistGrid() {
        List<Wertpapier> wertpapiere = watchlistService.getWertpapiereInWatchlist(aktuellerNutzer.getId());
        watchlistGrid.setItems(wertpapiere);
    }
}
