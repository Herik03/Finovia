package org.vaadin.example.application.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.html.Anchor;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Zeigt das Handbuch als eigene View an.
 * Die HTML-Datei wird aus den Ressourcen geladen und der <body>-Inhalt angezeigt.
 */
@Route("hilfe")
@PageTitle("Hilfe / Handbuch – Finovia")
@AnonymousAllowed
public class HandbuchView extends VerticalLayout {
    public HandbuchView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        String htmlContent;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/resources/Handbuch.html")) {
            if (is != null) {
                Scanner scanner = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A");
                String fullHtml = scanner.hasNext() ? scanner.next() : "<p style='color:red'>Handbuch ist leer.</p>";
                int bodyStart = fullHtml.indexOf("<body>");
                int bodyEnd = fullHtml.indexOf("</body>");
                if (bodyStart != -1 && bodyEnd != -1) {
                    htmlContent = fullHtml.substring(bodyStart + 6, bodyEnd); // 6 = Länge von <body>
                } else {
                    htmlContent = fullHtml; // Fallback: alles anzeigen
                }
            } else {
                htmlContent = "<p style='color:red'>Handbuch konnte nicht gefunden werden.</p>";
            }
        } catch (Exception e) {
            htmlContent = "<p style='color:red'>Handbuch konnte nicht geladen werden.</p>";
        }
        Div htmlDiv = new Div();
        htmlDiv.getElement().setProperty("innerHTML", htmlContent);
        htmlDiv.getStyle().set("max-width", "1000px");
        add(htmlDiv);


    }
}

