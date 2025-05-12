package org.vaadin.example.application.views;

import com.vaadin.flow.theme.lumo.LumoUtility;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.Security.SecurtyService;
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
@Route(value = "")
@PageTitle("Finovia - Dashboard")
@PermitAll
public class MainView extends VerticalLayout {

    /** Die Navigationsleiste auf der linken Seite der Anwendung */
    private final VerticalLayout sideNav;

    /** Der Hauptinhaltsbereich der Anwendung */
    private final VerticalLayout mainContent;

    /** Das Layout, das die Navigationsleiste und den Hauptinhalt umschließt */
    private final HorizontalLayout contentWrapper;

    /** Der Inhaltsbereich des Dashboards */
    private final VerticalLayout dashboardContent;

    /** Der Service für Depot-Operationen */
    private final DepotService depotService;

    /**
     * Konstruktor für die MainView.
     * <p>
     * Initialisiert alle Layout-Komponenten und richtet die Benutzeroberfläche ein.
     * Die Ansicht besteht aus einer Seitenleiste, einem Hauptinhaltsbereich und
     * einer Übersicht der Depots.
     * 
     * @param depotService Der Service für Depot-Operationen
     */
    @Autowired
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
    }

    /**
     * Konfiguriert die Navigationsleiste auf der linken Seite der Anwendung.
     * <p>
     * Erstellt und konfiguriert die Seitenleiste mit dem Logo und Navigationsbuttons
     * für verschiedene Bereiche der Anwendung (Dashboard, Depot, Benutzer, Einstellungen,
     * Logout und API).
     */
    private void setupSideNav() {
        sideNav.setWidth("250px");
        sideNav.setHeightFull();
        sideNav.setPadding(false);
        sideNav.setSpacing(false);
        sideNav.getStyle().set("background-color", "var(--lumo-contrast-5pct)");
        VerticalLayout verticallayout = new VerticalLayout();
        VerticalLayout btmLayout = new VerticalLayout();

        H1 logo = new H1("Finovia");
        logo.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0").set("padding", "var(--lumo-space-m)");

        Button dashboardBtn = createNavButton("Dashboard", VaadinIcon.DASHBOARD);
        dashboardBtn.addClickListener(e -> UI.getCurrent().navigate(""));
        
        Button depotBtn = createNavButton("Depot", VaadinIcon.PIGGY_BANK);
        depotBtn.addClickListener(e -> UI.getCurrent().navigate("depot"));
        
        // Einstellungen-Button mit Dropdown für Support
        Button settingsBtn = createNavButton("Einstellungen", VaadinIcon.COG);
        settingsBtn.addClickListener(e -> {
            UI.getCurrent().navigate(SettingsView.class);
        });
        
        Button APIBtn = createNavButton("API", VaadinIcon.CODE);
        APIBtn.addClickListener(e -> UI.getCurrent().navigate("search"));
        
        Button userBtn = createNavButton("Benutzer", VaadinIcon.USER);
        userBtn.addClickListener(e -> UI.getCurrent().navigate("user"));
        
        Button logoutBtn = createNavButton("Logout", VaadinIcon.SIGN_OUT);
        logoutBtn.addClickListener(e -> new SecurtyService().logout());

        verticallayout.add(logo, dashboardBtn, depotBtn, settingsBtn, APIBtn);
        verticallayout.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.START);
        verticallayout.setPadding(false);
        verticallayout.setSpacing(false);
        
        btmLayout.add(userBtn, logoutBtn);
        btmLayout.getStyle().setFlexGrow("1");
        btmLayout.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.END);
        sideNav.add(verticallayout, btmLayout);
    }

    /**
     * Konfiguriert den Hauptinhaltsbereich der Anwendung.
     * <p>
     * Erstellt und konfiguriert den Hauptinhaltsbereich mit einer Kopfzeile und
     * dem Dashboard-Inhalt, der eine Willkommensnachricht enthält.
     */
    private void setupMainContent() {
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
    }

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
     * Erstellt einen Navigationsbutton für die Seitenleiste.
     * <p>
     * Der Button wird mit dem angegebenen Text und Icon erstellt und mit einem
     * speziellen Stil für die Seitenleiste versehen.
     *
     * @param text Der anzuzeigende Text des Buttons
     * @param icon Das zu verwendende Vaadin-Icon
     * @return Ein konfigurierter Button für die Seitenleiste
     */
    private Button createNavButton(String text, VaadinIcon icon) {
        Button button = new Button(text, icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        button.setWidthFull();
        button.getStyle()
                .set("padding", "var(--lumo-space-m)")
                .set("text-align", "left");
        return button;
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