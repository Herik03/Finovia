package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode; // Add this import for JustifyContentMode
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.classes.Wertpapier; // Import Wertpapier
import org.vaadin.example.application.models.SearchResult; // This import might not be directly used in WertpapierView but is okay.
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.WatchlistService; // Import WatchlistService
import org.vaadin.example.application.services.NutzerService; // Import NutzerService
import org.vaadin.example.application.repositories.WertpapierRepository; // Import WertpapierRepository
import org.springframework.security.core.context.SecurityContextHolder; // For getting current user
import org.springframework.stereotype.Component; // <--- This is the crucial import

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Diese Klasse stellt eine Benutzeroberfläche zur Anzeige von Wertpapierdetails dar.
 * Sie zeigt historische Kursverläufe, fundamentale Finanzkennzahlen sowie eine
 * Unternehmensbeschreibung an und nutzt dazu Daten aus dem AlphaVantageService.
 *
 * Die Oberfläche wird in einem Dialog dargestellt, welcher responsive gestaltet ist.
 */
@Component // <--- This annotation tells Spring to manage this class as a bean.
public class WertpapierView extends VerticalLayout {

    private final AlphaVantageService alphaVantageService;
    private final WatchlistService watchlistService; // Inject WatchlistService
    private final NutzerService nutzerService;       // Inject NutzerService
    private final WertpapierRepository wertpapierRepository; // Inject WertpapierRepository to save if new

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Add constructor parameters for new services
    public WertpapierView(AlphaVantageService alphaVantageService, WatchlistService watchlistService, NutzerService nutzerService, WertpapierRepository wertpapierRepository) {
        this.alphaVantageService = alphaVantageService;
        this.watchlistService = watchlistService;
        this.nutzerService = nutzerService;
        this.wertpapierRepository = wertpapierRepository;
    }


    /**
     * Öffnet einen Dialog mit Kursverlauf, Fundamentaldaten und Beschreibung für das gegebene Symbol.
     * @param symbol Das Börsensymbol des Wertpapiers
     */
    public void displayWertpapierDetails(String symbol) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Details für: " + symbol);
        dialog.setWidthFull();
        dialog.setHeightFull();
        dialog.setModal(true);
        dialog.setDraggable(true);
        dialog.setResizable(true);

