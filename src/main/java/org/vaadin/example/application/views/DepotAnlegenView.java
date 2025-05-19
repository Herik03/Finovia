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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.DepotService;

import java.util.UUID;

@Route(value = "depotanlegen")
@PageTitle("Depot Anlegen")
@PermitAll
public class DepotAnlegenView extends AbstractSideNav {

    private final DepotService depotService;

    @Autowired
    public DepotAnlegenView(DepotService depotService) {
        super();
        this.depotService = depotService;

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        contentLayout.setWidthFull();

        setupDepotForm(contentLayout);
        
        // Content-Layout zum Hauptinhalt hinzufügen
        addToMainContent(contentLayout);
    }

    private void setupDepotForm(VerticalLayout contentLayout) {
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
                Nutzer currentUser = getAktuellenNutzer();

                // Erstelle ein neues Depot
                String name = depotName.getValue() + " (" + depotTyp.getValue() + ")";
                Depot neuesDepot = new Depot(name, currentUser);
                assert currentUser != null;
                currentUser.depotHinzufuegen(neuesDepot);

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
        contentLayout.add(depotForm);
    }

    private Nutzer getAktuellenNutzer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Nutzer userDetails) {
            return userDetails;
        }
        return null;
    }
}
//TODO :Anbinden an die Datenbank
//TODO :Erstellen eines Depot-Objekts und eventuelle erweiterung der Eingabe
//TODO :Validierung der Eingaben
//TODO :Fehlerbehandlung
//TODO :Styling und Layout-Optimierung
//TODO :Testen der Funktionalität