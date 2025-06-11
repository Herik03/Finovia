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
import org.vaadin.example.application.classes.Anleihe;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.AnleiheVerkaufService;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.NutzerService;

import java.util.List;

@Route("verkaufen/anleihe/:symbol")
@PageTitle("Anleihe verkaufen")
@PermitAll
public class AnleiheVerkaufenView extends AbstractSideNav implements BeforeEnterObserver {

    private final AnleiheVerkaufService anleiheVerkaufService;
    private final DepotService depotService;
    private final SecurityService securityService;
    private final NutzerService nutzerService;

    private TextField symbolField;
    private TextField einzelkursField;
    private NumberField stueckzahlField;
    private ComboBox<Depot> depotComboBox;
    private TextField kursField;
    private final double gebuehren = 2.50;

    @Autowired
    public AnleiheVerkaufenView(AnleiheVerkaufService anleiheVerkaufService,
                                DepotService depotService,
                                SecurityService securityService,
                                NutzerService nutzerService) {
        super(securityService);
        this.anleiheVerkaufService = anleiheVerkaufService;
        this.depotService = depotService;
        this.securityService = securityService;
        this.nutzerService = nutzerService;

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setPadding(true);
        contentLayout.setSpacing(true);

        setupUI(contentLayout);
        addToMainContent(contentLayout);
    }

    private void setupUI(VerticalLayout container) {
        Button backButton = new Button("Zurück zur Übersicht", VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        backButton.addClickListener(e -> UI.getCurrent().navigate("depot-details")); // z.B. Depot-Übersicht

        H3 title = new H3("Anleihe verkaufen");
        title.addClassName("view-title");

        symbolField = new TextField("Anleihe-Symbol");
        symbolField.setPlaceholder("z. B. BND009");
        symbolField.setWidthFull();

        einzelkursField = new TextField("Aktueller Kurs (€/Anleihe)");
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

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("400px");
        formLayout.add(symbolField, einzelkursField, stueckzahlField, depotComboBox, gebuehrenField, kursField);

        Button verkaufButton = new Button("Jetzt Verkaufen");
        verkaufButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        verkaufButton.getStyle().set("margin-top", "20px");
        verkaufButton.setWidthFull();

        // Kurs aktualisieren wenn Symbol oder Stückzahl geändert werden
        symbolField.addValueChangeListener(e -> {
            aktualisiereEinzelkurs();
            aktualisiereKurs();
        });
        stueckzahlField.addValueChangeListener(e -> aktualisiereKurs());

        verkaufButton.addClickListener(event -> {
            String symbol = symbolField.getValue();
            Double stueckzahlDouble = stueckzahlField.getValue();
            Depot depot = depotComboBox.getValue();

            if (symbol == null || symbol.isBlank()) {
                Notification.show("Bitte Symbol angeben.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            if (depot == null) {
                Notification.show("Bitte ein Depot auswählen.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            if (stueckzahlDouble == null || stueckzahlDouble < 1) {
                Notification.show("Bitte eine gültige Stückzahl angeben.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            int stueckzahl = stueckzahlDouble.intValue();

            Anleihe verkaufteAnleihe = anleiheVerkaufService.verkaufeAnleihe(symbol, stueckzahl, depot, nutzerService.getAngemeldeterNutzer());
            if (verkaufteAnleihe != null) {
                Notification.show("Anleihe erfolgreich verkauft! (" + stueckzahl + " Stück)")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                // Navigation zu "Meine Verkäufe"
                UI.getCurrent().navigate("meineverkaeufe");
            } else {
                Notification.show("Verkauf fehlgeschlagen. Bitte Symbol und Stückzahl prüfen.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        VerticalLayout centerLayout = new VerticalLayout(title, formLayout, verkaufButton);
        centerLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        centerLayout.setSpacing(true);
        centerLayout.setPadding(true);
        centerLayout.setWidthFull();
        centerLayout.getStyle().set("max-width", "600px");
        centerLayout.getStyle().set("margin", "0 auto");

        container.add(backButton, centerLayout);
    }

    private void aktualisiereEinzelkurs() {
        String symbol = symbolField.getValue();
        if (symbol != null && !symbol.isBlank()) {
            try {
                double kurs = anleiheVerkaufService.getKursFürSymbol(symbol);
                einzelkursField.setValue(String.format("%.2f", kurs));
            } catch (Exception e) {
                einzelkursField.setValue("Fehler");
            }
        } else {
            einzelkursField.setValue("0.00");
        }
    }

    private void aktualisiereKurs() {
        String symbol = symbolField.getValue();
        Double stueckzahl = stueckzahlField.getValue();

        if (symbol != null && !symbol.isBlank() && stueckzahl != null && stueckzahl > 0) {
            try {
                double kurs = anleiheVerkaufService.getKursFürSymbol(symbol);
                double gesamt = kurs * stueckzahl + gebuehren;
                kursField.setValue(String.format("%.2f", gesamt));
            } catch (Exception ex) {
                kursField.setValue("Fehler");
            }
        } else {
            kursField.setValue("0.00");
        }
    }

    private Long getAktuelleNutzerId() {
        UserDetails userDetails = securityService.getAuthenticatedUser();
        if (userDetails == null) {
            return null;
        }
        Nutzer nutzer = nutzerService.findByUsername(userDetails.getUsername());
        return (nutzer != null) ? nutzer.getId() : null;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String symbol = event.getRouteParameters().get("symbol").orElse("");
        if (!symbol.isBlank()) {
            symbolField.setValue(symbol);
            symbolField.setReadOnly(true); // Symbol aus URL -> nicht editierbar
            aktualisiereEinzelkurs();
            aktualisiereKurs();
        } else {
            symbolField.setReadOnly(false);
        }
    }
}
