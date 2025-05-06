package org.vaadin.example.application.views;

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

@Route(value = "")
@PageTitle("Finovia - Dashboard")
@PermitAll
public class MainView extends VerticalLayout {

    private final VerticalLayout sideNav;
    private final VerticalLayout mainContent;
    private final HorizontalLayout contentWrapper;
    private final VerticalLayout dashboardContent;
    private final FormLayout depotForm;

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

    private void setupSideNav() {
        sideNav.setWidth("250px");
        sideNav.setHeightFull();
        sideNav.setPadding(false);
        sideNav.setSpacing(false);
        sideNav.getStyle().set("background-color", "var(--lumo-contrast-5pct)");

        H1 logo = new H1("Finovia");
        logo.getStyle()
            .set("font-size", "var(--lumo-font-size-l)")
            .set("margin", "0")
            .set("padding", "var(--lumo-space-m)");

        Button dashboardBtn = createNavButton("Dashboard", VaadinIcon.DASHBOARD);
        dashboardBtn.addClickListener(e -> UI.getCurrent().navigate("dashboard"));
        Button depotBtn = createNavButton("Depot", VaadinIcon.PIGGY_BANK);
        depotBtn.addClickListener(e -> UI.getCurrent().navigate("depot"));
        Button userBtn = createNavButton("Benutzer", VaadinIcon.USER);
        userBtn.addClickListener(e -> UI.getCurrent().navigate("user"));
        Button settingsBtn = createNavButton("Einstellungen", VaadinIcon.COG);
        settingsBtn.addClickListener(e -> UI.getCurrent().navigate("settings"));
        Button logoutBtn = createNavButton("Logout", VaadinIcon.SIGN_OUT);
        logoutBtn.addClickListener(e -> new SecurtyService().logout());
        var APIBtn = createNavButton("API", VaadinIcon.CODE);
        APIBtn.addClickListener(e -> UI.getCurrent().navigate("api"));

        sideNav.add(logo, dashboardBtn, depotBtn, userBtn, settingsBtn, logoutBtn, APIBtn);
    }

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

    private Button createNavButton(String text, VaadinIcon icon) {
        Button button = new Button(text, icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        button.setWidthFull();
        button.getStyle()
                .set("padding", "var(--lumo-space-m)")
                .set("text-align", "left");
        return button;
    }

    private VerticalLayout createWelcomeMessage() {
        VerticalLayout welcome = new VerticalLayout();
        welcome.setAlignItems(FlexComponent.Alignment.CENTER);
        welcome.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        
        H2 welcomeText = new H2("Willkommen bei Finovia");
        Paragraph welcomeDesc = new Paragraph("Ihr persönlicher Brocker");
        
        welcome.add(welcomeText, welcomeDesc);
        return welcome;
    }
}