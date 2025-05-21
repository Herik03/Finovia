package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@PageTitle("Finovia - Depot eröffnen")
@AnonymousAllowed
public class StartingPageView extends VerticalLayout {

    public StartingPageView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        add(createHeader());
        add(createHeroSection());
        add(createFeatureSection());
        add(createCTASection());
        add(createFooter()); // Füge den Footer hinzu
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setPadding(true);
        header.setAlignItems(Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.getStyle().set("background-color", "#eef4fb");
        header.getStyle().set("flex-wrap", "wrap");

        Image logo = new Image("images/finovia-logo.png", "Finovia Logo");
        logo.setWidth("50px");

        HorizontalLayout nav = new HorizontalLayout();
        nav.setSpacing(true);
        nav.getStyle().set("flex-wrap", "wrap");;

        Anchor startseite = new Anchor("#", "Startseite");
        Anchor funktionen = new Anchor("#funktionen", "Funktionen");
        Anchor preise = new Anchor("#preise", "Preise");
        Anchor hilfe = new Anchor("#hilfe", "Hilfe");

        Button loginButton = new Button("Login", e -> UI.getCurrent().navigate("login"));
        loginButton.getStyle()
                .set("background-color", "white")
                .set("color", "#2b4c5f")
                .set("border", "1px solid #2b4c5f")
                .set("border-radius", "6px")
                .set("padding", "6px 12px");

        nav.add(startseite, funktionen, preise, hilfe, loginButton);
        header.add(logo, nav);
        return header;
    }

    private FlexLayout createHeroSection() {
        FlexLayout hero = new FlexLayout();
        hero.setWidthFull();
        hero.getStyle().set("flex-wrap", "wrap");;
        hero.setJustifyContentMode(JustifyContentMode.CENTER);
        hero.setAlignItems(Alignment.CENTER);
        hero.getStyle().set("padding", "40px 20px");
        hero.getStyle().set("background-color", "#ffffff");

        // Textbereich
        VerticalLayout textBlock = new VerticalLayout();
        textBlock.setSpacing(true);
        textBlock.setPadding(false);
        textBlock.setWidthFull();
        textBlock.setMaxWidth("500px");

        H1 title = new H1("Starte dein Depot mit Vertrauen.");
        title.getStyle().set("font-size", "2em");

        Paragraph subtitle = new Paragraph("Investieren leicht gemacht – für Anfänger & Fortgeschrittene");
        subtitle.getStyle().set("font-size", "1.1em");

        Button primaryButton = new Button("Depot eröffnen", e -> UI.getCurrent().navigate("register"));
        primaryButton.getStyle().set("background-color", "#3b61e2")
                .set("color", "white")
                .set("border-radius", "8px")
                .set("padding", "10px 20px");

        Button secondaryButton = new Button("Mehr erfahren");
        secondaryButton.getStyle().set("background-color", "#e0e0e0")
                .set("color", "#000")
                .set("border-radius", "8px")
                .set("padding", "10px 20px");

        HorizontalLayout buttons = new HorizontalLayout(primaryButton, secondaryButton);
        buttons.setSpacing(true);
        buttons.getStyle().set("flex-wrap", "wrap");;

        textBlock.add(title, subtitle, buttons);

        // Bildbereich
        Image illustration = new Image("images/illustration.png", "Illustration");
        illustration.setWidth("300px");
        illustration.getStyle().set("max-width", "100%");

        hero.add(textBlock, illustration);
        return hero;
    }

    private VerticalLayout createFeatureSection() {
        VerticalLayout section = new VerticalLayout();
        section.setId("funktionen");
        section.setWidthFull();
        section.setAlignItems(Alignment.CENTER);
        section.setSpacing(true);
        section.setPadding(true);
        section.getStyle().set("background-color", "#ffffff");

        H2 headline = new H2("Funktionen im Überblick");

        // Features Layout
        FlexLayout features = new FlexLayout();
        features.getStyle().set("flex-wrap", "wrap");;
        features.setJustifyContentMode(JustifyContentMode.CENTER);
        features.getStyle().set("gap", "30px");;

        features.add(
                createFeature("Einfach Depot eröffnen", "Schnelle und unkomplizierte Kontoeröffnung", "icon-depot.png"),
                createFeature("Aktien & ETFs vergleichen", "Details, Informationen und Vergleichsmöglichkeiten", "icon-vergleich.png"),
                createFeature("Echtzeit-Daten & Analysen", "Marktinformationen und Auswertungen in Echtzeit", "icon-analyse.png")
        );

        section.add(headline, features);
        return section;
    }

    private VerticalLayout createFeature(String title, String desc, String iconFile) {
        Image icon = new Image("images/" + iconFile, title);
        icon.setHeight("40px");

        Paragraph featureTitle = new Paragraph(title);
        featureTitle.getStyle().set("font-weight", "bold").set("margin", "0");

        Paragraph featureDesc = new Paragraph(desc);
        featureDesc.getStyle().set("font-size", "0.9em").set("text-align", "center").set("margin", "0");

        VerticalLayout layout = new VerticalLayout(icon, featureTitle, featureDesc);
        layout.setAlignItems(Alignment.CENTER);
        layout.setSpacing(false);
        layout.setWidth("220px");

        return layout;
    }

    private VerticalLayout createCTASection() {
        VerticalLayout cta = new VerticalLayout();
        cta.setAlignItems(Alignment.CENTER);
        cta.setSpacing(true);
        cta.setPadding(true);
        cta.getStyle().set("background-color", "#f5f5f5");

        H2 text = new H2("Jetzt kostenlos starten");

        Button button = new Button("Jetzt kostenlos starten", e -> UI.getCurrent().navigate("register"));
        button.getStyle().set("background-color", "#3b61e2")
                .set("color", "white")
                .set("border-radius", "8px")
                .set("padding", "12px 30px")
                .set("font-size", "1.1em");

        cta.add(text, button);
        return cta;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.setWidthFull();
        footer.getStyle().set("background-color", "#2b4c5f")
                .set("color", "white")
                .set("padding", "20px");

        HorizontalLayout container = new HorizontalLayout();
        container.setWidthFull();
        container.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        container.setAlignItems(Alignment.CENTER);
        container.getStyle().set("flex-wrap", "wrap");

        Paragraph copyright = new Paragraph("© 2025 Finovia. Alle Rechte vorbehalten.");
        copyright.getStyle().set("font-size", "0.9em");

        HorizontalLayout links = new HorizontalLayout();
        links.setSpacing(true);

        Anchor impressum = new Anchor("#impressum", "Impressum");
        impressum.getStyle().set("color", "white").set("font-size", "0.9em");

        Anchor datenschutz = new Anchor("#datenschutz", "Datenschutz");
        datenschutz.getStyle().set("color", "white").set("font-size", "0.9em");

        links.add(impressum, datenschutz);

        container.add(copyright, links);
        footer.add(container);

        return footer;
    }
}