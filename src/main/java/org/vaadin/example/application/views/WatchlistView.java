package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Watchlist;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.HashMap;
import java.util.Map;

/**
 * View-Klasse für die Anzeige und Verwaltung der Watchlist eines Nutzers.
 * Diese Klasse bietet eine Benutzeroberfläche zur Anzeige aller gespeicherten
 * Wertpapiere in der Watchlist und ermöglicht es dem Nutzer, Wertpapiere zu
 * entfernen oder Details anzuzeigen. Sie wurde erweitert, um aktuelle Preis-
 * und Trendinformationen anzuzeigen und in das Side-Navigation-Layout zu integrieren.
 */
@Route("watchlist")
@PageTitle("Meine Watchlist")
@PermitAll
public class WatchlistView extends AbstractSideNav {

    private final WatchlistService watchlistService;
    private final NutzerService nutzerService;
    private final AlphaVantageService alphaVantageService;
    private final WertpapierView wertpapierView;
    private final Grid<Wertpapier> grid = new Grid<>(Wertpapier.class, false);
    private Nutzer currentUser;
    private final Map<Integer, Span> priceSpanMap = new HashMap<>(); // Map für Preis-Spans
    private final Map<Integer, HorizontalLayout> trendLayoutMap = new HashMap<>(); // Map für Trend-Layouts
    private final Map<Integer, Span> trendTextSpanMap = new HashMap<>();
    private final Map<Integer, Icon> trendIconMap = new HashMap<>();

    /**
     * Konstruktor für die WatchlistView.
     *
     * @param watchlistService    Service für den Zugriff auf Watchlist-Funktionen
     * @param nutzerService       Service für den Zugriff auf Nutzer-Funktionen
     * @param alphaVantageService Service für den Zugriff auf Wertpapier-Daten
     * @param wertpapierView      Service für die Anzeige von Wertpapierdetails
     */
    @Autowired
    public WatchlistView(WatchlistService watchlistService, NutzerService nutzerService, AlphaVantageService alphaVantageService, WertpapierView wertpapierView) {
        super(); // Ruft den Konstruktor von AbstractSideNav auf
        this.watchlistService = watchlistService;
        this.nutzerService = nutzerService;
        this.alphaVantageService = alphaVantageService;
        this.wertpapierView = wertpapierView;

        // Erstelle ein VerticalLayout für den Hauptinhalt der Watchlist
        VerticalLayout watchlistContent = new VerticalLayout();
        watchlistContent.setSizeFull(); // Stellt sicher, dass das Layout den gesamten verfügbaren Platz einnimmt
        watchlistContent.setPadding(true);
        watchlistContent.setSpacing(true);
        watchlistContent.addClassNames(LumoUtility.Padding.LARGE, LumoUtility.Gap.MEDIUM); // Zusätzliche Polsterung und Lücken

        H2 header = new H2("Meine Watchlist");
        header.addClassNames(LumoUtility.Margin.Top.NONE, LumoUtility.Margin.Bottom.MEDIUM); // Oberen Rand entfernen, unteren Rand hinzufügen
        watchlistContent.add(header);

        // Refresh Button hinzufügen
        Button refreshButton = new Button("Aktualisieren", new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        refreshButton.addClickListener(e -> loadData());
        refreshButton.addClassNames(LumoUtility.Margin.Bottom.MEDIUM);

        HorizontalLayout topControls = new HorizontalLayout(refreshButton);
        topControls.setWidthFull();
        topControls.setJustifyContentMode(JustifyContentMode.END); // Button rechts ausrichten
        watchlistContent.add(topControls);


        configureGrid();
        // Setzt flex-grow auf 1, damit das Grid den übrigen Platz im VerticalLayout einnimmt
        // Dies ist der entscheidende Schritt, um das Grid "größer" zu machen,
        // da es sich nun vertikal ausdehnt.
        watchlistContent.setFlexGrow(1, grid);
        watchlistContent.add(grid); // Grid zum Watchlist-Inhalt hinzufügen


        // Füge das Watchlist-Inhaltslayout zum Hauptinhaltsbereich der SideNav hinzu
        addToMainContent(watchlistContent);

        loadData();
    }

    /**
     * Konfiguriert das Grid für die Anzeige der Wertpapiere. Es wurde erweitert,
     * um aktuelle Preis- und Trendinformationen anzuzeigen.
     */
    private void configureGrid() {
        grid.setSizeFull(); // Grid nimmt die gesamte Größe des verfügbaren Raums ein
        grid.addClassNames(LumoUtility.BorderRadius.MEDIUM, LumoUtility.BoxShadow.SMALL, LumoUtility.Background.CONTRAST_5); // Abgerundete Ecken, Schatten, leichter Hintergrund

        grid.addColumn(Wertpapier::getName).setHeader("Name").setFlexGrow(2);

        // Spalte für den aktuellen Preis
        grid.addComponentColumn(wertpapier -> {
            Span priceSpan = new Span("Loading...");
            priceSpan.setId("price-" + wertpapier.getWertpapierId());
            priceSpanMap.put(Math.toIntExact(wertpapier.getWertpapierId()), priceSpan); // Speichern der Span
            return priceSpan;
        }).setHeader("Aktueller Preis").setFlexGrow(1);

        // Spalte für die Trendanzeige
        grid.addComponentColumn(wertpapier -> {
            HorizontalLayout trendLayout = new HorizontalLayout();
            trendLayout.setAlignItems(Alignment.CENTER);
            Span trendText = new Span("Loading...");
            trendText.setId("trend-text-" + wertpapier.getWertpapierId());
            Icon trendIcon = new Icon();
            trendIcon.setId("trend-icon-" + wertpapier.getWertpapierId());
            trendLayout.add(trendText, trendIcon);
            trendLayoutMap.put(Math.toIntExact(wertpapier.getWertpapierId()), trendLayout); // Speichern des Layouts
            trendTextSpanMap.put(Math.toIntExact(wertpapier.getWertpapierId()), trendText);
            trendIconMap.put(Math.toIntExact(wertpapier.getWertpapierId()), trendIcon);
            return trendLayout;
        }).setHeader("Trend (24h)").setFlexGrow(1);

        // Aktionen-Spalte
        grid.addComponentColumn(wertpapier -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button detailsButton = new Button(new Icon(VaadinIcon.SEARCH), e -> showDetails(wertpapier));
            detailsButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            detailsButton.getElement().setAttribute("title", "Details anzeigen");
            Button removeButton = new Button(new Icon(VaadinIcon.TRASH), e -> removeFromWatchlist(wertpapier));
            removeButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            removeButton.getElement().setAttribute("title", "Von Watchlist entfernen");
            actions.add(detailsButton, removeButton);
            return actions;
        }).setHeader("Aktionen").setFlexGrow(1);
    }

