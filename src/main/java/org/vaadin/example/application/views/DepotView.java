package org.vaadin.example.application.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "depot")
@PageTitle("Depot")
@RolesAllowed("user")
public class DepotView extends HorizontalLayout {
    HorizontalLayout depotForm;
    public DepotView(){
        depotForm=new HorizontalLayout();
        setupDepotForm();
        add(depotForm);
    }

    private void setupDepotForm() {
        depotForm.setMaxWidth("600px");


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
                Notification.show("Depot erfolgreich erstellt!", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                depotName.clear();
                depotTyp.clear();
                iban.clear();
            } else {
                Notification.show("Bitte alle Pflichtfelder ausfüllen!", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        speichernButton.addClickShortcut(Key.ENTER);

        depotForm.add(new H2("Neues Depot anlegen"), depotName, depotTyp, iban, speichernButton);


    }


}
