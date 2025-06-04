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
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Anleihe;
import org.vaadin.example.application.classes.ETF;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.Security.SecurityService;
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
    private final WertpapierDetailViewFactory wertpapierView;
    private final Grid<Wertpapier> grid = new Grid<>(Wertpapier.class, false);
    private Nutzer currentUser;
    private final Map<Integer, Span> priceSpanMap = new HashMap<>();
    private final Map<Integer, HorizontalLayout> trendLayoutMap = new HashMap<>();
    private final Map<Integer, Span> trendTextSpanMap = new HashMap<>();
    private final Map<Integer, Icon> trendIconMap = new HashMap<>();
    private final SecurityService securityService;
    @Autowired
    private WertpapierDetailViewFactory detailViewFactory;


    /**
     * Konstruktor für die WatchlistView.
     *
     * @param watchlistService    Service für den Zugriff auf Watchlist-Funktionen
     * @param nutzerService       Service für den Zugriff auf Nutzer-Funktionen
     * @param alphaVantageService Service für den Zugriff auf Wertpapier-Daten
     * @param detailViewFactory      Service für die Anzeige von Wertpapierdetails
     */
    @Autowired
    public WatchlistView(WatchlistService watchlistService, NutzerService nutzerService, AlphaVantageService alphaVantageService, WertpapierDetailViewFactory detailViewFactory, SecurityService securityService) {
        super(securityService);
        this.watchlistService = watchlistService;
        this.nutzerService = nutzerService;
        this.alphaVantageService = alphaVantageService;
        this.wertpapierView = detailViewFactory;
        this.securityService = securityService;
        this.detailViewFactory = detailViewFactory;



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

        // Header für breitere Preisinformationen aktualisiert
        grid.addComponentColumn(wertpapier -> {
            // Initialisiere mit tatsächlichen Werten statt "Loading..."
            String priceText = getInitialPriceText(wertpapier);
            Span priceSpan = new Span(priceText);
            priceSpan.setId("price-" + wertpapier.getWertpapierId());
            priceSpanMap.put(Math.toIntExact(wertpapier.getWertpapierId()), priceSpan);
            return priceSpan;
        }).setHeader("Preisinformation").setFlexGrow(1);

//        grid.addComponentColumn(wertpapier -> {
//            HorizontalLayout trendLayout = new HorizontalLayout();
//            trendLayout.setAlignItems(Alignment.CENTER);
//            Span trendText = new Span("Loading...");
//            trendText.setId("trend-text-" + wertpapier.getWertpapierId());
//            Icon trendIcon = new Icon();
//            trendIcon.setId("trend-icon-" + wertpapier.getWertpapierId());
//            trendLayout.add(trendText, trendIcon);
//            trendLayoutMap.put(Math.toIntExact(wertpapier.getWertpapierId()), trendLayout);
//            trendTextSpanMap.put(Math.toIntExact(wertpapier.getWertpapierId()), trendText);
//            trendIconMap.put(Math.toIntExact(wertpapier.getWertpapierId()), trendIcon);
//            return trendLayout;
//        }).setHeader("Trend (24h)").setFlexGrow(1);

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
                    // Die optimierte Methode verwenden, die Join Fetch nutzt, um das LazyInitializationException-Problem zu beheben
                    List<Wertpapier> wertpapiere = watchlistService.getWertpapiereInUserWatchlist(currentUser.getId());
                    grid.setItems(wertpapiere);

                    // Benachrichtigung anzeigen, dass Daten geladen werden
                    showNotification("Aktualisiere Preisdaten für " + wertpapiere.size() + " Wertpapiere...", NotificationVariant.LUMO_PRIMARY);

                    if (wertpapiere.isEmpty()) {
                        showNotification("Ihre Watchlist ist leer. Fügen Sie Wertpapiere hinzu, um sie zu beobachten.", NotificationVariant.LUMO_PRIMARY);
                    } else {
                        // Explizit den DataProvider aktualisieren, um sicherzustellen, dass alle Änderungen angezeigt werden
                        grid.getDataProvider().refreshAll();
                        showNotification("Preisdaten wurden erfolgreich aktualisiert.", NotificationVariant.LUMO_SUCCESS);
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
            Dialog detailsDialog = wertpapierView.getDetailsDialog(wertpapier);


            detailsDialog.addDetachListener(event -> openSideNav());
            detailsDialog.open();
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

    /**
     * Berechnet den initialen Preistext für ein Wertpapier basierend auf seinem Typ und verfügbaren Daten.
     *
     * @param wertpapier Das Wertpapier, für das der Preistext berechnet werden soll
     * @return Ein formatierter Preistext oder eine Meldung, wenn keine Daten verfügbar sind
     */
    private String getInitialPriceText(Wertpapier wertpapier) {
        String type = wertpapier.getTyp();
        List<Kurs> kursDaten = wertpapier.getKurse();
        Kurs latestKurs = (kursDaten != null && !kursDaten.isEmpty()) ? kursDaten.get(kursDaten.size() - 1) : null;

        switch (type) {
            case "Aktie":
                if (wertpapier instanceof Aktie) {
                    Aktie aktie = (Aktie) wertpapier;
                    if (latestKurs != null) {
                        return String.format("%.2f USD ", latestKurs.getSchlusskurs());
                    } else if (aktie.getBookValue() != 0.0) {
                        return String.format("%.2f USD ", aktie.getBookValue());
                    } else {
                        return "Keine Preisdaten";
                    }
                } else {
                    return "Fehler: Typ Aktie";
                }
            case "ETF":
                if (latestKurs != null) {
                    return String.format("%.2f USD ", latestKurs.getSchlusskurs());
                } else {
                    return "Keine Kursdaten";
                }
            case "Anleihe":
                if (wertpapier instanceof Anleihe) {
                    Anleihe anleihe = (Anleihe) wertpapier;
                    if (latestKurs != null) {
                        return String.format("%.2f USD ", latestKurs.getSchlusskurs());
                    } else if (anleihe.getNennwert() != 0.0) {
                        return String.format("%.2f USD ", anleihe.getNennwert());
                    } else {
                        return "Keine Preisdaten";
                    }
                } else {
                    return "Fehler: Typ Anleihe";
                }
            default:
                if (latestKurs != null) {
                    return String.format("%.2f USD ", latestKurs.getSchlusskurs());
                } else {
                    return "Keine Preisdaten";
                }
        }
    }
}