    /**
     * Lädt die Daten der Watchlist des aktuellen Nutzers.
     * Ruft außerdem asynchron die aktuellen Preise und Trends ab.
     */
    private void loadData() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        currentUser = nutzerService.getNutzerByUsername(username);

        if (currentUser != null) {
            try {
                Optional<Watchlist> watchlistOpt = watchlistService.getWatchlistForUser(currentUser.getId());

                if (watchlistOpt.isPresent()) {
                    List<Wertpapier> wertpapiere = watchlistOpt.get().getWertpapiere();
                    grid.setItems(wertpapiere);

                    // Asynchrone Abfrage von Preis und Trend für jedes Wertpapier
                    for (Wertpapier wertpapier : wertpapiere) {
                        // Verwenden Sie CompletableFuture, um die Abfragen parallel auszuführen
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                // Annahme: Es gibt eine Methode im AlphaVantageService, um den aktuellen Preis abzurufen
                                double aktuellerPreis = alphaVantageService.getAktuellerKurs(wertpapier.getName());
                                // Annahme: Und eine für den Trend
                                double prozentualeAenderung24h = alphaVantageService.getProzentualeAenderung24h(wertpapier.getName());
                                return new Object[]{aktuellerPreis, prozentualeAenderung24h}; //Return als Object Array
                            } catch (Exception e) {
                                // Loggen Sie die Ausnahme, um Fehlerbehebung zu erleichtern
                                e.printStackTrace(); // TODO: Replace with proper logging
                                return new Object[] {null, null};
                            }
                        }).thenAccept(result -> {
                            // Aktualisieren Sie die UI mit den abgerufenen Daten
                            UI.getCurrent().access(() -> {
                                Span priceSpan = priceSpanMap.get(wertpapier.getWertpapierId());
                                Span trendTextSpan = trendTextSpanMap.get(wertpapier.getWertpapierId());
                                Icon trendIcon = trendIconMap.get(wertpapier.getWertpapierId());
                                if (priceSpan != null && trendTextSpan != null && trendIcon != null) {
                                    if (result[0] != null && result[1] != null) {
                                        double aktuellerPreis = (double) result[0];
                                        double prozentualeAenderung24h = (double) result[1];
                                        priceSpan.setText(String.format("%.2f USD", aktuellerPreis));

                                        String trendText = String.format("%.2f%% (24h)", prozentualeAenderung24h);
                                        trendTextSpan.setText(trendText);

                                        if (prozentualeAenderung24h > 0) {
                                            //  trendIcon.setIcon(VaadinIcon.TREND_UP); // Icon wird nicht gesetzt, siehe Anmerkung unten
                                            trendIcon.setColor("green");
                                        } else if (prozentualeAenderung24h < 0) {
                                            // trendIcon.setIcon(VaadinIcon.TREND_DOWN); // Icon wird nicht gesetzt, siehe Anmerkung unten
                                            trendIcon.setColor("red");
                                        } else {
                                            //trendIcon.setIcon(VaadinIcon.TRENDING_FLAT); // Icon wird nicht gesetzt, siehe Anmerkung unten
                                            trendIcon.setColor("gray");
                                        }
                                    } else {
                                        priceSpan.setText("Keine Daten");
                                        trendTextSpan.setText("Keine Daten");
                                        trendIcon.setIcon(VaadinIcon.INFO_CIRCLE);
                                        trendIcon.setColor("gray");
                                    }
                                }
                            });
                        });
                    }

