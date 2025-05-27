package org.vaadin.example.application.services;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.PendingJavaScriptResult;

/**
 * Ein einfacher Canvas-Wrapper für Chart.js in Vaadin.
 */
@Tag("canvas")
public class ChartCanvas extends Component {

    public ChartCanvas() {
        getElement().setAttribute("width", "400");
        getElement().setAttribute("height", "200");
        getElement().setAttribute("id", "myChartCanvas");
    }

    /**
     * Initialisiert das Chart mit den übergebenen Daten.
     * @param chartConfig JSON-String mit Chart.js Konfiguration
     * @return Promise vom JS-Executor, falls du darauf warten möchtest
     */
    public PendingJavaScriptResult initChart(String chartConfig) {
        // Führt im Browser das JS aus, um das Chart zu initialisieren
        // Dabei wird angenommen, dass Chart.js schon via <script> eingebunden ist
        String js = "const ctx = this;" +
                "const config = JSON.parse($0);" +
                "if(window.myChart) { window.myChart.destroy(); }" +
                "window.myChart = new Chart(ctx, config);";
        return getElement().executeJs(js, chartConfig);
    }
}
