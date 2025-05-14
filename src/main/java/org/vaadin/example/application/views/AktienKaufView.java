package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.services.AktienKaufService;
import org.vaadin.example.application.services.KaufService;

@Route("kaufen/:symbol")
@PageTitle("Aktie kaufen")
@PermitAll
public class AktienKaufView extends AbstractSideNav {

    private final AktienKaufService aktienKaufService;
    private final KaufService kaufService;

    public AktienKaufView(AktienKaufService aktienKaufService, KaufService kaufService) {
        super();
        this.aktienKaufService = aktienKaufService;
        this.kaufService = kaufService;

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

        H3 title = new H3("Aktien Kaufen");
        title.addClassName("view-title");

        TextField symbolField = new TextField("Aktiensymbol");
        symbolField.setPlaceholder("z. B. AAPL");

        NumberField stueckzahlField = new NumberField("Stückzahl");
        stueckzahlField.setMin(1);
        stueckzahlField.setValue(1.0);

        TextField handelsplatzField = new TextField("Handelsplatz");
        handelsplatzField.setPlaceholder("z. B. NASDAQ");

        TextField kursField = new TextField("Gesamtpreis (€)");
        kursField.setReadOnly(true);
        kursField.setValue("0.00");

        // Kurs automatisch aktualisieren
        symbolField.addValueChangeListener(e -> aktualisiereKurs(symbolField, stueckzahlField, kursField));
        stueckzahlField.addValueChangeListener(e -> aktualisiereKurs(symbolField, stueckzahlField, kursField));

        Button kaufButton = new Button("Jetzt Kaufen");
        kaufButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        kaufButton.addClickListener(event -> {
            String symbol = symbolField.getValue();
            int stueckzahl = stueckzahlField.getValue().intValue();
            String handelsplatz = handelsplatzField.getValue();

            Aktie gekaufteAktie = aktienKaufService.kaufeAktie(symbol, stueckzahl, handelsplatz);

            if (gekaufteAktie != null) {
                Kauf kauf = (Kauf) gekaufteAktie.getTransaktionen().get(0);
                kaufService.addKauf(kauf);

                Notification.show("Erfolgreich gekauft: " + gekaufteAktie.getName()
                                + " (" + stueckzahl + " Stück)")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                UI.getCurrent().navigate("meine-kauefe");
            } else {
                Notification.show("Kauf fehlgeschlagen. Bitte prüfen Sie das Symbol.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        VerticalLayout formLayout = new VerticalLayout(
                symbolField, stueckzahlField, handelsplatzField, kursField, kaufButton
        );
        formLayout.setSpacing(true);
        formLayout.setPadding(false);
        formLayout.setAlignItems(FlexComponent.Alignment.START);
        formLayout.setMaxWidth("600px");

        container.add(backButton, title, formLayout);
    }

    private void aktualisiereKurs(TextField symbolField, NumberField stueckzahlField, TextField kursField) {
        String symbol = symbolField.getValue();
        Double stueckzahl = stueckzahlField.getValue();

        if (symbol != null && !symbol.isBlank() && stueckzahl != null && stueckzahl > 0) {
            try {
                double einzelkurs = aktienKaufService.getKursFürSymbol(symbol);
                double gesamtkurs = einzelkurs * stueckzahl;
                kursField.setValue(String.format("%.2f", gesamtkurs));
            } catch (Exception ex) {
                kursField.setValue("Fehler");
            }
        } else {
            kursField.setValue("0.00");
        }
    }
}