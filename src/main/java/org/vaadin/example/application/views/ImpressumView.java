package org.vaadin.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * Vaadin View für das Impressum der fiktiven Trading-App "Finovia".
 * Dieser View enthält grundlegende Informationen, die in einem Impressum enthalten sein sollten.
 */
@Route("impressum") // Definiert die URL-Route für diesen View
@PageTitle("Impressum - Finovia") // Setzt den Titel der Browser-Seite
@AnonymousAllowed
public class ImpressumView extends VerticalLayout {

    /**
     * Konstruktor der ImpressumView-Klasse.
     * Initialisiert das Layout und fügt die erforderlichen Komponenten hinzu.
     */
    public ImpressumView() {
        // Setzt grundlegende Styling-Eigenschaften für das Layout
        setSpacing(false); // Kein zusätzlicher Abstand zwischen den Komponenten
        setPadding(true); // Innenabstand für das Layout
        setSizeFull(); // Nimmt die gesamte verfügbare Größe ein
        setAlignItems(Alignment.CENTER); // Zentriert den Inhalt horizontal

        // Titel des Impressums
        H1 title = new H1("Impressum");
        add(title);

        // --- Abschnitt: Angaben gemäß § 5 TMG ---
        H2 section1Title = new H2("Angaben gemäß § 5 TMG");
        add(section1Title);

        add(new Paragraph("Finovia GmbH"));
        add(new Paragraph("Musterstraße 123"));
        add(new Paragraph("12345 Musterstadt"));
        add(new Paragraph("Deutschland"));

        add(new Paragraph(" ")); // Leerer Absatz für besseres Layout

        // Vertreten durch:
        add(new Paragraph("Vertreten durch:"));
        add(new Paragraph("Geschäftsführer: Max Mustermann"));
        add(new Paragraph("Geschäftsführerin: Erika Musterfrau"));

        add(new Paragraph(" ")); // Leerer Absatz für besseres Layout

        // Handelsregister
        add(new Paragraph("Handelsregister: Amtsgericht Musterstadt"));
        add(new Paragraph("Registernummer: HRB 12345"));

        add(new Paragraph(" ")); // Leerer Absatz für besseres Layout

        // Umsatzsteuer-ID
        add(new Paragraph("Umsatzsteuer-Identifikationsnummer gemäß § 27 a Umsatzsteuergesetz:"));
        add(new Paragraph("DE123456789"));

        add(new Paragraph(" ")); // Leerer Absatz für besseres Layout

        // --- Abschnitt: Kontakt ---
        H2 section2Title = new H2("Kontakt");
        add(section2Title);

        add(new Paragraph("Telefon: +49 (0) 123 456789-0"));
        add(new Paragraph("Telefax: +49 (0) 123 456789-9"));
        add(new Paragraph("E-Mail: info@finovia.de"));
        add(new Paragraph("Web: www.finovia.de"));

        add(new Paragraph(" ")); // Leerer Absatz für besseres Layout

        // --- Abschnitt: Streitbeilegung ---
        H2 section3Title = new H2("Streitbeilegung");
        add(section3Title);

        add(new Paragraph("Die Europäische Kommission stellt eine Plattform zur Online-Streitbeilegung (OS) bereit:"));
        add(new Paragraph("https://ec.europa.eu/consumers/odr"));
        add(new Paragraph("Unsere E-Mail-Adresse finden Sie oben im Impressum."));

        add(new Paragraph("Wir sind nicht bereit oder verpflichtet, an Streitbeilegungsverfahren vor einer Verbraucherschlichtungsstelle teilzunehmen."));

        add(new Paragraph(" ")); // Leerer Absatz für besseres Layout

        // --- Abschnitt: Haftung für Inhalte ---
        H2 section4Title = new H2("Haftung für Inhalte");
        add(section4Title);

        add(new Paragraph("Als Diensteanbieter sind wir gemäß § 7 Abs.1 TMG für eigene Inhalte auf diesen Seiten nach den allgemeinen Gesetzen verantwortlich. Nach §§ 8 bis 10 TMG sind wir als Diensteanbieter jedoch nicht verpflichtet, übermittelte oder gespeicherte fremde Informationen zu überwachen oder nach Umständen zu forschen, die auf eine rechtswidrige Tätigkeit hinweisen."));
        add(new Paragraph("Verpflichtungen zur Entfernung oder Sperrung der Nutzung von Informationen nach den allgemeinen Gesetzen bleiben hiervon unberührt. Eine diesbezügliche Haftung ist jedoch erst ab dem Zeitpunkt der Kenntnis einer konkreten Rechtsverletzung möglich. Bei Bekanntwerden von entsprechenden Rechtsverletzungen werden wir diese Inhalte umgehend entfernen."));

        add(new Paragraph(" ")); // Leerer Absatz für besseres Layout

        // --- Abschnitt: Haftung für Links ---
        H2 section5Title = new H2("Haftung für Links");
        add(section5Title);

        add(new Paragraph("Unser Angebot enthält Links zu externen Websites Dritter, auf deren Inhalte wir keinen Einfluss haben. Deshalb können wir für diese fremden Inhalte auch keine Gewähr übernehmen. Für die Inhalte der verlinkten Seiten ist stets der jeweilige Anbieter oder Betreiber der Seiten verantwortlich. Die verlinkten Seiten wurden zum Zeitpunkt der Verlinkung auf mögliche Rechtsverstöße überprüft. Rechtswidrige Inhalte waren zum Zeitpunkt der Verlinkung nicht erkennbar."));
        add(new Paragraph("Eine permanente inhaltliche Kontrolle der verlinkten Seiten ist jedoch ohne konkrete Anhaltspunkte einer Rechtsverletzung nicht zumutbar. Bei Bekanntwerden von Rechtsverletzungen werden wir derartige Links umgehend entfernen."));

        add(new Paragraph(" ")); // Leerer Absatz für besseres Layout

        // --- Abschnitt: Urheberrecht ---
        H2 section6Title = new H2("Urheberrecht");
        add(section6Title);

        add(new Paragraph("Die durch die Seitenbetreiber erstellten Inhalte und Werke auf diesen Seiten unterliegen dem deutschen Urheberrecht. Die Vervielfältigung, Bearbeitung, Verbreitung und jede Art der Verwertung außerhalb der Grenzen des Urheberrechtes bedürfen der schriftlichen Zustimmung des jeweiligen Autors bzw. Erstellers. Downloads und Kopien dieser Seite sind nur für den privaten, nicht kommerziellen Gebrauch gestattet."));
        add(new Paragraph("Soweit die Inhalte auf dieser Seite nicht vom Betreiber erstellt wurden, werden die Urheberrechte Dritter beachtet. Insbesondere werden Inhalte Dritter als solche gekennzeichnet. Sollten Sie trotzdem auf eine Urheberrechtsverletzung aufmerksam werden, bitten wir um einen entsprechenden Hinweis. Bei Bekanntwerden von Rechtsverletzungen werden wir derartige Inhalte umgehend entfernen."));
    }
}