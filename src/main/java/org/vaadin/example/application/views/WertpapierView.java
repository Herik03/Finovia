package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.button.ButtonVariant; // Import für ButtonVariant

import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.classes.Nutzer; // Import für Nutzer
import org.vaadin.example.application.classes.Watchlist; // Import für Watchlist
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.models.SearchResult;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.WatchlistService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Diese Klasse stellt eine Benutzeroberfläche zur Anzeige von Wertpapierdetails dar.
 * Sie zeigt historische Kursverläufe, fundamentale Finanzkennzahlen sowie eine
 * Unternehmensbeschreibung an und nutzt dazu Daten aus dem AlphaVantageService.
 *
 * Die Oberfläche wird in einem Dialog dargestellt, welcher responsive gestaltet ist.
 * Zudem wurde die Funktionalität erweitert, um den Status des "Zur Watchlist hinzufügen"-Buttons
 * basierend auf der Watchlist des aktuellen Benutzers anzupassen.
 */

@Component
@CssImport("./themes/finovia/styles.css")
public class WertpapierView extends VerticalLayout {

    private final AlphaVantageService alphaVantageService;
    private String anzeigeName;
    private final WatchlistService watchlistService;
    private final NutzerService nutzerService;
    private final WertpapierRepository wertpapierRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    /**
     * Konstruktor für die WertpapierView.
     * Spring injiziert hier die benötigten Services und Repositories.
     *
     * @param alphaVantageService Der Service zum Abrufen von Wertpapierdaten (Kurse, Fundamentaldaten).
     * @param watchlistService Der Service zur Verwaltung der Benutzer-Watchlists.
     * @param nutzerService Der Service zum Abrufen von Benutzerinformationen.
     * @param wertpapierRepository Das Repository zum Speichern und Abrufen von Wertpapier-Entitäten.
     */
    public WertpapierView(AlphaVantageService alphaVantageService, WatchlistService watchlistService, NutzerService nutzerService, WertpapierRepository wertpapierRepository) {
        this.alphaVantageService = alphaVantageService;
        this.watchlistService = watchlistService;
        this.nutzerService = nutzerService;
        this.wertpapierRepository = wertpapierRepository;
    }