        try {
            // Note: alphaVantageService.search(symbol) returns a List<SearchResult>,
            // you might want to adjust this to fetch a single exact match more efficiently
            List<SearchResult> searchResults = alphaVantageService.search(symbol);
            SearchResult result = searchResults.stream()
                    .filter(r -> r.getSymbol().equalsIgnoreCase(symbol))
                    .findFirst()
                    .orElse(null);
            String anzeigeName = result != null ? result.getName() : symbol;

            List<Kurs> kurse = alphaVantageService.getMonthlySeries(symbol); // Default to Monthly for initial chart

            if (kurse.isEmpty()) {
                Notification.show("Keine Daten gefunden.", 3000, Notification.Position.MIDDLE);
                return;
            }

            VerticalLayout layout = new VerticalLayout();
            layout.setSizeFull();
            layout.setPadding(false);
            layout.setSpacing(false);
            layout.setMargin(false);
            layout.add(new H2("Wertpapier: " + anzeigeName));

            Select<String> timeFrameSelect = new Select<>();
            timeFrameSelect.setLabel("Zeitraum");
            timeFrameSelect.setItems("Intraday", "Täglich", "Wöchentlich", "Monatlich");
            timeFrameSelect.setValue("Monatlich");
            layout.add(timeFrameSelect);

            VerticalLayout chartContainer = new VerticalLayout();
            chartContainer.setSizeFull();
            chartContainer.setPadding(false);
            chartContainer.setSpacing(false);
            layout.add(chartContainer);

            updateChart(chartContainer, symbol, "Monatlich");

            Aktie aktie = alphaVantageService.getFundamentalData(symbol);
            if (aktie != null) {
                Scroller scroller = new Scroller(createInfoGrid(aktie));
                scroller.setSizeFull();
                scroller.setScrollDirection(Scroller.ScrollDirection.BOTH);

                String beschreibung = aktie.getDescription();
                if (beschreibung == null || beschreibung.isBlank()) {
                    beschreibung = "Keine Beschreibung verfügbar.";
                }

                Span descriptionSpan = new Span(beschreibung);
                descriptionSpan.getStyle().set("white-space", "pre-line");

                VerticalLayout descriptionBox = new VerticalLayout(descriptionSpan);
                descriptionBox.setSizeFull();
                descriptionBox.getStyle()
                        .set("background", "#f4f4f4")
                        .set("padding", "1rem")
                        .set("border-radius", "8px")
                        .set("box-shadow", "0 2px 4px rgba(0,0,0,0.05)")
                        .set("font-size", "0.95rem");


                HorizontalLayout infoAndDescriptionLayout = new HorizontalLayout(scroller, descriptionBox);
                infoAndDescriptionLayout.setSizeFull();
                infoAndDescriptionLayout.setSpacing(true);
                infoAndDescriptionLayout.setFlexGrow(1, scroller);
                infoAndDescriptionLayout.setFlexGrow(2, descriptionBox);

                layout.add(infoAndDescriptionLayout);
            }

            timeFrameSelect.addValueChangeListener(event -> {
                updateChart(chartContainer, symbol, event.getValue());
            });

            // --- Add "Save to Watchlist" button ---
            Button saveToWatchlistButton = new Button("Zur Watchlist hinzufügen");
            saveToWatchlistButton.addClickListener(event -> {
                saveWertpapierToWatchlist(symbol);
            });
            // Place the button
            HorizontalLayout buttonLayout = new HorizontalLayout(saveToWatchlistButton);
            buttonLayout.setWidthFull();
            buttonLayout.setJustifyContentMode(JustifyContentMode.END); // Align to the right
            layout.add(buttonLayout);
            // --- End of button addition ---


            Button closeButton = new Button("✕", event -> dialog.close());
            closeButton.getStyle()
                    .set("position", "absolute")
                    .set("top", "10px")
                    .set("right", "10px")
                    .set("background", "red")
                    .set("color", "white")
                    .set("border", "none")
                    .set("z-index", "1000");
            dialog.add(closeButton);
            dialog.add(layout);

            dialog.open();

        } catch (Exception e) {
            Notification.show("Fehler beim Abrufen der Daten: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    /**
     * Saves the currently viewed Wertpapier to the logged-in user's watchlist.
     * @param symbol The symbol of the Wertpapier to save.
     */
    private void saveWertpapierToWatchlist(String symbol) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        org.vaadin.example.application.classes.Nutzer currentUser = nutzerService.getNutzerByUsername(currentUsername);

        if (currentUser == null) {
            Notification.show("Fehler: Aktueller Nutzer nicht gefunden.", 3000, Notification.Position.MIDDLE);
            return;
        }

        // Check if Wertpapier already exists in the database by its name/symbol
        // In a real application, you might want a more robust way to find/create Wertpapier,
        // perhaps by fetching it from AlphaVantage and saving it if it doesn't exist.
        // For simplicity, we'll try to find by name or create a new one.
        // This is a simplification; a better approach would be to have a 'WertpapierService'
        // that manages the persistence of Wertpapiere fetched from AlphaVantage.

        // For this example, we'll create a dummy Wertpapier if it doesn't exist
        // You'll need to adapt this based on how your Wertpapier objects are managed and identified.
        Optional<Wertpapier> existingWertpapier = wertpapierRepository.findByNameIgnoreCase(symbol); // Assuming you have a findByNameIgnoreCase method in your repository

        Wertpapier wertpapierToAdd;
        if (existingWertpapier.isPresent()) {
            wertpapierToAdd = existingWertpapier.get();
        } else {
            // Create a new Wertpapier entry. This is a placeholder;
            // ideally, you'd populate more details (like actual type, etc.)
            // and have a dedicated service for this.
            Aktie newAktie = new Aktie(); // Or whatever specific Wertpapier type
            newAktie.setName(symbol); // Using symbol as name for simplicity here
            wertpapierToAdd = wertpapierRepository.save(newAktie); // Save the new Wertpapier
            Notification.show("Wertpapier '" + symbol + "' wurde in der Datenbank erstellt.", 3000, Notification.Position.MIDDLE);
        }

        try {
            watchlistService.addWertpapierToUserWatchlist(currentUser.getId(), wertpapierToAdd.getWertpapierId());
            Notification.show(symbol + " wurde zur Watchlist hinzugefügt!", 3000, Notification.Position.MIDDLE);
        } catch (IllegalArgumentException e) {
            Notification.show("Fehler beim Hinzufügen zur Watchlist: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            Notification.show("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }


    /**
     * Erstellt das Chart-Objekt basierend auf einer Liste von Kursen.
     * @param kurse Liste der Kursdaten
     * @param title Titel des Charts
     * @param timeFrame Zeitintervall zur Achsentitel-Auswahl
     * @return Ein konfiguriertes Chart-Objekt
     */
    private Chart buildChart(List<Kurs> kurse, String title, String timeFrame) {
        Chart chart = new Chart(ChartType.LINE);
        Configuration config = chart.getConfiguration();
        config.setTitle("Kursverlauf für " + title);

        XAxis xAxis = new XAxis();
        switch (timeFrame.toLowerCase()) {
            case "intraday":
                xAxis.setTitle("Stunde");
                break;
            case "täglich":
                xAxis.setTitle("Tag");
                break;
            case "wöchentlich":
                xAxis.setTitle("Woche");
                break;
            case "monatlich":
            default:
                xAxis.setTitle("Monat");
                break;
        }
        config.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Schlusskurs (USD)");
        config.addyAxis(yAxis);

        DataSeries series = new DataSeries();
        series.setName(" ");
        // Ensure that the number of items for the X-axis matches the number of courses,
        // or use actual dates for better X-axis representation.
        // For simplicity, using index here, but dates are preferred.
        int index = 1;
        for (Kurs kurs : kurse) {
            series.add(new DataSeriesItem(String.valueOf(index), kurs.getSchlusskurs()));
            index++;
        }
        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setMarker(new Marker(false));
        series.setPlotOptions(plotOptions);

        config.setSeries(series);
        return chart;
    }
    /**
     * Aktualisiert den Kurs-Chart je nach ausgewähltem Zeitraum.
     * @param chartContainer Layout, das den Chart enthält
     * @param symbol Börsensymbol
     * @param timeFrame Zeitintervall für die Kursanzeige (z.B. "Täglich")
     */
    private void updateChart(VerticalLayout chartContainer, String symbol, String timeFrame) {
        List<Kurs> kurse;
        try {
            switch (timeFrame) {
                case "Intraday":
                    kurse = alphaVantageService.getIntradaySeries(symbol);
                    break;
                case "Täglich":
                    kurse = alphaVantageService.getDailySeries(symbol);
                    break;
                case "Wöchentlich":
                    kurse = alphaVantageService.getWeeklySeries(symbol);
                    break;
                case "Monatlich":
                default:
                    kurse = alphaVantageService.getMonthlySeries(symbol);
                    break;
            }

            Chart newChart = buildChart(kurse, symbol, timeFrame.toLowerCase().replace(" ", "_"));

            chartContainer.removeAll();
            chartContainer.add(newChart);

        } catch (Exception e) {
            Notification.show("Fehler beim Aktualisieren des Charts: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }
    /**
     * Erstellt eine Zeile mit zwei (oder drei) beschrifteten Informationsblöcken.
     */
    private HorizontalLayout createInfoRow(String label1, Object value1, String label2, Object value2) {
        return createInfoRow(label1, value1, label2, value2, null, null);
    }

    /**
     * Erstellt eine Zeile mit bis zu drei beschrifteten Informationsblöcken.
     */
    private HorizontalLayout createInfoRow(String label1, Object value1, String label2, Object value2, String label3, Object value3) {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();
        row.setSpacing(true);

        row.add(createSingleInfoBlock(label1, value1));
        row.add(createSingleInfoBlock(label2, value2));
        if (label3 != null) {
            row.add(createSingleInfoBlock(label3, value3));
        }

        return row;
    }
    /**
     * Erzeugt einen einzelnen Block mit Label und Wert für die Anzeige in der Übersicht.
     */
    private VerticalLayout createSingleInfoBlock(String label, Object value) {
        String displayValue = (value == null || value.toString().equals("0") || value.toString().isBlank())
                ? "n.v." : value.toString();

        Span labelSpan = new Span(label);
        labelSpan.getStyle().set("font-weight", "bold").set("font-size", "0.85rem");

        Span valueSpan = new Span(displayValue);
        valueSpan.getStyle().set("font-size", "1rem");

        VerticalLayout block = new VerticalLayout(labelSpan, valueSpan);
        block.setPadding(false);
        block.setSpacing(false);
        block.setWidth("100%");
        block.getStyle().set("min-width", "150px");

        return block;
    }
    /**
     * Erstellt ein vertikales Layout mit mehreren Zeilen von fundamentalen Finanzkennzahlen.
     * @param aktie Die Aktie mit den anzuzeigenden Fundamentaldaten
     * @return Layout mit gestylten Kennzahlenblöcken
     */
    private VerticalLayout createInfoGrid(Aktie aktie) {
        VerticalLayout gridLayout = new VerticalLayout();
        gridLayout.setSpacing(true);
        gridLayout.setPadding(true);
        gridLayout.setWidthFull();
        gridLayout.getStyle().set("background", "#f9f9f9").set("border-radius", "8px");

        gridLayout.add(createInfoRow("Unternehmensname", aktie.getUnternehmensname(), "Industrie", aktie.getIndustry(), "Sektor", aktie.getSector()));
        gridLayout.add(createInfoRow("Marktkapitalisierung", aktie.getMarketCap(), "EBITDA", aktie.getEbitda(), "PEG Ratio", aktie.getPegRatio()));
        gridLayout.add(createInfoRow("Buchwert", aktie.getBookValue(), "EPS", aktie.getEps(), "Beta", aktie.getBeta()));
        gridLayout.add(createInfoRow("52W Hoch", aktie.getYearHigh(), "52W Tief", aktie.getYearLow(), "Dividende/Aktie", aktie.getDividendPerShare()));
        gridLayout.add(createInfoRow("Dividendenrendite", aktie.getDividendYield(), "Dividenden-Datum", aktie.getDividendDate()));

        return gridLayout;
    }
}