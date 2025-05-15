package org.vaadin.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.services.NutzerService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Route("user")
@PageTitle("Benutzer - Finovia")
@PermitAll
public class UserView extends AbstractSideNav {

    private final NutzerService nutzerService;
    private Nutzer aktuellerNutzer;
    
    private final VerticalLayout profilContainer = new VerticalLayout();
    private final VerticalLayout benachrichtigungenContainer = new VerticalLayout();
    
    @Autowired
    public UserView(NutzerService nutzerService) {
        super(); // Ruft den Konstruktor der Basisklasse auf
        this.nutzerService = nutzerService;
        
        // Hauptüberschrift
        H1 title = new H1("Mein Profil");
        title.addClassNames(LumoUtility.Margin.Bottom.MEDIUM);
        
        // Aktuelle Nutzerdaten laden
        ladeAktuellenNutzer();
        
        // Profilsection erstellen
        erstelleProfilSection();
        
        // Benachrichtigungssection erstellen
        erstelleBenachrichtigungenSection();
        
        // Container für den Hauptinhalt erstellen
        VerticalLayout userContentLayout = new VerticalLayout();
        userContentLayout.setPadding(true);
        userContentLayout.setSpacing(true);
        
        // Inhalte zum Container hinzufügen
        userContentLayout.add(title, profilContainer, benachrichtigungenContainer);
        
        // Container zum Hauptinhaltsbereich hinzufügen
        addToMainContent(userContentLayout);
    }
    