    /**
     * Öffnet einen Dialog mit Kursverlauf, Fundamentaldaten und Beschreibung für das gegebene Symbol.
     * Der Dialog wird responsive gestaltet und bietet Optionen zur Anzeige von Kursen in verschiedenen Zeitrahmen,
     * fundamentalen Daten und einer Unternehmensbeschreibung. Zudem kann das Wertpapier zur Watchlist hinzugefügt werden.
     *
     * @param symbol Das Börsensymbol des Wertpapiers, dessen Details angezeigt werden sollen.
     * @return Der erstellte und geöffnete Dialog, damit auf dessen Schließen reagiert werden kann.
     */
    public Dialog displayWertpapierDetails(String symbol) {
        Dialog dialog = new Dialog();
        //dialog.setHeaderTitle("Details für: " + symbol);
        dialog.setWidthFull();
        dialog.setHeightFull();
        dialog.setModal(true);
        dialog.setDraggable(true);
        dialog.setResizable(true);

        try {
            // Versucht, den Anzeigenamen des Wertpapiers über die Suche zu ermitteln
            List<SearchResult> searchResults = alphaVantageService.search(symbol);
            SearchResult result = searchResults.stream()
                    .filter(r -> r.getSymbol().equalsIgnoreCase(symbol))
                    .findFirst()
                    .orElse(null);
             anzeigeName = result != null ? result.getName() : symbol;

            // Ruft die monatlichen Kursdaten als Standard ab
            List<Kurs> kurse = alphaVantageService.getMonthlySeries(symbol);

            // Wenn keine Kursdaten gefunden werden, wird eine Benachrichtigung angezeigt
            if (kurse.isEmpty()) {
                Notification.show("Keine Daten gefunden.", 3000, Notification.Position.MIDDLE);
                dialog.add(new VerticalLayout(new Span("Keine Daten für " + symbol + " gefunden.")));
                dialog.open();
                return dialog;
            }

            VerticalLayout layout = new VerticalLayout();
            layout.setSizeFull();
            layout.setPadding(false);
            layout.setSpacing(false);
            layout.setMargin(false);
            H2 titel = new H2("Wertpapier: " + anzeigeName);
            titel.addClassName("dialog-title");
            layout.add(titel);


            Select<String> timeFrameSelect = new Select<>();
            timeFrameSelect.setLabel("Zeitraum");
            timeFrameSelect.setItems("Intraday", "Täglich", "Wöchentlich", "Monatlich");
            timeFrameSelect.setValue("Monatlich");

            Button addToWatchlistButton = new Button("Zur Watchlist hinzufügen", new Icon(VaadinIcon.PLUS_CIRCLE));
            addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
            addToWatchlistButton.addClickListener(event -> saveWertpapierToWatchlist(symbol));

            // Überprüfen, ob das Wertpapier bereits in der Watchlist ist und den Button anpassen
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            Nutzer currentUser = nutzerService.getNutzerByUsername(currentUsername);

            if (currentUser != null) {
                Optional<Watchlist> watchlistOpt = watchlistService.getWatchlistForUser(currentUser.getId());
                if (watchlistOpt.isPresent()) {
                    // Annahme: Wertpapier.getName() ist der eindeutige Symbol-Bezeichner
                    boolean isInWatchlist = watchlistOpt.get().getWertpapiere().stream()
                            .anyMatch(wp -> wp.getName() != null && wp.getName().equalsIgnoreCase(symbol));

                    if (isInWatchlist) {
                        addToWatchlistButton.setText("Auf Watchlist");
                        addToWatchlistButton.setEnabled(false); // Button deaktivieren
                        addToWatchlistButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                        addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST); // Grau einfärben
                    }
                }
            }

            HorizontalLayout timeFrameAndButtonLayout = new HorizontalLayout(timeFrameSelect, addToWatchlistButton);
            timeFrameAndButtonLayout.setAlignItems(Alignment.BASELINE);
            timeFrameAndButtonLayout.setSpacing(true);

            layout.add(timeFrameAndButtonLayout);

            VerticalLayout chartContainer = new VerticalLayout();
            chartContainer.setSizeFull();
            chartContainer.setPadding(false);
            chartContainer.setSpacing(false);
            layout.add(chartContainer);

            updateChart(chartContainer, symbol, "Monatlich");

            // Ruft fundamentale Daten ab und zeigt sie an
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
                descriptionBox.addClassName("info-box");

                HorizontalLayout infoAndDescriptionLayout = new HorizontalLayout(scroller, descriptionBox);
                infoAndDescriptionLayout.setSizeFull();
                infoAndDescriptionLayout.setSpacing(true);
                infoAndDescriptionLayout.setFlexGrow(1, scroller);
                infoAndDescriptionLayout.setFlexGrow(2, descriptionBox);

                layout.add(infoAndDescriptionLayout);
            } else {
                Span keineDaten = new Span("⚠️ Keine fundamentalen Daten für das Symbol '" + symbol + "' gefunden.");
                keineDaten.getStyle()
                        .set("font-size", "1.2rem")
                        .set("color", "red")
                        .set("padding", "1rem");

                VerticalLayout infoPlaceholder = new VerticalLayout(keineDaten);
                infoPlaceholder.setWidthFull();
                infoPlaceholder.setHeight("150px");
                infoPlaceholder.addClassName("warning-box");


                layout.add(infoPlaceholder);
            }




            // Listener für die Änderung des Zeitrahmens

            timeFrameSelect.addValueChangeListener(event -> {
                updateChart(chartContainer, symbol, event.getValue());
            });

