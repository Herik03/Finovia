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
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.services.DepotService;

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

    private final VerticalLayout dashboardContent;

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
        super();
        this.depotService = depotService;

        dashboardContent = new VerticalLayout();
        dashboardContent.setWidthFull();
        dashboardContent.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        H2 title = new H2("Dashboard");
        header.add(title);

        dashboardContent.add(createWelcomeMessage());

        addToMainContent(header, dashboardContent);

        setupDepotOverview();
    }

    private void setupDepotOverview() {
        H2 title = new H2("Meine Depots");

        VerticalLayout depotHeader = new VerticalLayout(title);
        depotHeader.setSpacing(true);
        depotHeader.setPadding(false);
        depotHeader.setAlignItems(FlexComponent.Alignment.CENTER);

        List<Depot> depots = depotService.getAllDepots();

        VerticalLayout depotList = new VerticalLayout();
        depotList.setSpacing(true);
        depotList.setPadding(false);
        depotList.setAlignItems(FlexComponent.Alignment.STRETCH); // damit volle Breite genutzt wird

        if (depots.isEmpty()) {
            depotList.add(new Span("Keine Depots vorhanden. Erstellen Sie ein neues Depot."));
        } else {
            for (Depot depot : depots) {
                // HorizontalLayout für nebeneinander Balken-Chart und PieChart
                HorizontalLayout depotRow = new HorizontalLayout();
                depotRow.setWidthFull();
                depotRow.setSpacing(true);

                Div depotBox = createDepotBoxWithChart(depot);
                Div pieChart = createDepotPieChart(depot);

                depotBox.setWidth("60%");   // ca. 60% Breite
                pieChart.setWidth("35%");   // ca. 35% Breite

                depotRow.add(depotBox, pieChart);
                depotRow.expand(depotBox); // depotBox wächst mit

                depotList.add(depotRow);
            }
        }

        dashboardContent.add(depotHeader, depotList);
    }

    private Div createDepotBoxWithChart(Depot depot) {
        Div depotBox = new Div();
        depotBox.addClassName("depot-box");

        depotBox.add(new H3(depot.getName()));
        depotBox.add(new Div(new Span("Besitzer: " + depot.getBesitzer().getVollerName())));
        depotBox.add(new Div(new Span("Anzahl Wertpapiere: " + depot.getWertpapiere().size())));

        Canvas canvas = new Canvas("depotChart" + depot.getDepotId(), 400, 200);
        depotBox.add(canvas);

        List<String> labels = depot.getWertpapiere().stream()
                .map(Wertpapier::getName)
                .map(name -> "\"" + name + "\"") // Labels müssen als Strings in JS
                .collect(Collectors.toList());

        String labelsJS = "[" + String.join(",", labels) + "]";

        List<Double> values = depot.getWertpapiere().stream()
                .map(this::getAktuellerWert)
                .collect(Collectors.toList());

        String valuesJS = values.toString(); // z.B. [1.1, 2.3, 3.7]

        String js = String.format(
                """
                (function(canvas) {
                    if (window.depotChart) {
                        window.depotChart.destroy();
                    }
                    const ctx = canvas.getContext('2d');
                    window.depotChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: %s,
                            datasets: [{
                                label: 'Aktueller Kurs',
                                data: %s,
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
                """, labelsJS, valuesJS);

        canvas.getElement().executeJs(js);

        // Styling bleibt gleich
        depotBox.getStyle().set("border", "1px solid #ccc");
        depotBox.getStyle().set("padding", "10px");
        depotBox.getStyle().set("border-radius", "5px");
        depotBox.getStyle().set("cursor", "pointer");
        depotBox.getStyle().set("margin-bottom", "10px");
        depotBox.getStyle().set("background-color", "#f8f8f8");
        depotBox.setWidth("400px");

        depotBox.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(DetailedDepotView.class, depot.getDepotId()));
        });

        return depotBox;
    }

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

        String js = String.format(
                """
                (function(canvas) {
                  const ctx = canvas.getContext('2d');
                  if(window.pieChart) {
                    window.pieChart.destroy();
                  }
                  window.pieChart = new Chart(ctx, {
                    type: 'pie',
                    data: {
                      labels: %s,
                      datasets: [{
                        data: %s,
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
                labelsJS, valuesJS
        );

        canvas.getElement().executeJs(js);

        // Styling
        pieChartBox.getStyle().set("border", "1px solid #ccc");
        pieChartBox.getStyle().set("padding", "10px");
        pieChartBox.getStyle().set("border-radius", "5px");
        pieChartBox.getStyle().set("margin-top", "15px");
        pieChartBox.setWidth("400px");

        return pieChartBox;
    }

    private Double getAktuellerWert(Wertpapier wertpapier) {
        return wertpapier.getKurse().stream()
                .max(Comparator.comparing(Kurs::getDatum))
                .map(Kurs::getKurswert)
                .orElse(0.0);
    }

    private String createChartJSData(List<String> labels, List<Double> values) {
        String labelsJS = labels.stream()
                .map(label -> "\"" + label + "\"")
                .collect(Collectors.joining(", ", "[", "]"));

        String valuesJS = values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));

        return """
        (function(canvas){
          if(window.depotChart) {
            window.depotChart.destroy();
          }
          const ctx = canvas.getContext('2d');
          window.depotChart = new Chart(ctx, {
            type: 'bar',
            data: {
              labels: %s,
              datasets: [{
                label: 'Aktueller Kurs',
                data: %s,
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
        })(arguments[0]);
        """.formatted(labelsJS, valuesJS);
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
