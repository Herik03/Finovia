package org.vaadin.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.example.application.Security.SecurtyService;

/**
 * Abstrakte Basisklasse für alle Views mit gemeinsamer Seitennavigation.
 * <p>
 * Diese Klasse stellt das grundlegende Layout mit Seitenleiste und Hauptinhaltsbereich
 * bereit, das von allen Views verwendet werden kann.
 */
public abstract class AbstractSideNav extends VerticalLayout {

    /** Die Navigationsleiste auf der linken Seite der Anwendung */
    protected final VerticalLayout sideNav;

    /** Der Hauptinhaltsbereich der Anwendung */
    protected final VerticalLayout mainContent;
    
    /** Ein Container für den Hauptinhaltsbereich, der für das Scrollen verantwortlich ist */
    protected final Div mainContentContainer;

    /** Das Layout, das die Navigationsleiste und den Hauptinhalt umschließt */
    protected final HorizontalLayout contentWrapper;

    /** Die Breite der Seitenleiste in Pixeln */
    private static final String SIDENAV_WIDTH = "250px";
    
    /**
     * Konstruktor für AbstractView.
     * <p>
     * Initialisiert das grundlegende Layout mit Seitenleiste und Hauptinhaltsbereich.
     */
    public AbstractSideNav() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Layout-Komponenten initialisieren
        sideNav = new VerticalLayout();
        mainContent = new VerticalLayout();
        mainContentContainer = new Div();
        contentWrapper = new HorizontalLayout();

        // Seitenleiste konfigurieren
        setupSideNav();

        // Hauptinhalt konfigurieren
        configureMainContent();

        // Container für Hauptinhalt konfigurieren
        mainContentContainer.add(mainContent);
        mainContentContainer.getStyle()
                .set("overflow", "auto")
                .set("height", "100%")
                .set("width", "100%");

        // Layouts zusammenfügen
        contentWrapper.add(mainContentContainer);
        contentWrapper.setWidthFull();
        contentWrapper.setHeightFull();
        contentWrapper.setPadding(false);
        contentWrapper.setSpacing(false);
        contentWrapper.getStyle()
                .set("position", "relative")
                .set("padding-left", SIDENAV_WIDTH); // Abstand für die feste SideNav

        // ContentWrapper hinzufügen
        add(contentWrapper);
        
        // SideNav nach dem ContentWrapper hinzufügen, damit es über dem Content liegt
        add(sideNav);
        
        // Die SideNav mit CSS absolut positionieren
        sideNav.getStyle()
                .set("position", "fixed")
                .set("top", "0")
                .set("left", "0")
                .set("bottom", "0")
                .set("width", SIDENAV_WIDTH)
                .set("z-index", "1000"); // Stellt sicher, dass die Navigation über dem Inhalt liegt
    }

    /**
     * Konfiguriert die Navigationsleiste auf der linken Seite der Anwendung.
     */
    protected void setupSideNav() {
        sideNav.setWidth(SIDENAV_WIDTH);
        sideNav.setHeightFull();
        sideNav.setPadding(false);
        sideNav.setSpacing(false);
        sideNav.getStyle()
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 0 10px rgba(0, 0, 0, 0.1)"); // Schatten hinzufügen
        
        VerticalLayout topLayout = new VerticalLayout();
        VerticalLayout bottomLayout = new VerticalLayout();

        H1 logo = new H1("Finovia");
        logo.getStyle().set("font-size", "var(--lumo-font-size-l)")
             .set("margin", "0")
             .set("padding", "var(--lumo-space-m)");

        Button dashboardBtn = createNavButton("Dashboard", VaadinIcon.DASHBOARD);
        dashboardBtn.addClickListener(e -> UI.getCurrent().navigate(""));
        
        Button depotBtn = createNavButton("Depot", VaadinIcon.PIGGY_BANK);
        depotBtn.addClickListener(e -> UI.getCurrent().navigate("depot"));
        
        Button settingsBtn = createNavButton("Einstellungen", VaadinIcon.COG);
        settingsBtn.addClickListener(e -> UI.getCurrent().navigate(SettingsView.class));
        
        Button apiBtn = createNavButton("Wertpapiere", VaadinIcon.CODE);
        apiBtn.addClickListener(e -> UI.getCurrent().navigate("search"));
        
        Button aktieKaufenBtn = createNavButton("Kaufen", VaadinIcon.CART);
        aktieKaufenBtn.addClickListener(e -> UI.getCurrent().navigate("kaufen"));

        Button userBtn = createNavButton("Benutzer", VaadinIcon.USER);
        userBtn.addClickListener(e -> UI.getCurrent().navigate("user"));
        
        Button logoutBtn = createNavButton("Logout", VaadinIcon.SIGN_OUT);
        logoutBtn.addClickListener(e -> new SecurtyService().logout());

        topLayout.add(logo, dashboardBtn, depotBtn, settingsBtn, apiBtn, aktieKaufenBtn);
        topLayout.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.START);
        topLayout.setPadding(false);
        topLayout.setSpacing(false);

        bottomLayout.add(userBtn, logoutBtn);
        bottomLayout.getStyle().setFlexGrow("1");
        bottomLayout.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.END);
        
        sideNav.add(topLayout, bottomLayout);
    }

    /**
     * Konfiguriert den Hauptinhaltsbereich der Anwendung.
     * <p>
     * Diese Methode sollte von abgeleiteten Klassen überschrieben werden, um
     * den spezifischen Inhalt der View hinzuzufügen.
     */
    protected void configureMainContent() {
        mainContent.setWidthFull();
        mainContent.setPadding(true);
        mainContent.setSpacing(true);
    }

    /**
     * Gibt den Hauptinhaltsbereich zurück.
     * <p>
     * Diese Methode ermöglicht es abgeleiteten Klassen, den Hauptinhaltsbereich
     * zu manipulieren, beispielsweise um Komponenten hinzuzufügen, zu entfernen
     * oder den Inhalt komplett auszutauschen.
     *
     * @return Das VerticalLayout, das den Hauptinhalt enthält
     */
    protected VerticalLayout getMainContent() {
        return mainContent;
    }

    /**
     * Fügt Komponenten zum Hauptinhaltsbereich hinzu.
     * <p>
     * Hilfsmethode, um Komponenten zum Hauptinhaltsbereich hinzuzufügen.
     * 
     * @param components Die hinzuzufügenden Komponenten
     */
    protected void addToMainContent(Component... components) {
        mainContent.add(components);
    }

    /**
     * Leert den Hauptinhaltsbereich und fügt neue Komponenten hinzu.
     * <p>
     * Diese Methode ist nützlich, wenn der gesamte Inhalt ersetzt werden soll,
     * beispielsweise beim Wechsel zwischen verschiedenen Ansichten innerhalb einer View.
     *
     * @param components Die neuen Komponenten, die hinzugefügt werden sollen
     */
    protected void setMainContent(Component... components) {
        mainContent.removeAll();
        mainContent.add(components);
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
    protected Button createNavButton(String text, VaadinIcon icon) {
        Button button = new Button(text, icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        button.setWidthFull();
        button.getStyle()
                .set("padding", "var(--lumo-space-m)")
                .set("text-align", "left");
        return button;
    }
}