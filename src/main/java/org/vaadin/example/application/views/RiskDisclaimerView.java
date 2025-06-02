package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("risikohinweise")
@PageTitle("Finovia - Risikohinweise zum Wertpapierhandel")
@AnonymousAllowed
public class RiskDisclaimerView extends VerticalLayout {

    public RiskDisclaimerView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        getStyle().set("background-color", "var(--lumo-contrast-5pct)");

        VerticalLayout contentContainer = new VerticalLayout();
        contentContainer.setPadding(true);
        contentContainer.setSpacing(true);
        contentContainer.setMaxWidth("900px");
        contentContainer.setWidth("100%");
        contentContainer.getStyle()
                .set("background-color", "var(--lumo-base-color)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("box-shadow", "0 6px 12px rgba(0,0,0,0.1)");
        contentContainer.setMargin(Boolean.parseBoolean("50px auto"));

        Anchor backToHomeTop = new Anchor("", "← Zurück zur Startseite");
        backToHomeTop.getStyle()
                .set("margin-bottom", "20px")
                .set("color", "var(--lumo-primary-color)")
                .set("font-weight", "bold")
                .set("text-decoration", "none");
        backToHomeTop.addAttachListener(e -> {
            backToHomeTop.getElement().addEventListener("mouseover", event -> backToHomeTop.getStyle().set("text-decoration", "underline"));
            backToHomeTop.getElement().addEventListener("mouseout", event -> backToHomeTop.getStyle().set("text-decoration", "none"));
        });
        contentContainer.add(backToHomeTop);


        H1 title = new H1("Wichtige Risikohinweise zum Handel mit Wertpapieren");
        title.getStyle().set("color", "var(--lumo-error-color)");
        title.getStyle().set("text-align", "center");
        title.getStyle().set("margin-bottom", "20px");
        contentContainer.add(title);

        Paragraph intro = new Paragraph(
                "Bitte lesen Sie die folgenden Informationen sorgfältig durch, bevor Sie mit dem Handel von Wertpapieren über Finovia beginnen. Der Handel an Finanzmärkten ist mit erheblichen Risiken verbunden und nicht für jeden Anleger geeignet. **Sie können Ihr gesamtes eingesetztes Kapital verlieren.**"
        );
        intro.getStyle().set("font-size", "var(--lumo-font-size-l)");
        intro.getStyle().set("text-align", "center");
        intro.getStyle().set("margin-bottom", "30px");
        contentContainer.add(intro);

        Div separator1 = new Div();
        separator1.getStyle()
                .set("width", "100%")
                .set("height", "1px")
                .set("background-color", "var(--lumo-contrast-10pct)")
                .set("margin", "30px 0");
        contentContainer.add(separator1);

        H2 generalRisks = new H2("1. Allgemeine Risiken des Wertpapierhandels");
        generalRisks.getStyle().set("color", "var(--lumo-header-text-color)");
        contentContainer.add(generalRisks,
                createStyledParagraph("Der Handel mit Finanzinstrumenten wie Aktien, Anleihen, ETFs, Derivaten und anderen Wertpapieren kann zum **vollständigen Verlust** Ihres eingesetzten Kapitals führen. Es besteht keine Garantie, dass Ihre Anlage an Wert gewinnt. Die Wertentwicklung in der Vergangenheit ist **kein verlässlicher Indikator** für die zukünftige Performance."),
                createStyledParagraph("•  **Marktrisiko:** Die Kurse von Wertpapieren können aufgrund von globalen Wirtschaftsdaten, politischen Ereignissen, Unternehmensnachrichten, Branchentrends oder der allgemeinen Marktstimmung stark und unvorhersehbar schwanken."),
                createStyledParagraph("•  **Liquiditätsrisiko:** Bestimmte Wertpapiere können schwer zu kaufen oder zu verkaufen sein, insbesondere in volatilen Marktphasen oder bei geringem Handelsvolumen. Dies kann zu ungünstigen Ausführungspreisen oder dem Unvermögen, Positionen zu schließen, führen."),
                createStyledParagraph("•  **Zinsänderungsrisiko:** Besonders bei Anleihen und festverzinslichen Produkten können steigende Zinsen den Wert Ihrer Anlage negativ beeinflussen."),
                createStyledParagraph("•  **Währungsrisiko:** Wenn Sie in Wertpapiere investieren, die in einer anderen Währung als Ihrer Heimatwährung notieren, können Wechselkursschwankungen Ihre Rendite positiv oder negativ beeinflussen, unabhängig von der Performance des Wertpapiers selbst."),
                createStyledParagraph("•  **Bonitätsrisiko/Emittentenrisiko:** Das Risiko, dass der Emittent eines Wertpapiers (z.B. ein Unternehmen bei Aktien oder Anleihen, ein Staat bei Staatsanleihen) seinen Zahlungsverpflichtungen nicht nachkommen kann.")
        );

        H2 specificRisks = new H2("2. Spezifische Risiken bei Finovia und Online-Trading");
        specificRisks.getStyle().set("color", "var(--lumo-header-text-color)");
        contentContainer.add(specificRisks,
                createStyledParagraph("Neben den allgemeinen Risiken des Wertpapierhandels sind bei der Nutzung einer Online-Trading-Plattform wie Finovia weitere spezifische Risiken zu beachten:"),
                createStyledParagraph("•  **Technologisches Risiko:** Es besteht das Risiko von Systemausfällen, Verzögerungen bei der Datenübertragung, Softwarefehlern oder Hardwaredefekten, die den Handel beeinträchtigen oder zu Verlusten führen können. Obwohl Finovia modernste Technologie einsetzt, können technische Probleme nie vollständig ausgeschlossen werden."),
                createStyledParagraph("•  **Internet- und Cybersicherheitsrisiken:** Transaktionen und die Speicherung von Daten über das Internet sind anfällig für Risiken wie Hacking, Phishing, Denial-of-Service-Angriffe oder Malware. Obwohl Finovia hohe Sicherheitsstandards implementiert und ständig aktualisiert, liegt ein Teil der Verantwortung beim Nutzer, seine Zugangsdaten und Endgeräte zu schützen."),
                createStyledParagraph("•  **Fehlbedienungsrisiko:** Fehler bei der Eingabe von Aufträgen oder der Nutzung der Plattform durch den Nutzer können zu unbeabsichtigten Transaktionen oder Verlusten führen."),
                createStyledParagraph("•  **Keine Finanzberatung:** Die Informationen, Analysen und Tools, die Finovia bereitstellt, dienen ausschließlich Informations- und Bildungszwecken. Sie stellen **keine individuelle Finanzberatung, Anlageempfehlung oder Steuerberatung** dar. Sie müssen Ihre Anlageentscheidungen selbst treffen und sind für diese eigenverantwortlich."),
                createStyledParagraph("•  **Regulierungsrisiko:** Änderungen in Gesetzen, Steuervorschriften oder aufsichtsrechtlichen Bestimmungen können sich auf Ihre Anlagen, die Funktionsweise der Plattform oder die Besteuerung von Gewinnen auswirken.")
        );

        H2 recommendations = new H2("3. Empfehlungen und Risikomanagement");
        recommendations.getStyle().set("color", "var(--lumo-header-text-color)");
        contentContainer.add(recommendations,
                createStyledParagraph("Um Ihre Risiken zu minimieren und verantwortungsvoll zu handeln, empfehlen wir Ihnen Folgendes:"),
                createStyledParagraph("•  **Informieren Sie sich gründlich:** Stellen Sie sicher, dass Sie die Funktionsweise, Chancen und Risiken jedes Finanzinstruments verstehen, bevor Sie investieren."),
                createStyledParagraph("•  **Investieren Sie nur Kapital, dessen Verlust Sie sich leisten können:** Spekulieren Sie niemals mit Geld, das Sie für Ihren Lebensunterhalt benötigen oder dessen Verlust Ihre finanzielle Situation wesentlich beeinträchtigen würde."),
                createStyledParagraph("•  **Diversifizieren Sie Ihr Portfolio:** Verteilen Sie Ihre Anlagen auf verschiedene Wertpapiere, Branchen, Regionen und Anlageklassen, um das Risiko zu streuen."),
                createStyledParagraph("•  **Setzen Sie sich persönliche Grenzen:** Definieren Sie klare Anlageziele, Risikobereitschaft und nutzen Sie Risikomanagement-Tools wie Stop-Loss-Orders, wenn verfügbar."),
                createStyledParagraph("•  **Behalten Sie einen langfristigen Anlagehorizont:** Kurzfristige Spekulationen sind in der Regel risikoreicher."),
                createStyledParagraph("•  **Konsultieren Sie einen unabhängigen Finanzberater:** Wenn Sie unsicher sind, ob eine Anlage für Ihre persönliche Situation geeignet ist, suchen Sie professionellen Rat bei einem unabhängigen Finanzberater oder Steuerberater.")
        );

        Div separator2 = new Div();
        separator2.getStyle()
                .set("width", "100%")
                .set("height", "1px")
                .set("background-color", "var(--lumo-contrast-10pct)")
                .set("margin", "30px 0");
        contentContainer.add(separator2);

        Paragraph finalStatement = new Paragraph(
                "Mit der Nutzung der Dienste von Finovia bestätigen Sie, dass Sie diese Risikohinweise gelesen und verstanden haben und die damit verbundenen Risiken akzeptieren. Für weiterführende Informationen zum Anlegerschutz empfehlen wir Ihnen die Webseiten der zuständigen Aufsichtsbehörden."
        );
        finalStatement.getStyle().set("font-size", "var(--lumo-font-size-m)").set("text-align", "center");
        contentContainer.add(finalStatement);

        HorizontalLayout externalLinks = new HorizontalLayout();
        externalLinks.setSpacing(true);
        externalLinks.getStyle().set("flex-wrap", "wrap");
        externalLinks.setJustifyContentMode(JustifyContentMode.CENTER);
        externalLinks.add(
                createExternalLink("https://www.bafin.de/DE/Verbraucher/GeldanlageWertpapiere/Wertpapiergeschaefte/wertpapiergeschaefte_node.html", "BaFin - Risiken der Geldanlage und Wertpapiere (Deutschland)"),
                createExternalLink("https://www.esma.europa.eu/investor-corner", "ESMA - Investor Corner (Europäische Union, Englisch)")

        );
        contentContainer.add(externalLinks);

        add(contentContainer);
    }

    private Paragraph createStyledParagraph(String text) {
        Paragraph p = new Paragraph(text);
        p.getStyle().set("font-size", "var(--lumo-font-size-m)");
        p.getStyle().set("line-height", "1.6");
        p.getStyle().set("color", "var(--lumo-body-text-color)");
        p.getStyle().set("margin-bottom", "10px");
        return p;
    }

    private Anchor createExternalLink(String href, String text) {
        Anchor link = new Anchor(href, text);
        link.setTarget("_blank");
        link.getStyle()
                .set("color", "var(--lumo-primary-color)")
                .set("font-weight", "500")
                .set("text-decoration", "underline")
                .set("padding", "5px 10px")
                .set("transition", "color 0.3s ease");
        link.addAttachListener(e -> {
            link.getElement().addEventListener("mouseover", event -> link.getStyle().set("color", "var(--lumo-primary-color-shade)"));
            link.getElement().addEventListener("mouseout", event -> link.getStyle().set("color", "var(--lumo-primary-color)"));
        });
        return link;
    }
}