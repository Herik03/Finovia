package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;
import java.util.List;
import java.util.Optional;

/**
 * Ansicht für die Anzeige von Aktieninformationen.
 * Diese Klasse erweitert die abstrakte Basisklasse {@link AbstractWertpapierView}
 * und implementiert spezifische Logik für die Anzeige und Interaktion mit Aktien.
 */
@Component
public class AktienView extends AbstractWertpapierView {

    @Autowired private NutzerService nutzerService;
    @Autowired private WatchlistService watchlistService;
    @Autowired private WertpapierRepository wertpapierRepository;

    /**
     * Konstruktor zur Initialisierung der AktienView.
     * Erfordert die Bereitstellung aller benötigten Services und Repositories.
     *
     * @param alphaVantageService   Service zur Kursabfrage über die AlphaVantage API
     * @param watchlistService      Service zur Verwaltung von Nutzer-Watchlists
     * @param nutzerService         Service zur Identifikation und Verwaltung des eingeloggten Nutzers
     * @param wertpapierRepository  Repository für Wertpapierdatenbankzugriffe
     */
    @Autowired
    public AktienView(AlphaVantageService alphaVantageService,
                      WatchlistService watchlistService,
                      NutzerService nutzerService,
                      WertpapierRepository wertpapierRepository) {
        super(alphaVantageService, watchlistService, nutzerService, wertpapierRepository);
        this.nutzerService = nutzerService;
        this.watchlistService = watchlistService;
        this.wertpapierRepository = wertpapierRepository;
    }

