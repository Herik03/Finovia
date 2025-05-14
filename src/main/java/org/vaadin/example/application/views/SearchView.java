package org.vaadin.example.application.views;

import com.vaadin.flow.server.auth.AnonymousAllowed;
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

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    public SearchView(AlphaVantageService alphaVantageService) {
        super(); // Ruft den Konstruktor der Basisklasse AbstractSideNav auf
        this.alphaVantageService = alphaVantageService;

        // Wir erstellen ein VerticalLayout für den Inhalt
        VerticalLayout searchContent = new VerticalLayout();
        searchContent.setHeightFull();
        searchContent.setMaxWidth("1200px");
        searchContent.setMargin(true);
        searchContent.addClassName("stock-view");
        
        // UI konfigurieren
        configureUI(searchContent);
        configureGrid();
        setupListeners();

        // Startansicht konfigurieren
        progressBar.setVisible(false);
        resultGrid.setVisible(true);
        
        // Füge das Content-Layout zum Hauptinhaltsbereich hinzu
        addToMainContent(searchContent);
    }

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

        container.add(
                title,
                searchLayout,
                progressBar,
                resultGrid
        );
    }

    private void configureGrid() {
        resultGrid.addColumn(SearchResult::getSymbol).setHeader("Symbol").setAutoWidth(true);
        resultGrid.addColumn(SearchResult::getName).setHeader("Name").setAutoWidth(true).setFlexGrow(1);
        resultGrid.addColumn(SearchResult::getRegion).setHeader("Region").setAutoWidth(true);
        resultGrid.addColumn(SearchResult::getCurrency).setHeader("Währung").setAutoWidth(true);

        // Aktionsspalte mit Details- und Kaufen-Button
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


    private void setupListeners() {
        // Suche bei Enter-Taste oder Button-Klick ausführen
        searchField.addKeyPressListener(Key.ENTER, event -> performSearch());
        searchButton.addClickListener(event -> performSearch());

        // Doppelklick auf Zeile
        resultGrid.addItemDoubleClickListener(event -> showDetails(event.getItem()));
    }

    private void performSearch() {
        String keyword = searchField.getValue();
        if (keyword == null || keyword.isBlank()) {
            showNotification("Bitte ein Stichwort eingeben.", NotificationVariant.LUMO_ERROR);
            return;
        }

        // UI-Status aktualisieren
        progressBar.setVisible(true);
        // Wichtig: Grid nicht unsichtbar machen!
        searchButton.setEnabled(false);

        CompletableFuture
                .supplyAsync(() -> alphaVantageService.search(keyword))
                .thenAccept(results -> {
                    // Verzögerte UI-Aktualisierung mit einer expliziten Verzögerung
                    // Dies kann bei UI-Update-Problemen helfen
                    try {
                        Thread.sleep(100); // Kurze Verzögerung
                    } catch (InterruptedException ignored) {}

                    getUI().ifPresent(ui -> ui.access(() -> {
                        handleSearchResults(results);
                    }));
                });
    }

    private void handleSearchResults(List<SearchResult> results) {
        progressBar.setVisible(false);
        searchButton.setEnabled(true);

        // Grid explizit leeren
        resultGrid.setItems(List.of());

        // Explizites DataProvider Update erzwingen
        if (results.isEmpty()) {
            showNotification("Keine Ergebnisse gefunden.", NotificationVariant.LUMO_CONTRAST);
        } else {
            // DataProvider erstellen und explizit setzen
            resultGrid.setItems(results);
            // Erzwinge ein vollständiges Grid-Update
            resultGrid.getDataProvider().refreshAll();
            showNotification(results.size() + " Treffer gefunden.", NotificationVariant.LUMO_SUCCESS);
        }

        // Explizit Layout-Updates anfordern
        resultGrid.recalculateColumnWidths();
        resultGrid.getDataProvider().refreshAll();
    }
    private void showDetails(SearchResult result) {
        WertpapierView wertpapierView = new WertpapierView(alphaVantageService);
        wertpapierView.displayWertpapierDetails(result.getSymbol());
    }

        
   /* private void showDetails(SearchResult result) {
        // Hier könntest du einen Dialog öffnen oder zu einer Detail-Ansicht navigieren
        Notification notification = Notification.show(
                "Symbol: " + result.getSymbol() +
                        "\nName: " + result.getName() +
                        "\nRegion: " + result.getRegion() +
                        "\nWährung: " + result.getCurrency(),
                3000,
                Notification.Position.MIDDLE
        );
        notification.open();
        
        // Future: Navigation zu einer DetailView
        // getUI().ifPresent(ui -> ui.navigate(WertpapierView.class, result.getSymbol()));
    }
        
    */

    private void showNotification(String message, NotificationVariant variant) {
        Notification notification = new Notification(message, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(variant);
        notification.open();
    }

    private void navigateToKaufView(String symbol) {
        getUI().ifPresent(ui -> ui.navigate("kaufen" + symbol));
    }
}