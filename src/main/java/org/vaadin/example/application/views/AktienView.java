package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.models.SearchResult;
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;

import java.util.List;
import java.util.Optional;

@Component
public class AktienView extends AbstractWertpapierView implements WertpapierDetailView {

    @Autowired private NutzerService nutzerService;
    @Autowired private WatchlistService watchlistService;
    @Autowired private WertpapierRepository wertpapierRepository;

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


    @Override
    public Dialog createDetailsDialog(Wertpapier wertpapier) {
        String symbol = wertpapier.getSymbol();
        Aktie aktie = alphaVantageService.getFundamentalData(symbol);
        this.anzeigeName = aktie.getName();

        Dialog dialog = new Dialog();
        dialog.setWidthFull();
        dialog.setHeightFull();

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        H2 titel = new H2("Aktie: " + anzeigeName);
        layout.add(titel);

        Select<String> timeFrameSelect = new Select<>();
        timeFrameSelect.setLabel("Zeitraum");
        timeFrameSelect.setItems("Intraday", "Täglich", "Wöchentlich", "Monatlich");
        timeFrameSelect.setValue("Monatlich");

        Button addToWatchlistButton = new Button("Zur Watchlist hinzufügen", new Icon(VaadinIcon.PLUS_CIRCLE));
        addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);

        // Watchlist prüfen
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Nutzer currentUser = nutzerService.getNutzerByUsername(currentUsername);

        if (currentUser != null) {
            Optional<Watchlist> watchlistOpt = watchlistService.getWatchlistForUser(currentUser.getId());
            if (watchlistOpt.isPresent()) {
                boolean isInWatchlist = watchlistOpt.get().getWertpapiere().stream()
                        .anyMatch(wp -> wp.getName() != null && wp.getName().equalsIgnoreCase(symbol));
                if (isInWatchlist) {
                    addToWatchlistButton.setText("Auf Watchlist");
                    addToWatchlistButton.setEnabled(false);
                    addToWatchlistButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    addToWatchlistButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
                }
            }
        }


        addToWatchlistButton.addClickListener(event -> {
            if (currentUser != null) {
                Optional<Wertpapier> existing = wertpapierRepository.findByNameIgnoreCase(symbol);
                Wertpapier toAdd = existing.orElseGet(() -> wertpapierRepository.save(aktie));
                watchlistService.addWertpapierToUserWatchlist(currentUser.getId(), toAdd.getWertpapierId());
                Notification.show("Zur Watchlist hinzugefügt", 3000, Notification.Position.MIDDLE);
            }
        });

        HorizontalLayout topRow = new HorizontalLayout(timeFrameSelect, addToWatchlistButton);
        layout.add(topRow);

        VerticalLayout chartContainer = new VerticalLayout();
        chartContainer.setSizeFull();
        layout.add(chartContainer);

        updateChart(chartContainer, symbol, "Monatlich", anzeigeName);

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

    private VerticalLayout createInfoGrid(Aktie aktie) {
        VerticalLayout gridLayout = new VerticalLayout();
        gridLayout.setSpacing(true);
        gridLayout.setPadding(true);
        gridLayout.setWidthFull();

        gridLayout.add(createInfoRow("Unternehmensname", aktie.getUnternehmensname(), "Industrie", aktie.getIndustry(), "Sektor", aktie.getSector()));
        gridLayout.add(createInfoRow("Marktkapitalisierung", aktie.getMarketCap(), "EBITDA", aktie.getEbitda(), "PEG Ratio", aktie.getPegRatio()));
        gridLayout.add(createInfoRow("Buchwert", aktie.getBookValue(), "EPS", aktie.getEps(), "Beta", aktie.getBeta()));
        gridLayout.add(createInfoRow("52W Hoch", aktie.getYearHigh(), "52W Tief", aktie.getYearLow(), "Dividende/Aktie", aktie.getDividendPerShare()));
        gridLayout.add(createInfoRow("Dividendenrendite", aktie.getDividendYield(), "Dividenden-Datum", aktie.getDividendDate()));

        return gridLayout;
    }
}
