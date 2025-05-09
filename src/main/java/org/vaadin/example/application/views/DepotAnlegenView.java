package org.vaadin.example.application.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.DepotService;

import java.util.UUID;

@Route(value = "depotanlegen")
@PageTitle("Depot Anlegen")
@PermitAll
public class DepotAnlegenView extends VerticalLayout {

    private final DepotService depotService;

    @Autowired
    public DepotAnlegenView(DepotService depotService) {
        this.depotService = depotService;

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setWidthFull();

        setupDepotForm();
    }

    private void setupDepotForm() {
        VerticalLayout depotForm = new VerticalLayout();
        depotForm.setMaxWidth("600px");
        depotForm.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        TextField depotName = new TextField("Depot-Name");
        depotName.setRequiredIndicatorVisible(true);
        depotName.setPrefixComponent(VaadinIcon.PIGGY_BANK.create());

        Select<String> depotTyp = new Select<>();
        depotTyp.setLabel("Depot-Typ");
        depotTyp.setItems("Aktiendepot", "ETF-Depot", "Gemischtes Depot");
        depotTyp.setRequiredIndicatorVisible(true);

        TextField iban = new TextField("IBAN");
        iban.setRequiredIndicatorVisible(true);

        Button speichernButton = new Button("Depot erstellen", VaadinIcon.CHECK.create());
        speichernButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        speichernButton.addClickListener(e -> {
            if (!depotName.isEmpty() && !depotTyp.isEmpty() && !iban.isEmpty()) {
                // Erstelle einen Beispiel-Nutzer (in einer echten Anwendung würde hier der eingeloggte Nutzer verwendet)
                Nutzer currentUser = new Nutzer("Max", "Mustermann", "max@example.com", "password", "maxmuster");
                currentUser.setId(1);

                // Erstelle ein neues Depot
                String depotId = UUID.randomUUID().toString();
                String name = depotName.getValue() + " (" + depotTyp.getValue() + ")";
                Depot neuesDepot = new Depot(depotId, name, currentUser);

                // Speichere das Depot in der Datenbank
                depotService.saveDepot(neuesDepot);

                Notification.show("Depot erfolgreich erstellt!", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                // Felder zurücksetzen
                depotName.clear();
                depotTyp.clear();
                iban.clear();

                // Zur Depot-Übersicht navigieren
                UI.getCurrent().navigate(DepotView.class);
            } else {
                Notification.show("Bitte alle Pflichtfelder ausfüllen!", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        speichernButton.addClickShortcut(Key.ENTER);

        depotForm.add(new H2("Neues Depot anlegen"), depotName, depotTyp, iban, speichernButton);
        add(depotForm);
    }
}
//TODO :Anbinden an die Datenbank
//TODO :Erstellen eines Depot-Objekts und eventuelle erweiterung der Eingabe
//TODO :Validierung der Eingaben
//TODO :Fehlerbehandlung
//TODO :Styling und Layout-Optimierung
//TODO :Testen der Funktionalität
