package org.vaadin.example.application.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.classes.enums.SearchResultTypeEnum;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.classes.SearchResult;
import org.vaadin.example.application.factory.WertpapierDetailViewFactory;
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.vaadin.example.application.services.AlphaVantageService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.dialog.Dialog; // Import for Dialog
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * View-Klasse für die Wertpapier-Suche.
 * Ermöglicht die Suche nach Wertpapieren, die Anzeige von Suchergebnissen
 * und den Zugriff auf detaillierte Wertpapierinformationen.
 * Zudem wurde die Navigation und die Steuerung der Seitenleiste verbessert.
 */
@Route("search")
@PageTitle("Wertpapier-API")
@CssImport("./styles/stock-view.css")
@AnonymousAllowed
public class SearchView extends AbstractSideNav {
    private final AlphaVantageService alphaVantageService;
    private final TextField searchField = new TextField("Wertpapier suchen");
    private final Button searchButton = new Button("Suchen", new Icon(VaadinIcon.SEARCH));
    private final ProgressBar progressBar = new ProgressBar();
    private final Grid<SearchResult> resultGrid = new Grid<>(SearchResult.class, false);
    private final SecurityService securityService;

    @Autowired
    private WertpapierRepository wertpapierRepository;
    @Autowired
    private WertpapierDetailViewFactory detailViewFactory;

    /**
     * Konstruktor für die SearchView.
     * Spring injiziert hier die benötigten Services.
     *
     * @param alphaVantageService Der Service zum Abrufen von Wertpapierdaten.
     *
     */
    @Autowired
    public SearchView(AlphaVantageService alphaVantageService, WertpapierDetailViewFactory detailViewFactory, SecurityService securityService) {
        super(securityService);
        this.securityService = securityService;
        this.alphaVantageService = alphaVantageService;

        VerticalLayout searchContent = new VerticalLayout();
        searchContent.setHeightFull();
        searchContent.setMaxWidth("1200px");
        searchContent.setMargin(true);
        searchContent.addClassName("stock-view");

        configureUI(searchContent);
        configureGrid();
        setupListeners();

        progressBar.setVisible(false);
        resultGrid.setVisible(true);

        addToMainContent(searchContent);
    }

