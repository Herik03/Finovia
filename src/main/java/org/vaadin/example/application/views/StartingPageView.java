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
        add(createFooter());
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setPadding(true);
        header.setAlignItems(Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.getStyle().set("background-color", "var(--lumo-shade-5pct)");
        header.getStyle().set("flex-wrap", "wrap");

        Image logo = new Image("icons/logo.png", "Finovia Logo");
        logo.setWidth("50px");

        HorizontalLayout nav = new HorizontalLayout();
        nav.setSpacing(true);
        nav.getStyle().set("flex-wrap", "wrap");


        Anchor startseite = new Anchor("#", "Startseite");
        Anchor funktionen = new Anchor("#funktionen", "Funktionen");

        Anchor hilfe = new Anchor("#hilfe", "Hilfe");

        Button loginButton = new Button("Login", e -> UI.getCurrent().navigate("login"));
        loginButton.getStyle()
                .set("background-color", "white")
                .set("color", "var(--lumo-primary-text-color)")
                .set("border", "1px solid var(--lumo-shade-20pct)")
                .set("border-radius", "var(--lumo-border-radius)")
                .set("padding", "6px 12px");

        nav.add(startseite, funktionen,  hilfe, loginButton);
        header.add(logo, nav);
        return header;
    }

    private FlexLayout createHeroSection() {
        FlexLayout hero = new FlexLayout();
        hero.setWidthFull();
        hero.getStyle().set("flex-wrap", "wrap");
        hero.setJustifyContentMode(JustifyContentMode.CENTER);
        hero.setAlignItems(Alignment.CENTER);
        hero.getStyle().set("padding", "40px 20px");
        hero.getStyle().set("background-color", "var(--lumo-base-color)");

        VerticalLayout textBlock = new VerticalLayout();
        textBlock.setSpacing(true);
        textBlock.setPadding(false);
        textBlock.setWidthFull();
        textBlock.setMaxWidth("500px");

        H1 title = new H1("Starte dein Depot mit Vertrauen.");
        title.getStyle().set("font-size", "var(--lumo-font-size-xxl)");
        title.getStyle().set("color", "var(--lumo-header-text-color)");

        Paragraph subtitle = new Paragraph("Investieren leicht gemacht – für Anfänger & Fortgeschrittene");
        subtitle.getStyle().set("font-size", "var(--lumo-font-size-l)");
        subtitle.getStyle().set("color", "var(--lumo-body-text-color)");

        Button primaryButton = new Button("Depot eröffnen", e -> UI.getCurrent().navigate("register"));
        primaryButton.getStyle().set("background-color", "var(--lumo-primary-color)")
                .set("color", "white")
                .set("border-radius", "var(--lumo-border-radius)")
                .set("padding", "10px 20px");

        Button secondaryButton = new Button("Mehr erfahren");
        secondaryButton.getStyle().set("background-color", "var(--lumo-shade-30pct)")
                .set("color", "var(--lumo-body-text-color)")
                .set("border-radius", "var(--lumo-border-radius)")
                .set("padding", "10px 20px");
        secondaryButton.addClickListener(e -> UI.getCurrent().getPage().setLocation("mehr-erfahren.html"));

        HorizontalLayout buttons = new HorizontalLayout(primaryButton, secondaryButton);
        buttons.setSpacing(true);
        buttons.getStyle().set("flex-wrap", "wrap");

        textBlock.add(title, subtitle, buttons);

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
        section.getStyle().set("background-color", "var(--lumo-base-color)");

        H2 headline = new H2("Funktionen im Überblick");
        headline.getStyle().set("color", "var(--lumo-header-text-color)");

        FlexLayout features = new FlexLayout();
        features.getStyle().set("flex-wrap", "wrap");
        features.setJustifyContentMode(JustifyContentMode.CENTER);
        features.getStyle().set("gap", "30px");

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
        featureTitle.getStyle().set("font-weight", "bold").set("margin", "0")
                .set("color", "var(--lumo-body-text-color)");

        Paragraph featureDesc = new Paragraph(desc);
        featureDesc.getStyle().set("font-size", "var(--lumo-font-size-s)").set("text-align", "center").set("margin", "0")
                .set("color", "var(--lumo-secondary-text-color)");

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
        cta.getStyle().set("background-color", "var(--lumo-shade-10pct)");

        H2 text = new H2("Jetzt kostenlos starten");
        text.getStyle().set("color", "var(--lumo-header-text-color)");

        Button button = new Button("Jetzt kostenlos starten", e -> UI.getCurrent().navigate("register"));
        button.getStyle().set("background-color", "var(--lumo-primary-color)")
                .set("color", "white")
                .set("border-radius", "var(--lumo-border-radius)")
                .set("padding", "12px 30px")
                .set("font-size", "var(--lumo-font-size-l)");

        cta.add(text, button);
        return cta;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.setWidthFull();
        footer.getStyle().set("background-color", "var(--lumo-secondary-color-shade)")
                .set("color", "white")
                .set("padding", "20px");

        HorizontalLayout container = new HorizontalLayout();
        container.setWidthFull();
        container.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        container.setAlignItems(Alignment.CENTER);
        container.getStyle().set("flex-wrap", "wrap");

        Paragraph copyright = new Paragraph("© 2025 Finovia. Alle Rechte vorbehalten.");
        copyright.getStyle().set("font-size", "var(--lumo-font-size-s)");

        HorizontalLayout links = new HorizontalLayout();
        links.setSpacing(true);

        Button impressumButton = new Button("Impressum");
        impressumButton.addClickListener(e -> UI.getCurrent().getPage().setLocation("impressum"));
        impressumButton.getStyle()
                .set("background", "none")
                .set("border", "none")
                .set("padding", "0")
                .set("color", "white")
                .set("font-size", "var(--lumo-font-size-s)")
                .set("text-decoration", "underline")
                .set("cursor", "pointer");

        Button datenschutzButton = new Button("Datenschutz");
        datenschutzButton.addClickListener(e -> UI.getCurrent().getPage().setLocation("datenschutz"));
        datenschutzButton.getStyle()
                .set("background", "none")
                .set("border", "none")
                .set("padding", "0")
                .set("color", "white")
                .set("font-size", "var(--lumo-font-size-s)")
                .set("text-decoration", "underline")
                .set("cursor", "pointer");

        links.add(impressumButton, datenschutzButton);

        container.add(copyright, links);
        footer.add(container);

        return footer;
    }
}