            // Schließen-Button für den Dialog
            Button closeButton = new Button("✕", event -> dialog.close());
            closeButton.addClassName("dialog-close-button");

            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY); // Weniger aufdringlich
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

            return dialog; // Gibt den Dialog zurück

        } catch (Exception e) {
            Notification.show("Fehler beim Abrufen der Daten: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            dialog.add(new VerticalLayout(new Span("Fehler beim Laden der Details: " + e.getMessage())));
            dialog.open();
            return dialog; // Gibt den Dialog auch im Fehlerfall zurück
        }
    }

    /**
     * Speichert das aktuell angezeigte Wertpapier in der Watchlist des eingeloggten Benutzers.
     * Wenn das Wertpapier noch nicht in der Datenbank existiert, wird es zuerst gespeichert.
     *
     * @param symbol Das Börsensymbol des Wertpapiers, das zur Watchlist hinzugefügt werden soll.
     */
    private void saveWertpapierToWatchlist(String symbol) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Nutzer currentUser = nutzerService.getNutzerByUsername(currentUsername);

        if (currentUser == null) {
            Notification.show("Fehler: Aktueller Nutzer nicht gefunden.", 3000, Notification.Position.MIDDLE);
            return;
        }

        // Versucht, das Wertpapier anhand des Namens in der Datenbank zu finden
        Optional<Wertpapier> existingWertpapier = wertpapierRepository.findByNameIgnoreCase(symbol);

        Wertpapier wertpapierToAdd;
        if (existingWertpapier.isPresent()) {
            wertpapierToAdd = existingWertpapier.get();
        } else {
            // Wenn nicht vorhanden, ein neues Aktie-Objekt erstellen und speichern
            Aktie newAktie = new Aktie();
            newAktie.setName(symbol); // Symbol als Name verwenden
            wertpapierToAdd = wertpapierRepository.save(newAktie);
            Notification.show("Wertpapier '" + symbol + "' wurde in der Datenbank erstellt.", 3000, Notification.Position.MIDDLE);
        }

        try {
            // Fügt das Wertpapier zur Watchlist des Benutzers hinzu
            watchlistService.addWertpapierToUserWatchlist(currentUser.getId(), wertpapierToAdd.getWertpapierId());
            Notification.show(symbol + " wurde zur Watchlist hinzugefügt!", 3000, Notification.Position.MIDDLE);
            // Nach dem Hinzufügen den Button aktualisieren (deaktivieren und Text ändern)
            // Dies erfordert einen Zugriff auf den Button, der in der displayWertpapierDetails-Methode erstellt wird.
            // Da der Button lokal in displayWertpapierDetails erstellt wird, ist eine direkte Aktualisierung
            // von hier aus nicht trivial ohne Übergabe des Buttons oder Neuladen des Dialogs.
            // Für eine vollständige Aktualisierung müsste der Dialog neu geladen oder der Button als Feld gespeichert werden.
            // Da der Dialog nach dem Schließen ohnehin neu aufgebaut wird, ist dies hier weniger kritisch.
        } catch (IllegalArgumentException e) {
            Notification.show("Fehler beim Hinzufügen zur Watchlist: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            Notification.show("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }


    /**
     * Erstellt ein Chart-Objekt basierend auf einer Liste von Kursen.
     * Das Chart zeigt den Kursverlauf als Liniendiagramm an.
     *
     * @param kurse Liste der Kursdaten, die im Chart dargestellt werden sollen.
     * @param title Titel des Charts.
     * @param timeFrame Zeitintervall zur Achsentitel-Auswahl (z.B. "Intraday", "Täglich", "Monatlich").
     * @return Ein konfiguriertes Chart-Objekt.
     */
    private Chart buildChart(List<Kurs> kurse, String title, String timeFrame) {
        Chart chart = new Chart(ChartType.LINE);
        Configuration config = chart.getConfiguration();
        config.setTitle("Kursverlauf für " + anzeigeName );

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
        series.setName(" "); // Leerer Name, da der Titel des Charts ausreicht
        int index = 1;
        for (Kurs kurs : kurse) {
            series.add(new DataSeriesItem(String.valueOf(index), kurs.getSchlusskurs()));
            index++;
        }
        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setMarker(new Marker(false)); // Keine Marker auf der Linie anzeigen
        series.setPlotOptions(plotOptions);

        config.setSeries(series);
        return chart;
    }

    /**
     * Aktualisiert den Kurs-Chart im angegebenen Container basierend auf dem Symbol und dem Zeitrahmen.
     * Die entsprechenden Kursdaten werden über den AlphaVantageService abgerufen.
     *
     * @param chartContainer Das VerticalLayout, das den Chart enthält und aktualisiert werden soll.
     * @param symbol Das Börsensymbol des Wertpapiers.
     * @param timeFrame Das Zeitintervall für die Kursanzeige (z.B. "Intraday", "Täglich", "Wöchentlich", "Monatlich").
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

            chartContainer.removeAll(); // Entfernt den alten Chart
            chartContainer.add(newChart); // Fügt den neuen Chart hinzu

        } catch (Exception e) {
            Notification.show("Fehler beim Aktualisieren des Charts: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    /**
     * Erstellt eine horizontale Layout-Zeile mit zwei beschrifteten Informationsblöcken.
     *
     * @param label1 Beschriftung für den ersten Informationsblock.
     * @param value1 Wert für den ersten Informationsblock.
     * @param label2 Beschriftung für den zweiten Informationsblock.
     * @param value2 Wert für den zweiten Informationsblock.
     * @return Ein HorizontalLayout mit zwei Informationsblöcken.
     */
    private HorizontalLayout createInfoRow(String label1, Object value1, String label2, Object value2) {
        return createInfoRow(label1, value1, label2, value2, null, null);
    }

    /**
     * Erstellt eine horizontale Layout-Zeile mit bis zu drei beschrifteten Informationsblöcken.
     *
     * @param label1 Beschriftung für den ersten Informationsblock.
     * @param value1 Wert für den ersten Informationsblock.
     * @param label2 Beschriftung für den zweiten Informationsblock.
     * @param value2 Wert für den zweiten Informationsblock.
     * @param label3 (Optional) Beschriftung für den dritten Informationsblock.
     * @param value3 (Optional) Wert für den dritten Informationsblock.
     * @return Ein HorizontalLayout mit den angegebenen Informationsblöcken.
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
     * Erzeugt einen einzelnen vertikalen Block mit einem Label und einem Wert für die Anzeige in der Übersicht.
     * Werte, die null, "0" oder leer sind, werden als "n.v." (nicht verfügbar) angezeigt.
     *
     * @param label Die Beschriftung des Informationsblocks.
     * @param value Der Wert, der im Informationsblock angezeigt werden soll.
     * @return Ein VerticalLayout, das den beschrifteten Informationsblock darstellt.
     */
    private VerticalLayout createSingleInfoBlock(String label, Object value) {
        String displayValue = (value == null || value.toString().equals("0") || value.toString().isBlank())
                ? "n.v." : value.toString();

        Span labelSpan = new Span(label);
        labelSpan.getStyle().set("font-weight", "bold").set("font-size", "0.85rem");

        Span valueSpan = new Span(displayValue);
        labelSpan.addClassName("info-label");
        valueSpan.addClassName("info-value");


        VerticalLayout block = new VerticalLayout(labelSpan, valueSpan);
        block.setPadding(false);
        block.setSpacing(false);
        block.setWidth("100%");
        block.getStyle().set("min-width", "150px");

        return block;
    }

    /**
     * Erstellt ein vertikales Layout mit mehreren Zeilen von fundamentalen Finanzkennzahlen einer Aktie.
     * Die Kennzahlen werden in einem ansprechenden Grid-Layout dargestellt.
     *
     * @param aktie Die Aktie mit den anzuzeigenden Fundamentaldaten.
     * @return Ein VerticalLayout, das die fundamentalen Finanzkennzahlen in einem Grid darstellt.
     */
    private VerticalLayout createInfoGrid(Aktie aktie) {
        VerticalLayout gridLayout = new VerticalLayout();
        gridLayout.setSpacing(true);
        gridLayout.setPadding(true);
        gridLayout.setWidthFull();
        gridLayout.addClassName("info-box");

        gridLayout.add(createInfoRow("Unternehmensname", aktie.getUnternehmensname(), "Industrie", aktie.getIndustry(), "Sektor", aktie.getSector()));
        gridLayout.add(createInfoRow("Marktkapitalisierung", aktie.getMarketCap(), "EBITDA", aktie.getEbitda(), "PEG Ratio", aktie.getPegRatio()));
        gridLayout.add(createInfoRow("Buchwert", aktie.getBookValue(), "EPS", aktie.getEps(), "Beta", aktie.getBeta()));
        gridLayout.add(createInfoRow("52W Hoch", aktie.getYearHigh(), "52W Tief", aktie.getYearLow(), "Dividende/Aktie", aktie.getDividendPerShare()));
        gridLayout.add(createInfoRow("Dividendenrendite", aktie.getDividendYield(), "Dividenden-Datum", aktie.getDividendDate()));

        return gridLayout;
    }
}