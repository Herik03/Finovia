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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.AktienKaufService;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.NutzerService;

import java.util.Arrays;
import java.util.List;

@Route("kaufen/:symbol")
@PageTitle("Aktie kaufen")
@PermitAll
public class AktienKaufView extends AbstractSideNav {

    private final AktienKaufService aktienKaufService;
    private final DepotService depotService;
    private final SecurityService securityService;
    private final NutzerService nutzerService;
    private final List<String> handelsplaetze = Arrays.asList("Xetra", "Frankfurt", "Tradegate");

    @Autowired
    public AktienKaufView(AktienKaufService aktienKaufService,
                          DepotService depotService,
                          SecurityService securityService,
                          NutzerService nutzerService) {
        super();
        this.aktienKaufService = aktienKaufService;
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
        RouterLink routerLink = new RouterLink("", MainView.class);
        routerLink.add(backButton);

        VerticalLayout topLeftLayout = new VerticalLayout(routerLink);
        topLeftLayout.setPadding(false);
        topLeftLayout.setSpacing(false);
        topLeftLayout.setWidthFull();
        topLeftLayout.setAlignItems(FlexComponent.Alignment.START);

        H3 title = new H3("Aktien Kaufen");
        title.addClassName("view-title");

        TextField symbolField = new TextField("Aktiensymbol");
        symbolField.setPlaceholder("z. B. AAPL");
        symbolField.setWidthFull();

        TextField einzelkursField = new TextField("Aktueller Kurs (€/Aktie)");
        einzelkursField.setReadOnly(true);
        einzelkursField.setValue("0.00");
        einzelkursField.setWidthFull();

        NumberField stueckzahlField = new NumberField("Stückzahl");
        stueckzahlField.setMin(1);
        stueckzahlField.setValue(1.0);
        stueckzahlField.setWidthFull();

        ComboBox<String> handelsplatzAuswahl = new ComboBox<>("Handelsplatz auswählen");
        handelsplatzAuswahl.setItems(handelsplaetze);
        handelsplatzAuswahl.setPlaceholder("Handelsplatz wählen");
        handelsplatzAuswahl.setRequired(true);
        handelsplatzAuswahl.setWidthFull();

        NumberField gebuehrenField = new NumberField("Gebühren (EUR)");
        gebuehrenField.setValue(2.50);
        gebuehrenField.setReadOnly(true);
        gebuehrenField.setWidthFull();

        TextField kursField = new TextField("Gesamtpreis (€)");
        kursField.setReadOnly(true);
        kursField.setValue("0.00");
        kursField.setWidthFull();

        ComboBox<Depot> depotComboBox = new ComboBox<>("Depot auswählen");
        List<Depot> depots = depotService.getDepotsByNutzerId(getAktuelleNutzerId());
        depotComboBox.setItems(depots);
        depotComboBox.setItemLabelGenerator(Depot::getName);
        depotComboBox.setWidthFull();

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("400px");
        formLayout.add(symbolField, einzelkursField, stueckzahlField, handelsplatzAuswahl, gebuehrenField, kursField, depotComboBox);

        Button kaufButton = new Button("Jetzt Kaufen");
        kaufButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        kaufButton.getStyle().set("margin-top", "20px");
        kaufButton.setWidthFull();

        symbolField.addValueChangeListener(e -> {
            aktualisiereEinzelkurs(symbolField, einzelkursField);
            aktualisiereKurs(symbolField, stueckzahlField, kursField);
        });
        stueckzahlField.addValueChangeListener(e -> aktualisiereKurs(symbolField, stueckzahlField, kursField));

        kaufButton.addClickListener(event -> {
            String symbol = symbolField.getValue();
            Double stueckzahlDouble = stueckzahlField.getValue();
            if (stueckzahlDouble == null || stueckzahlDouble < 1) {
                Notification.show("Bitte eine gültige Stückzahl angeben.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            int stueckzahl = stueckzahlDouble.intValue();

            String handelsplatz = handelsplatzAuswahl.getValue();
            Depot depot = depotComboBox.getValue();

            if (symbol == null || symbol.isBlank() || depot == null || handelsplatz == null || handelsplatz.isBlank()) {
                Notification.show("Bitte Symbol, Depot und Handelsplatz angeben.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            Aktie gekaufteAktie = aktienKaufService.kaufeAktie(symbol, stueckzahl, handelsplatz, depot);

            if (gekaufteAktie != null) {
                Notification.show("Erfolgreich gekauft: " + gekaufteAktie.getName() + " (" + stueckzahl + " Stück)")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                UI.getCurrent().navigate("transaktionen");
            } else {
                Notification.show("Kauf fehlgeschlagen. Bitte prüfen Sie das Symbol.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        VerticalLayout centerLayout = new VerticalLayout(title, formLayout, kaufButton);
        centerLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        centerLayout.setSpacing(true);
        centerLayout.setPadding(true);
        centerLayout.setWidthFull();
        centerLayout.getStyle().set("max-width", "600px");
        centerLayout.getStyle().set("margin", "0 auto");

        container.add(topLeftLayout, centerLayout);
    }

    private void aktualisiereEinzelkurs(TextField symbolField, TextField einzelkursField) {
        String symbol = symbolField.getValue();
        if (symbol != null && !symbol.isBlank()) {
            try {
                double einzelkurs = aktienKaufService.getKursFürSymbol(symbol);
                einzelkursField.setValue(String.format("%.2f", einzelkurs));
            } catch (Exception e) {
                einzelkursField.setValue("Fehler");
            }
        } else {
            einzelkursField.setValue("0.00");
        }
    }

    private void aktualisiereKurs(TextField symbolField, NumberField stueckzahlField, TextField kursField) {
        String symbol = symbolField.getValue();
        Double stueckzahl = stueckzahlField.getValue();

        if (symbol != null && !symbol.isBlank() && stueckzahl != null && stueckzahl > 0) {
            try {
                double einzelkurs = aktienKaufService.getKursFürSymbol(symbol);
                double gesamtkurs = einzelkurs * stueckzahl + 2.50; // Gebühren addiert
                kursField.setValue(String.format("%.2f", gesamtkurs));
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
        Nutzer nutzer = nutzerService.findByUsername(userDetails.getUsername()); // korrekt: Nutzer zurückgeben
        return (nutzer != null) ? nutzer.getId() : null;
    }
}
