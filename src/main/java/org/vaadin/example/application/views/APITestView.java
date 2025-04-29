package org.vaadin.example.application.views;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.fundamentaldata.response.CompanyOverviewResponse;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.models.StockQuote;
import org.vaadin.example.application.services.AlphaVantageService;

@Route("api")
@AnonymousAllowed
public class APITestView extends VerticalLayout {

    private final AlphaVantageService alphaVantageService;

    @Autowired
    public APITestView(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;

        var symbolField = new TextField("Symbol: ");
        var fetchButton = new Button("Fetch");
        fetchButton.setWidth("100px");

        fetchButton.addClickListener(e -> {
            String symbol = symbolField.getValue().trim().toUpperCase();
            if (!symbol.isEmpty()) {
                StockQuote quote = alphaVantageService.getStockQuote(symbol);
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
