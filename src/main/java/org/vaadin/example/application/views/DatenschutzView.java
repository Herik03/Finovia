package org.vaadin.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div; // Für Listenstruktur
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * Vaadin View für die Datenschutzerklärung der fiktiven Trading-App "Finovia".
 * Dieser View enthält detaillierte Informationen zur Datenverarbeitung gemäß DSGVO.
 */
@Route("datenschutz") // Definiert die URL-Route für diesen View
@PageTitle("Datenschutzerklärung - Finovia") // Setzt den Titel der Browser-Seite
@AnonymousAllowed
public class DatenschutzView extends VerticalLayout {

    public DatenschutzView() {
        // Setzt grundlegende Styling-Eigenschaften für das Layout
        setSpacing(false); // Kein zusätzlicher Abstand zwischen den Komponenten
        setPadding(true); // Innenabstand für das Layout
        setSizeFull(); // Nimmt die gesamte verfügbare Größe ein
        setAlignItems(Alignment.CENTER); // Zentriert den Inhalt horizontal

        // Hauptcontainer für die Datenschutzerklärung, um eine maximale Breite zu setzen
        Div datenschutzContainer = new Div();
        datenschutzContainer.setWidth("80%"); // Setzt die Breite auf 80% des übergeordneten Elements
        datenschutzContainer.setMaxWidth("900px"); // Maximale Breite für bessere Lesbarkeit auf großen Bildschirmen
        add(datenschutzContainer);

        // Titel der Datenschutzerklärung
        H1 title = new H1("Datenschutzerklärung für die Finovia Trading App");
        datenschutzContainer.add(title);

        Paragraph introText = new Paragraph("Wir begrüßen Sie bei Finovia! Der Schutz Ihrer personenbezogenen Daten ist für uns von höchster Bedeutung, insbesondere im Umgang mit sensiblen Finanzdaten. Diese Datenschutzerklärung informiert Sie umfassend über die Art, den Umfang und den Zweck der Verarbeitung Ihrer personenbezogenen Daten im Rahmen der Nutzung unserer mobilen Trading-App und der damit verbundenen Dienstleistungen. Wir verarbeiten Ihre Daten ausschließlich im Einklang mit den Bestimmungen der Datenschutz-Grundverordnung (DSGVO), des Bundesdatenschutzgesetzes (BDSG) sowie aller relevanten Finanzmarktgesetze (z.B. Geldwäschegesetz, Wertpapierhandelsgesetz).");
        datenschutzContainer.add(introText);

        // --- Abschnitt: 1. Name und Kontaktdaten des Verantwortlichen ---
        H2 section1Title = new H2("1. Name und Kontaktdaten des Verantwortlichen");
        datenschutzContainer.add(section1Title);

        datenschutzContainer.add(new Paragraph("Verantwortlich im Sinne der DSGVO und anderer nationaler Datenschutzgesetze ist die fiktive:"));

        Div contactInfo = new Div();
        contactInfo.add(new Paragraph("<strong>Finovia GmbH</strong> (fiktiv)"));
        contactInfo.add(new Paragraph("Vertreten durch die Geschäftsführung: [Name der Geschäftsführung, fiktiv]"));
        contactInfo.add(new Paragraph("Handelsregisternummer: HRB 12345 (fiktiv)"));
        contactInfo.add(new Paragraph("[Fiktive Straße und Hausnummer]"));
        contactInfo.add(new Paragraph("[Fiktive PLZ und Ort]"));
        contactInfo.add(new Paragraph("Deutschland"));
        contactInfo.add(new Paragraph("E-Mail: datenschutz@finovia-trading.de (fiktiv)"));
        contactInfo.add(new Paragraph("Telefon: +49 123 4567890 (fiktiv)"));
        datenschutzContainer.add(contactInfo);

        datenschutzContainer.add(new Paragraph("<strong>Datenschutzbeauftragter:</strong>"));
        Div dsbInfo = new Div();
        dsbInfo.add(new Paragraph("Da wir in erheblichem Umfang personenbezogene Daten verarbeiten, haben wir einen Datenschutzbeauftragten bestellt. Sie erreichen diesen unter:"));
        dsbInfo.add(new Paragraph("[Name des Datenschutzbeauftragten, fiktiv]"));
        dsbInfo.add(new Paragraph("c/o Finovia GmbH"));
        dsbInfo.add(new Paragraph("[Fiktive Straße und Hausnummer]"));
        dsbInfo.add(new Paragraph("[Fiktive PLZ und Ort]"));
        dsbInfo.add(new Paragraph("E-Mail: datenschutzbeauftragter@finovia-trading.de (fiktiv)"));
        datenschutzContainer.add(dsbInfo);

        // --- Abschnitt: 2. Begriffsbestimmungen ---
        H2 section2Title = new H2("2. Begriffsbestimmungen");
        datenschutzContainer.add(section2Title);
        datenschutzContainer.add(new Paragraph("Diese Datenschutzerklärung verwendet die Begriffe der DSGVO. Eine detaillierte Erläuterung finden Sie unter "));
        Anchor gdprLink = new Anchor("https://gdpr-info.eu/art-4-gdpr/", "Art. 4 DSGVO");
        gdprLink.setTarget("_blank"); // Öffnet Link in neuem Tab
        datenschutzContainer.add(gdprLink);
        datenschutzContainer.add(new Paragraph(". Beispiele:"));

        Div definitionsList = new Div();
        definitionsList.add(new Paragraph("• <strong>Personenbezogene Daten:</strong> Informationen, die sich auf eine identifizierte oder identifizierbare natürliche Person beziehen (z.B. Name, Adresse, E-Mail-Adresse, Finanzdaten)."));
        definitionsList.add(new Paragraph("• <strong>Besondere Kategorien personenbezogener Daten:</strong> Daten, aus denen die rassische und ethnische Herkunft, politische Meinungen, religiöse oder weltanschauliche Überzeugungen oder die Gewerkschaftszugehörigkeit hervorgehen, sowie genetische Daten, biometrische Daten zur eindeutigen Identifizierung, Gesundheitsdaten oder Daten zum Sexualleben oder zur sexuellen Orientierung (relevant z.B. für Biometrie-Login)."));
        definitionsList.add(new Paragraph("• <strong>Verarbeitung:</strong> Jeder Vorgang im Zusammenhang mit personenbezogenen Daten (Erheben, Speichern, Nutzen, Übermitteln, Löschen)."));
        datenschutzContainer.add(definitionsList);

        // --- Abschnitt: 3. Datenverarbeitung im Rahmen unserer Dienste ---
        H2 section3Title = new H2("3. Datenverarbeitung im Rahmen unserer Dienste");
        datenschutzContainer.add(section3Title);
        datenschutzContainer.add(new Paragraph("Die Verarbeitung Ihrer Daten erfolgt stets auf einer gesetzlichen Grundlage und zu klar definierten Zwecken."));

        // 3.1. Eröffnung und Verwaltung des Trading-Kontos
        H3 subSection3_1Title = new H3("3.1. Eröffnung und Verwaltung des Trading-Kontos (Onboarding & KYC)");
        datenschutzContainer.add(subSection3_1Title);
        datenschutzContainer.add(new Paragraph("Zur Eröffnung Ihres Trading-Kontos und zur Einhaltung gesetzlicher Vorschriften (insbesondere Geldwäschegesetz - GWG, Wertpapierhandelsgesetz - WpHG) erheben wir folgende Daten:"));
        Div onboardingList = new Div();
        onboardingList.add(new Paragraph("• <strong>Identifikationsdaten:</strong> Vorname, Nachname, Geburtsdatum, Geburtsort, Staatsangehörigkeit, Adresse, E-Mail-Adresse, Telefonnummer."));
        onboardingList.add(new Paragraph("• <strong>Legitimationsdaten:</strong> Kopien von Ausweisdokumenten (Personalausweis, Reisepass), ggf. Video-Ident-Verfahren (inkl. Aufzeichnung)."));
        onboardingList.add(new Paragraph("• <strong>Steuerliche Daten:</strong> Steuer-Identifikationsnummer, Ansässigkeitsstaat für steuerliche Zwecke."));
        onboardingList.add(new Paragraph("• <strong>Finanzielle Daten:</strong> Bankverbindung (IBAN, BIC) für Ein- und Auszahlungen."));
        onboardingList.add(new Paragraph("• <strong>Kenntnisse und Erfahrungen:</strong> Angaben zu Ihren Erfahrungen und Kenntnissen im Wertpapierhandel sowie Ihre finanziellen Verhältnisse und Anlageziele zur Erstellung Ihres Anlegerprofils (MiFID II)."));
        onboardingList.add(new Paragraph("• <strong>Risikoprofilierung:</strong> Daten zur Bewertung Ihres Risikoprofils gemäß MiFID II."));
        onboardingList.add(new Paragraph("• <strong>PEP-Prüfung:</strong> Prüfung, ob Sie eine politisch exponierte Person sind."));
        datenschutzContainer.add(onboardingList);
        datenschutzContainer.add(new Paragraph("<strong>Zweck:</strong> Erfüllung der vertraglichen Pflichten zur Führung des Trading-Kontos (Art. 6 Abs. 1 lit. b DSGVO), Erfüllung gesetzlicher Pflichten (Art. 6 Abs. 1 lit. c DSGVO), insbesondere zur Geldwäscheprävention (§ 10 ff. GWG), Know-Your-Customer (KYC)-Prozesse, Geeignetheitsprüfung gemäß WpHG."));
        datenschutzContainer.add(new Paragraph("<strong>Speicherdauer:</strong> Die Daten werden gemäß den gesetzlichen Aufbewahrungsfristen (oft 5-10 Jahre nach Beendigung der Geschäftsbeziehung) gespeichert."));

        // 3.2. Abwicklung von Transaktionen und Depotführung
        H3 subSection3_2Title = new H3("3.2. Abwicklung von Transaktionen und Depotführung");
        datenschutzContainer.add(subSection3_2Title);
        datenschutzContainer.add(new Paragraph("Zur Durchführung Ihrer Handelsaufträge und zur Führung Ihres Depots verarbeiten wir:"));
        Div transactionsList = new Div();
        transactionsList.add(new Paragraph("• <strong>Transaktionsdaten:</strong> Kauf- und Verkaufsaufträge, Orderdetails (Wertpapier, Stückzahl, Preis, Zeitpunkt), Ausführungsdaten, Transaktionshistorie, Gewinne/Verluste."));
        transactionsList.add(new Paragraph("• <strong>Depotdaten:</strong> Art und Anzahl der gehaltenen Wertpapiere, deren aktueller Wert, Performance-Daten."));
        transactionsList.add(new Paragraph("• <strong>Zahlungsdaten:</strong> Details zu Ein- und Auszahlungen (Betrag, Zeitpunkt, verwendeter Zahlungsdienstleister)."));
        datenschutzContainer.add(transactionsList);
        datenschutzContainer.add(new Paragraph("<strong>Zweck:</strong> Erfüllung des Vertrages zur Durchführung von Wertpapiergeschäften (Art. 6 Abs. 1 lit. b DSGVO), Erfüllung gesetzlicher Pflichten (Art. 6 Abs. 1 lit. c DSGVO, z.B. Meldepflichten gegenüber der BaFin, Steuerbehörden)."));
        datenschutzContainer.add(new Paragraph("<strong>Empfänger:</strong> Börsen, Handelsplätze, Clearingstellen, Banken, Zahlungsdienstleister, gegebenenfalls externe Depotbanken (falls Finovia selbst keine Banklizenz besitzt)."));
        datenschutzContainer.add(new Paragraph("<strong>Speicherdauer:</strong> Entsprechend den gesetzlichen Aufbewahrungspflichten für Geschäfts- und Steuerunterlagen."));

        // 3.3. App-Nutzungsdaten und technische Informationen
        H3 subSection3_3Title = new H3("3.3. App-Nutzungsdaten und technische Informationen");
        datenschutzContainer.add(subSection3_3Title);
        datenschutzContainer.add(new Paragraph("Bei jeder Nutzung unserer App werden technische Informationen erfasst, um die Funktionalität, Sicherheit und Stabilität zu gewährleisten:"));
        Div appUsageList = new Div();
        appUsageList.add(new Paragraph("• <strong>Gerätedaten:</strong> Gerätekennung, Betriebssystem und -version, Spracheinstellungen."));
        appUsageList.add(new Paragraph("• <strong>Zugriffsdaten:</strong> IP-Adresse, Datum und Uhrzeit des Zugriffs, übertragene Datenmenge, Fehlermeldungen."));
        appUsageList.add(new Paragraph("• <strong>Nutzungsdaten:</strong> Funktionen, die Sie in der App verwenden, Interaktionen mit der Benutzeroberfläche (anonymisiert oder pseudonymisiert zur Fehleranalyse und Verbesserung)."));
        datenschutzContainer.add(appUsageList);
        datenschutzContainer.add(new Paragraph("<strong>Zweck:</strong> Bereitstellung und Sicherstellung der Funktionalität der App, Fehlerbehebung, IT-Sicherheit (Art. 6 Abs. 1 lit. f DSGVO - berechtigtes Interesse)."));
        datenschutzContainer.add(new Paragraph("<strong>Speicherdauer:</strong> IP-Adressen werden nach 7 Tagen anonymisiert/gelöscht. Nutzungsdaten werden pseudonymisiert gespeichert, solange sie für die Fehleranalyse und Optimierung relevant sind."));

        // 3.4. Kontakt und Support
        H3 subSection3_4Title = new H3("3.4. Kontakt und Support");
        datenschutzContainer.add(subSection3_4Title);
        datenschutzContainer.add(new Paragraph("Wenn Sie unseren Kundenservice über die App-Chatfunktion, E-Mail oder Telefon kontaktieren, verarbeiten wir Ihre Kommunikationsdaten und die in Ihrer Anfrage enthaltenen Informationen zur Bearbeitung Ihres Anliegens."));
        datenschutzContainer.add(new Paragraph("<strong>Zweck:</strong> Bearbeitung Ihrer Support-Anfragen und zur Erfüllung von vertraglichen Pflichten (Art. 6 Abs. 1 lit. b DSGVO) oder auf Basis unseres berechtigten Interesses an einem effektiven Kundenservice (Art. 6 Abs. 1 lit. f DSGVO)."));
        datenschutzContainer.add(new Paragraph("<strong>Speicherdauer:</strong> Die Daten werden gespeichert, solange sie für die Bearbeitung der Anfrage und zur Erfüllung rechtlicher Pflichten (z.B. Dokumentationspflichten) erforderlich sind."));

        // 3.5. Biometrische Authentifizierung
        H3 subSection3_5Title = new H3("3.5. Biometrische Authentifizierung (Face ID / Fingerprint)");
        datenschutzContainer.add(subSection3_5Title);
        datenschutzContainer.add(new Paragraph("Wenn Sie sich für die biometrische Authentifizierung (z.B. Face ID, Fingerprint) entscheiden, um den Login in die App zu vereinfachen, werden diese biometrischen Daten ausschließlich auf Ihrem Gerät verarbeitet und gespeichert. Wir als Finovia haben <strong>keinen Zugriff</strong> auf Ihre biometrischen Daten. Die App erhält lediglich eine Bestätigung von Ihrem Betriebssystem, ob die Authentifizierung erfolgreich war."));
        datenschutzContainer.add(new Paragraph("<strong>Zweck:</strong> Vereinfachung des Logins auf Grundlage Ihrer freiwilligen Einwilligung (Art. 6 Abs. 1 lit. a DSGVO i.V.m. Art. 9 Abs. 2 lit. a DSGVO) und Ihrer expliziten Aktivierung in den App-Einstellungen."));
        datenschutzContainer.add(new Paragraph("<strong>Speicherdauer:</strong> Die Daten verbleiben auf Ihrem Gerät und werden von uns nicht gespeichert."));

        // 3.6. Push-Benachrichtigungen
        H3 subSection3_6Title = new H3("3.6. Push-Benachrichtigungen");
        datenschutzContainer.add(subSection3_6Title);
        datenschutzContainer.add(new Paragraph("Wenn Sie Push-Benachrichtigungen aktivieren, um z.B. über Kursalarme oder wichtige App-Updates informiert zu werden, wird eine gerätespezifische Token-ID an den Push-Dienstleister (z.B. Apple Push Notification Service, Google Firebase Cloud Messaging) übermittelt. Diese Token-ID ist nicht direkt personenbezogen und dient ausschließlich dazu, die Benachrichtigungen an das richtige Gerät zu senden."));
        datenschutzContainer.add(new Paragraph("<strong>Zweck:</strong> Bereitstellung des von Ihnen gewünschten Dienstes auf Grundlage Ihrer Einwilligung (Art. 6 Abs. 1 lit. a DSGVO)."));
        datenschutzContainer.add(new Paragraph("<strong>Widerruf:</strong> Sie können Push-Benachrichtigungen jederzeit in den Einstellungen Ihres Geräts oder der App deaktivieren."));

        // 3.7. Marketing und Personalisierung (optional)
        H3 subSection3_7Title = new H3("3.7. Marketing und Personalisierung (optional)");
        datenschutzContainer.add(subSection3_7Title);
        datenschutzContainer.add(new Paragraph("Soweit Sie uns eine separate Einwilligung erteilt haben (z.B. im Rahmen des Registrierungsprozesses oder in den App-Einstellungen), verwenden wir Ihre Daten für:"));
        Div marketingList = new Div();
        marketingList.add(new Paragraph("• <strong>Direktmarketing:</strong> Zusendung von Informationen über neue Produkte, Dienstleistungen oder Aktionen von Finovia per E-Mail oder App-Benachrichtigung."));
        marketingList.add(new Paragraph("• <strong>Personalisierung:</strong> Anpassung des App-Erlebnisses und Anzeige relevanter Inhalte oder Produkte auf Basis Ihrer Nutzungsgewohnheiten und Ihres Anlageprofils (hierbei kann es sich um automatisiertes Profiling handeln, siehe Punkt 5)."));
        datenschutzContainer.add(marketingList);
        datenschutzContainer.add(new Paragraph("<strong>Zweck:</strong> Marketing und Personalisierung auf Grundlage Ihrer Einwilligung (Art. 6 Abs. 1 lit. a DSGVO)."));
        datenschutzContainer.add(new Paragraph("<strong>Widerruf:</strong> Sie können Ihre Einwilligung jederzeit mit Wirkung für die Zukunft in den App-Einstellungen oder durch eine E-Mail an unseren Datenschutzbeauftragten widerrufen."));

        // --- Abschnitt: 4. Empfänger von Daten und Drittlandtransfers ---
        H2 section4Title = new H2("4. Empfänger von Daten und Drittlandtransfers");
        datenschutzContainer.add(section4Title);
        datenschutzContainer.add(new Paragraph("Um unsere Dienste anbieten zu können, arbeiten wir mit ausgewählten Dienstleistern zusammen, die Zugriff auf personenbezogene Daten haben können. Dies sind:"));
        Div recipientsList = new Div();
        recipientsList.add(new Paragraph("• <strong>Banken und Finanzdienstleister:</strong> Partnerbanken für Depotführung, Börsen, Handelsplätze, Clearingstellen, Zahlungsdienstleister."));
        recipientsList.add(new Paragraph("• <strong>IT-Dienstleister:</strong> Für Hosting, Cloud-Services, App-Entwicklung und -Wartung, Datenanalyse, Support-Software (z.B. für Chat-Funktionen)."));
        recipientsList.add(new Paragraph("• <strong>Identifizierungsdienstleister:</strong> Für KYC-Prozesse (z.B. Video-Ident-Anbieter)."));
        recipientsList.add(new Paragraph("• <strong>Behörden und Aufsichtsinstanzen:</strong> BaFin, Bundesbank, Finanzämter, Strafverfolgungsbehörden im Rahmen unserer gesetzlichen Verpflichtungen."));
        recipientsList.add(new Paragraph("• <strong>Wirtschaftsprüfer, Rechtsanwälte, Berater:</strong> Zur Erfüllung unserer Pflichten und zur Wahrnehmung unserer berechtigten Interessen."));
        datenschutzContainer.add(recipientsList);
        datenschutzContainer.add(new Paragraph("Soweit Daten an Dienstleister außerhalb des Europäischen Wirtschaftsraums (EWR) übermittelt werden, stellen wir sicher, dass ein angemessenes Datenschutzniveau gewährleistet ist (z.B. durch EU-Standardvertragsklauseln, Angemessenheitsbeschlüsse oder Ihre ausdrückliche Einwilligung)."));

        // --- Abschnitt: 5. Automatisierte Entscheidungsfindung und Profiling ---
        H2 section5Title = new H2("5. Automatisierte Entscheidungsfindung und Profiling");
        datenschutzContainer.add(section5Title);
        datenschutzContainer.add(new Paragraph("Im Rahmen unserer Dienste nutzen wir teilweise automatisierte Entscheidungen und Profiling, insbesondere zur:"));
        Div automatedDecisionList = new Div();
        automatedDecisionList.add(new Paragraph("• <strong>Geldwäsche- und Betrugsprävention:</strong> Systeme prüfen automatisch Transaktionen und Verhaltensmuster auf Auffälligkeiten, die auf Geldwäsche oder Betrug hindeuten könnten."));
        automatedDecisionList.add(new Paragraph("• <strong>Geeignetheits- und Angemessenheitsprüfung (MiFID II):</strong> Basierend auf Ihren Angaben zu Kenntnissen, Erfahrungen und finanziellen Verhältnissen wird automatisiert ein Anlegerprofil erstellt, um die Geeignetheit von Finanzinstrumenten für Sie zu bewerten."));
        automatedDecisionList.add(new Paragraph("• <strong>Personalisierung (falls eingewilligt):</strong> Algorithmen können Ihre Nutzungsgewohnheiten analysieren, um Ihnen personalisierte Inhalte oder Produkte anzuzeigen."));
        datenschutzContainer.add(automatedDecisionList);
        datenschutzContainer.add(new Paragraph("<strong>Ihre Rechte:</strong> Sie haben das Recht, nicht einer ausschließlich auf einer automatisierten Verarbeitung – einschließlich Profiling – beruhenden Entscheidung unterworfen zu werden, die Ihnen gegenüber rechtliche Wirkung entfaltet oder Sie in ähnlicher Weise erheblich beeinträchtigt (Art. 22 DSGVO). Sie können in diesen Fällen das Eingreifen einer Person von Finovia verlangen, Ihren eigenen Standpunkt darlegen und die Entscheidung anfechten."));

        // --- Abschnitt: 6. Ihre Rechte als betroffene Person ---
        H2 section6Title = new H2("6. Ihre Rechte als betroffene Person");
        datenschutzContainer.add(section6Title);
        datenschutzContainer.add(new Paragraph("Sie haben uns gegenüber folgende Rechte hinsichtlich Ihrer personenbezogenen Daten:"));

        Div rightsList = new Div();
        rightsList.add(new Paragraph("1. <strong>Auskunft (Art. 15 DSGVO):</strong> Über die von uns verarbeiteten Daten, Verarbeitungszwecke, Datenkategorien, Empfänger, Speicherdauer, Herkunft der Daten."));
        rightsList.add(new Paragraph("2. <strong>Berichtigung (Art. 16 DSGVO):</strong> Korrektur unrichtiger oder Vervollständigung unvollständiger Daten."));
        rightsList.add(new Paragraph("3. <strong>Löschung (Art. 17 DSGVO):</strong> Recht auf \"Vergessenwerden\", soweit keine gesetzlichen Aufbewahrungspflichten entgegenstehen."));
        rightsList.add(new Paragraph("4. <strong>Einschränkung der Verarbeitung (Art. 18 DSGVO):</strong> Daten dürfen nur noch gespeichert, aber nicht weiter verarbeitet werden."));
        rightsList.add(new Paragraph("5. <strong>Datenübertragbarkeit (Art. 20 DSGVO):</strong> Erhalt Ihrer Daten in einem strukturierten, gängigen und maschinenlesbaren Format oder Übermittlung an einen anderen Verantwortlichen."));
        rightsList.add(new Paragraph("6. <strong>Widerruf von Einwilligungen (Art. 7 Abs. 3 DSGVO):</strong> Widerruf jederzeit mit Wirkung für die Zukunft."));
        rightsList.add(new Paragraph("7. <strong>Widerspruch (Art. 21 DSGVO):</strong> Widerspruch gegen die Verarbeitung Ihrer Daten, insbesondere bei Direktmarketing."));
        rightsList.add(new Paragraph("8. <strong>Beschwerderecht bei einer Aufsichtsbehörde (Art. 77 DSGVO):</strong> Wenn Sie der Ansicht sind, dass die Verarbeitung Ihrer Daten gegen die DSGVO verstößt. Die für uns zuständige Aufsichtsbehörde ist:"));
        datenschutzContainer.add(rightsList);

        Div aufsichtsbehoerdeInfo = new Div();
        aufsichtsbehoerdeInfo.add(new Paragraph("Landesbeauftragte für Datenschutz und Informationsfreiheit Nordrhein-Westfalen"));
        aufsichtsbehoerdeInfo.add(new Paragraph("Postfach 20 04 44"));
        aufsichtsbehoerdeInfo.add(new Paragraph("40102 Düsseldorf"));
        aufsichtsbehoerdeInfo.add(new Paragraph("Telefon: 0211/38424-0"));
        aufsichtsbehoerdeInfo.add(new Paragraph("E-Mail: poststelle@ldi.nrw.de"));
        datenschutzContainer.add(aufsichtsbehoerdeInfo);

        datenschutzContainer.add(new Paragraph("Zur Ausübung Ihrer Rechte kontaktieren Sie bitte unseren Datenschutzbeauftragten unter der oben genannten E-Mail-Adresse."));

        // --- Abschnitt: 7. Datensicherheit ---
        H2 section7Title = new H2("7. Datensicherheit");
        datenschutzContainer.add(section7Title);
        datenschutzContainer.add(new Paragraph("Wir setzen geeignete technische und organisatorische Sicherheitsmaßnahmen nach dem Stand der Technik ein, um Ihre Daten gegen zufällige oder vorsätzliche Manipulationen, teilweisen oder vollständigen Verlust, Zerstörung oder gegen den unbefugten Zugriff Dritter zu schützen. Unsere Sicherheitsmaßnahmen werden entsprechend der technologischen Entwicklung fortlaufend verbessert."));
        datenschutzContainer.add(new Paragraph("Dazu gehören u.a. die Verschlüsselung der Datenübertragung (TLS/SSL), Zugriffskontrollen, Pseudonymisierung/Anonymisierung, regelmäßige Backups und Audits."));

        // --- Abschnitt: 8. Aktualität und Änderungen dieser Datenschutzerklärung ---
        H2 section8Title = new H2("8. Aktualität und Änderungen dieser Datenschutzerklärung");
        datenschutzContainer.add(section8Title);
        datenschutzContainer.add(new Paragraph("Diese Datenschutzerklärung ist aktuell gültig und hat den Stand Mai 2025."));
        datenschutzContainer.add(new Paragraph("Durch die Weiterentwicklung unserer App und Angebote oder aufgrund geänderter gesetzlicher bzw. behördlicher Vorgaben kann es notwendig werden, diese Datenschutzerklärung anzupassen. Die jeweils aktuelle Datenschutzerklärung kann jederzeit in der Finovia App unter [Menüpunkt oder Link] sowie auf unserer Webseite unter [Link zur Datenschutzerklärung] von Ihnen abgerufen und ausgedruckt werden."));
    }
}