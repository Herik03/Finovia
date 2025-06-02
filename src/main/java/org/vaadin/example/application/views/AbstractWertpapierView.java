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
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Abstrakte Basisklasse für Ansichten, die Wertpapierinformationen darstellen.
 * <p>
 * Beinhaltet Funktionen zur Erstellung von Kurscharts, zur Anzeige von Metadaten
 * sowie zur Interaktion mit der Watchlist.
 */
public abstract class AbstractWertpapierView {

    /** Service zur Kursabfrage über die AlphaVantage API. */
    protected final AlphaVantageService alphaVantageService;

    /** Service zur Verwaltung von Nutzer-Watchlists. */
    protected final WatchlistService watchlistService;

    /** Service zur Identifikation und Verwaltung des eingeloggten Nutzers. */
    protected final NutzerService nutzerService;

    /** Repository für Wertpapierdatenbankzugriffe. */
    protected final WertpapierRepository wertpapierRepository;

    /** Optionaler Anzeigename des Wertpapiers. */
    protected String anzeigeName;

    /** Datumsformatierung für Anzeigezwecke. */
    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Konstruktor zur Initialisierung aller erforderlichen Services.
     *
     * @param alphaVantageService   Service zur Kursabfrage
     * @param watchlistService      Service zur Watchlistverwaltung
     * @param nutzerService         Service zur Nutzerverwaltung
     * @param wertpapierRepository  Repository zur Wertpapierverwaltung
     */
    public AbstractWertpapierView(AlphaVantageService alphaVantageService,
                                  WatchlistService watchlistService,
                                  NutzerService nutzerService,
                                  WertpapierRepository wertpapierRepository) {
        this.alphaVantageService = alphaVantageService;
        this.watchlistService = watchlistService;
        this.nutzerService = nutzerService;
        this.wertpapierRepository = wertpapierRepository;
    }

    /**
     * Lädt Kursdaten für das gegebene Symbol und erstellt ein entsprechendes Chart.
     *
     * @param chartContainer Das Layout, in dem das Chart angezeigt wird
     * @param symbol         Das Symbol des Wertpapiers
     * @param timeFrame      Der gewählte Zeitrahmen (z. B. "Täglich", "Wöchentlich")
     * @param anzeigeName    Anzeigename für die Chartüberschrift
     */
    protected void updateChart(VerticalLayout chartContainer, String symbol, String timeFrame, String anzeigeName) {
        try {
            List<Kurs> kurse;
            switch (timeFrame) {
                case "Intraday": kurse = alphaVantageService.getIntradaySeries(symbol); break;
                case "Täglich": kurse = alphaVantageService.getDailySeries(symbol); break;
                case "Wöchentlich": kurse = alphaVantageService.getWeeklySeries(symbol); break;
                default: kurse = alphaVantageService.getMonthlySeries(symbol); break;
            }

            Chart chart = buildChart(kurse, symbol, timeFrame, anzeigeName);
            chartContainer.removeAll();
            chartContainer.add(chart);
        } catch (Exception e) {
            Notification.show("Fehler beim Aktualisieren des Charts: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    /**
     * Erstellt ein Liniendiagramm basierend auf Kursdaten.
     *
     * @param kurse        Liste der Kursdaten
     * @param symbol       Symbol des Wertpapiers
     * @param timeFrame    Zeitintervall der Daten
     * @param anzeigeName  Überschrift des Diagramms
     * @return Ein konfiguriertes {@link Chart}-Objekt
     */
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

    /**
     * Erstellt eine Informationszeile mit zwei Spalten.
     *
     * @param label1 Beschriftung des ersten Werts
     * @param value1 Erster Wert
     * @param label2 Beschriftung des zweiten Werts
     * @param value2 Zweiter Wert
     * @return Eine horizontale Layout-Zeile
     */
    protected HorizontalLayout createInfoRow(String label1, Object value1, String label2, Object value2) {
        return createInfoRow(label1, value1, label2, value2, null, null);
    }

    /**
     * Erstellt eine Informationszeile mit zwei oder drei Spalten.
     *
     * @param label1 Beschriftung des ersten Werts
     * @param value1 Erster Wert
     * @param label2 Beschriftung des zweiten Werts
     * @param value2 Zweiter Wert
     * @param label3 (Optional) Beschriftung des dritten Werts
     * @param value3 (Optional) Dritter Wert
     * @return Eine horizontale Layout-Zeile
     */
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

    /**
     * Erstellt einen vertikalen Informationsblock mit Label und Wert.
     *
     * @param label Die Beschriftung
     * @param value Der anzuzeigende Wert
     * @return Ein vertikal strukturierter Layout-Block
     */
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

    /**
     * Abstrakte Methode zur Erstellung eines Detaildialogs für ein bestimmtes Wertpapier.
     *
     * @param wertpapier Das anzuzeigende Wertpapier
     * @return Ein Dialog mit Wertpapierdetails
     */
    public abstract Dialog createDetailsDialog(Wertpapier wertpapier);

    /**
     * Erstellt einen Button, der ein Wertpapier zur Watchlist des aktuellen Nutzers hinzufügt.
     *
     * @param symbol Das Symbol des hinzuzufügenden Wertpapiers
     * @return Der erstellte Button
     */
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
