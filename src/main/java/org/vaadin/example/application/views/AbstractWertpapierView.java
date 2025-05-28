package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.models.SearchResult;
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class AbstractWertpapierView {

    protected final AlphaVantageService alphaVantageService;
    protected final WatchlistService watchlistService;
    protected final NutzerService nutzerService;
    protected final WertpapierRepository wertpapierRepository;

    protected String anzeigeName;
    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AbstractWertpapierView(AlphaVantageService alphaVantageService,
                                  WatchlistService watchlistService,
                                  NutzerService nutzerService,
                                  WertpapierRepository wertpapierRepository) {
        this.alphaVantageService = alphaVantageService;
        this.watchlistService = watchlistService;
        this.nutzerService = nutzerService;
        this.wertpapierRepository = wertpapierRepository;
    }

    protected void updateChart(VerticalLayout chartContainer, String symbol, String timeFrame, String anzeigeName) {
        try {
            List<Kurs> kurse;
            switch (timeFrame) {
                case "Intraday": kurse = alphaVantageService.getIntradaySeries(symbol); break;
                case "Täglich": kurse = alphaVantageService.getDailySeries(symbol); break;
                case "Wöchentlich": kurse = alphaVantageService.getWeeklySeries(symbol); break;
                default: kurse = alphaVantageService.getMonthlySeries(symbol); break;
            }

            Chart chart = buildChart(kurse, symbol, timeFrame, anzeigeName); // hier übergeben
            chartContainer.removeAll();
            chartContainer.add(chart);
        } catch (Exception e) {
            Notification.show("Fehler beim Aktualisieren des Charts: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }


    protected Chart buildChart(List<Kurs> kurse, String symbol, String timeFrame, String anzeigeName) {
        Chart chart = new Chart(ChartType.LINE);
        Configuration config = chart.getConfiguration();
        config.setTitle("Kursverlauf für " + anzeigeName);

        XAxis xAxis = new XAxis();
        switch (timeFrame.toLowerCase()) {
            case "intraday": xAxis.setTitle("Stunde"); break;
            case "täglich": xAxis.setTitle("Tag"); break;
            case "wöchentlich": xAxis.setTitle("Woche"); break;
            default: xAxis.setTitle("Monat");
        }
        config.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Schlusskurs (USD)");
        config.addyAxis(yAxis);

        DataSeries series = new DataSeries();
        for (int i = 0; i < kurse.size(); i++) {
            series.add(new DataSeriesItem(String.valueOf(i), kurse.get(i).getSchlusskurs()));
        }
        config.setSeries(series);

        PlotOptionsLine options = new PlotOptionsLine();
        options.setMarker(new Marker(false));
        series.setPlotOptions(options);

        return chart;
    }

    protected SearchResult findeSearchResult(String symbol) {
        List<SearchResult> searchResults = alphaVantageService.search(symbol);
        return searchResults.stream()
                .filter(r -> r.getSymbol().equalsIgnoreCase(symbol))
                .findFirst()
                .orElse(null);
    }

    protected HorizontalLayout createInfoRow(String label1, Object value1, String label2, Object value2) {
        return createInfoRow(label1, value1, label2, value2, null, null);
    }

    protected HorizontalLayout createInfoRow(String label1, Object value1, String label2, Object value2, String label3, Object value3) {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();

        row.add(createInfoBlock(label1, value1));
        row.add(createInfoBlock(label2, value2));
        if (label3 != null) {
            row.add(createInfoBlock(label3, value3));
        }

        return row;
    }

    private VerticalLayout createInfoBlock(String label, Object value) {
        String val = (value == null || value.toString().isBlank() || value.toString().equals("0")) ? "n.v." : value.toString();

        Span labelSpan = new Span(label);
        Span valueSpan = new Span(val);

        VerticalLayout block = new VerticalLayout(labelSpan, valueSpan);
        block.setPadding(false);
        block.setSpacing(false);
        block.setWidth("100%");
        return block;
    }

    public abstract Dialog createDetailsDialog(Wertpapier wertpapier);

    protected Button createWatchlistButton(String symbol) {
        Button button = new Button("Zur Watchlist hinzufügen");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickListener(event -> {
            try {
                Wertpapier wertpapier = wertpapierRepository.findBySymbol(symbol)
                        .orElseThrow(() -> new IllegalArgumentException("Wertpapier nicht gefunden: " + symbol));

                Nutzer nutzer = nutzerService.getAngemeldeterNutzer();
                watchlistService.addWertpapierToUserWatchlist(nutzer.getNutzerId(), wertpapier.getWertpapierId());

                Notification.show("Zur Watchlist hinzugefügt: " + symbol, 3000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Fehler: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        return button;
    }


}
