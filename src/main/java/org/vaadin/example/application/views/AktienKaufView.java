package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import jakarta.annotation.security.PermitAll;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.services.AktienKaufService;


import static org.apache.el.lang.ELArithmetic.add;

//Batuhan Güvercin
@PageTitle("Aktie kaufen")
@CssImport("./.styles/stock-view.css")
@PermitAll
public class AktienKaufView extends VerticalLayout implements RouterLayout {
    private final AktienKaufService aktienKaufService;

    public AktienKaufView(AktienKaufService aktienKaufService) {
        this.aktienKaufService = aktienKaufService;
        setupUI();
    }

    private void setupUI() {
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
        kaufButton.addClickListener(event -> {
            String symbol = symbolField.getValue();
            int stueckzahl = stueckzahlField.getValue().intValue();
            String handelsplatz = handelsplatzField.getValue();

            Aktie gekaufteAktie = aktienKaufService.kaufeAktie(symbol, stueckzahl, handelsplatz);

            if (gekaufteAktie != null) {
                Notification.show("Erfolgreich gekauft: " + gekaufteAktie.getName()
                        + " (" + stueckzahl + " Stück")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } else {
                Notification.show("Kauf fehlgeschlagen. Bitte prüfen Sie das Symbol.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        HorizontalLayout inputLayout = new HorizontalLayout(symbolField, stueckzahlField, handelsplatzField, kaufButton);
        inputLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        add(title, inputLayout);
    }
}
