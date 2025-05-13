package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.charts.Chart;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.services.AlphaVantageService;

import java.util.List;

/**
 * Erweiterte View zur Darstellung von Wertpapierdetails mit Chart.
 * Diese Klasse wird von der SearchView aufgerufen und öffnet ein Dialogfenster mit Wertpapierdetails.
 *
 * @author Jan Schwarzer
 */

public class WertpapierView extends VerticalLayout {

    private final AlphaVantageService alphaVantageService;

//    @Autowired
    public WertpapierView(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

    /**
     * Zeigt die Wertpapierdetails in einem Dialog an, basierend auf dem Symbol.
     *
     * @param symbol Das Symbol des Wertpapiers
     */
    public void displayWertpapierDetails(String symbol) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Details für: " + symbol);
        dialog.setWidthFull(); // Dialog nimmt die volle Breite des Bildschirms ein
        dialog.setHeightFull(); // Dialog nimmt die volle Höhe des Bildschirms ein

        try {
            // Initialisiere die Kursdaten für den Chart
            List<Kurs> kurse = alphaVantageService.getDailySeries(symbol);

            if (kurse.isEmpty()) {
                Notification.show("Keine Daten gefunden.", 3000, Notification.Position.MIDDLE);
                return;
            }


//        if (!wertpapier.getKurse().isEmpty()) {
//            layout.add(new Span("Aktueller Preis: " + wertpapier.getKurse().get(0).getSchlusskurs() + " €"));
//        } else {
//            layout.add(new Span("Kein Kurs verfügbar"));
//        }


            VerticalLayout layout = new VerticalLayout();
            layout.setSizeFull();
            layout.setPadding(false);
            layout.setSpacing(false);
            layout.setMargin(false);
            layout.add(new H2("Wertpapier: " + symbol));

            // Chart-Initialisierung
            Chart chart = new Chart(ChartType.LINE);
            Configuration configuration = chart.getConfiguration();
            configuration.setTitle("Kursverlauf für " + symbol);

            DataSeries series = new DataSeries();
            for (Kurs kurs : kurse) {
                series.add(new DataSeriesItem(kurs.getDatum().toString(), kurs.getSchlusskurs()));
            }
            configuration.addSeries(series);

            // X- und Y-Achse konfigurieren
            XAxis xAxis = new XAxis();
            xAxis.setTitle("Datum");
            configuration.addxAxis(xAxis);

            YAxis yAxis = new YAxis();
            yAxis.setTitle("Schlusskurs (€)");
            configuration.addyAxis(yAxis);

            Select<String> timeFrameSelect = new Select<>();
            timeFrameSelect.setItems("1 Woche", "1 Monat", "3 Monate", "1 Jahr", "Max");
            timeFrameSelect.setPlaceholder("Zeitraum auswählen");
            timeFrameSelect.setValue("1 Jahr");
            timeFrameSelect.addValueChangeListener(event -> updateChart(chart, symbol, event.getValue()));

            Button closeButton = new Button("Schließen", event -> dialog.close());

            layout.add(timeFrameSelect, chart, new HorizontalLayout(closeButton));
            dialog.add(layout);
            dialog.open();

        } catch (Exception e) {
            Notification.show("Fehler beim Abrufen der Daten: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    /**
     * Aktualisiert den Chart basierend auf dem ausgewählten Zeitraum.
     *
     * @param chart    Das Chart-Objekt
     * @param symbol   Das Symbol des Wertpapiers
     * @param timeFrame Der ausgewählte Zeitraum
     */

    private void updateChart(Chart chart, String symbol, String timeFrame) {
        List<Kurs> kurse;
        try {
            switch (timeFrame) {
                case "1 Woche":
                    kurse = alphaVantageService.getDailySeries(symbol).subList(0, 7);
                    break;
                case "1 Monat":
                    kurse = alphaVantageService.getDailySeries(symbol).subList(0, 30);
                    break;
                case "3 Monate":
                    kurse = alphaVantageService.getDailySeries(symbol).subList(0, 90);
                    break;
                case "1 Jahr":
                    kurse = alphaVantageService.getDailySeries(symbol).subList(0, 365);
                    break;
                default:
                    kurse = alphaVantageService.getDailySeries(symbol);
            }

            DataSeries series = new DataSeries();
            int dayCount = 1;
            for (Kurs kurs : kurse) {
                series.add(new DataSeriesItem(String.valueOf(dayCount++), kurs.getSchlusskurs()));
            }

            PlotOptionsLine plotOptions = new PlotOptionsLine();
            plotOptions.setMarker(new Marker(false)); // Keine Punkte anzeigen
            series.setPlotOptions(plotOptions);

            chart.getConfiguration().getxAxis().setTitle("Datum (in Tagen)");
            chart.getConfiguration().getyAxis().setTitle("Schlusskurs (€)");
            chart.getConfiguration().setSeries(series);
            chart.drawChart();

        } catch (Exception e) {
            Notification.show("Fehler beim Aktualisieren des Charts: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

}
