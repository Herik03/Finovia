package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.models.SearchResult;
import org.vaadin.example.application.services.AlphaVantageService;
import com.vaadin.flow.component.orderedlayout.Scroller;


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

            Chart chart = buildChart(kurse, anzeigeName);
            layout.add(chart);

            Aktie aktie = alphaVantageService.getFundamentalData(symbol);
            if (aktie != null) {
                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setPadding(false);
                infoLayout.setSpacing(false);
                infoLayout.setWidthFull();

                infoLayout.add(createInfoRow("Unternehmensname", aktie.getUnternehmensname()));
                infoLayout.add(createInfoRow("Industrie", aktie.getIndustry()));
                infoLayout.add(createInfoRow("Sektor", aktie.getSector()));
                infoLayout.add(createInfoRow("Marktkapitalisierung", aktie.getMarketCap()));
                infoLayout.add(createInfoRow("EBITDA", aktie.getEbitda()));
                infoLayout.add(createInfoRow("PEG Ratio", aktie.getPegRatio()));
                infoLayout.add(createInfoRow("Buchwert", aktie.getBookValue()));
                infoLayout.add(createInfoRow("EPS", aktie.getEps()));
                infoLayout.add(createInfoRow("Beta", aktie.getBeta()));
                infoLayout.add(createInfoRow("52W Hoch", aktie.getYearHigh()));
                infoLayout.add(createInfoRow("52W Tief", aktie.getYearLow()));
                infoLayout.add(createInfoRow("Dividende/Aktie", aktie.getDividendPerShare()));
                infoLayout.add(createInfoRow("Dividendenrendite", aktie.getDividendYield()));
                infoLayout.add(createInfoRow("Dividenden-Datum", aktie.getDividendDate()));

                Scroller scroller = new Scroller(infoLayout);
                scroller.setHeight("250px");
                scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);

                layout.add(scroller);
            }

            timeFrameSelect.addValueChangeListener(event -> {
                layout.remove(chart);
                updateChart(layout, symbol, event.getValue());
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

    private void updateChart(VerticalLayout layout, String symbol, String timeFrame) {
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

            layout.getChildren()
                    .filter(component -> component instanceof Chart)
                    .forEach(layout::remove);

            layout.addComponentAtIndex(2, newChart); // nach dem Select wieder einfügen

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
    private HorizontalLayout createInfoRow(String label, Object value) {
        String displayValue = (value == null || value.toString().equals("0") || value.toString().isBlank())
                ? "n.v." : value.toString();

        Span labelSpan = new Span(label + ":");
        labelSpan.getStyle().set("font-weight", "bold").set("width", "200px").set("display", "inline-block");

        Span valueSpan = new Span(displayValue);

        HorizontalLayout row = new HorizontalLayout(labelSpan, valueSpan);
        row.setWidthFull();
        row.setSpacing(true);
        return row;
    }

}