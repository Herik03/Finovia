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
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.AktienVerkaufService;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.NutzerService;

import java.util.List;

/**
 * View zum Verkaufen von Aktien.
 * Ermöglicht dem Nutzer, eine Aktie zu verkaufen, indem er das Symbol, den aktuellen Kurs,
 * die Stückzahl und das Depot auswählt.
 */
@Route("verkaufen/aktie/:symbol")
@PageTitle("Aktie verkaufen")
@PermitAll
public class AktieVerkaufenView extends AbstractSideNav implements BeforeEnterObserver {

    private final AktienVerkaufService aktienVerkaufService;
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
     * Konstruktor der View, der alle benötigten Services injiziert.
     *
     * @param aktienVerkaufService Service zum Verarbeiten von Aktienverkäufen
     * @param depotService         Service zur Verwaltung von Depots
     * @param securityService      Service für Sicherheits- und Authentifizierungsfunktionen
     * @param nutzerService        Service zur Nutzerverwaltung
     */
    @Autowired
    public AktieVerkaufenView(AktienVerkaufService aktienVerkaufService,
                              DepotService depotService,
                              SecurityService securityService,
                              NutzerService nutzerService) {
        super(securityService);
        this.aktienVerkaufService = aktienVerkaufService;
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
     * Erstellt die Benutzeroberfläche für die Verkauf-Ansicht.
     * Enthält Eingabefelder für Aktiensymbol, aktuellen Kurs, Stückzahl und Depot.
     * Fügt auch einen Button zum Auslösen des Verkaufs hinzu.
     *
     * @param container Das Layout, in dem die UI-Komponenten platziert werden
     */
    private void setupUI(VerticalLayout container) {
        // Back Button mit RouterLink auf MainView
        RouterLink backLink = new RouterLink("Zurück zur Übersicht", MainView.class);
        Button backButton = new Button(VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        backLink.add(backButton);

        // Layout für den oberen linken Bereich
        VerticalLayout topLeftLayout = new VerticalLayout(backLink);
        topLeftLayout.setPadding(false);
        topLeftLayout.setSpacing(false);
        topLeftLayout.setWidthFull();
        topLeftLayout.setAlignItems(FlexComponent.Alignment.START);

        // Titel und Eingabefelder für den Verkauf
        H3 title = new H3("Aktien Verkaufen");
        title.addClassName("view-title");

        // Eingabefelder für den Verkauf
        symbolField = new TextField("Aktiensymbol");
        symbolField.setPlaceholder("z. B. AAPL");
        symbolField.setWidthFull();

        // Aktueller Kurs der Aktie
        einzelkursField = new TextField("Aktueller Kurs (€/Aktie)");
        einzelkursField.setReadOnly(true);
        einzelkursField.setValue("0.00");
        einzelkursField.setWidthFull();

        // Stückzahl-Feld für die Anzahl der zu verkaufenden Aktien
        stueckzahlField = new NumberField("Stückzahl");
        stueckzahlField.setMin(1);
        stueckzahlField.setValue(1.0);
        stueckzahlField.setWidthFull();

        // Depot-Auswahl für den Verkauf
        depotComboBox = new ComboBox<>("Depot auswählen");
        List<Depot> depots = depotService.getDepotsByNutzerId(getAktuelleNutzerId());
        depotComboBox.setItems(depots);
        depotComboBox.setItemLabelGenerator(Depot::getName);
        depotComboBox.setWidthFull();

        // Gebühren und Gesamtpreis-Felder
        TextField gebuehrenField = new TextField("Gebühren (EUR)");
        gebuehrenField.setValue(String.format("%.2f", gebuehren));
        gebuehrenField.setReadOnly(true);
        gebuehrenField.setWidthFull();

        // Gesamtpreis-Feld, das den Gesamtpreis der Transaktion anzeigt
        kursField = new TextField("Gesamtpreis (€)");
        kursField.setReadOnly(true);
        kursField.setValue("0.00");
        kursField.setWidthFull();

        // Layout für die Formulareingaben
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("400px");
        formLayout.add(symbolField, einzelkursField, stueckzahlField, depotComboBox, gebuehrenField, kursField);

        // Button zum Auslösen des Verkaufs
        Button verkaufButton = new Button("Jetzt Verkaufen");
        verkaufButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        verkaufButton.getStyle().set("margin-top", "20px");
        verkaufButton.setWidthFull();

        // Listener für Kurs-Aktualisierung bei Änderung der Felder
        symbolField.addValueChangeListener(e -> {
            aktualisiereEinzelkurs();
            aktualisiereKurs();
        });
        stueckzahlField.addValueChangeListener(e -> aktualisiereKurs());

        // Listener für den Verkauf-Button
        verkaufButton.addClickListener(event -> {
            String symbol = symbolField.getValue();
            Double stueckzahlDouble = stueckzahlField.getValue();
            Depot depot = depotComboBox.getValue();

            if (symbol == null || symbol.isBlank() || depot == null) {
                Notification.show("Bitte Symbol und Depot angeben.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            if (stueckzahlDouble == null || stueckzahlDouble < 1) {
                Notification.show("Bitte eine gültige Stückzahl angeben.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            int stueckzahl = stueckzahlDouble.intValue();

            Aktie verkaufteAktie = aktienVerkaufService.verkaufeAktie(symbol, stueckzahl, depot);

            if (verkaufteAktie != null) {
                Notification.show("Erfolgreich verkauft: " + verkaufteAktie.getName() + " (" + stueckzahl + " Stück)")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().navigate("transaktionen");
            } else {
                Notification.show("Verkauf fehlgeschlagen. Bitte prüfen Sie das Symbol und Ihre Stückzahl.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Layout für die zentrale Ansicht
        VerticalLayout centerLayout = new VerticalLayout(title, formLayout, verkaufButton);
        centerLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        centerLayout.setSpacing(true);
        centerLayout.setPadding(true);
        centerLayout.setWidthFull();
        centerLayout.getStyle().set("max-width", "600px");
        centerLayout.getStyle().set("margin", "0 auto");

        container.add(topLeftLayout, centerLayout);
    }

    /**
     * Aktualisiert den Einzelkurs der Aktie basierend auf dem eingegebenen Symbol.
     * Wenn das Symbol leer ist, wird der Kurs auf "0.00" gesetzt.
     */
    private void aktualisiereEinzelkurs() {
        String symbol = symbolField.getValue();
        if (symbol != null && !symbol.isBlank()) {
            try {
                double einzelkurs = aktienVerkaufService.getKursFürSymbol(symbol);
                einzelkursField.setValue(String.format("%.2f", einzelkurs));
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
                double einzelkurs = aktienVerkaufService.getKursFürSymbol(symbol);
                double gesamtkurs = einzelkurs * stueckzahl + gebuehren;
                kursField.setValue(String.format("%.2f", gesamtkurs));
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
     * Hier wird das Symbol aus den Routenparametern geladen und die Felder entsprechend aktualisiert.
     *
     * @param event Das Ereignis, das vor dem Anzeigen der View ausgelöst wird
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String symbol = event.getRouteParameters().get("symbol").orElse("");
        if (!symbol.isBlank()) {
            symbolField.setValue(symbol);
            aktualisiereEinzelkurs();
            aktualisiereKurs();
        }
    }
}
