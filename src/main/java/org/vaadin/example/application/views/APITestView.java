package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.models.StockQuote;
import org.vaadin.example.application.services.AlphaVantageService;

/**
 * Eine Testansicht für die Alpha Vantage API-Integration.
 * <p>
 * Diese Klasse stellt eine einfache Benutzeroberfläche zum Testen der Alpha Vantage API bereit.
 * Benutzer können ein Aktiensymbol eingeben und Informationen zu dieser Aktie abrufen.
 * Die abgerufenen Daten werden in einem Dialog angezeigt.
 * <p>
 * Diese Ansicht ist für alle Benutzer zugänglich, auch ohne Authentifizierung.
 *
 * @author Finovia Team
 * @version 1.0
 */
@Route("api")
@AnonymousAllowed
public class APITestView extends VerticalLayout {

    /** Der Service für den Zugriff auf die Alpha Vantage API */
    private final AlphaVantageService alphaVantageService;

    /**
     * Konstruktor für die APITestView.
     * <p>
     * Initialisiert die Benutzeroberfläche mit einem Eingabefeld für das Aktiensymbol
     * und einem Button zum Abrufen der Daten. Der Button-Klick-Handler ruft die
     * Aktiendaten ab und zeigt sie in einem Dialog an.
     *
     * @param alphaVantageService Der Service für den Zugriff auf die Alpha Vantage API
     */
    @Autowired
    public APITestView(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;

        var symbolField = new TextField("Symbol: ");
        var fetchButton = new Button("Fetch");
        fetchButton.setWidth("100px");

        fetchButton.addClickListener(e -> {
            String symbol = symbolField.getValue().trim().toUpperCase();
            if (!symbol.isEmpty()) {
                StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
                if (quote != null) {
                    Dialog dialog = createDialog(quote);
                    add(dialog);
                    dialog.open();
                } else {
                    Notification.show("Keine Daten gefunden.", 3000, Notification.Position.MIDDLE);
                }
            }
        });

        add(symbolField, fetchButton);
    }

    /**
     * Erstellt einen Dialog zur Anzeige von Aktieninformationen.
     * <p>
     * Der Dialog zeigt Informationen zu einer Aktie an, basierend auf dem übergebenen
     * StockQuote-Objekt. Der Dialog enthält einen Titel mit dem Aktiensymbol,
     * eine Überschrift mit den Aktieninformationen und einen "Zurück"-Button.
     *
     * @param quote Das StockQuote-Objekt mit den anzuzeigenden Aktieninformationen
     * @return Ein konfigurierter Dialog mit den Aktieninformationen
     */
    private static Dialog createDialog(StockQuote quote){
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Stock information for: " + quote.getSymbol());

        var headline = new H2(quote.toString());

        VerticalLayout dialogLayout = new VerticalLayout(headline);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        dialog.add(dialogLayout);

        Button cancelButton = new Button("Back", u -> dialog.close());
        dialog.getFooter().add(cancelButton);

        return dialog;

    }
}