    private void ladeAktuellenNutzer() {
        try {
            // Aktuelle Nutzer-ID aus dem Security Context holen
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            
            // Nutzer aus dem Service laden
            aktuellerNutzer = nutzerService.getNutzerByUsername(username);
            
            if (aktuellerNutzer == null) {
                // Fallback für Entwicklungszwecke - Nutzer mit einer ID laden
                aktuellerNutzer = nutzerService.getNutzerById(new Long(1));
                
                if (aktuellerNutzer == null) {
                    Notification.show("Nutzerdaten konnten nicht geladen werden", 
                            3000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        } catch (Exception e) {
            Notification.show("Fehler beim Laden der Nutzerdaten: " + e.getMessage(), 
                    3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
    
    private void erstelleProfilSection() {
        profilContainer.removeAll();
        profilContainer.setPadding(false);
        profilContainer.setSpacing(true);
        
        H2 profilTitle = new H2("Persönliche Informationen");
        profilTitle.addClassNames(LumoUtility.Margin.Bottom.SMALL, LumoUtility.Margin.Top.MEDIUM);
        
        Div profilCard = new Div();
        profilCard.addClassNames(LumoUtility.Background.BASE, LumoUtility.BoxShadow.SMALL, 
                LumoUtility.BorderRadius.MEDIUM, LumoUtility.Padding.MEDIUM);
        profilCard.setWidthFull();
        
        VerticalLayout profilLayout = new VerticalLayout();
        profilLayout.setPadding(false);
        profilLayout.setSpacing(true);
        
        if (aktuellerNutzer != null) {
            // Formularfelder zum Anzeigen der Nutzerdaten
            TextField vornameField = new TextField("Vorname");
            vornameField.setValue(aktuellerNutzer.getVorname());
            vornameField.setReadOnly(true);
            
            TextField nachnameField = new TextField("Nachname");
            nachnameField.setValue(aktuellerNutzer.getNachname());
            nachnameField.setReadOnly(true);
            
            EmailField emailField = new EmailField("E-Mail");
            emailField.setValue(aktuellerNutzer.getEmail());
            emailField.setReadOnly(true);
            
            TextField benutzernameField = new TextField("Benutzername");
            benutzernameField.setValue(aktuellerNutzer.getUsername());
            benutzernameField.setReadOnly(true);
            
            // Passwort-Anzeige mit Passwort-Ändern-Button
            HorizontalLayout passwortLayout = new HorizontalLayout();
            passwortLayout.setSpacing(true);
            passwortLayout.setAlignItems(FlexComponent.Alignment.BASELINE);

            TextField passwortField = new TextField("Passwort");
            passwortField.setValue("••••••••");
            passwortField.setReadOnly(true);

            Button passwortAendernButton = new Button("Passwort ändern");
            passwortAendernButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            passwortAendernButton.addClickListener(e -> UI.getCurrent().navigate("passwortvergessen"));

            passwortLayout.add(passwortField, passwortAendernButton);
            
            TextField registriertField = new TextField("Registriert seit");
            registriertField.setValue(aktuellerNutzer.getRegistrierungsDatum()
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            registriertField.setReadOnly(true);
            
            TextField depotsField = new TextField("Anzahl Depots");
            depotsField.setValue(String.valueOf(aktuellerNutzer.getDepots().size()));
            depotsField.setReadOnly(true);
            
            Button speichernButton = new Button("Änderungen speichern");
            Button abbrechenButton = new Button("Abbrechen");
            Button bearbeitenButton = new Button("Daten bearbeiten");
            
            // Dann die Buttons konfigurieren
            bearbeitenButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            bearbeitenButton.addClickListener(e -> {
                vornameField.setReadOnly(false);
                nachnameField.setReadOnly(false);
                emailField.setReadOnly(false);


                e.getSource().setVisible(false);
                speichernButton.setVisible(true);
                abbrechenButton.setVisible(true);
            });
            
            speichernButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            speichernButton.setVisible(false);
            speichernButton.addClickListener(e -> {
                // Nutzer aktualisieren
                aktuellerNutzer.setVorname(vornameField.getValue());
                aktuellerNutzer.setNachname(nachnameField.getValue());
                aktuellerNutzer.setEmail(emailField.getValue());
                
                // Nutzer im Service speichern
                nutzerService.speichereNutzer(aktuellerNutzer);
                
                // Felder wieder auf readonly setzen
                vornameField.setReadOnly(true);
                nachnameField.setReadOnly(true);
                emailField.setReadOnly(true);
                
                // Buttons umschalten
                e.getSource().setVisible(false);
                abbrechenButton.setVisible(false);
                bearbeitenButton.setVisible(true);
                
                Notification.show("Profildaten wurden gespeichert", 
                        3000, Notification.Position.BOTTOM_START)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            });
            
            abbrechenButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            abbrechenButton.setVisible(false);
            abbrechenButton.addClickListener(e -> {
                // Ursprüngliche Werte wiederherstellen
                vornameField.setValue(aktuellerNutzer.getVorname());
                nachnameField.setValue(aktuellerNutzer.getNachname());
                emailField.setValue(aktuellerNutzer.getEmail());
                
                // Felder wieder auf readonly setzen
                vornameField.setReadOnly(true);
                nachnameField.setReadOnly(true);
                emailField.setReadOnly(true);
                
                // Buttons umschalten
                e.getSource().setVisible(false);
                speichernButton.setVisible(false);
                bearbeitenButton.setVisible(true);
            });
            
            // Layout für Buttons erstellen
            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.add(bearbeitenButton, speichernButton, abbrechenButton);
            
            // Alle Komponenten zum Layout hinzufügen
            profilLayout.add(
                    vornameField, 
                    nachnameField, 
                    emailField, 
                    benutzernameField, 
                    passwortLayout,
                    registriertField,
                    depotsField,
                    buttonLayout
            );
        } else {
            // Fallback wenn kein Nutzer geladen wurde
            Paragraph errorMsg = new Paragraph("Nutzerdaten konnten nicht geladen werden.");
            profilLayout.add(errorMsg);
        }
        
        profilCard.add(profilLayout);
        profilContainer.add(profilTitle, profilCard);
    }
    
    private void erstelleBenachrichtigungenSection() {
        // Bestehende Methode, keine Änderungen erforderlich
        benachrichtigungenContainer.removeAll();
        benachrichtigungenContainer.setPadding(false);
        benachrichtigungenContainer.setSpacing(true);
        
        H2 benachrichtigungenTitle = new H2("Meine Benachrichtigungen");
        benachrichtigungenTitle.addClassNames(LumoUtility.Margin.Bottom.SMALL, LumoUtility.Margin.Top.MEDIUM);
        
        Div benachrichtigungenCard = new Div();
        benachrichtigungenCard.addClassNames(LumoUtility.Background.BASE, LumoUtility.BoxShadow.SMALL, 
                LumoUtility.BorderRadius.MEDIUM, LumoUtility.Padding.MEDIUM);
        benachrichtigungenCard.setWidthFull();
        
        VerticalLayout benachrichtigungenLayout = new VerticalLayout();
        benachrichtigungenLayout.setPadding(false);
        benachrichtigungenLayout.setSpacing(true);
        
        if (aktuellerNutzer != null) {
            List<String> benachrichtigungen = aktuellerNutzer.getBenachrichtigungen();
            
            if (benachrichtigungen.isEmpty()) {
                Paragraph leereNachricht = new Paragraph("Sie haben keine neuen Benachrichtigungen.");
                benachrichtigungenLayout.add(leereNachricht);
            } else {
                // Button zum Löschen aller Benachrichtigungen
                Button alleLoeschenButton = new Button("Alle löschen");
                alleLoeschenButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
                alleLoeschenButton.addClickListener(e -> {
                    aktuellerNutzer.allesBenachrichtigungenLoeschen();
                    erstelleBenachrichtigungenSection(); // Sektion aktualisieren
                    Notification.show("Alle Benachrichtigungen wurden gelöscht", 
                            3000, Notification.Position.BOTTOM_START);
                });
                
                benachrichtigungenLayout.add(alleLoeschenButton);
                
                // Benachrichtigungen anzeigen
                for (int i = 0; i < benachrichtigungen.size(); i++) {
                    final int index = i;
                    String nachricht = benachrichtigungen.get(i);
                    
                    Div nachrichtBox = new Div();
                    nachrichtBox.addClassNames(
                            LumoUtility.BorderRadius.SMALL,
                            LumoUtility.Padding.SMALL,
                            LumoUtility.Background.CONTRAST_5,
                            LumoUtility.Margin.Vertical.XSMALL
                    );
                    
                    HorizontalLayout nachrichtLayout = new HorizontalLayout();
                    nachrichtLayout.setWidthFull();
                    nachrichtLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
                    nachrichtLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                    
                    Span nachrichtText = new Span(nachricht);
                    
                    Button loeschenButton = new Button("Löschen");
                    loeschenButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_SMALL);
                    loeschenButton.addClickListener(e -> {
                        aktuellerNutzer.benachrichtigungLoeschen(index);
                        erstelleBenachrichtigungenSection(); // Sektion aktualisieren
                    });
                    
                    nachrichtLayout.add(nachrichtText, loeschenButton);
                    nachrichtBox.add(nachrichtLayout);
                    benachrichtigungenLayout.add(nachrichtBox);
                }
            }
        } else {
            // Fallback wenn kein Nutzer geladen wurde
            Paragraph errorMsg = new Paragraph("Benachrichtigungen konnten nicht geladen werden.");
            benachrichtigungenLayout.add(errorMsg);
        }
        
        benachrichtigungenCard.add(benachrichtigungenLayout);
        benachrichtigungenContainer.add(benachrichtigungenTitle, benachrichtigungenCard);
    }
}