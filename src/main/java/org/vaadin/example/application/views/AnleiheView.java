package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.Anleihe;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.services.AnleiheKaufService;
import org.vaadin.example.application.repositories.WertpapierRepository;
import org.vaadin.example.application.services.AlphaVantageService;
import org.vaadin.example.application.services.NutzerService;
import org.vaadin.example.application.services.WatchlistService;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;


import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
public class AnleiheView extends AbstractWertpapierView{

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final AnleiheKaufService anleiheKaufService;

    @Autowired
    public AnleiheView(AlphaVantageService alphaVantageService,
                       WatchlistService watchlistService,
                       NutzerService nutzerService,
                       WertpapierRepository wertpapierRepository,
                       AnleiheKaufService anleiheKaufService) {
        super(alphaVantageService, watchlistService, nutzerService, wertpapierRepository);
        this.anleiheKaufService = anleiheKaufService;
    }


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

            VerticalLayout layout = new VerticalLayout();
            layout.setSizeFull();
            layout.setPadding(false);
            layout.setSpacing(false);
            layout.setMargin(false);

            H2 titel = new H2("Anleihe: " + name);
            titel.addClassName("dialog-title");
            layout.add(titel);

            Select<String> timeFrameSelect = new Select<>();
            timeFrameSelect.setLabel("Zeitraum");
            timeFrameSelect.setItems("Intraday", "Täglich", "Wöchentlich", "Monatlich");
            timeFrameSelect.setValue("Monatlich");

            Button addToWatchlistButton = createWatchlistButton(symbol);

            HorizontalLayout timeFrameAndButtonLayout = new HorizontalLayout(timeFrameSelect, addToWatchlistButton);
            timeFrameAndButtonLayout.setAlignItems(Alignment.BASELINE);
            timeFrameAndButtonLayout.setSpacing(true);

            Button kaufButton = new Button("Kaufen", e -> {
                try {
                    UI.getCurrent().navigate("anleihe-kaufen/" + symbol);
                } catch (Exception ex) {
                    Notification.show("Fehler beim Weiterleiten zur Kaufmaske: " + ex.getMessage(), 5000, Notification.Position.MIDDLE);
                }
            });

            kaufButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            HorizontalLayout buttons = new HorizontalLayout(timeFrameSelect, addToWatchlistButton, kaufButton);
            buttons.setAlignItems(Alignment.BASELINE);
            buttons.setSpacing(true);
            layout.add(buttons);

            VerticalLayout chartContainer = new VerticalLayout();
            chartContainer.setSizeFull();
            layout.add(chartContainer);

            updateChart(chartContainer, symbol, "Monatlich", anleihe.getName() );

            // Anleihe-spezifische Informationen
            VerticalLayout infoBox = new VerticalLayout();
            infoBox.setSizeFull();
            infoBox.setSpacing(true);
            infoBox.setPadding(true);
            infoBox.addClassName("info-box");

            infoBox.add(createInfoRow("Emittent", anleihe.getEmittent(),
                    "Kupon", anleihe.getKupon() + " %",
                    "Laufzeit", anleihe.getLaufzeit() != null ? formatter.format(anleihe.getLaufzeit()) : "n.v."));

            infoBox.add(createInfoRow("Nennwert", anleihe.getNennwert() + " €", "", ""));


            layout.add(infoBox);

            timeFrameSelect.addValueChangeListener(event ->
                    updateChart(chartContainer, symbol, event.getValue(),anzeigeName)
            );


            // Close-Button erstellen und oben rechts ins Layout einfügen
            Button closeButton = new Button(VaadinIcon.CLOSE.create(), e -> dialog.close());
            closeButton.addClassName("dialog-close-button");
            layout.add(closeButton); // Füge ihn ins Layout ein – nicht direkt in den Dialog!

// Danach das Layout in den Dialog setzen
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
