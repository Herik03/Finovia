package org.vaadin.example.application.views;

import com.vaadin.flow.theme.lumo.LumoUtility;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.vaadin.example.application.Security.SecurtyService;

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

    /** Das Formular zum Erstellen eines neuen Depots */
    private final FormLayout depotForm;

    /**
     * Konstruktor für die MainView.
     * <p>
     * Initialisiert alle Layout-Komponenten und richtet die Benutzeroberfläche ein.
     * Die Ansicht besteht aus einer Seitenleiste, einem Hauptinhaltsbereich und
     * einem Formular zum Erstellen eines neuen Depots.
     */
    public MainView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Layout-Komponenten initialisieren
        sideNav = new VerticalLayout();
        mainContent = new VerticalLayout();
        contentWrapper = new HorizontalLayout();
        dashboardContent = new VerticalLayout();
        depotForm = new FormLayout();

        // Seitenleiste konfigurieren
        setupSideNav();

        // Hauptinhalt konfigurieren
        setupMainContent();

        // Depot-Formular konfigurieren
        setupDepotForm();

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
        dashboardBtn.addClickListener(e -> UI.getCurrent().navigate("dashboard"));
        Button depotBtn = createNavButton("Depot", VaadinIcon.PIGGY_BANK);
        depotBtn.addClickListener(e -> UI.getCurrent().navigate("depot"));
        Button userBtn = createNavButton("Benutzer", VaadinIcon.USER);
        userBtn.addClickListener(e -> UI.getCurrent().navigate("user"));
//        Button settingsBtn = createNavButton("Einstellungen", VaadinIcon.COG);
//        settingsBtn.addClickListener(e -> UI.getCurrent().navigate("settings"));
        Button logoutBtn = createNavButton("Logout", VaadinIcon.SIGN_OUT);
        logoutBtn.addClickListener(e -> new SecurtyService().logout());
//        var APIBtn = createNavButton("API", VaadinIcon.CODE);
//        APIBtn.addClickListener(e -> UI.getCurrent().navigate("search"));

        verticallayout.add(logo, dashboardBtn, depotBtn, settingsBtn, APIBtn, aktieKaufenBtn);
        verticallayout.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.START);
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
     * Konfiguriert das Formular zum Erstellen eines neuen Depots.
     * <p>
     * Erstellt und konfiguriert ein Formular mit Eingabefeldern für Depot-Name,
     * Depot-Typ und IBAN sowie einem Button zum Speichern. Das Formular wird
     * dem Dashboard-Inhalt hinzugefügt.
     * <p>
     * Bei erfolgreicher Erstellung eines Depots wird eine Erfolgsmeldung angezeigt
     * und die Eingabefelder werden geleert. Bei fehlenden Pflichtfeldern wird eine
     * Fehlermeldung angezeigt.
     */
    private void setupDepotForm() {
        depotForm.setMaxWidth("600px");
        depotForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        TextField depotName = new TextField("Depot-Name");
        depotName.setRequiredIndicatorVisible(true);
        depotName.setPrefixComponent(VaadinIcon.PIGGY_BANK.create());

        Select<String> depotTyp = new Select<>();
        depotTyp.setLabel("Depot-Typ");
        depotTyp.setItems("Aktiendepot", "ETF-Depot", "Gemischtes Depot");
        depotTyp.setRequiredIndicatorVisible(true);

        TextField iban = new TextField("IBAN");
        iban.setRequiredIndicatorVisible(true);


        Button speichernButton = new Button("Depot erstellen", VaadinIcon.CHECK.create());
        speichernButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        speichernButton.addClickListener(e -> {
            if (!depotName.isEmpty() && !depotTyp.isEmpty() && !iban.isEmpty()) {
                Notification.show("Depot erfolgreich erstellt!", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                depotName.clear();
                depotTyp.clear();
                iban.clear();
            } else {
                Notification.show("Bitte alle Pflichtfelder ausfüllen!", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        speichernButton.addClickShortcut(Key.ENTER);

        depotForm.add(new H2("Neues Depot anlegen"), depotName, depotTyp, iban, speichernButton);
        dashboardContent.add(depotForm);
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