                    if (wertpapiere.isEmpty()) {
                        showNotification("Ihre Watchlist ist leer. Fügen Sie Wertpapiere hinzu, um sie zu beobachten.", NotificationVariant.LUMO_PRIMARY);
                    }
                } else {
                    grid.setItems();
                    showNotification("Sie haben noch keine Watchlist. Eine Watchlist wird automatisch erstellt, wenn Sie ein Wertpapier hinzufügen.", NotificationVariant.LUMO_PRIMARY);
                }
            } catch (Exception e) {
                showNotification("Fehler beim Laden der Watchlist: " + e.getMessage(), NotificationVariant.LUMO_ERROR);
            }
        } else {
            showNotification("Nutzer nicht eingeloggt oder nicht gefunden", NotificationVariant.LUMO_ERROR);
        }
    }

    /**
     * Zeigt detaillierte Informationen zu einem Wertpapier an.
     * Schließt die Seitenleiste vor dem Öffnen des Detaildialogs und öffnet sie wieder,
     * sobald der Dialog geschlossen wird.
     *
     * @param wertpapier Das ausgewählte Wertpapier
     */
    private void showDetails(Wertpapier wertpapier) {
        try {
            // Schließe die Seitenleiste, bevor der Dialog geöffnet wird
            closeSideNav();

            String symbol = wertpapier.getName();
            // Rufe den Dialog von WertpapierView ab
            Dialog detailsDialog = wertpapierView.displayWertpapierDetails(symbol);

            // Füge einen Listener hinzu, der openSideNav() aufruft, wenn der Dialog geschlossen wird
            detailsDialog.addDetachListener(event -> openSideNav());
        } catch (Exception e) {
            showNotification("Fehler beim Anzeigen der Details: " + e.getMessage(), NotificationVariant.LUMO_ERROR);
        }
    }

    /**
     * Entfernt ein Wertpapier aus der Watchlist.
     * Nach dem Entfernen werden die Daten neu geladen, um das Grid zu aktualisieren.
     *
     * @param wertpapier Das zu entfernende Wertpapier
     */
    private void removeFromWatchlist(Wertpapier wertpapier) {
        try {
            watchlistService.removeWertpapierFromUserWatchlist(currentUser.getId(), wertpapier.getWertpapierId());
            loadData(); // Daten neu laden
            showNotification(wertpapier.getName() + " wurde aus der Watchlist entfernt", NotificationVariant.LUMO_SUCCESS);
        } catch (Exception e) {
            showNotification("Fehler beim Entfernen aus der Watchlist: " + e.getMessage(), NotificationVariant.LUMO_ERROR);
        }
    }

    /**
     * Zeigt eine Benachrichtigung mit der angegebenen Nachricht und Variante an.
     *
     * @param message Die anzuzeigende Nachricht
     * @param variant Die Variante der Benachrichtigung
     */
    private void showNotification(String message, NotificationVariant variant) {
        Notification notification = Notification.show(message, 3000, Notification.Position.MIDDLE);
        notification.addThemeVariants(variant);
    }
}