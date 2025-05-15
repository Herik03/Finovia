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
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.AktienKaufService;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.KaufService;
import org.vaadin.example.application.services.NutzerService;

import java.util.List;

@Route("kaufen/:symbol")
@PageTitle("Aktie kaufen")
@PermitAll
public class AktienKaufView extends AbstractSideNav {

    private final AktienKaufService aktienKaufService;
    private final KaufService kaufService;
    private final DepotService depotService;
    private final NutzerService nutzerService;

    public AktienKaufView(AktienKaufService aktienKaufService, KaufService kaufService, DepotService depotService, NutzerService nutzerService
    ) {
        super();
        this.aktienKaufService = aktienKaufService;
        this.kaufService = kaufService;
        this.depotService = depotService;
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

        H3 title = new H3("Aktien Kaufen");
        title.addClassName("view-title");

        TextField symbolField = new TextField("Aktiensymbol");
        symbolField.setPlaceholder("z. B. AAPL");

        NumberField stueckzahlField = new NumberField("Stückzahl");
        stueckzahlField.setMin(1);
        stueckzahlField.setValue(1.0);

        TextField handelsplatzField = new TextField("Handelsplatz");
        handelsplatzField.setPlaceholder("z. B. NASDAQ");

        Button kaufButton = new Button("Jetzt Kaufen");
        kaufButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        // ... bestehender Code ...

        // Depot-Auswahl hinzufügen
        Select<Depot> depotSelect = new Select<>();
        depotSelect.setLabel("Depot auswählen");

        // Hole den aktuellen Benutzer
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ?
                ((UserDetails)principal).getUsername() : principal.toString();

        Nutzer nutzer = nutzerService.getNutzerByUsername(username);
        if (nutzer != null) {
            List<Depot> depots = depotService.getDepotsByNutzerId(nutzer.getId());
            depotSelect.setItems(depots);
            depotSelect.setItemLabelGenerator(Depot::getName);
        }

        // Kaufbutton-Logik anpassen
        kaufButton.addClickListener(event -> {
            Depot selectedDepot = depotSelect.getValue();
            if (selectedDepot == null) {
                Notification.show("Bitte wählen Sie ein Depot aus")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            String symbol = symbolField.getValue();
            int stueckzahl = stueckzahlField.getValue().intValue();
            String handelsplatz = handelsplatzField.getValue();

            Aktie gekaufteAktie = aktienKaufService.kaufeAktie(symbol, stueckzahl, handelsplatz);

            if (gekaufteAktie != null) {
                Kauf kauf = (Kauf) gekaufteAktie.getTransaktionen().get(0);
                kaufService.addKauf(kauf);

                // Aktie zum ausgewählten Depot hinzufügen
                selectedDepot.wertpapierHinzufuegen(gekaufteAktie);
                depotService.saveDepot(selectedDepot);

                Notification.show("Erfolgreich gekauft: " + gekaufteAktie.getName()
                                + " (" + stueckzahl + " Stück) und zum Depot '"
                                + selectedDepot.getName() + "' hinzugefügt")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                UI.getCurrent().navigate("meine-kauefe");
            } else {
                Notification.show("Kauf fehlgeschlagen. Bitte prüfen Sie das Symbol.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Layout anpassen
        VerticalLayout formLayout = new VerticalLayout(
                symbolField, stueckzahlField, handelsplatzField, depotSelect, kaufButton
        );
        formLayout.setSpacing(true);
        formLayout.setPadding(false);
        formLayout.setAlignItems(FlexComponent.Alignment.START);
        formLayout.setMaxWidth("600px");

        container.add(backButton, title, formLayout);
    }
}