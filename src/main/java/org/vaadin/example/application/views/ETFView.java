package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.ETF;
import org.vaadin.example.application.classes.ETFDividende;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.repositories.KursRepository;
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ansicht für die Detaildarstellung eines ETFs (Exchange Traded Fund).
 * <p>
 * Diese View zeigt Kursverläufe, ETF-spezifische Informationen wie Emittent und Index,
 * sowie Interaktionen wie Watchlist-Hinzufügung und Zeitrahmenwechsel.
 */
@Component
public class ETFView extends AbstractWertpapierView {

    /** Formatierung für Zeitstempel auf Kursdaten. */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /** Repository zur Kursdatenabfrage für ETFs. */
    private final KursRepository kursRepository;

    /**
     * Konstruktor zur Initialisierung aller Services und Repositories.
     *
     * @param alphaVantageService   Service zur externen Kursabfrage
     * @param watchlistService      Service zur Verwaltung der Watchlist
     * @param nutzerService         Service zur Nutzeridentifikation
     * @param wertpapierRepository  Repository für Wertpapierdaten
     * @param kursRepository        Repository für Kursverläufe
     */
    @Autowired
    public ETFView(AlphaVantageService alphaVantageService,
                   WatchlistService watchlistService,
                   NutzerService nutzerService,
                   WertpapierRepository wertpapierRepository,
                   KursRepository kursRepository) {
        super(alphaVantageService, watchlistService, nutzerService, wertpapierRepository);
        this.kursRepository = kursRepository;
    }

    /**
     * Erstellt einen Dialog mit Detailinformationen und Kursverlauf zum übergebenen ETF.
     *
     * @param wertpapier Das ETF-Objekt, das angezeigt werden soll
     * @return Ein Dialog mit ETF-Details, Chart, Watchlist-Option und Metadaten
     */
    @Override
    public Dialog createDetailsDialog(Wertpapier wertpapier) {
        Dialog dialog = new Dialog();
        dialog.setWidthFull();
        dialog.setHeightFull();
        dialog.setModal(true);
        dialog.setDraggable(true);
        dialog.setResizable(true);

        try {
            ETF etf = (ETF) wertpapier;
            String symbol = etf.getSymbol();
            String name = etf.getName() != null ? etf.getName() : etf.getSymbol();
            this.anzeigeName = name; // wichtig für updateChart()

            List<Kurs> kurse = kursRepository.findByWertpapier_SymbolOrderByDatumAsc(symbol);

            if (kurse.isEmpty()) {
                Notification.show("Keine Kursdaten für " + symbol + " gefunden.", 3000, Notification.Position.MIDDLE);
                dialog.add(new VerticalLayout(new Span("Keine Daten für " + symbol + " vorhanden.")));
                dialog.open();
                return dialog;
            }

            // Hauptlayout vorbereiten
            VerticalLayout layout = new VerticalLayout();
            layout.setSizeFull();
            layout.setPadding(false);
            layout.setSpacing(false);
            layout.setMargin(false);

            // Titel
            H2 titel = new H2("ETF: " + name);
            titel.addClassName("dialog-title");
            layout.add(titel);

            // Auswahl für Zeitrahmen
            Select<String> timeFrameSelect = new Select<>();
            timeFrameSelect.setLabel("Zeitraum");
            timeFrameSelect.setItems("Intraday", "Täglich", "Wöchentlich", "Monatlich");
            timeFrameSelect.setValue("Monatlich");

            // Watchlist-Button
            Button addToWatchlistButton = createWatchlistButton(symbol);

            HorizontalLayout timeFrameAndButtonLayout = new HorizontalLayout(timeFrameSelect, addToWatchlistButton);
            timeFrameAndButtonLayout.setAlignItems(Alignment.BASELINE);
            timeFrameAndButtonLayout.setSpacing(true);
            layout.add(timeFrameAndButtonLayout);

            // Chart-Bereich
            VerticalLayout chartContainer = new VerticalLayout();
            chartContainer.setSizeFull();
            layout.add(chartContainer);

            // Initiale Chartanzeige
            updateChart(chartContainer, symbol, "Monatlich", anzeigeName);

            // ETF-spezifische Informationen
            VerticalLayout infoBox = new VerticalLayout();
            infoBox.setSizeFull();
            infoBox.setSpacing(true);
            infoBox.setPadding(true);
            infoBox.addClassName("info-box");

            // Aktuellen Kurs aus der Datenbank abrufen
            Kurs latestKurs = kurse.isEmpty() ? null : kurse.get(kurse.size() - 1);

            // Preisinformation erstellen
            String preisInfo;
            if (latestKurs != null) {
                preisInfo = String.format("%.2f USD ", latestKurs.getSchlusskurs());
            } else {
                preisInfo = "Keine aktuellen Kursdaten";
            }

            // Preisinformation als erste Zeile hinzufügen
            infoBox.add(createInfoRow("Aktueller Kurs", preisInfo, "Datum", latestKurs != null ? latestKurs.getDatum().format(formatter) : "N/A"));
            infoBox.add(createInfoRow("Emittent", etf.getEmittent(), "Index", etf.getIndex()));
            // Letzte ETF-Dividende anzeigen
            etf.getAusschuettungen().stream()
                    .filter(a -> a instanceof ETFDividende)
                    .map(a -> (ETFDividende) a)
                    .max((d1, d2) -> d1.getDatum().compareTo(d2.getDatum()))
                    .ifPresent(div -> {
                        infoBox.add(createInfoRow(
                                "Letzte Dividende", div.getDatum().format(formatter),
                                "Betrag", String.format("%.2f €", div.getBetrag()),
                                "Frequenz", div.getFrequenz() != null ? div.getFrequenz().name() : "k.A."
                        ));
                    });
            layout.add(infoBox);

            // Zeitrahmenwechsel-Listener
            timeFrameSelect.addValueChangeListener(event ->
                    updateChart(chartContainer, symbol, event.getValue(), anzeigeName)
            );

            // Schließen-Button
            Button closeButton = new Button(VaadinIcon.CLOSE.create(), e -> dialog.close());
            closeButton.addClassName("dialog-close-button");
            layout.add(closeButton);

            dialog.add(layout);
            dialog.open();
            return dialog;

        } catch (Exception e) {
            Notification.show("Fehler beim Laden des ETF-Dialogs: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            dialog.add(new VerticalLayout(new Span("Fehler beim Laden der Details: " + e.getMessage())));
            dialog.open();
            return dialog;
        }
    }
}
