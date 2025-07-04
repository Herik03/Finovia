package org.vaadin.example.application.views;

import org.vaadin.example.application.services.Canvas;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.services.DepotService;
import org.vaadin.example.application.services.NutzerService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Die Hauptansicht der Finovia-Anwendung, die als Dashboard dient.
 *
 * Diese Klasse zeigt dynamisch die Depots des Nutzers mit ChartJS-Charts, die aktuelle Kurswerte aus dem Depot darstellen.
 *
 * @author Finovia Team
 * @version 1.1
 */
@Route("uebersicht")
@PageTitle("Finovia - Dashboard")
@PermitAll
public class MainView extends AbstractSideNav {

    /** Der Inhaltsbereich des Dashboards */
    private final VerticalLayout dashboardContent;

    /** Der Service für Depot-Operationen */
    private final DepotService depotService;

    /** Der Service für Sicherheitsoperationen */
    private final SecurityService securityService;

    /** Der Service für Nutzer-Operationen */
    private final NutzerService nutzerService;


    /**
     * Konstruktor für die MainView.
     * <p>
     * Initialisiert alle Layout-Komponenten und richtet die Benutzeroberfläche ein.
     * Die Ansicht besteht aus einer Seitenleiste, einem Hauptinhaltsbereich und
     * einer Übersicht der Depots.
     * @param depotService Der Service für Depot-Operationen, um Depots zu laden.
     */
    @Autowired
    public MainView(DepotService depotService, SecurityService securityService, NutzerService nutzerService) {
        super(securityService);
        this.depotService = depotService;
        this.securityService = securityService;
        this.nutzerService = nutzerService;

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
     * Konfiguriert die Depot-Übersicht, die alle Depots des Nutzers anzeigt.
     * Jedes Depot wird mit einem Balken-Chart für aktuelle Kurswerte und einem
     * Kreisdiagramm für die Verteilung der Wertpapiere dargestellt.
     */
    private void setupDepotOverview() {
        H2 title = new H2("Meine Depots");

        VerticalLayout depotHeader = new VerticalLayout(title);
        depotHeader.setSpacing(true);
        depotHeader.setPadding(false);
        depotHeader.setAlignItems(FlexComponent.Alignment.CENTER);

        // Depots des aktuellen Nutzers aus der Datenbank laden
        List<Depot> depots;
        UserDetails userDetails = securityService.getAuthenticatedUser();
        if (userDetails != null) {
            Nutzer currentUser = nutzerService.getNutzerByUsername(userDetails.getUsername());
            if (currentUser != null) {
                depots = depotService.getDepotsByNutzerId(currentUser.getId());
            } else {
                depots = List.of(); // Leere Liste, wenn Nutzer nicht gefunden
            }
        } else {
            depots = List.of(); // Leere Liste, wenn nicht authentifiziert
        }

        // Layout für die Liste der Depots
        VerticalLayout depotList = new VerticalLayout();
        depotList.setSpacing(true);
        depotList.setPadding(false);
        depotList.setWidthFull();
        depotList.setAlignItems(FlexComponent.Alignment.STRETCH); // damit volle Breite genutzt wird

        // Prüfen, ob Depots vorhanden sind und diese anzeigen
        if (depots.isEmpty()) {
            depotList.add(new Span("Keine Depots vorhanden. Erstellen Sie ein neues Depot."));
        } else {
            for (Depot depot : depots) {
                // HorizontalLayout für nebeneinander Balken-Chart und PieChart
                HorizontalLayout depotRow = new HorizontalLayout();
                depotRow.setWidthFull();
                depotRow.setSpacing(true);
                depotRow.setAlignItems(Alignment.START);
                depotRow.setDefaultVerticalComponentAlignment(Alignment.START);

                Div depotBox = createDepotBoxWithChart(depot);
                Div pieChart = createDepotPieChart(depot);

                depotBox.setWidth("60%");   // ca. 60% Breite
                pieChart.setWidth("34%");   // ca. 35% Breite

                depotRow.add(depotBox, pieChart);
                depotRow.expand(depotBox); // depotBox wächst mit

                depotList.add(depotRow);
            }
        }

        dashboardContent.add(depotHeader, depotList);
    }

    /**
     * Erstellt eine Box für ein Depot mit einem Balken-Chart für die aktuellen Kurswerte.
     *
     * @param depot Das Depot, das angezeigt werden soll
     * @return Eine Div-Komponente, die das Depot und das Chart enthält
     */
    private Div createDepotBoxWithChart(Depot depot) {
        Div depotBox = new Div();
        depotBox.addClassName("depot-box");

        depotBox.add(new H3(depot.getName()));
        depotBox.add(new Div(new Span("Besitzer: " + depot.getBesitzer().getVollerName())));
        depotBox.add(new Div(new Span("Anzahl Wertpapiere: " + depot.getWertpapiere().size())));

        Canvas canvas = new Canvas("depotChart" + depot.getDepotId(), 400, 200);
        depotBox.add(canvas);

        // Labels und Werte für das Balken-Chart vorbereiten
        List<String> labels = depot.getWertpapiere().stream()
                .map(Wertpapier::getName)
                .map(name -> "\"" + name + "\"") // Labels müssen als Strings in JS
                .toList();

        String labelsJS = "[" + String.join(",", labels) + "]";

        // Aktuelle Werte der Wertpapiere abrufen
        List<Double> values = depot.getWertpapiere().stream()
                .map(this::getAktuellerWert)
                .toList();

        String valuesJS = values.toString(); // z.B. [1.1, 2.3, 3.7]

        // JavaScript-Code für das Chart
        String chartVar = "depotChart_" + depot.getDepotId();
        String js = String.format(
                """
                (function(canvas) {
                    const ctx = canvas.getContext('2d');
                    if (window.%1$s) {
                        window.%1$s.destroy();
                    }
                    
                    window.%1$s = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: %2$s,
                            datasets: [{
                                label: 'Aktueller Kurs',
                                data: %3$s,
                                backgroundColor: 'rgba(54, 162, 235, 0.7)',
                                borderColor: 'rgba(54, 162, 235, 1)',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            scales: {
                                y: { beginAtZero: true }
                            }
                        }
                    });
                })(this);
                """, chartVar,labelsJS, valuesJS);

        canvas.getElement().executeJs(js);

        // Styling bleibt gleich
        depotBox.getStyle().set("border", "1px solid #ccc");
        depotBox.getStyle().set("padding", "10px");
        depotBox.getStyle().set("border-radius", "5px");
        depotBox.getStyle().set("cursor", "pointer");
        depotBox.getStyle().set("margin-bottom", "10px");
        depotBox.getStyle().set("background-color", "#f8f8f8");
        depotBox.setWidth("400px");
        depotBox.getStyle().set("display", "flex");
        depotBox.getStyle().set("flex-direction", "column");
        depotBox.getStyle().set("justify-content", "flex-start");

        depotBox.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(DetailedDepotView.class, depot.getDepotId()));
        });

        return depotBox;
    }

    /**
     * Erstellt ein Kreisdiagramm (Pie Chart) für die Verteilung der Wertpapier-Klassen im Depot.
     *
     * @param depot Das Depot, dessen Verteilung angezeigt werden soll
     * @return Eine Div-Komponente, die das Kreisdiagramm enthält
     */
    private Div createDepotPieChart(Depot depot) {
        Div pieChartBox = new Div();
        pieChartBox.addClassName("piechart-box");

        pieChartBox.add(new H3("Depot Verteilung"));

        Canvas canvas = new Canvas("pieChart" + depot.getDepotId(), 400, 200);
        pieChartBox.add(canvas);

        // Wertpapier-Klassen zählen
        Map<String, Long> klasseCount = depot.getWertpapiere().stream()
                .map(wp -> wp.getClass().getSimpleName()) // z.B. "Aktie", "ETF", "Anleihe"
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));

        // Labels und Werte vorbereiten
        List<String> labels = new ArrayList<>(klasseCount.keySet());
        List<Long> values = labels.stream().map(klasseCount::get).collect(Collectors.toList());

        // JS-Strings für Chart
        String labelsJS = labels.stream()
                .map(l -> "\"" + l + "\"")
                .collect(Collectors.joining(", ", "[", "]"));

        String valuesJS = values.toString();

        String chartVar = "pieChart_" + depot.getDepotId();
        String js = String.format(
                """
                (function(canvas) {
                  const ctx = canvas.getContext('2d');
                  if(window.%1$s) {
                    window.%1$s.destroy();
                  }
                  window.%1$s = new Chart(ctx, {
                    type: 'pie',
                    data: {
                      labels: %2$s,
                      datasets: [{
                        data: %3$s,
                        backgroundColor: [
                          'rgba(255, 99, 132, 0.7)',
                          'rgba(54, 162, 235, 0.7)',
                          'rgba(255, 206, 86, 0.7)',
                          'rgba(75, 192, 192, 0.7)',
                          'rgba(153, 102, 255, 0.7)',
                          'rgba(255, 159, 64, 0.7)'
                        ],
                        borderColor: 'white',
                        borderWidth: 2
                      }]
                    },
                    options: {
                      responsive: true,
                      plugins: {
                        legend: {
                          position: 'right'
                        }
                      }
                    }
                  });
                })(this);
                """,
                chartVar,labelsJS, valuesJS
        );

        canvas.getElement().executeJs(js);

        // Styling
        pieChartBox.getStyle().set("border", "1px solid #ccc");
        pieChartBox.getStyle().set("padding", "10px");
        pieChartBox.getStyle().set("border-radius", "5px");
        pieChartBox.setWidth("400px");
        pieChartBox.getStyle().set("display", "flex");
        pieChartBox.getStyle().set("flex-direction", "column");
        pieChartBox.getStyle().set("justify-content", "flex-start");

        return pieChartBox;
    }

    /**
     * Holt den aktuellen Kurswert eines Wertpapiers.
     * Der Kurswert wird aus dem letzten Kurs des Wertpapiers ermittelt.
     *
     * @param wertpapier Das Wertpapier, dessen aktuellen Kurswert ermittelt werden soll
     * @return Der aktuelle Kurswert oder 0.0, wenn kein Kurs vorhanden ist
     */
    private Double getAktuellerWert(Wertpapier wertpapier) {
        return wertpapier.getKurse().stream()
                .max(Comparator.comparing(Kurs::getDatum))
                .map(Kurs::getKurswert)
                .orElse(0.0);
    }



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
