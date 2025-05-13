package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

@Route("settings")
@PageTitle("Einstellungen - Finovia")
@PermitAll
public class SettingsView extends VerticalLayout {

    public SettingsView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setPadding(true);
        setSpacing(true);
        
        H1 title = new H1("Einstellungen");
        Paragraph description = new Paragraph("Verwalten Sie hier Ihre Einstellungen und Präferenzen.");
        
        Button supportButton = new Button("Support kontaktieren");
        supportButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        supportButton.addClickListener(e -> UI.getCurrent().navigate("support"));
        supportButton.addClassNames(LumoUtility.Margin.Top.MEDIUM);
        
        add(title, description, supportButton);
    }
}