package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.ETF;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.ETFVerkaufService;
import org.vaadin.example.application.services.NutzerService;

import java.util.List;

/**
 * View zum Verkauf von ETFs.
 * Diese View ermöglicht es Nutzern, ETFs zu verkaufen, indem sie das Symbol, den aktuellen Kurs,
 * die Stückzahl und das Depot auswählen. Die Gebühren werden automatisch berechnet und angezeigt.
 */
@Route("verkaufen/etf/:symbol")
@PageTitle("ETF verkaufen")
@PermitAll
public class ETFVerkaufenView extends AbstractSideNav implements BeforeEnterObserver {

    private final ETFVerkaufService etfVerkaufService;
    private final DepotService depotService;
    private final SecurityService securityService;
    private final NutzerService nutzerService;

    private TextField symbolField;
    private TextField einzelkursField;
    private NumberField stueckzahlField;
    private ComboBox<Depot> depotComboBox;
    private TextField kursField;
    private final double gebuehren = 2.50;

    /**
     * Konstruktor der ETFVerkaufenView.
     * Initialisiert die View mit den erforderlichen Services und UI-Komponenten.
     *
     * @param etfVerkaufService Service zum Verkauf von ETFs
     * @param depotService      Service zur Verwaltung von Depots
     * @param securityService   Service für Sicherheitsfunktionen
     * @param nutzerService     Service zur Nutzerverwaltung
     */
    @Autowired
    public ETFVerkaufenView(ETFVerkaufService etfVerkaufService,
                            DepotService depotService,
                            SecurityService securityService,
                            NutzerService nutzerService) {
        super(securityService);
        this.etfVerkaufService = etfVerkaufService;
        this.depotService = depotService;
        this.securityService = securityService;
        this.nutzerService = nutzerService;

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setPadding(true);
        contentLayout.setSpacing(true);

        setupUI(contentLayout);
        addToMainContent(contentLayout);
    }

