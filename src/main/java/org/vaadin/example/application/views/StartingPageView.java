package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("") // Diese View ist die Startseite
@PageTitle("Finovia - Depot eröffnen")
@AnonymousAllowed // Anonyme Nutzer dürfen diese Seite sehen
public class StartingPageView extends VerticalLayout {

    public StartingPageView() {
        // Setze Layout-Größe und zentriere den Inhalt
        setSizeFull();
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Titel und Text
        H1 title = new H1("Willkommen bei Finovia");
        title.getStyle().set("color", "#2b4c5f"); // Anpassung der Farbe des Titels
        title.getStyle().set("font-size", "3em");

        Paragraph info = new Paragraph("Starte jetzt mit deiner Geldanlage. Eröffne ein Depot in wenigen Schritten.");
        info.getStyle().set("font-size", "1.2em");
        info.getStyle().set("text-align", "center");
        info.getStyle().set("margin-bottom", "20px");

        // Bild einfügen (Korrekt im 'frontend/images' Ordner ablegen)
        Image backgroundImage = new Image("frontend/styles/finoviaTest.jpg", "finovia");
        backgroundImage.setWidth("100%");  // Bildbreite auf 100% setzen
        backgroundImage.setHeight("40vh"); // Bildhöhe auf 40% der Viewporthöhe setzen
        backgroundImage.getStyle().set("object-fit", "cover");  // Bild wird zugeschnitten, falls nötig
        add(backgroundImage);

        // Buttons
        Button registerButton = new Button("Depot eröffnen", event ->
                UI.getCurrent().navigate("register")
        );
        registerButton.addClassName("primary-button");
        registerButton.getStyle().set("background-color", "#5f9ea0");
        registerButton.getStyle().set("color", "white");
        registerButton.getStyle().set("border-radius", "8px");
        registerButton.getStyle().set("padding", "10px 20px");

        Button loginButton = new Button("Ich habe schon ein Konto", event ->
                UI.getCurrent().navigate("login")
        );
        loginButton.addClassName("secondary-button");
        loginButton.getStyle().set("background-color", "#f0f8ff");
        loginButton.getStyle().set("color", "#2b4c5f");
        loginButton.getStyle().set("border-radius", "8px");
        loginButton.getStyle().set("padding", "10px 20px");

        // Buttons nebeneinander platzieren
        VerticalLayout buttonsLayout = new VerticalLayout(registerButton, loginButton);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setAlignItems(Alignment.CENTER);

        // Alle Komponenten zur Layout hinzufügen
        add(title, info, buttonsLayout);
    }
}