    /**
     * Konfiguriert die Benutzeroberfläche der SearchView.
     * Fügt Suchfeld, Suchbutton, Fortschrittsanzeige und einen direkten Button zur Watchlist hinzu.
     *
     * @param container Das VerticalLayout, in das die UI-Komponenten eingefügt werden.
     */
    private void configureUI(VerticalLayout container) {
        H2 title = new H2("Wertpapier-Suche");
        title.addClassName("view-title");

        searchField.setPlaceholder("Symbol oder Name eingeben");
        searchField.setClearButtonVisible(true);
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setWidth("300px");

        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        progressBar.setIndeterminate(true);
        progressBar.setWidth("300px");

        HorizontalLayout searchLayout = new HorizontalLayout(searchField, searchButton);
        searchLayout.setAlignItems(Alignment.BASELINE);

        // Button zum Navigieren zur Watchlist
        Button goToWatchlistButton = new Button("Zur Watchlist", new Icon(VaadinIcon.STAR));
        goToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        goToWatchlistButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(WatchlistView.class)));

        // Layout für die oberen Steuerelemente
        HorizontalLayout topControls = new HorizontalLayout(searchLayout, goToWatchlistButton);
        topControls.setWidthFull();
        topControls.setAlignItems(Alignment.BASELINE);
        topControls.setJustifyContentMode(JustifyContentMode.BETWEEN); // Suchfeld links, Watchlist-Button rechts

        // Konfiguriert das Grid für die Suchergebnisse
        container.add(
                title,
                topControls, // Verwende das neue Layout für die oberen Steuerelemente
                progressBar,
                resultGrid
        );
    }

    /**
     * Konfiguriert das Grid zur Anzeige der Suchergebnisse.
     * Definiert Spalten für Symbol, Name, Region, Währung und Aktionen (Details, Kaufen).
     */
    private void configureGrid() {

        resultGrid.addColumn(SearchResult::symbol).setHeader("Symbol").setAutoWidth(true);
        resultGrid.addColumn(SearchResult::name).setHeader("Name").setAutoWidth(true).setFlexGrow(1);
        resultGrid.addColumn(SearchResult::region).setHeader("Region").setAutoWidth(true);
        resultGrid.addColumn(SearchResult::currency).setHeader("Währung").setAutoWidth(true);

        // Spalte für den Typ des Suchergebnisses
        resultGrid.addColumn(new ComponentRenderer<>(result -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button detailsButton = new Button("Details", new Icon(VaadinIcon.INFO_CIRCLE));
            detailsButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            detailsButton.addClickListener(e -> showDetails(result));

            Button kaufenButton = new Button("Kaufen", new Icon(VaadinIcon.CART));
            kaufenButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
            kaufenButton.addClickListener(e ->
                    {
                        var url = switch(result.type()) {
                            case SearchResultTypeEnum.AKTIE -> "aktie/";
                            case SearchResultTypeEnum.ETF -> "etf/";
                            case SearchResultTypeEnum.ANLEIHE -> "anleihe/";
                            default -> "";
                        };
                        getUI().ifPresent(ui -> ui.navigate("kaufen/" + url + result.symbol()));
                    }
            );

            actions.add(detailsButton, kaufenButton);
            return actions;
        })).setHeader("Aktion").setAutoWidth(true);

        resultGrid.setHeight("400px");
        resultGrid.addClassName("results-grid");
    }

    /**
     * Richtet die Event-Listener für das Suchfeld, den Suchbutton und das Ergebnis-Grid ein.
     */
    private void setupListeners() {
        searchField.addKeyPressListener(Key.ENTER, event -> performSearch());
        searchButton.addClickListener(event -> performSearch());

        resultGrid.addItemDoubleClickListener(event -> showDetails(event.getItem()));
    }

    /**
     * Führt die Wertpapier-Suche basierend auf dem eingegebenen Stichwort aus.
     * Aktualisiert den UI-Status (Fortschrittsanzeige, Button-Status) während der Suche.
     */
    private void performSearch() {
        String keyword = searchField.getValue();
        if (keyword == null || keyword.isBlank()) {
            showNotification("Bitte ein Stichwort eingeben.", NotificationVariant.LUMO_ERROR);
            return;
        }

        progressBar.setVisible(true);
        searchButton.setEnabled(false);

        // Asynchrone Suche, um UI-Blockierung zu vermeiden
        CompletableFuture
                .supplyAsync(() -> {
                    List<SearchResult> apiResults = alphaVantageService.search(keyword);
                    List<Wertpapier> lokaleTreffer = wertpapierRepository.searchByNameOrSymbol(keyword);

                    //List<Wertpapier> lokaleTreffer = wertpapierRepository.findByNameContainingIgnoreCase(keyword);
                    List<SearchResult> lokaleResults = lokaleTreffer.stream()
                            .map(w -> {
                                SearchResultTypeEnum typ;
                                if (w instanceof ETF) {
                                    typ = SearchResultTypeEnum.ETF;
                                } else if (w instanceof Anleihe) {
                                    typ = SearchResultTypeEnum.ANLEIHE;
                                } else {
                                    typ = SearchResultTypeEnum.UNBEKANNT;
                                }

                                return new SearchResult(
                                        w.getSymbol(),
                                        w.getName(),
                                        "Lokale DB",
                                        "EUR",
                                        typ
                                );
                            })
                            .toList();
                    apiResults.addAll(lokaleResults);
                    return apiResults;
                })
                .thenAccept(results -> getUI().ifPresent(ui -> ui.access(() -> handleSearchResults(results))));
    }


    /**
     * Verarbeitet die Suchergebnisse und aktualisiert das Grid.
     * Zeigt Benachrichtigungen basierend auf den gefundenen Ergebnissen an.
     *
     * @param results Die Liste der gefundenen Suchergebnisse.
     */
    private void handleSearchResults(List<SearchResult> results) {
        progressBar.setVisible(false);
        searchButton.setEnabled(true);

        if (results.isEmpty()) {
            resultGrid.setItems(List.of());
            showNotification("Keine Ergebnisse gefunden.", NotificationVariant.LUMO_CONTRAST);
        } else {
            resultGrid.setItems(results);
            showNotification(results.size() + " Treffer gefunden.", NotificationVariant.LUMO_SUCCESS);
        }

        resultGrid.getDataProvider().refreshAll();
        resultGrid.recalculateColumnWidths();
    }

    /**
     * Öffnet ein Detail-Dialogfenster für das übergebene Suchergebnis.
     * <p>
     * Wenn das Wertpapier bereits in der Datenbank existiert, wird dessen gespeicherte Version verwendet.
     * Falls nicht, wird es über die AlphaVantage-API geladen (nur für Aktien).
     *
     * Beim Schließen des Dialogs wird automatisch das SideNav erneut eingeblendet.
     *
     * @param result Das {@link SearchResult}, das die Symbolinformationen enthält
     */
    private void showDetails(SearchResult result) {
        closeSideNav();

        Optional<Wertpapier> dbWertpapierOpt = wertpapierRepository.findBySymbol(result.symbol());
        Dialog detailsDialog;

        if (dbWertpapierOpt.isPresent()) {
            detailsDialog = detailViewFactory.getDetailsDialog(dbWertpapierOpt.get());
        } else {
            Aktie aktie = alphaVantageService.getFundamentalData(result.symbol());
            if (aktie == null) {
                showNotification("Wertpapier nicht gefunden.", NotificationVariant.LUMO_ERROR);
                return;
            }
            detailsDialog = detailViewFactory.getDetailsDialog(aktie);
        }

        detailsDialog.addDetachListener(event -> openSideNav());
    }

    /**
     * Zeigt eine Benachrichtigung mit der angegebenen Nachricht und Variante an.
     *
     * @param message Die anzuzeigende Nachricht.
     * @param variant Die Variante der Benachrichtigung (z.B. LUMO_ERROR, LUMO_SUCCESS).
     */
    private void showNotification(String message, NotificationVariant variant) {
        Notification notification = new Notification(message, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(variant);
        notification.open();
    }
}