    /**
     * Erstellt die Benutzeroberfläche für den Verkauf von ETFs.
     * Fügt alle erforderlichen Felder, Buttons und Layouts hinzu.
     *
     * @param container Das Container-Layout, in dem die UI-Komponenten platziert werden
     */
    private void setupUI(VerticalLayout container) {
        // Zurück-Button wie bei AktienKaufView
        Button backButton = new Button("Zurück zur Übersicht", VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        com.vaadin.flow.router.RouterLink routerLink = new com.vaadin.flow.router.RouterLink("", MainView.class);
        routerLink.add(backButton);
        VerticalLayout topLeftLayout = new VerticalLayout(routerLink);
        topLeftLayout.setPadding(false);
        topLeftLayout.setSpacing(false);
        topLeftLayout.setWidthFull();
        topLeftLayout.setAlignItems(FlexComponent.Alignment.START);

        H3 title = new H3("ETF verkaufen");
        title.addClassName("view-title");

        symbolField = new TextField("ETF-Symbol");
        symbolField.setReadOnly(true);
        symbolField.setWidthFull();

        einzelkursField = new TextField("Aktueller Kurs (€/ETF)");
        einzelkursField.setReadOnly(true);
        einzelkursField.setValue("0.00");
        einzelkursField.setWidthFull();

        stueckzahlField = new NumberField("Stückzahl");
        stueckzahlField.setMin(1);
        stueckzahlField.setValue(1.0);
        stueckzahlField.setWidthFull();

        depotComboBox = new ComboBox<>("Depot auswählen");
        List<Depot> depots = depotService.getDepotsByNutzerId(getAktuelleNutzerId());
        depotComboBox.setItems(depots);
        depotComboBox.setItemLabelGenerator(Depot::getName);
        depotComboBox.setWidthFull();

        TextField gebuehrenField = new TextField("Gebühren (EUR)");
        gebuehrenField.setValue(String.format("%.2f", gebuehren));
        gebuehrenField.setReadOnly(true);
        gebuehrenField.setWidthFull();

        kursField = new TextField("Gesamtpreis (€)");
        kursField.setReadOnly(true);
        kursField.setValue("0.00");
        kursField.setWidthFull();

        Button verkaufButton = new Button("Jetzt Verkaufen");
        verkaufButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        verkaufButton.addClassName("filled-button");
        verkaufButton.setWidthFull();


        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("400px");
        formLayout.getStyle().set("margin", "0 auto");
        formLayout.add(symbolField, einzelkursField, stueckzahlField, gebuehrenField, kursField, depotComboBox, verkaufButton);


        symbolField.addValueChangeListener(e -> {
            aktualisiereEinzelkurs();
            aktualisiereKurs();
        });
        stueckzahlField.addValueChangeListener(e -> {
            if (stueckzahlField.getValue() != null && stueckzahlField.getValue().intValue() > 0) {
                aktualisiereKurs();
            } else {
                stueckzahlField.setErrorMessage("Stückzahl muss größer als 0 sein.");
            }
        });

        // Button-Listener für den Verkauf von ETFs
        verkaufButton.addClickListener(event -> {
            String symbol = symbolField.getValue();
            Double stueckzahlDouble = stueckzahlField.getValue();
            Depot depot = depotComboBox.getValue();

            if (symbol == null || symbol.isBlank() || depot == null) {
                Notification.show("Bitte Symbol und Depot angeben.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            if (stueckzahlDouble == null || stueckzahlDouble < 1) {
                Notification.show("Bitte eine gültige Stückzahl angeben.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            int stueckzahl = stueckzahlDouble.intValue();

            ETF verkaufterETF = etfVerkaufService.verkaufeETF(symbol, stueckzahl, depot, nutzerService.getAngemeldeterNutzer());
            if (verkaufterETF != null) {
                Notification.show("ETF erfolgreich verkauft! (" + stueckzahl + " Stück)")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().navigate("transaktionen");
            } else {
                Notification.show("Verkauf fehlgeschlagen. Bitte Symbol und Stückzahl prüfen.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        VerticalLayout centerLayout = new VerticalLayout(title, formLayout);

        centerLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        centerLayout.setSpacing(true);
        centerLayout.setPadding(true);
        centerLayout.setWidthFull();
        centerLayout.getStyle().set("max-width", "600px");
        centerLayout.getStyle().set("margin", "0 auto");

        container.add(topLeftLayout, centerLayout);
    }

    /**
     * Aktualisiert den Einzelkurs des ETFs basierend auf dem eingegebenen Symbol.
     * Wenn das Symbol leer ist, wird der Kurs auf "0.00" gesetzt.
     */
    private void aktualisiereEinzelkurs() {
        String symbol = symbolField.getValue();
        if (symbol != null && !symbol.isBlank()) {
            try {
                double kurs = etfVerkaufService.getKursFürSymbol(symbol);
                einzelkursField.setValue(String.format("%.2f", kurs));
            } catch (Exception e) {
                einzelkursField.setValue("Fehler");
            }
        } else {
            einzelkursField.setValue("0.00");
        }
    }

    /**
     * Aktualisiert den Gesamtpreis basierend auf dem Einzelkurs und der Stückzahl.
     * Wenn das Symbol oder die Stückzahl ungültig ist, wird der Kurs auf "0.00" gesetzt.
     */
    private void aktualisiereKurs() {
        String symbol = symbolField.getValue();
        Double stueckzahl = stueckzahlField.getValue();

        if (symbol != null && !symbol.isBlank() && stueckzahl != null && stueckzahl > 0) {
            try {
                double kurs = etfVerkaufService.getKursFürSymbol(symbol);
                double gesamt = kurs * stueckzahl + gebuehren;
                kursField.setValue(String.format("%.2f", gesamt));
            } catch (Exception ex) {
                kursField.setValue("Fehler");
            }
        } else {
            kursField.setValue("0.00");
        }
    }

    /**
     * Holt die ID des aktuell angemeldeten Nutzers.
     * Wenn kein Nutzer angemeldet ist, wird null zurückgegeben.
     *
     * @return Die ID des aktuellen Nutzers oder null, wenn kein Nutzer angemeldet ist
     */
    private Long getAktuelleNutzerId() {
        UserDetails userDetails = securityService.getAuthenticatedUser();
        if (userDetails == null) {
            return null;
        }
        Nutzer nutzer = nutzerService.findByUsername(userDetails.getUsername());
        return (nutzer != null) ? nutzer.getId() : null;
    }

    /**
     * Wird aufgerufen, bevor die View angezeigt wird.
     * Hier wird das Symbol aus den Routenparametern geladen und die UI entsprechend aktualisiert.
     *
     * @param event Das BeforeEnterEvent, das Informationen über die Navigation enthält
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String symbol = event.getRouteParameters().get("symbol").orElse("");
        if (!symbol.isBlank()) {
            symbolField.setValue(symbol);
            symbolField.setReadOnly(true);
            aktualisiereEinzelkurs();
            aktualisiereKurs();
        } else {
            symbolField.setReadOnly(false);
        }
    }
}
