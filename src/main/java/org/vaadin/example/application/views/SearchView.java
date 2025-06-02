package org.vaadin.example.application.views;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.models.SearchResult;
import org.vaadin.example.application.services.AlphaVantageService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment; // Import for Alignment

import java.util.List;
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
    private final WertpapierView wertpapierView;

    private final TextField searchField = new TextField("Wertpapier suchen");
    private final Button searchButton = new Button("Suchen", new Icon(VaadinIcon.SEARCH));
    private final ProgressBar progressBar = new ProgressBar();
    private final Grid<SearchResult> resultGrid = new Grid<>(SearchResult.class, false);
    private final SecurityService securityService;
    /**
     * Konstruktor für die SearchView.
     * Spring injiziert hier die benötigten Services.
     *
     * @param alphaVantageService Der Service zum Abrufen von Wertpapierdaten.
     * @param wertpapierView Die View zum Anzeigen von Wertpapierdetails.
     */
    @Autowired
    public SearchView(AlphaVantageService alphaVantageService, WertpapierView wertpapierView, SecurityService securityService) {
        super(securityService);
        this.securityService = securityService;
        this.alphaVantageService = alphaVantageService;
        this.wertpapierView = wertpapierView;

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
        H3 title = new H3("Wertpapier-Suche");
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

        // NEUER BUTTON: Direkt zur Watchlist
        Button goToWatchlistButton = new Button("Zur Watchlist", new Icon(VaadinIcon.STAR));
        goToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        goToWatchlistButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(WatchlistView.class)));

        HorizontalLayout topControls = new HorizontalLayout(searchLayout, goToWatchlistButton);
        topControls.setWidthFull();
        topControls.setAlignItems(Alignment.BASELINE);
        topControls.setJustifyContentMode(JustifyContentMode.BETWEEN); // Suchfeld links, Watchlist-Button rechts

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

        resultGrid.addColumn(SearchResult::getSymbol).setHeader("Symbol").setAutoWidth(true);
        resultGrid.addColumn(SearchResult::getName).setHeader("Name").setAutoWidth(true).setFlexGrow(1);
        resultGrid.addColumn(SearchResult::getRegion).setHeader("Region").setAutoWidth(true);
        resultGrid.addColumn(SearchResult::getCurrency).setHeader("Währung").setAutoWidth(true);

        resultGrid.addColumn(new ComponentRenderer<>(result -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button detailsButton = new Button("Details", new Icon(VaadinIcon.INFO_CIRCLE));
            detailsButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            detailsButton.addClickListener(e -> showDetails(result));

            Button kaufenButton = new Button("Kaufen", new Icon(VaadinIcon.CART));
            kaufenButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
            kaufenButton.addClickListener(e ->
                    getUI().ifPresent(ui -> ui.navigate("kaufen/" + result.getSymbol()))
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

        CompletableFuture
                .supplyAsync(() -> alphaVantageService.search(keyword))
                .thenAccept(results -> {
                    getUI().ifPresent(ui -> ui.access(() -> {
                        handleSearchResults(results);
                    }));
                });
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
     * Zeigt detaillierte Informationen zu einem ausgewählten Wertpapier an.
     * Schließt die Seitenleiste vor dem Öffnen des Detaildialogs und öffnet sie wieder,
     * sobald der Dialog geschlossen wird.
     *
     * @param result Das ausgewählte Suchergebnis.
     */
    private void showDetails(SearchResult result) {
        // Schließe die Seitenleiste, bevor der Dialog geöffnet wird
        closeSideNav();

        // Rufe den Dialog von WertpapierView ab
        Dialog detailsDialog = wertpapierView.displayWertpapierDetails(result.getSymbol());

        // Füge einen Listener hinzu, der openSideNav() aufruft, wenn der Dialog geschlossen wird
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

    /**
     * Navigiert zur Kaufansicht für ein bestimmtes Wertpapier.
     *
     * @param symbol Das Börsensymbol des Wertpapiers.
     */
    private void navigateToKaufView(String symbol) {
        getUI().ifPresent(ui -> ui.navigate("kaufen/" + symbol));
    }
}