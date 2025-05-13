package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.services.AlphaVantageService;

/**
 * View zur Darstellung von Wertpapierdaten.
 * Diese View wird durch die APITestView ausgelöst und zeigt die Daten des Wertpapiers an.
 *
 * @author Jan Schwarzer
 */
@Route("wertpapier")
@AnonymousAllowed
public class WertpapierView extends VerticalLayout {

    private final AlphaVantageService alphaVantageService;

    @Autowired
    public WertpapierView(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;

        // Platzhalter für Symbolfeld, wird durch APITestView befüllt
        TextField symbolField = new TextField("Wertpapier Symbol:");
        Button fetchButton = new Button("Anzeigen");

        fetchButton.addClickListener(e -> {
            String symbol = symbolField.getValue().trim().toLowerCase();
            if (!symbol.isEmpty()) {
                Wertpapier wertpapier = alphaVantageService.fetchWertpapierData(symbol);
                if (wertpapier != null) {
                    showWertpapierDetails(wertpapier);
                }
            }
        });

        add(symbolField, fetchButton);
    }

    /**
     * Methode zur Darstellung der Wertpapierdetails.
     *
     * @param wertpapier Das Wertpapierobjekt, das angezeigt werden soll
     */
    private void showWertpapierDetails(Wertpapier wertpapier) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Wertpapier Details: " + wertpapier.getName());

        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2("Name: " + wertpapier.getName()));
        layout.add(new Span("ISIN: " + wertpapier.getIsin()));

            if (kurse.isEmpty()) {
                Notification.show("Keine Daten gefunden.", 3000, Notification.Position.MIDDLE);
                return;
            }

        if (wertpapier instanceof org.vaadin.example.application.classes.Aktie aktie) {
            layout.add(new Span("Unternehmensname: " + aktie.getUnternehmensname()));
            layout.add(new Span("Anzahl Aktien: " + aktie.getAnzahl()));
            layout.add(new Span("Dividendenrendite: " + (aktie.getDividenden().isEmpty() ? "Keine Dividenden" : aktie.getDividenden().get(0).getDividendenRendite() + "%")));
        }

        if (wertpapier instanceof org.vaadin.example.application.classes.Anleihe anleihe) {
            layout.add(new Span("Emittent: " + anleihe.getEmittent()));
            layout.add(new Span("Kupon: " + anleihe.getKupon() + "%"));
            layout.add(new Span("Laufzeit: " + anleihe.getLaufzeit()));
        }

        if (wertpapier instanceof org.vaadin.example.application.classes.ETF etf) {
            layout.add(new Span("Fondsname: " + etf.getFondsname()));
            layout.add(new Span("Index: " + etf.getIndex()));
            layout.add(new Span("Ausschüttung: " + etf.getAusschüttung()));
        }

        Button closeButton = new Button("Schließen", event -> dialog.close());
        HorizontalLayout buttons = new HorizontalLayout(closeButton);
        layout.add(buttons);

        dialog.add(layout);
        dialog.open();
    }
}
