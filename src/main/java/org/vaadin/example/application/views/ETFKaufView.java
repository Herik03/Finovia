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
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.ETF;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.ETFKaufService;
import org.vaadin.example.application.services.NutzerService;

import java.util.Arrays;
import java.util.List;

@Route("kaufen/etf/:symbol")
@PageTitle("ETF kaufen")
@PermitAll
public class ETFKaufView extends AbstractSideNav implements BeforeEnterObserver {
    private final ETFKaufService etfKaufService;
    private final DepotService depotService;
    private final SecurityService securityService;
    private final NutzerService nutzerService;
    private final List<String> handelsplaetze = Arrays.asList("NYSE");

    private TextField symbolField;
    private TextField einzelkursField;
    private NumberField stueckzahlField;
    private ComboBox<String> handelsplatzAuswahl;
    private TextField kursField;
    private String initialSymbol;

    @Autowired
    public ETFKaufView(ETFKaufService etfKaufService, DepotService depotService,
                       SecurityService securityService, NutzerService nutzerService) {
        super();
        this.etfKaufService = etfKaufService;
        this.depotService = depotService;
        this.securityService = securityService;
        this.nutzerService = nutzerService;

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setPadding(true);
        contentLayout.setSpacing(true);
        contentLayout.setAlignItems(Alignment.CENTER);

        initUI(contentLayout);
        addToMainContent(contentLayout);
    }

    /**
     * Erstellt und konfiguriert alle UI-Komponenten der Kaufansicht.
     *
     * @param container Der übergeordnete Layout-Container
     */
    private void initUI(VerticalLayout container) {
        // Zurück-Button
        Button backButton = new Button("Zurück zur Übersicht", VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        RouterLink routerLink = new RouterLink("", MainView.class);
        routerLink.add(backButton);

        VerticalLayout topLeftLayout = new VerticalLayout(routerLink);
        topLeftLayout.setPadding(false);
        topLeftLayout.setSpacing(false);
        topLeftLayout.setWidthFull();
        topLeftLayout.setAlignItems(FlexComponent.Alignment.START);

        // Titel
        H3 title = new H3("Anleihe kaufen");
        title.addClassName("view-title");

        // Formularfelder
        symbolField = new TextField("Symbol");
        symbolField.setReadOnly(true);
        symbolField.setWidthFull();

        einzelkursField = new TextField("Kurs (€)");
        einzelkursField.setReadOnly(true);
        einzelkursField.setValue("0.00");
        einzelkursField.setWidthFull();

        stueckzahlField = new NumberField("Stückzahl");
        stueckzahlField.setMin(1);
        stueckzahlField.setValue(1.0);
        stueckzahlField.setWidthFull();

        handelsplatzAuswahl = new ComboBox<>("Handelsplatz");
        handelsplatzAuswahl.setItems(handelsplaetze);
        handelsplatzAuswahl.setValue("NYSE");
        handelsplatzAuswahl.setWidthFull();

        kursField = new TextField("Gesamtpreis (€)");
        kursField.setReadOnly(true);
        kursField.setValue("0.00");
        kursField.setWidthFull();

        ComboBox<Depot> depotComboBox = new ComboBox<>("Depot auswählen");
        List<Depot> depots = depotService.getDepotsByNutzerId(getAktuelleNutzerId());
        depotComboBox.setItems(depots);
        depotComboBox.setItemLabelGenerator(Depot::getName);
        depotComboBox.setWidthFull();

        // Formularlayout
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("400px");
        formLayout.getStyle().set("margin", "0 auto");
        formLayout.add(symbolField, einzelkursField, stueckzahlField, handelsplatzAuswahl, kursField, depotComboBox);

        // Kauf-Button
        Button kaufButton = new Button("Jetzt Kaufen");
        kaufButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        kaufButton.setWidthFull();

        // Event-Listener
        symbolField.addValueChangeListener(e -> aktualisiereEinzelkurs());
        stueckzahlField.addValueChangeListener(e -> aktualisiereKurs());

        kaufButton.addClickListener(event -> {
            String symbol = symbolField.getValue();
            int stueckzahl = stueckzahlField.getValue().intValue();
            String handelsplatz = handelsplatzAuswahl.getValue();
            Depot depot = depotComboBox.getValue();

            if (symbol.isBlank() || depot == null || handelsplatz == null) {
                Notification.show("Bitte alle Felder ausfüllen.", 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            ETF etf = etfKaufService.kaufeETF(symbol, stueckzahl, handelsplatz, depot);
            if (etf != null) {
                Notification.show("Anleihe erfolgreich gekauft!", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().navigate("transaktionen");
            } else {
                Notification.show("Fehler beim Kauf. Symbol prüfen.", 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Layoutstruktur
        VerticalLayout centerLayout = new VerticalLayout(title, formLayout, kaufButton);
        centerLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        centerLayout.setSpacing(true);
        centerLayout.setPadding(true);
        centerLayout.setWidthFull();
        centerLayout.getStyle().set("max-width", "600px");
        centerLayout.getStyle().set("margin", "0 auto");
        container.add(topLeftLayout, centerLayout);

        if (initialSymbol != null && !initialSymbol.isBlank()) {
            symbolField.setValue(initialSymbol);
            aktualisiereEinzelkurs();
        }
    }

    /**
     * Holt den aktuellen Kurs für das eingegebene Symbol und aktualisiert das Kursfeld.
     */
    private void aktualisiereEinzelkurs() {
        String symbol = symbolField.getValue(); //Wichtig, da man rein theoretisch auch ein anderes Symbol eingeben könnte
        try {
            double kurs = etfKaufService.getKursFuerSymbol(symbol);
            einzelkursField.setValue(String.format("%.2f", kurs));
            aktualisiereKurs();
        } catch (Exception e) {
            einzelkursField.setValue("Fehler");
        }
    }

    /**
     * Aktualisiert den Gesamtpreis basierend auf Kurs und Stückzahl.
     * Es wird ein fixer Zuschlag (z. B. Gebühren) von 2,50 € berücksichtigt.
     */
    private void aktualisiereKurs() {
        String value = einzelkursField.getValue();
        if (value != null && !value.equals("Fehler")) {
            double einzelkurs = Double.parseDouble(value.replace(",", "."));
            int stueckzahl = stueckzahlField.getValue().intValue();
            double gesamt = einzelkurs * stueckzahl + 2.50;
            kursField.setValue(String.format("%.2f", gesamt));
        }
    }

    /**
     * Liefert die ID des aktuell angemeldeten Nutzers.
     *
     * @return Nutzer-ID oder {@code null}, wenn kein Nutzer gefunden wurde
     */
    private Long getAktuelleNutzerId() {
        UserDetails userDetails = securityService.getAuthenticatedUser();
        Nutzer nutzer = nutzerService.findByUsername(userDetails.getUsername());
        return (nutzer != null) ? nutzer.getId() : null;
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String symbol = event.getRouteParameters()
                .get("symbol")
                .orElse("");

        initialSymbol = symbol;

        if (!symbol.isBlank()) {
            symbolField.setValue(symbol);
            aktualisiereEinzelkurs();
        }
    }
}
