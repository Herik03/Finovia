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
import org.vaadin.example.application.classes.Kurs;
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
    private final Map<Integer, Span> priceSpanMap = new HashMap<>();
    private final Map<Integer, HorizontalLayout> trendLayoutMap = new HashMap<>();
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
        super();
        this.watchlistService = watchlistService;
        this.nutzerService = nutzerService;
        this.alphaVantageService = alphaVantageService;
        this.wertpapierView = wertpapierView;

        VerticalLayout watchlistContent = new VerticalLayout();
        watchlistContent.setSizeFull();
        watchlistContent.setPadding(true);
        watchlistContent.setSpacing(true);
        watchlistContent.addClassNames(LumoUtility.Padding.LARGE, LumoUtility.Gap.MEDIUM);

        watchlistContent.setHeightFull();
        watchlistContent.getStyle().set("min-height", "100vh");

        H2 header = new H2("Meine Watchlist");
        header.addClassNames(LumoUtility.Margin.Top.NONE, LumoUtility.Margin.Bottom.MEDIUM);
        watchlistContent.add(header);

        Button refreshButton = new Button("Aktualisieren", new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        refreshButton.addClickListener(e -> loadData());
        refreshButton.addClassNames(LumoUtility.Margin.Bottom.MEDIUM);

        HorizontalLayout topControls = new HorizontalLayout(refreshButton);
        topControls.setWidthFull();
        topControls.setJustifyContentMode(JustifyContentMode.END);
        watchlistContent.add(topControls);

        configureGrid();

        watchlistContent.setFlexGrow(1, grid);
        watchlistContent.add(grid);

        addToMainContent(watchlistContent);

        getMainContent().setSizeFull();
        getMainContent().setFlexGrow(1, watchlistContent);

        loadData();
    }

    /**
     * Konfiguriert das Grid für die Anzeige der Wertpapiere. Es wurde erweitert,
     * um aktuelle Preis- und Trendinformationen anzuzeigen.
     */
    private void configureGrid() {
        grid.setSizeFull();

        grid.setHeightFull();
        grid.getStyle().set("flex-grow", "1");
        grid.getStyle().set("min-height", "400px");
        grid.getStyle().set("max-height", "none");

        grid.addClassNames(LumoUtility.BorderRadius.MEDIUM, LumoUtility.BoxShadow.SMALL, LumoUtility.Background.CONTRAST_5);

        grid.addColumn(Wertpapier::getName).setHeader("Name").setFlexGrow(2);

        grid.addComponentColumn(wertpapier -> {
            Span priceSpan = new Span("Loading...");
            priceSpan.setId("price-" + wertpapier.getWertpapierId());
            priceSpanMap.put(Math.toIntExact(wertpapier.getWertpapierId()), priceSpan);
            return priceSpan;
        }).setHeader("Aktueller Preis").setFlexGrow(1);

        grid.addComponentColumn(wertpapier -> {
            HorizontalLayout trendLayout = new HorizontalLayout();
            trendLayout.setAlignItems(Alignment.CENTER);
            Span trendText = new Span("Loading...");
            trendText.setId("trend-text-" + wertpapier.getWertpapierId());
            Icon trendIcon = new Icon();
            trendIcon.setId("trend-icon-" + wertpapier.getWertpapierId());
            trendLayout.add(trendText, trendIcon);
            trendLayoutMap.put(Math.toIntExact(wertpapier.getWertpapierId()), trendLayout);
            trendTextSpanMap.put(Math.toIntExact(wertpapier.getWertpapierId()), trendText);
            trendIconMap.put(Math.toIntExact(wertpapier.getWertpapierId()), trendIcon);
            return trendLayout;
        }).setHeader("Trend (24h)").setFlexGrow(1);

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

                    for (Wertpapier wertpapier : wertpapiere) {

                        List<Kurs> aktuellerPreis = wertpapier.getKurse();

                        UI.getCurrent().access(() -> {
                            Span priceSpan = priceSpanMap.get(wertpapier.getWertpapierId());
                            if (priceSpan != null) {
                                priceSpan.setText(String.format("%.2f USD", aktuellerPreis));
                            }
                        });

                        // Asynchrone Abfrage der prozentualen Änderung in den letzten 24 Stunden
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                return alphaVantageService.getProzentualeAenderung24h(wertpapier.getName());
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        }).thenAccept(prozentualeAenderung24h -> {
                            UI.getCurrent().access(() -> {
                                Span trendTextSpan = trendTextSpanMap.get(wertpapier.getWertpapierId());
                                Icon trendIcon = trendIconMap.get(wertpapier.getWertpapierId());
                                if (trendTextSpan != null && trendIcon != null) {
                                    if (prozentualeAenderung24h != null) {
                                        String trendText = String.format("%.2f%% (24h)", prozentualeAenderung24h);
                                        trendTextSpan.setText(trendText);

                                        if (prozentualeAenderung24h > 0) {
                                            trendIcon.setColor("green");
                                        } else if (prozentualeAenderung24h < 0) {
                                            trendIcon.setColor("red");
                                        } else {
                                            trendIcon.setColor("gray");
                                        }
                                    } else {
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
            closeSideNav();

            String symbol = wertpapier.getName();
            Dialog detailsDialog = wertpapierView.displayWertpapierDetails(symbol);

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
            loadData();
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