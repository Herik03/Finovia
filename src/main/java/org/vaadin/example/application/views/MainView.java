package org.vaadin.example.application.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.services.DepotService;

import java.util.List;

/**
 * Die Hauptansicht der Finovia-Anwendung, die als Dashboard dient.
 * <p>
 * Diese Klasse stellt die Startseite der Anwendung dar und enthält:
 * <ul>
 *   <li>Eine Navigationsleiste auf der linken Seite</li>
 *   <li>Einen Hauptinhaltsbereich mit Willkommensnachricht</li>
 *   <li>Ein Formular zum Erstellen eines neuen Depots</li>
 * </ul>
 * <p>
 * Die Ansicht ist für alle authentifizierten Benutzer zugänglich.
 * 
 * @author Finovia Team
 * @version 1.0
 */
@Route("uebersicht")
@PageTitle("Finovia - Dashboard")
@PermitAll
public class MainView extends AbstractSideNav {
    
    /** Der Inhaltsbereich des Dashboards */
    private final VerticalLayout dashboardContent;
    
    /** Der Service für Depot-Operationen */
    private final DepotService depotService;
    
    @Autowired
    public MainView(DepotService depotService) {
        super(); // Ruft den Konstruktor der AbstractView auf, der setupSideNav und configureMainContent aufruft
        this.depotService = depotService;
        
        // Dashboard-Content erstellen
        dashboardContent = new VerticalLayout();
        dashboardContent.setWidthFull();
        dashboardContent.setAlignItems(FlexComponent.Alignment.CENTER);
        
        // Kopfzeile
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        H2 title = new H2("Dashboard");
        header.add(title);
        
        // Content hinzufügen
        dashboardContent.add(createWelcomeMessage());
        
        // Zum Hauptinhalt hinzufügen
        addToMainContent(header, dashboardContent);
        
        // Depot-Übersicht konfigurieren
        setupDepotOverview();
    }

    /**
     * Konstruktor für die MainView.
     * <p>
     * Initialisiert alle Layout-Komponenten und richtet die Benutzeroberfläche ein.
     * Die Ansicht besteht aus einer Seitenleiste, einem Hauptinhaltsbereich und
     * einer Übersicht der Depots.
     * 
     * @param depotService Der Service für Depot-Operationen
     */
    /*@Autowired
    public MainView(DepotService depotService) {
        this.depotService = depotService;

        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Layout-Komponenten initialisieren
        sideNav = new VerticalLayout();
        mainContent = new VerticalLayout();
        contentWrapper = new HorizontalLayout();
        dashboardContent = new VerticalLayout();

        // Seitenleiste konfigurieren
        setupSideNav();

        // Hauptinhalt konfigurieren
        setupMainContent();

        // Depot-Übersicht konfigurieren
        setupDepotOverview();

        // Layouts zusammenfügen
        contentWrapper.add(sideNav, mainContent);
        contentWrapper.setSizeFull();
        contentWrapper.setSpacing(false);

        add(contentWrapper);
    }*/



    /**
     * Konfiguriert den Hauptinhaltsbereich der Anwendung.
     * <p>
     * Erstellt und konfiguriert den Hauptinhaltsbereich mit einer Kopfzeile und
     * dem Dashboard-Inhalt, der eine Willkommensnachricht enthält.
     */
    /*private void setupMainContent() {
        mainContent.setSizeFull();
        mainContent.setPadding(true);
        mainContent.setSpacing(true);

        // Kopfzeile
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        H2 title = new H2("Dashboard");
        header.add(title);

        // Dashboard-Content
        dashboardContent.setWidthFull();
        dashboardContent.setAlignItems(FlexComponent.Alignment.CENTER);
        dashboardContent.add(createWelcomeMessage());

        mainContent.add(header, dashboardContent);
    }*/

    /**
     * Konfiguriert die Übersicht der Depots.
     * <p>
     * Zeigt eine Liste der Depots des Nutzers an und bietet einen Button zum Erstellen
     * eines neuen Depots. Die Übersicht wird dem Dashboard-Inhalt hinzugefügt.
     */
    private void setupDepotOverview() {
        // Überschrift
        H2 title = new H2("Meine Depots");

        // Layout für Überschrift
        VerticalLayout depotHeader = new VerticalLayout(title);
        depotHeader.setSpacing(true);
        depotHeader.setPadding(false);
        depotHeader.setAlignItems(FlexComponent.Alignment.CENTER);

        // Depots aus der Datenbank laden und anzeigen
        List<Depot> depots = depotService.getAllDepots();

        // Layout für die Depot-Liste
        VerticalLayout depotList = new VerticalLayout();
        depotList.setSpacing(true);
        depotList.setPadding(false);
        depotList.setAlignItems(FlexComponent.Alignment.CENTER);

        if (depots.isEmpty()) {
            depotList.add(new Span("Keine Depots vorhanden. Erstellen Sie ein neues Depot."));
        } else {
            // Depots anzeigen
            for (Depot depot : depots) {
                Div depotBox = createDepotBox(depot);
                depotList.add(depotBox);
            }
        }

        // Alles zum Dashboard-Content hinzufügen
        dashboardContent.add(depotHeader, depotList);
    }

    /**
     * Erstellt eine Box für die Anzeige eines Depots.
     * 
     * @param depot Das anzuzeigende Depot
     * @return Eine Div-Komponente, die das Depot darstellt
     */
    private Div createDepotBox(Depot depot) {
        Div depotBox = new Div();
        depotBox.addClassName("depot-box");

        // Depot-Name als Überschrift
        depotBox.add(new H3(depot.getName()));

        // Depot-Informationen
        depotBox.add(new Div(new Span("Besitzer: " + depot.getBesitzer().getVollerName())));
        depotBox.add(new Div(new Span("Anzahl Wertpapiere: " + depot.getWertpapiere().size())));

        // Styling
        depotBox.getStyle().set("border", "1px solid #ccc");
        depotBox.getStyle().set("padding", "10px");
        depotBox.getStyle().set("border-radius", "5px");
        depotBox.getStyle().set("cursor", "pointer");
        depotBox.getStyle().set("margin-bottom", "10px");
        depotBox.getStyle().set("background-color", "#f8f8f8");
        depotBox.setWidth("400px");

        // Klick-Handler für Navigation zur Detailansicht
        depotBox.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(DetailedDepotView.class, depot.getDepotId()));
        });

        return depotBox;
    }



    /**
     * Erstellt eine Willkommensnachricht für das Dashboard.
     * <p>
     * Die Nachricht besteht aus einer Überschrift "Willkommen bei Finovia" und
     * einem Untertitel "Ihr persönlicher Broker". Die Nachricht wird zentriert
     * in einem VerticalLayout angezeigt.
     *
     * @return Ein VerticalLayout mit der Willkommensnachricht
     */
    private VerticalLayout createWelcomeMessage() {
        VerticalLayout welcome = new VerticalLayout();
        welcome.setAlignItems(FlexComponent.Alignment.CENTER);
        welcome.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        H2 welcomeText = new H2("Willkommen bei Finovia");
        Paragraph welcomeDesc = new Paragraph("Ihr persönlicher Broker");

        welcome.add(welcomeText, welcomeDesc);
        return welcome;
    }
}