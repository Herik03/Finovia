package org.vaadin.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span; // Für fettgedruckten Text
import com.vaadin.flow.component.html.UnorderedList; // Für Listen
import com.vaadin.flow.component.html.ListItem; // Für Listenelemente
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("datenschutz")
@PageTitle("Datenschutzerklärung - Finovia")
@AnonymousAllowed
public class DatenschutzView extends VerticalLayout {

    public DatenschutzView() {
        setSpacing(false);
        setPadding(false); // Padding wird im Container gesetzt
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        VerticalLayout datenschutzContainer = new VerticalLayout();
        datenschutzContainer.setPadding(true);
        datenschutzContainer.setSpacing(true);
        datenschutzContainer.setWidth("100%");
        datenschutzContainer.setMaxWidth("900px"); // Maximale Breite für bessere Lesbarkeit
        datenschutzContainer.getStyle()
                .set("background-color", "var(--lumo-base-color)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("box-shadow", "0 6px 12px rgba(0,0,0,0.1)")
                .set("margin", "50px auto"); // Zentriert den Container vertikal und horizontal

        // Zurück zur Startseite Link
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
        datenschutzContainer.add(backToHomeTop);

        H1 title = new H1("Datenschutzerklärung für die Finovia Trading App");
        title.getStyle().set("text-align", "center").set("margin-bottom", "20px");
        datenschutzContainer.add(title);

        Paragraph introText = createStyledParagraph("Wir begrüßen Sie bei Finovia! Der Schutz Ihrer personenbezogenen Daten ist für uns von höchster Bedeutung, insbesondere im Umgang mit sensiblen Finanzdaten. Diese Datenschutzerklärung informiert Sie umfassend über die Art, den Umfang und den Zweck der Verarbeitung Ihrer personenbezogenen Daten im Rahmen der Nutzung unserer mobilen Trading-App und der damit verbundenen Dienstleistungen. Wir verarbeiten Ihre Daten ausschließlich im Einklang mit den Bestimmungen der Datenschutz-Grundverordnung (DSGVO), des Bundesdatenschutzgesetzes (BDSG) sowie aller relevanten Finanzmarktgesetze (z.B. Geldwäschegesetz, Wertpapierhandelsgesetz).");
        introText.getStyle().set("text-align", "center").set("margin-bottom", "30px");
        datenschutzContainer.add(introText);

        // --- Abschnitt: 1. Name und Kontaktdaten des Verantwortlichen ---
        H2 section1Title = new H2("1. Name und Kontaktdaten des Verantwortlichen");
        datenschutzContainer.add(section1Title);

        datenschutzContainer.add(createStyledParagraph("Verantwortlich im Sinne der DSGVO und anderer nationaler Datenschutzgesetze ist die fiktive:"));

        datenschutzContainer.add(createBoldParagraph("Finovia GmbH (fiktiv)"));
        datenschutzContainer.add(createStyledParagraph("Vertreten durch die Geschäftsführung: Max Mustermann (fiktiv)"));
        datenschutzContainer.add(createStyledParagraph("Handelsregisternummer: HRB 12345 (fiktiv)"));
        datenschutzContainer.add(createStyledParagraph("Musterstraße 1"));
        datenschutzContainer.add(createStyledParagraph("12345 Musterstadt"));
        datenschutzContainer.add(createStyledParagraph("Deutschland"));
        datenschutzContainer.add(createStyledParagraph("E-Mail: datenschutz@finovia-trading.de (fiktiv)"));
        datenschutzContainer.add(createStyledParagraph("Telefon: +49 123 4567890 (fiktiv)"));

        datenschutzContainer.add(createBoldParagraph("Datenschutzbeauftragter:"));
        datenschutzContainer.add(createStyledParagraph("Da wir in erheblichem Umfang personenbezogene Daten verarbeiten, haben wir einen Datenschutzbeauftragten bestellt. Sie erreichen diesen unter:"));
        datenschutzContainer.add(createStyledParagraph("Dr. Anna Mustermann (fiktiv)"));
        datenschutzContainer.add(createStyledParagraph("c/o Finovia GmbH"));
        datenschutzContainer.add(createStyledParagraph("Musterstraße 1"));
        datenschutzContainer.add(createStyledParagraph("12345 Musterstadt"));
        datenschutzContainer.add(createStyledParagraph("E-Mail: datenschutzbeauftragter@finovia-trading.de (fiktiv)"));

        // --- Abschnitt: 2. Begriffsbestimmungen ---
        H2 section2Title = new H2("2. Begriffsbestimmungen");
        datenschutzContainer.add(section2Title);
        datenschutzContainer.add(createStyledParagraph("Diese Datenschutzerklärung verwendet die Begriffe der DSGVO. Eine detaillierte Erläuterung finden Sie unter "));
        Anchor gdprLink = new Anchor("https://gdpr-info.eu/art-4-gdpr/", "Art. 4 DSGVO");
        gdprLink.setTarget("_blank");
        datenschutzContainer.add(gdprLink);
        datenschutzContainer.add(createStyledParagraph(". Beispiele:"));

        UnorderedList definitionsList = new UnorderedList();
        definitionsList.add(new ListItem(createBoldSpan("Personenbezogene Daten:"), new Span(" Informationen, die sich auf eine identifizierte oder identifizierbare natürliche Person beziehen (z.B. Name, Adresse, E-Mail-Adresse, Finanzdaten).")));
        definitionsList.add(new ListItem(createBoldSpan("Besondere Kategorien personenbezogener Daten:"), new Span(" Daten, aus denen die rassische und ethnische Herkunft, politische Meinungen, religiöse oder weltanschauliche Überzeugungen oder die Gewerkschaftszugehörigkeit hervorgehen, sowie genetische Daten, biometrische Daten zur eindeutigen Identifizierung, Gesundheitsdaten oder Daten zum Sexualleben oder zur sexuellen Orientierung (relevant z.B. für Biometrie-Login).")));
        definitionsList.add(new ListItem(createBoldSpan("Verarbeitung:"), new Span(" Jeder Vorgang im Zusammenhang mit personenbezogenen Daten (Erheben, Speichern, Nutzen, Übermitteln, Löschen).")));
        datenschutzContainer.add(definitionsList);

        // --- Abschnitt: 3. Datenverarbeitung im Rahmen unserer Dienste ---
        H2 section3Title = new H2("3. Datenverarbeitung im Rahmen unserer Dienste");
        datenschutzContainer.add(section3Title);
        datenschutzContainer.add(createStyledParagraph("Die Verarbeitung Ihrer Daten erfolgt stets auf einer gesetzlichen Grundlage und zu klar definierten Zwecken."));

        // 3.1. Eröffnung und Verwaltung des Trading-Kontos
        H3 subSection3_1Title = new H3("3.1. Eröffnung und Verwaltung des Trading-Kontos (Onboarding & KYC)");
        datenschutzContainer.add(subSection3_1Title);
        datenschutzContainer.add(createStyledParagraph("Zur Eröffnung Ihres Trading-Kontos und zur Einhaltung gesetzlicher Vorschriften (insbesondere Geldwäschegesetz - GWG, Wertpapierhandelsgesetz - WpHG) erheben wir folgende Daten:"));
        UnorderedList onboardingList = new UnorderedList();
        onboardingList.add(new ListItem(createBoldSpan("Identifikationsdaten:"), new Span(" Vorname, Nachname, Geburtsdatum, Geburtsort, Staatsangehörigkeit, Adresse, E-Mail-Adresse, Telefonnummer.")));
        onboardingList.add(new ListItem(createBoldSpan("Legitimationsdaten:"), new Span(" Kopien von Ausweisdokumenten (Personalausweis, Reisepass), ggf. Video-Ident-Verfahren (inkl. Aufzeichnung).")));
        onboardingList.add(new ListItem(createBoldSpan("Steuerliche Daten:"), new Span(" Steuer-Identifikationsnummer, Ansässigkeitsstaat für steuerliche Zwecke.")));
        onboardingList.add(new ListItem(createBoldSpan("Finanzielle Daten:"), new Span(" Bankverbindung (IBAN, BIC) für Ein- und Auszahlungen.")));
        onboardingList.add(new ListItem(createBoldSpan("Kenntnisse und Erfahrungen:"), new Span(" Angaben zu Ihren Erfahrungen und Kenntnissen im Wertpapierhandel sowie Ihre finanziellen Verhältnisse und Anlageziele zur Erstellung Ihres Anlegerprofils (MiFID II).")));
        onboardingList.add(new ListItem(createBoldSpan("Risikoprofilierung:"), new Span(" Daten zur Bewertung Ihres Risikoprofils gemäß MiFID II.")));
        onboardingList.add(new ListItem(createBoldSpan("PEP-Prüfung:"), new Span(" Prüfung, ob Sie eine politisch exponierte Person sind.")));
        datenschutzContainer.add(onboardingList);
        datenschutzContainer.add(createBoldParagraph("Zweck:"), createStyledParagraph(" Erfüllung der vertraglichen Pflichten zur Führung des Trading-Kontos (Art. 6 Abs. 1 lit. b DSGVO), Erfüllung gesetzlicher Pflichten (Art. 6 Abs. 1 lit. c DSGVO), insbesondere zur Geldwäscheprävention (§ 10 ff. GWG), Know-Your-Customer (KYC)-Prozesse, Geeignetheitsprüfung gemäß WpHG."));
        datenschutzContainer.add(createBoldParagraph("Speicherdauer:"), createStyledParagraph(" Die Daten werden gemäß den gesetzlichen Aufbewahrungsfristen (oft 5-10 Jahre nach Beendigung der Geschäftsbeziehung) gespeichert."));

        // 3.2. Abwicklung von Transaktionen und Depotführung
        H3 subSection3_2Title = new H3("3.2. Abwicklung von Transaktionen und Depotführung");
        datenschutzContainer.add(subSection3_2Title);
        datenschutzContainer.add(createStyledParagraph("Zur Durchführung Ihrer Handelsaufträge und zur Führung Ihres Depots verarbeiten wir:"));
        UnorderedList transactionsList = new UnorderedList();
        transactionsList.add(new ListItem(createBoldSpan("Transaktionsdaten:"), new Span(" Kauf- und Verkaufsaufträge, Orderdetails (Wertpapier, Stückzahl, Preis, Zeitpunkt), Ausführungsdaten, Transaktionshistorie, Gewinne/Verluste.")));
        transactionsList.add(new ListItem(createBoldSpan("Depotdaten:"), new Span(" Art und Anzahl der gehaltenen Wertpapiere, deren aktueller Wert, Performance-Daten.")));
        transactionsList.add(new ListItem(createBoldSpan("Zahlungsdaten:"), new Span(" Details zu Ein- und Auszahlungen (Betrag, Zeitpunkt, verwendeter Zahlungsdienstleister).")));
        datenschutzContainer.add(transactionsList);
        datenschutzContainer.add(createBoldParagraph("Zweck:"), createStyledParagraph(" Erfüllung des Vertrages zur Durchführung von Wertpapiergeschäften (Art. 6 Abs. 1 lit. b DSGVO), Erfüllung gesetzlicher Pflichten (Art. 6 Abs. 1 lit. c DSGVO, z.B. Meldepflichten gegenüber der BaFin, Steuerbehörden)."));
        datenschutzContainer.add(createBoldParagraph("Empfänger:"), createStyledParagraph(" Börsen, Handelsplätze, Clearingstellen, Banken, Zahlungsdienstleister, gegebenenfalls externe Depotbanken (falls Finovia selbst keine Banklizenz besitzt)."));
        datenschutzContainer.add(createBoldParagraph("Speicherdauer:"), createStyledParagraph(" Entsprechend den gesetzlichen Aufbewahrungspflichten für Geschäfts- und Steuerunterlagen."));

        // 3.3. App-Nutzungsdaten und technische Informationen
        H3 subSection3_3Title = new H3("3.3. App-Nutzungsdaten und technische Informationen");
        datenschutzContainer.add(subSection3_3Title);
        datenschutzContainer.add(createStyledParagraph("Bei jeder Nutzung unserer App werden technische Informationen erfasst, um die Funktionalität, Sicherheit und Stabilität zu gewährleisten:"));
        UnorderedList appUsageList = new UnorderedList();
        appUsageList.add(new ListItem(createBoldSpan("Gerätedaten:"), new Span(" Gerätekennung, Betriebssystem und -version, Spracheinstellungen.")));
        appUsageList.add(new ListItem(createBoldSpan("Zugriffsdaten:"), new Span(" IP-Adresse, Datum und Uhrzeit des Zugriffs, übertragene Datenmenge, Fehlermeldungen.")));
        appUsageList.add(new ListItem(createBoldSpan("Nutzungsdaten:"), new Span(" Funktionen, die Sie in der App verwenden, Interaktionen mit der Benutzeroberfläche (anonymisiert oder pseudonymisiert zur Fehleranalyse und Verbesserung).")));
        datenschutzContainer.add(appUsageList);
        datenschutzContainer.add(createBoldParagraph("Zweck:"), createStyledParagraph(" Bereitstellung und Sicherstellung der Funktionalität der App, Fehlerbehebung, IT-Sicherheit (Art. 6 Abs. 1 lit. f DSGVO - berechtigtes Interesse)."));
        datenschutzContainer.add(createBoldParagraph("Speicherdauer:"), createStyledParagraph(" IP-Adressen werden nach 7 Tagen anonymisiert/gelöscht. Nutzungsdaten werden pseudonymisiert gespeichert, solange sie für die Fehleranalyse und Optimierung relevant sind."));

        // 3.4. Kontakt und Support
        H3 subSection3_4Title = new H3("3.4. Kontakt und Support");
        datenschutzContainer.add(subSection3_4Title);
        datenschutzContainer.add(createStyledParagraph("Wenn Sie unseren Kundenservice über die App-Chatfunktion, E-Mail oder Telefon kontaktieren, verarbeiten wir Ihre Kommunikationsdaten und die in Ihrer Anfrage enthaltenen Informationen zur Bearbeitung Ihres Anliegens."));
        datenschutzContainer.add(createBoldParagraph("Zweck:"), createStyledParagraph(" Bearbeitung Ihrer Support-Anfragen und zur Erfüllung von vertraglichen Pflichten (Art. 6 Abs. 1 lit. b DSGVO) oder auf Basis unseres berechtigten Interesses an einem effektiven Kundenservice (Art. 6 Abs. 1 lit. f DSGVO)."));
        datenschutzContainer.add(createBoldParagraph("Speicherdauer:"), createStyledParagraph(" Die Daten werden gespeichert, solange sie für die Bearbeitung der Anfrage und zur Erfüllung rechtlicher Pflichten (z.B. Dokumentationspflichten) erforderlich sind."));

        // 3.5. Biometrische Authentifizierung
        H3 subSection3_5Title = new H3("3.5. Biometrische Authentifizierung (Face ID / Fingerprint)");
        datenschutzContainer.add(subSection3_5Title);
        datenschutzContainer.add(createStyledParagraph("Wenn Sie sich für die biometrische Authentifizierung (z.B. Face ID, Fingerprint) entscheiden, um den Login in die App zu vereinfachen, werden diese biometrischen Daten ausschließlich auf Ihrem Gerät verarbeitet und gespeichert. Wir als Finovia haben "), createBoldSpan("keinen Zugriff"), createStyledParagraph(" auf Ihre biometrischen Daten. Die App erhält lediglich eine Bestätigung von Ihrem Betriebssystem, ob die Authentifizierung erfolgreich war."));
        datenschutzContainer.add(createBoldParagraph("Zweck:"), createStyledParagraph(" Vereinfachung des Logins auf Grundlage Ihrer freiwilligen Einwilligung (Art. 6 Abs. 1 lit. a DSGVO i.V.m. Art. 9 Abs. 2 lit. a DSGVO) und Ihrer expliziten Aktivierung in den App-Einstellungen."));
        datenschutzContainer.add(createBoldParagraph("Speicherdauer:"), createStyledParagraph(" Die Daten verbleiben auf Ihrem Gerät und werden von uns nicht gespeichert."));

        // 3.6. Push-Benachrichtigungen
        H3 subSection3_6Title = new H3("3.6. Push-Benachrichtigungen");
        datenschutzContainer.add(subSection3_6Title);
        datenschutzContainer.add(createStyledParagraph("Wenn Sie Push-Benachrichtigungen aktivieren, um z.B. über Kursalarme oder wichtige App-Updates informiert zu werden, wird eine gerätespezifische Token-ID an den Push-Dienstleister (z.B. Apple Push Notification Service, Google Firebase Cloud Messaging) übermittelt. Diese Token-ID ist nicht direkt personenbezogen und dient ausschließlich dazu, die Benachrichtigungen an das richtige Gerät zu senden."));
        datenschutzContainer.add(createBoldParagraph("Zweck:"), createStyledParagraph(" Bereitstellung des von Ihnen gewünschten Dienstes auf Grundlage Ihrer Einwilligung (Art. 6 Abs. 1 lit. a DSGVO)."));
        datenschutzContainer.add(createBoldParagraph("Widerruf:"), createStyledParagraph(" Sie können Push-Benachrichtigungen jederzeit in den Einstellungen Ihres Geräts oder der App deaktivieren."));

        // 3.7. Marketing und Personalisierung (optional)
        H3 subSection3_7Title = new H3("3.7. Marketing und Personalisierung (optional)");
        datenschutzContainer.add(subSection3_7Title);
        datenschutzContainer.add(createStyledParagraph("Soweit Sie uns eine separate Einwilligung erteilt haben (z.B. im Rahmen des Registrierungsprozesses oder in den App-Einstellungen), verwenden wir Ihre Daten für:"));
        UnorderedList marketingList = new UnorderedList();
        marketingList.add(new ListItem(createBoldSpan("Direktmarketing:"), new Span(" Zusendung von Informationen über neue Produkte, Dienstleistungen oder Aktionen von Finovia per E-Mail oder App-Benachrichtigung.")));
        marketingList.add(new ListItem(createBoldSpan("Personalisierung:"), new Span(" Anpassung des App-Erlebnisses und Anzeige relevanter Inhalte oder Produkte auf Basis Ihrer Nutzungsgewohnheiten und Ihres Anlageprofils (hierbei kann es sich um automatisiertes Profiling handeln, siehe Punkt 5).")));
        datenschutzContainer.add(marketingList);
        datenschutzContainer.add(createBoldParagraph("Zweck:"), createStyledParagraph(" Marketing und Personalisierung auf Grundlage Ihrer Einwilligung (Art. 6 Abs. 1 lit. a DSGVO)."));
        datenschutzContainer.add(createBoldParagraph("Widerruf:"), createStyledParagraph(" Sie können Ihre Einwilligung jederzeit mit Wirkung für die Zukunft in den App-Einstellungen oder durch eine E-Mail an unseren Datenschutzbeauftragten widerrufen."));

        // --- Abschnitt: 4. Empfänger von Daten und Drittlandtransfers ---
        H2 section4Title = new H2("4. Empfänger von Daten und Drittlandtransfers");
        datenschutzContainer.add(section4Title);
        datenschutzContainer.add(createStyledParagraph("Um unsere Dienste anbieten zu können, arbeiten wir mit ausgewählten Dienstleistern zusammen, die Zugriff auf personenbezogene Daten haben können. Dies sind:"));
        UnorderedList recipientsList = new UnorderedList();
        recipientsList.add(new ListItem(createBoldSpan("Banken und Finanzdienstleister:"), new Span(" Partnerbanken für Depotführung, Börsen, Handelsplätze, Clearingstellen, Zahlungsdienstleister.")));
        recipientsList.add(new ListItem(createBoldSpan("IT-Dienstleister:"), new Span(" Für Hosting, Cloud-Services, App-Entwicklung und -Wartung, Datenanalyse, Support-Software (z.B. für Chat-Funktionen).")));
        recipientsList.add(new ListItem(createBoldSpan("Identifizierungsdienstleister:"), new Span(" Für KYC-Prozesse (z.B. Video-Ident-Anbieter).")));
        recipientsList.add(new ListItem(createBoldSpan("Behörden und Aufsichtsinstanzen:"), new Span(" BaFin, Bundesbank, Finanzämter, Strafverfolgungsbehörden im Rahmen unserer gesetzlichen Verpflichtungen.")));
        recipientsList.add(new ListItem(createBoldSpan("Wirtschaftsprüfer, Rechtsanwälte, Berater:"), new Span(" Zur Erfüllung unserer Pflichten und zur Wahrnehmung unserer berechtigten Interessen.")));
        datenschutzContainer.add(recipientsList);
        datenschutzContainer.add(createStyledParagraph("Soweit Daten an Dienstleister außerhalb des Europäischen Wirtschaftsraums (EWR) übermittelt werden, stellen wir sicher, dass ein angemessenes Datenschutzniveau gewährleistet ist (z.B. durch EU-Standardvertragsklauseln, Angemessenheitsbeschlüsse oder Ihre ausdrückliche Einwilligung)."));

        // --- Abschnitt: 5. Automatisierte Entscheidungsfindung und Profiling ---
        H2 section5Title = new H2("5. Automatisierte Entscheidungsfindung und Profiling");
        datenschutzContainer.add(section5Title);
        datenschutzContainer.add(createStyledParagraph("Im Rahmen unserer Dienste nutzen wir teilweise automatisierte Entscheidungen und Profiling, insbesondere zur:"));
        UnorderedList automatedDecisionList = new UnorderedList();
        automatedDecisionList.add(new ListItem(createBoldSpan("Geldwäsche- und Betrugsprävention:"), new Span(" Systeme prüfen automatisch Transaktionen und Verhaltensmuster auf Auffälligkeiten, die auf Geldwäsche oder Betrug hindeuten könnten.")));
        automatedDecisionList.add(new ListItem(createBoldSpan("Geeignetheits- und Angemessenheitsprüfung (MiFID II):"), new Span(" Basierend auf Ihren Angaben zu Kenntnissen, Erfahrungen und finanziellen Verhältnissen wird automatisiert ein Anlegerprofil erstellt, um die Geeignetheit von Finanzinstrumenten für Sie zu bewerten.")));
        automatedDecisionList.add(new ListItem(createBoldSpan("Personalisierung (falls eingewilligt):"), new Span(" Algorithmen können Ihre Nutzungsgewohnheiten analysieren, um Ihnen personalisierte Inhalte oder Produkte anzuzeigen.")));
        datenschutzContainer.add(automatedDecisionList);
        datenschutzContainer.add(createStyledParagraph("Ihre Rechte: Sie haben das Recht, nicht einer ausschließlich auf einer automatisierten Verarbeitung – einschließlich Profiling – beruhenden Entscheidung unterworfen zu werden, die Ihnen gegenüber rechtliche Wirkung entfaltet oder Sie in ähnlicher Weise erheblich beeinträchtigt (Art. 22 DSGVO). Sie können in diesen Fällen das Eingreifen einer Person von Finovia verlangen, Ihren eigenen Standpunkt darlegen und die Entscheidung anfechten."));

        // --- Abschnitt: 6. Ihre Rechte als betroffene Person ---
        H2 section6Title = new H2("6. Ihre Rechte als betroffene Person");
        datenschutzContainer.add(section6Title);
        datenschutzContainer.add(createStyledParagraph("Sie haben uns gegenüber folgende Rechte hinsichtlich Ihrer personenbezogenen Daten:"));

        UnorderedList rightsList = new UnorderedList();
        rightsList.add(new ListItem(createBoldSpan("1. Auskunft (Art. 15 DSGVO):"), new Span(" Über die von uns verarbeiteten Daten, Verarbeitungszwecke, Datenkategorien, Empfänger, Speicherdauer, Herkunft der Daten.")));
        rightsList.add(new ListItem(createBoldSpan("2. Berichtigung (Art. 16 DSGVO):"), new Span(" Korrektur unrichtiger oder Vervollständigung unvollständiger Daten.")));
        rightsList.add(new ListItem(createBoldSpan("3. Löschung (Art. 17 DSGVO):"), new Span(" Recht auf \"Vergessenwerden\", soweit keine gesetzlichen Aufbewahrungspflichten entgegenstehen.")));
        rightsList.add(new ListItem(createBoldSpan("4. Einschränkung der Verarbeitung (Art. 18 DSGVO):"), new Span(" Daten dürfen nur noch gespeichert, aber nicht weiter verarbeitet werden.")));
        rightsList.add(new ListItem(createBoldSpan("5. Datenübertragbarkeit (Art. 20 DSGVO):"), new Span(" Erhalt Ihrer Daten in einem strukturierten, gängigen und maschinenlesbaren Format oder Übermittlung an einen anderen Verantwortlichen.")));
        rightsList.add(new ListItem(createBoldSpan("6. Widerruf von Einwilligungen (Art. 7 Abs. 3 DSGVO):"), new Span(" Widerruf jederzeit mit Wirkung für die Zukunft.")));
        rightsList.add(new ListItem(createBoldSpan("7. Widerspruch (Art. 21 DSGVO):"), new Span(" Widerspruch gegen die Verarbeitung Ihrer Daten, insbesondere bei Direktmarketing.")));
        rightsList.add(new ListItem(createBoldSpan("8. Beschwerderecht bei einer Aufsichtsbehörde (Art. 77 DSGVO):"), new Span(" Wenn Sie der Ansicht sind, dass die Verarbeitung Ihrer Daten gegen die DSGVO verstößt. Die für uns zuständige Aufsichtsbehörde ist:")));
        datenschutzContainer.add(rightsList);

        datenschutzContainer.add(createStyledParagraph("Landesbeauftragte für Datenschutz und Informationsfreiheit Nordrhein-Westfalen"));
        datenschutzContainer.add(createStyledParagraph("Postfach 20 04 44"));
        datenschutzContainer.add(createStyledParagraph("40102 Düsseldorf"));
        datenschutzContainer.add(createStyledParagraph("Telefon: 0211/38424-0"));
        datenschutzContainer.add(createStyledParagraph("E-Mail: poststelle@ldi.nrw.de"));

        datenschutzContainer.add(createStyledParagraph("Zur Ausübung Ihrer Rechte kontaktieren Sie bitte unseren Datenschutzbeauftragten unter der oben genannten E-Mail-Adresse."));

        // --- Abschnitt: 7. Datensicherheit ---
        H2 section7Title = new H2("7. Datensicherheit");
        datenschutzContainer.add(section7Title);
        datenschutzContainer.add(createStyledParagraph("Wir setzen geeignete technische und organisatorische Sicherheitsmaßnahmen nach dem Stand der Technik ein, um Ihre Daten gegen zufällige oder vorsätzliche Manipulationen, teilweisen oder vollständigen Verlust, Zerstörung oder gegen den unbefugten Zugriff Dritter zu schützen. Unsere Sicherheitsmaßnahmen werden entsprechend der technologischen Entwicklung fortlaufend verbessert."));
        datenschutzContainer.add(createStyledParagraph("Dazu gehören u.a. die Verschlüsselung der Datenübertragung (TLS/SSL), Zugriffskontrollen, Pseudonymisierung/Anonymisierung, regelmäßige Backups und Audits."));

        // --- Abschnitt: 8. Aktualität und Änderungen dieser Datenschutzerklärung ---
        H2 section8Title = new H2("8. Aktualität und Änderungen dieser Datenschutzerklärung");
        datenschutzContainer.add(section8Title);
        datenschutzContainer.add(createStyledParagraph("Diese Datenschutzerklärung ist aktuell gültig und hat den Stand Mai 2025."));
        datenschutzContainer.add(createStyledParagraph("Durch die Weiterentwicklung unserer App und Angebote oder aufgrund geänderter gesetzlicher bzw. behördlicher Vorgaben kann es notwendig werden, diese Datenschutzerklärung anzupassen. Die jeweils aktuelle Datenschutzerklärung kann jederzeit in der Finovia App unter 'Einstellungen > Datenschutz' sowie auf unserer Webseite unter 'finovia-trading.de/datenschutz' von Ihnen abgerufen und ausgedruckt werden."));

        add(datenschutzContainer);
    }

    // Hilfsmethode für konsistent gestylte Paragraphen
    private Paragraph createStyledParagraph(String text) {
        Paragraph p = new Paragraph(text);
        p.getStyle().set("font-size", "var(--lumo-font-size-m)");
        p.getStyle().set("line-height", "1.6");
        p.getStyle().set("color", "var(--lumo-body-text-color)");
        p.getStyle().set("margin-bottom", "10px");
        return p;
    }

    // Hilfsmethode für fettgedruckte Paragraphen (wenn der ganze Paragraph fett sein soll)
    private Paragraph createBoldParagraph(String text) {
        Paragraph p = createStyledParagraph(text);
        p.getStyle().set("font-weight", "bold");
        return p;
    }

    // Hilfsmethode für fettgedruckte Spans innerhalb von Texten oder Listen
    private Span createBoldSpan(String text) {
        Span span = new Span(text);
        span.getStyle().set("font-weight", "bold");
        return span;
    }
}