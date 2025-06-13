package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.services.AnleiheKaufService;
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Ansicht zur Darstellung von Detailinformationen über Anleihen.
 * <p>
 * Diese View wird über die Factory in der Anwendung zur Anzeige von Kursverläufen
 * und Detailinformationen wie Kupon, Emittent oder Laufzeit verwendet.
 */
@Component
public class AnleiheView extends AbstractWertpapierView {

    /** Formatierer für Datumsangaben. */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** Service für Anleihe-bezogene Transaktionen. */
    private final AnleiheKaufService anleiheKaufService;

    /**
     * Konstruktor zum Initialisieren aller benötigten Services.
     *
     * @param alphaVantageService   Service zur Kursabfrage
     * @param watchlistService      Service zur Verwaltung der Watchlist
     * @param nutzerService         Service zur Nutzerverwaltung
     * @param wertpapierRepository  Repository für Wertpapierdaten
     * @param anleiheKaufService    Service für Anleihekäufe
     */
    @Autowired
    public AnleiheView(AlphaVantageService alphaVantageService,
                       WatchlistService watchlistService,
                       NutzerService nutzerService,
                       WertpapierRepository wertpapierRepository,
                       AnleiheKaufService anleiheKaufService) {
        super(alphaVantageService, watchlistService, nutzerService, wertpapierRepository);
        this.anleiheKaufService = anleiheKaufService;
    }

    /**
     * Erstellt einen Dialog mit Detailinformationen zur übergebenen Anleihe.
     *
     * @param wertpapier Das anzuzeigende Wertpapier (Anleihe)
     * @return Ein vollständig aufgebauter Dialog mit Kursverlauf, Kennzahlen und Watchlist-Integration
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
            Anleihe anleihe = (Anleihe) wertpapier;
            String symbol = anleihe.getSymbol();
            String name = anleihe.getName();

            List<Kurs> kurse = alphaVantageService.getMonthlySeries(symbol);
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
            H2 titel = new H2("Anleihe: " + name);
            titel.addClassName("dialog-title");
            layout.add(titel);

            // Auswahl für Zeitrahmen
            Select<String> timeFrameSelect = new Select<>();
            timeFrameSelect.setLabel("Zeitraum");
            timeFrameSelect.setItems("Intraday", "Täglich", "Wöchentlich", "Monatlich");
            timeFrameSelect.setValue("Monatlich");

            // Watchlist-Button
            Button addToWatchlistButton = new Button("Zur Watchlist hinzufügen", new Icon(VaadinIcon.PLUS_CIRCLE));

            // Watchlist prüfen
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            Nutzer currentUser = nutzerService.getNutzerByUsername(currentUsername);

            // Prüfen, ob der Nutzer angemeldet ist und ob die Aktie bereits in der Watchlist ist
            if (currentUser != null) {
                Optional<Watchlist> watchlistOpt = watchlistService.getWatchlistForUser(currentUser.getId());
                if (watchlistOpt.isPresent()) {
                    boolean isInWatchlist = watchlistOpt.get().getWertpapiere().stream()
                            .anyMatch(wp -> wp.getName() != null && wp.getName().equalsIgnoreCase(symbol));
                    if (isInWatchlist) {
                        Notification.show("Diese Aktie ist bereits in deiner Watchlist!", 3000, Notification.Position.MIDDLE);
                        addToWatchlistButton.setEnabled(true);
                        addToWatchlistButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                        addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
                    }
                }
            }


            addToWatchlistButton.addClickListener(event -> {
                if (currentUser != null) {
                    Optional<Watchlist> watchlistOpt = watchlistService.getWatchlistForUser(currentUser.getId());
                    boolean isInWatchlist = watchlistOpt.isPresent() && watchlistOpt.get().getWertpapiere().stream()
                            .anyMatch(wp -> wp.getName() != null && wp.getName().equalsIgnoreCase(symbol));
                    if (isInWatchlist) {
                        Notification.show("Diese Aktie ist bereits in deiner Watchlist!", 3000, Notification.Position.MIDDLE);
                        addToWatchlistButton.setEnabled(false);
                        addToWatchlistButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                        addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
                    } else {
                        Optional<Wertpapier> existing = wertpapierRepository.findByNameIgnoreCase(symbol);
                        Wertpapier toAdd = existing.orElseGet(() -> wertpapierRepository.save(anleihe));
                        watchlistService.addWertpapierToUserWatchlist(currentUser.getId(), toAdd.getWertpapierId());
                        Notification.show("Zur Watchlist hinzugefügt", 3000, Notification.Position.MIDDLE);
                        addToWatchlistButton.setEnabled(false);
                        addToWatchlistButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                        addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
                    }
                }
            });

            HorizontalLayout timeFrameAndButtonLayout = new HorizontalLayout(timeFrameSelect, addToWatchlistButton);
            timeFrameAndButtonLayout.setAlignItems(Alignment.BASELINE);
            timeFrameAndButtonLayout.setSpacing(true);
            timeFrameAndButtonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            layout.add(timeFrameAndButtonLayout);

            // Chartbereich
            VerticalLayout chartContainer = new VerticalLayout();
            chartContainer.setSizeFull();
            layout.add(chartContainer);

            updateChart(chartContainer, symbol, "Monatlich", anleihe.getName());

            // Detailinformationen zur Anleihe
            VerticalLayout infoBox = new VerticalLayout();
            infoBox.setSizeFull();
            infoBox.setSpacing(true);
            infoBox.setPadding(true);
            infoBox.addClassName("info-box");

            infoBox.add(createInfoRow("Emittent", anleihe.getEmittent(),
                    "Kupon", anleihe.getKupon() + " %",
                    "Laufzeit", anleihe.getLaufzeit() != null ? formatter.format(anleihe.getLaufzeit()) : "n.v."));

            infoBox.add(createInfoRow("Nennwert", anleihe.getNennwert() + " €", "", ""));
            // Letzte Zinszahlung anzeigen
            anleihe.getAusschuettungen().stream()
                    .filter(a -> a instanceof Zinszahlung)
                    .map(a -> (Zinszahlung) a)
                    .max((z1, z2) -> z1.getDatum().compareTo(z2.getDatum()))
                    .ifPresent(zins -> {
                        infoBox.add(createInfoRow(
                                "Letzte Zinszahlung", zins.getDatum().format(formatter),
                                "Betrag", String.format("%.2f €", zins.getBetrag()),
                                "Frequenz", zins.getFrequenz().name()
                        ));
                    });
            layout.add(infoBox);

            // Aktualisiere Chart beim Wechsel des Zeitrahmens
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
            Notification.show("Fehler beim Laden der Anleihe-Details: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            dialog.add(new VerticalLayout(new Span("Fehler beim Laden der Details: " + e.getMessage())));
            dialog.open();
            return dialog;
        }
    }
}
