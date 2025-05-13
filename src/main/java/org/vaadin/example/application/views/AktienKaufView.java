package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.services.AktienKaufService;

/**
 * @author Batuhan Güvercin
 */
@Route(value = "kaufen")
@PageTitle("Aktie kaufen")
@PermitAll
public class AktienKaufView extends AbstractSideNav {
    private final AktienKaufService aktienKaufService;

    public AktienKaufView(AktienKaufService aktienKaufService) {
        super(); // Ruft den Konstruktor der Basisklasse AbstractSideNav auf
        this.aktienKaufService = aktienKaufService;
        
        // Container für den Inhalt erstellen
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setPadding(true);
        contentLayout.setSpacing(true);
        
        // UI-Elemente aufbauen
        setupUI(contentLayout);
        
        // Content-Layout zum Hauptinhaltsbereich hinzufügen
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

        // Layout für Eingabefelder – vertikal
        VerticalLayout formLayout = new VerticalLayout(symbolField, stueckzahlField, handelsplatzField, kaufButton);
        formLayout.setSpacing(true);
        formLayout.setPadding(false);
        formLayout.setAlignItems(FlexComponent.Alignment.START);
        formLayout.setMaxWidth("600px");

        // Komponenten zum Container hinzufügen
        container.add(backButton, title, formLayout);
    }
}