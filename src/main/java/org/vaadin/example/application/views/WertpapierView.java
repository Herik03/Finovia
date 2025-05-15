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
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.models.SearchResult;
import org.vaadin.example.application.services.AlphaVantageService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class WertpapierView extends VerticalLayout {

    private final AlphaVantageService alphaVantageService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public WertpapierView(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

    public void displayWertpapierDetails(String symbol) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Details für: " + symbol);
        dialog.setWidth("80vw");
        dialog.setHeight("80vh");
        dialog.setModal(true);
        dialog.setDraggable(true);
        dialog.setResizable(true);

        try {
            List<Kurs> kurse = alphaVantageService.getDailySeries(symbol);
            SearchResult result = alphaVantageService.search(symbol).stream()
                    .filter(r -> r.getSymbol().equalsIgnoreCase(symbol))
                    .findFirst()
                    .orElse(null);
            String anzeigeName = result != null ? result.getName() : symbol;

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
            timeFrameSelect.setItems("Intraday", "1 Woche", "1 Monat");
            timeFrameSelect.setValue("1 Monat");
            layout.add(timeFrameSelect);

// Erstelle Chart-Container (Platzhalter für Chart)
            VerticalLayout chartContainer = new VerticalLayout();
            chartContainer.setWidthFull();
            chartContainer.setPadding(false);
            chartContainer.setSpacing(false);
            layout.add(chartContainer);

// Lade initialen Chart in Container
            updateChart(chartContainer, symbol, "1 Monat");


            Aktie aktie = alphaVantageService.getFundamentalData(symbol);
            if (aktie != null) {
                Scroller scroller = new Scroller(createInfoGrid(aktie));
                scroller.setHeight("250px");
                scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
                layout.add(scroller);
            }

            timeFrameSelect.addValueChangeListener(event -> {
                updateChart(chartContainer, symbol, event.getValue());
            });


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

    private void updateChart(VerticalLayout chartContainer, String symbol, String timeFrame) {
        List<Kurs> kurse;
        try {
            switch (timeFrame) {
                case "Intraday":
                    kurse = alphaVantageService.getIntradaySeries(symbol);
                    break;
                case "1 Woche":
                    kurse = alphaVantageService.getWeeklySeries(symbol);
                    break;
                case "1 Monat":
                default:
                    kurse = alphaVantageService.getMonthlySeries(symbol);
            }

            Chart newChart = buildChart(kurse, symbol);

            chartContainer.removeAll();
            chartContainer.add(newChart);

        } catch (Exception e) {
            Notification.show("Fehler beim Aktualisieren des Charts: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }


    private Chart buildChart(List<Kurs> kurse, String title) {
        Chart chart = new Chart(ChartType.LINE);
        Configuration config = chart.getConfiguration();
        config.setTitle("Kursverlauf für " + title);

        XAxis xAxis = new XAxis();
        xAxis.setTitle("Datum");
        config.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Schlusskurs (€)");
        config.addyAxis(yAxis);

        DataSeries series = new DataSeries();
        series.setName(" "); // entfernt "Series 1"
        for (Kurs kurs : kurse) {
            String label = kurs.getDatum().format(formatter);
            series.add(new DataSeriesItem(label, kurs.getSchlusskurs()));
        }

        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setMarker(new Marker(false));
        series.setPlotOptions(plotOptions);

        config.setSeries(series);
        return chart;
    }

    private HorizontalLayout createInfoRow(String label1, Object value1, String label2, Object value2) {
        return createInfoRow(label1, value1, label2, value2, null, null);
    }

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