    /**
     * Erstellt einen Dialog zur Anzeige der Details eines Wertpapiers.
     * Dieser Dialog enthält Informationen über die Aktie, Charts und Interaktionsmöglichkeiten.
     *
     * @param wertpapier Das Wertpapier, dessen Details angezeigt werden sollen
     * @return Ein Dialog mit den Details der Aktie
     */
    @Override
    public Dialog createDetailsDialog(Wertpapier wertpapier) {
        String symbol = wertpapier.getSymbol();
        Aktie aktie = alphaVantageService.getFundamentalData(symbol);

        // Null-Check hinzufügen, um NullPointerException zu vermeiden
        if (aktie == null) {
            // Fallback: Verwende den Namen aus dem übergebenen Wertpapier
            this.anzeigeName = wertpapier.getName();

            // Erstelle einen einfachen Dialog mit Fehlermeldung
            Dialog errorDialog = new Dialog();
            errorDialog.setWidth("400px");

            VerticalLayout errorLayout = new VerticalLayout();
            errorLayout.add(new H2("Fehler beim Laden der Aktiendetails"));
            errorLayout.add(new com.vaadin.flow.component.html.Paragraph(
                    "Die Detaildaten für " + symbol + " konnten nicht geladen werden. " +
                            "Bitte versuchen Sie es später erneut oder kontaktieren Sie den Support."
            ));

            Button closeButton = new Button("Schließen", e -> errorDialog.close());
            closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            errorLayout.add(closeButton);

            errorDialog.add(errorLayout);
            errorDialog.open();
            return errorDialog;
        }

        // Setze den Anzeigenamen der Aktie
        this.anzeigeName = aktie.getName();

        // Dialog erstellen
        Dialog dialog = new Dialog();
        dialog.setWidthFull();
        dialog.setHeightFull();

        // Layout für den Dialog erstellen
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        H2 titel = new H2("Aktie: " + anzeigeName);
        layout.add(titel);

        // Dropdown für den Zeitraum
        Select<String> timeFrameSelect = new Select<>();
        timeFrameSelect.setLabel("Zeitraum");
        timeFrameSelect.setItems("Intraday", "Täglich", "Wöchentlich", "Monatlich");
        timeFrameSelect.setValue("Monatlich");

        // Button zum Hinzufügen zur Watchlist
        Button addToWatchlistButton = new Button("Zur Watchlist hinzufügen", new Icon(VaadinIcon.PLUS_CIRCLE));
        addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        addToWatchlistButton.getStyle().set("margin-top", "8px");

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
                    Wertpapier toAdd = existing.orElseGet(() -> wertpapierRepository.save(aktie));
                    watchlistService.addWertpapierToUserWatchlist(currentUser.getId(), toAdd.getWertpapierId());
                    Notification.show("Zur Watchlist hinzugefügt", 3000, Notification.Position.MIDDLE);
                    addToWatchlistButton.setEnabled(false);
                    addToWatchlistButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
                }
            }
        });


        HorizontalLayout timeFrameAndButtonLayout = new HorizontalLayout(timeFrameSelect, addToWatchlistButton);
        timeFrameAndButtonLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        timeFrameAndButtonLayout.setSpacing(true);
        timeFrameAndButtonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(timeFrameAndButtonLayout);


        // Container für das Chart
        VerticalLayout chartContainer = new VerticalLayout();
        chartContainer.setSizeFull();
        layout.add(chartContainer);

        updateChart(chartContainer, symbol, "Monatlich", anzeigeName);

        // Listener für den Zeitraum-Select
        timeFrameSelect.addValueChangeListener(event -> {
            updateChart(chartContainer, symbol, event.getValue(), anzeigeName);
        });

        layout.add(createInfoGrid(aktie));

        // Close-Button erstellen und oben rechts ins Layout einfügen
        Button closeButton = new Button(VaadinIcon.CLOSE.create(), e -> dialog.close());
        closeButton.addClassName("dialog-close-button");
        layout.add(closeButton); // Füge ihn ins Layout ein – nicht direkt in den Dialog!

        // Danach das Layout in den Dialog setzen
        dialog.add(layout);
        dialog.open();
        return dialog;
    }

    /**
     * Erstellt ein vertikales Layout mit Informationen zu einer Aktie.
     * Dieses Layout enthält verschiedene Informationen wie Kurs, Unternehmensname, Sektor usw.
     *
     * @param aktie Die Aktie, deren Informationen angezeigt werden sollen
     * @return Ein vertikales Layout mit den Aktieninformationen
     */
    private VerticalLayout createInfoGrid(Aktie aktie) {
        VerticalLayout gridLayout = new VerticalLayout();
        gridLayout.setSpacing(true);
        gridLayout.setPadding(true);
        gridLayout.setWidthFull();

        // Aktuellen Kurs aus der Datenbank abrufen
        List<Kurs> kursDaten = aktie.getKurse();
        Kurs latestKurs = (kursDaten != null && !kursDaten.isEmpty()) ? kursDaten.get(kursDaten.size() - 1) : null;

        // Preisinformation erstellen
        String preisInfo;
        if (latestKurs != null) {
            preisInfo = String.format("%.2f USD ", latestKurs.getSchlusskurs());
        } else {
            preisInfo = "Keine aktuellen Kursdaten";
        }

        // GridLayout mit den Informationen füllen
        gridLayout.add(createInfoRow("Aktueller Kurs", preisInfo, "Währung", aktie.getCurrency(), "Börse", aktie.getExchange()));
        gridLayout.add(createInfoRow("Unternehmensname", aktie.getUnternehmensname(), "Industrie", aktie.getIndustry(), "Sektor", aktie.getSector()));
        gridLayout.add(createInfoRow("Marktkapitalisierung", aktie.getMarketCap(), "EBITDA", aktie.getEbitda(), "PEG Ratio", aktie.getPegRatio()));
        gridLayout.add(createInfoRow("Buchwert", aktie.getBookValue(), "EPS", aktie.getEps(), "Beta", aktie.getBeta()));
        gridLayout.add(createInfoRow("52W Hoch", aktie.getYearHigh(), "52W Tief", aktie.getYearLow(), "Dividende/Aktie", aktie.getDividendPerShare()));
        gridLayout.add(createInfoRow("Dividendenrendite", aktie.getDividendYield(), "Dividenden-Datum", aktie.getDividendDate()));

        return gridLayout;
    }
}
