package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.charts.Chart;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;

import java.time.LocalDate;
import java.util.List;

/**
 * Erweiterte View zur Darstellung von Wertpapierdetails mit Chart.
 * Diese Klasse wird von der SearchView aufgerufen und öffnet ein Dialogfenster mit Wertpapierdetails.
 *
 * @author Jan Schwarzer
 */

public class WertpapierView extends VerticalLayout {

    private final AlphaVantageService alphaVantageService;
    private final NutzerService nutzerService;
    private final WatchlistService watchlistService;

//    @Autowired
    public WertpapierView(AlphaVantageService alphaVantageService, NutzerService nutzerService, WatchlistService watchlistService) {
        this.alphaVantageService = alphaVantageService;
        this.nutzerService = nutzerService;
        this.watchlistService = watchlistService;
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

            // Button zum Hinzufügen zur Watchlist
            Button addToWatchlistButton = new Button("Zur Watchlist hinzufügen", event -> {
                try {
                    // Aktuellen Nutzer ermitteln
                    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    String username;

                    if (principal instanceof UserDetails) {
                        username = ((UserDetails) principal).getUsername();
                    } else {
                        username = principal.toString();
                    }

                    // Nutzer aus dem Service laden
                    Nutzer nutzer = nutzerService.getNutzerByUsername(username);

                    if (nutzer == null) {
                        // Fallback für Entwicklungszwecke - Nutzer mit einer ID laden
                        nutzer = nutzerService.getNutzerById(1);
                    }

                    if (nutzer != null) {
                        // Einfaches Wertpapier-Objekt erstellen (in einer echten Anwendung würde man das Wertpapier aus der Datenbank laden)
                        // Da Aktie viele Parameter benötigt, erstellen wir ein einfaches Dummy-Objekt mit Minimaldaten
                        Aktie wertpapier = new Aktie(
                            symbol, // unternehmensname
                            "Beschreibung für " + symbol, // description
                            "NYSE", // exchange
                            "USD", // currency
                            "USA", // country
                            "Technologie", // sector
                            "Software", // industry
                            1000000000L, // marketCap
                            500000000L, // ebitda
                            2.5, // pegRatio
                            50.0, // bookValue
                            1.5, // dividendPerShare
                            2.0, // dividendYield
                            3.5, // eps
                            15.0, // forwardPE
                            1.2, // beta
                            200.0, // yearHigh
                            150.0, // yearLow
                            LocalDate.now() // dividendDate
                        );
                        wertpapier.setIsin(symbol);
                        wertpapier.setName(symbol);

                        // Zur Watchlist hinzufügen
                        boolean added = watchlistService.addWertpapierToWatchlist(nutzer.getId(), wertpapier);

                        if (added) {
                            Notification.show(symbol + " wurde zur Watchlist hinzugefügt", 
                                    3000, Notification.Position.BOTTOM_START)
                                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                            // Button deaktivieren, um mehrfaches Hinzufügen zu verhindern
                            event.getSource().setEnabled(false);
                        } else {
                            Notification.show("Fehler beim Hinzufügen zur Watchlist", 
                                    3000, Notification.Position.MIDDLE)
                                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
                        }
                    } else {
                        Notification.show("Nutzer konnte nicht ermittelt werden", 
                                3000, Notification.Position.MIDDLE)
                                .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                } catch (Exception e) {
                    Notification.show("Fehler: " + e.getMessage(), 
                            3000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });
            addToWatchlistButton.setIcon(new Icon(VaadinIcon.PLUS));
            addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            Button closeButton = new Button("Schließen", event -> dialog.close());
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            HorizontalLayout buttonLayout = new HorizontalLayout(addToWatchlistButton, closeButton);
            buttonLayout.setSpacing(true);

            layout.add(timeFrameSelect, chart, buttonLayout);
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
