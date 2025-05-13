package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

@Route("settings")
@PageTitle("Einstellungen - Finovia")
@PermitAll
public class SettingsView extends AbstractSideNav {

    public SettingsView() {
        super(); // Ruft den Konstruktor der Basisklasse auf
        
        // Erstelle das Inhalts-Layout für die Einstellungen
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        contentLayout.setPadding(true);
        contentLayout.setSpacing(true);
        
        H1 title = new H1("Einstellungen");
        Paragraph description = new Paragraph("Verwalten Sie hier Ihre Einstellungen und Präferenzen.");
        
        Button supportButton = new Button("Support kontaktieren");
        supportButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        supportButton.addClickListener(e -> UI.getCurrent().navigate("support"));
        supportButton.addClassNames(LumoUtility.Margin.Top.MEDIUM);
        
        // Komponenten zum Content-Layout hinzufügen
        contentLayout.add(title, description, supportButton);
        
        // Content-Layout zum Hauptinhaltsbereich hinzufügen
        addToMainContent(contentLayout);
    }
}