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
        getStyle().set("background-color", "var(--lumo-contrast-5pct)");

        add(createHeader());
        add(createHeroSection());
        add(createFeatureSection());
        add(createTestimonialSection());
        add(createCTASection());
        add(createRiskDisclaimerSection()); // NEU: Risikohinweis-Sektion vor dem Footer
        add(createFooter());
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setPadding(true);
        header.setAlignItems(Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.getStyle()
                .set("background-color", "var(--lumo-base-color)")
                .set("box-shadow", "0 4px 12px rgba(0,0,0,0.1)")
                .set("position", "sticky")
                .set("top", "0")
                .set("z-index", "1000")
                .set("flex-wrap", "wrap")
                .set("min-height", "70px");

        Image logo = new Image("icons/logo.png", "Finovia Logo");
        logo.setHeight("45px");

        HorizontalLayout nav = new HorizontalLayout();
        nav.setSpacing(true);
        nav.getStyle().set("flex-wrap", "wrap")
                .set("gap", "20px");

        Anchor startseite = new Anchor("#", "Startseite");
        styleNavLink(startseite);
        Anchor funktionen = new Anchor("#funktionen", "Funktionen");
        styleNavLink(funktionen);
        Anchor hilfe = new Anchor("#hilfe", "Hilfe");
        styleNavLink(hilfe);

        Button loginButton = new Button("Login", e -> UI.getCurrent().navigate("login"));
        loginButton.getStyle()
                .set("background-color", "var(--lumo-primary-color)")
                .set("color", "white")
                .set("border", "none")
                .set("border-radius", "var(--lumo-border-radius-m)")
                .set("padding", "10px 20px")
                .set("font-weight", "600")
                .set("cursor", "pointer")
                .set("transition", "background-color 0.3s ease, transform 0.2s ease");
        loginButton.addAttachListener(e -> {
            loginButton.getElement().addEventListener("mouseover", event -> loginButton.getStyle().set("background-color", "var(--lumo-primary-color-shade)").set("transform", "translateY(-2px)"));
            loginButton.getElement().addEventListener("mouseout", event -> loginButton.getStyle().set("background-color", "var(--lumo-primary-color)").set("transform", "translateY(0)"));
        });


        nav.add(startseite, funktionen, hilfe, loginButton);
        header.add(logo, nav);
        return header;
    }

    private void styleNavLink(Anchor anchor) {
        anchor.getStyle()
                .set("color", "var(--lumo-primary-text-color)")
                .set("font-weight", "500")
                .set("text-decoration", "none")
                .set("padding", "5px 10px")
                .set("transition", "color 0.3s ease, text-decoration 0.3s ease");
        anchor.addAttachListener(e -> {
            anchor.getElement().addEventListener("mouseover", event -> anchor.getStyle().set("color", "var(--lumo-primary-color)").set("text-decoration", "underline"));
            anchor.getElement().addEventListener("mouseout", event -> anchor.getStyle().set("color", "var(--lumo-primary-text-color)").set("text-decoration", "none"));
        });
    }


    private FlexLayout createHeroSection() {
        FlexLayout hero = new FlexLayout();
        hero.setWidthFull();
        hero.getStyle()
                .set("flex-wrap", "wrap")
                .set("justify-content", "center")
                .set("align-items", "center")
                .set("padding", "100px 20px")
                .set("background", "linear-gradient(to right, var(--lumo-primary-color-tint), var(--lumo-base-color))");
        hero.getStyle().set("gap", "50px");

        VerticalLayout textBlock = new VerticalLayout();
        textBlock.setSpacing(true);
        textBlock.setPadding(false);
        textBlock.setWidthFull();
        textBlock.setMaxWidth("700px");
        textBlock.setAlignItems(Alignment.START);

        H1 finoviaHeading = new H1("Finovia");
        finoviaHeading.getStyle()
                .set("font-size", "var(--lumo-font-size-xxxl)")
                .set("color", "var(--lumo-primary-color)")
                .set("margin-bottom", "10px");

        H1 title = new H1("Investieren leicht gemacht – Starte dein Depot mit Vertrauen.");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-xxxl)")
                .set("color", "var(--lumo-header-text-color)")
                .set("line-height", "1.1")
                .set("margin-bottom", "15px");

        Paragraph subtitle = new Paragraph("Wir bieten dir intuitive Werkzeuge und fundierte Informationen, die du für den erfolgreichen Handel mit Aktien und ETFs benötigst – egal ob du Anfänger bist oder ein erfahrener Investor.");
        subtitle.getStyle()
                .set("font-size", "var(--lumo-font-size-xl)")
                .set("color", "var(--lumo-secondary-text-color)")
                .set("margin-bottom", "40px");

        Button primaryButton = new Button("Depot jetzt eröffnen", e -> UI.getCurrent().navigate("register"));
        primaryButton.getStyle()
                .set("background-color", "var(--lumo-primary-color)")
                .set("color", "white")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("padding", "20px 40px")
                .set("font-size", "var(--lumo-font-size-l)")
                .set("font-weight", "bold")
                .set("cursor", "pointer")
                .set("box-shadow", "0 6px 12px rgba(0, 0, 0, 0.2)");
        primaryButton.addAttachListener(e -> {
            primaryButton.getElement().addEventListener("mouseover", event -> primaryButton.getStyle().set("background-color", "var(--lumo-primary-color-shade)").set("transform", "translateY(-3px)"));
            primaryButton.getElement().addEventListener("mouseout", event -> primaryButton.getStyle().set("background-color", "var(--lumo-primary-color)").set("transform", "translateY(0)"));
        });


        Button secondaryButton = new Button("Mehr erfahren", e -> UI.getCurrent().getPage().setLocation("mehr-erfahren.html"));
        secondaryButton.getStyle()
                .set("background-color", "transparent")
                .set("border", "2px solid var(--lumo-shade-30pct)")
                .set("color", "var(--lumo-primary-text-color)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("padding", "20px 40px")
                .set("font-size", "var(--lumo-font-size-l)")
                .set("font-weight", "bold")
                .set("cursor", "pointer")
                .set("transition", "background-color 0.3s ease, border-color 0.3s ease, transform 0.2s ease");
        secondaryButton.addAttachListener(e -> {
            secondaryButton.getElement().addEventListener("mouseover", event -> secondaryButton.getStyle().set("background-color", "var(--lumo-shade-5pct)").set("border-color", "var(--lumo-primary-color)").set("transform", "translateY(-3px)"));
            secondaryButton.getElement().addEventListener("mouseout", event -> secondaryButton.getStyle().set("background-color", "transparent").set("border-color", "var(--lumo-shade-30pct)").set("transform", "translateY(0)"));
        });


        HorizontalLayout buttons = new HorizontalLayout(primaryButton, secondaryButton);
        buttons.setSpacing(true);
        buttons.getStyle().set("flex-wrap", "wrap");

        textBlock.add(finoviaHeading, title, subtitle, buttons);

        Image illustration = new Image("images/illustration.png", "Illustration");
        illustration.setWidth("550px");
        illustration.getStyle().set("max-width", "100%");
        illustration.getStyle().set("border-radius", "var(--lumo-border-radius-xl)");
        illustration.getStyle().set("box-shadow", "0 15px 30px rgba(0,0,0,0.2)");

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
        section.getStyle().set("padding", "80px 20px");

        H2 headline = new H2("Deine Vorteile bei Finovia");
        headline.getStyle().set("color", "var(--lumo-header-text-color)");
        headline.getStyle().set("font-size", "var(--lumo-font-size-xxxl)");
        headline.getStyle().set("margin-bottom", "60px");

        FlexLayout features = new FlexLayout();
        features.getStyle().set("flex-wrap", "wrap");
        features.setJustifyContentMode(JustifyContentMode.CENTER);
        features.getStyle().set("gap", "40px");

        features.add(
                createFeatureCard("Nahtlose Kontoeröffnung", "Beginne in wenigen Minuten mit deiner Geldanlage – einfach und sicher, ohne unnötigen Papierkram.", "images/icon-depot.png"),
                createFeatureCard("Transparente Vergleiche", "Analysiere Aktien und ETFs detailliert mit unseren innovativen Tools, um die besten Entscheidungen zu treffen.", "images/icon-vergleich.png"),
                createFeatureCard("Echtzeit-Einblicke", "Bleibe mit aktuellen Marktinformationen und professionellen Analysen immer einen Schritt voraus und optimiere dein Portfolio.", "images/icon-analyse.png")
        );

        section.add(headline, features);
        return section;
    }

    private VerticalLayout createFeatureCard(String title, String desc, String iconFile) {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);
        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidth("300px");
        layout.getStyle()
                .set("background-color", "white")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.05)")
                .set("transition", "all 0.3s ease");
        layout.addAttachListener(e -> {
            layout.getElement().addEventListener("mouseover", event -> layout.getStyle().set("box-shadow", "0 8px 16px rgba(0,0,0,0.1)"));
            layout.getElement().addEventListener("mouseout", event -> layout.getStyle().set("box-shadow", "0 4px 8px rgba(0,0,0,0.05)"));
        });

        Image icon = new Image(iconFile, title);
        icon.setHeight("60px");

        H3 featureTitle = new H3(title);
        featureTitle.getStyle().set("font-weight", "bold").set("margin", "0")
                .set("color", "var(--lumo-header-text-color)");

        Paragraph featureDesc = new Paragraph(desc);
        featureDesc.getStyle().set("font-size", "var(--lumo-font-size-m)").set("text-align", "center").set("margin", "0")
                .set("color", "var(--lumo-body-text-color)");

        layout.add(icon, featureTitle, featureDesc);
        return layout;
    }

    private VerticalLayout createTestimonialSection() {
        VerticalLayout section = new VerticalLayout();
        section.setWidthFull();
        section.setAlignItems(Alignment.CENTER);
        section.setSpacing(true);
        section.setPadding(true);
        section.getStyle().set("background-color", "var(--lumo-contrast-5pct)");
        section.getStyle().set("padding", "80px 20px");

        H2 headline = new H2("Was unsere Kunden sagen");
        headline.getStyle().set("color", "var(--lumo-header-text-color)");
        headline.getStyle().set("font-size", "var(--lumo-font-size-xxl)");
        headline.getStyle().set("margin-bottom", "40px");

        FlexLayout testimonials = new FlexLayout();
        testimonials.getStyle().set("flex-wrap", "wrap");
        testimonials.setJustifyContentMode(JustifyContentMode.CENTER);
        testimonials.getStyle().set("gap", "30px");

        testimonials.add(
                createTestimonialCard(
                        "\"Finovia hat mir den Einstieg in die Welt der Aktien unglaublich leicht gemacht. Die Oberfläche ist intuitiv und die Informationen sehr hilfreich.\"",
                        "Max Mustermann",
                        "Privatanleger"
                ),
                createTestimonialCard(
                        "\"Als erfahrener Trader schätze ich die Echtzeit-Daten und tiefgehenden Analysetools. Finovia ist eine Bereicherung für mein Portfolio-Management.\"",
                        "Anna Schmidt",
                        "Finanzberaterin"
                ),
                createTestimonialCard(
                        "\"Der Kundenservice ist herausragend und die Gebühren sind fair. Ich kann Finovia jedem empfehlen, der ernsthaft investieren möchte.\"",
                        "Lena Mayer",
                        "Studentin & Investorin"
                )
        );

        section.add(headline, testimonials);
        return section;
    }

    private VerticalLayout createTestimonialCard(String quote, String author, String role) {
        VerticalLayout card = new VerticalLayout();
        card.setAlignItems(Alignment.CENTER);
        card.setSpacing(true);
        card.setPadding(true);
        card.setWidth("350px");
        card.getStyle()
                .set("background-color", "white")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("box-shadow", "0 6px 12px rgba(0,0,0,0.1)");
        card.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Paragraph quoteText = new Paragraph(quote);
        quoteText.getStyle()
                .set("font-size", "var(--lumo-font-size-m)")
                .set("text-align", "center")
                .set("font-style", "italic")
                .set("color", "var(--lumo-body-text-color)");

        Span authorName = new Span(author);
        authorName.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "var(--lumo-font-size-l)")
                .set("color", "var(--lumo-header-text-color)");

        Span authorRole = new Span(role);
        authorRole.getStyle()
                .set("font-size", "var(--lumo-font-size-s)")
                .set("color", "var(--lumo-secondary-text-color)");

        card.add(quoteText, authorName, authorRole);
        return card;
    }


    private VerticalLayout createCTASection() {
        VerticalLayout cta = new VerticalLayout();
        cta.setAlignItems(Alignment.CENTER);
        cta.setSpacing(true);
        cta.setPadding(true);
        cta.getStyle().set("background-color", "var(--lumo-primary-color)");
        cta.getStyle().set("color", "white");
        cta.getStyle().set("padding", "60px 20px");

        H2 text = new H2("Bereit für deinen Start in die Investment-Welt?");
        text.getStyle().set("color", "white");
        text.getStyle().set("font-size", "var(--lumo-font-size-xxl)");
        text.getStyle().set("margin-bottom", "20px");

        Button button = new Button("Jetzt kostenlos Depot eröffnen", e -> UI.getCurrent().navigate("register"));
        button.getStyle().set("background-color", "white")
                .set("color", "var(--lumo-primary-color)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("padding", "18px 40px")
                .set("font-size", "var(--lumo-font-size-l)")
                .set("font-weight", "bold")
                .set("cursor", "pointer")
                .set("transition", "background-color 0.3s ease, color 0.3s ease, transform 0.2s ease");
        button.addAttachListener(e -> {
            button.getElement().addEventListener("mouseover", event -> button.getStyle().set("background-color", "var(--lumo-shade-5pct)").set("transform", "translateY(-2px)"));
            button.getElement().addEventListener("mouseout", event -> button.getStyle().set("background-color", "white").set("transform", "translateY(0)"));
        });

        cta.add(text, button);
        return cta;
    }

    /**
     * Erstellt eine Risikohinweis-Sektion, die wichtige Informationen zum Handel mit Wertpapieren enthält.
     * Diese Sektion wird am Ende der Startseite angezeigt.
     *
     * @return Eine VerticalLayout-Komponente mit dem Risikohinweis
     */
    private VerticalLayout createRiskDisclaimerSection() {
        VerticalLayout section = new VerticalLayout();
        section.setWidthFull();
        section.setAlignItems(Alignment.CENTER);
        section.setPadding(true);
        section.getStyle()
                .set("background-color", "var(--lumo-error-color-10pct)")
                .set("padding", "30px 20px")
                .set("text-align", "center");

        H3 headline = new H3("Wichtiger Risikohinweis zum Handel mit Wertpapieren");
        headline.getStyle().set("color", "var(--lumo-error-color)").set("margin-bottom", "10px");

        Paragraph disclaimerText = new Paragraph(
                "Der Handel mit Wertpapieren (Aktien, ETFs etc.) birgt erhebliche Risiken und kann zum vollständigen Verlust des eingesetzten Kapitals führen. Die Wertentwicklung in der Vergangenheit ist kein verlässlicher Indikator für zukünftige Ergebnisse."
        );
        disclaimerText.getStyle()
                .set("font-size", "var(--lumo-font-size-m)")
                .set("color", "var(--lumo-secondary-text-color)")
                .set("max-width", "800px");

        // Link zur detaillierten Risikohinweis-Seite
        Anchor learnMoreLink = new Anchor("risikohinweise", "Mehr erfahren zu Risiken des Handels");
        learnMoreLink.getStyle()
                .set("color", "var(--lumo-error-color)")
                .set("font-weight", "bold")
                .set("text-decoration", "underline")
                .set("margin-top", "15px");
        learnMoreLink.addAttachListener(e -> {
            learnMoreLink.getElement().addEventListener("mouseover", event -> learnMoreLink.getStyle().set("color", "var(--lumo-error-color-dark)"));
            learnMoreLink.getElement().addEventListener("mouseout", event -> learnMoreLink.getStyle().set("color", "var(--lumo-error-color)"));
        });


        section.add(headline, disclaimerText, learnMoreLink);
        return section;
    }


    private Footer createFooter() {
        Footer footer = new Footer();
        footer.setWidthFull();
        footer.getStyle()
                .set("background-color", "var(--lumo-body-text-color)")
                .set("color", "white")
                .set("padding", "30px 40px");

        HorizontalLayout container = new HorizontalLayout();
        container.setWidthFull();
        container.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        container.setAlignItems(Alignment.CENTER);
        container.getStyle().set("flex-wrap", "wrap");
        container.setMaxWidth("1200px");
        container.setMargin(Boolean.parseBoolean("0 auto"));


        Paragraph copyright = new Paragraph("© " + java.time.Year.now().getValue() + " Finovia. Alle Rechte vorbehalten.");
        copyright.getStyle()
                .set("font-size", "var(--lumo-font-size-m)")
                .set("color", "var(--lumo-secondary-color)");


        HorizontalLayout links = new HorizontalLayout();
        links.setSpacing(true);
        links.getStyle().set("gap", "20px");

        Anchor impressumLink = new Anchor("impressum", "Impressum");
        impressumLink.getStyle()
                .set("color", "var(--lumo-secondary-color)")
                .set("font-size", "var(--lumo-font-size-m)")
                .set("text-decoration", "none")
                .set("font-weight", "500");
        impressumLink.addAttachListener(e -> {
            impressumLink.getElement().addEventListener("mouseover", event -> impressumLink.getStyle().set("text-decoration", "underline").set("color", "white"));
            impressumLink.getElement().addEventListener("mouseout", event -> impressumLink.getStyle().set("text-decoration", "none").set("color", "var(--lumo-secondary-color)"));
        });

        Anchor datenschutzLink = new Anchor("datenschutz", "Datenschutz");
        datenschutzLink.getStyle()
                .set("color", "var(--lumo-secondary-color)")
                .set("font-size", "var(--lumo-font-size-m)")
                .set("text-decoration", "none")
                .set("font-weight", "500");
        datenschutzLink.addAttachListener(e -> {
            datenschutzLink.getElement().addEventListener("mouseover", event -> datenschutzLink.getStyle().set("text-decoration", "underline").set("color", "white"));
            datenschutzLink.getElement().addEventListener("mouseout", event -> datenschutzLink.getStyle().set("text-decoration", "none").set("color", "var(--lumo-secondary-color)"));
        });


        Anchor riskDisclaimerLink = new Anchor("risikohinweise", "Risikohinweise");
        riskDisclaimerLink.getStyle()
                .set("color", "var(--lumo-secondary-color)")
                .set("font-size", "var(--lumo-font-size-m)")
                .set("text-decoration", "none")
                .set("font-weight", "500");
        riskDisclaimerLink.addAttachListener(e -> {
            riskDisclaimerLink.getElement().addEventListener("mouseover", event -> riskDisclaimerLink.getStyle().set("text-decoration", "underline").set("color", "white"));
            riskDisclaimerLink.getElement().addEventListener("mouseout", event -> riskDisclaimerLink.getStyle().set("text-decoration", "none").set("color", "var(--lumo-secondary-color)"));
        });


        links.add(impressumLink, datenschutzLink, riskDisclaimerLink); // Link hinzufügen
        container.add(copyright, links);
        footer.add(container);

        return footer;
    }
}