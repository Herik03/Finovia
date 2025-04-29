package org.vaadin.example.application.views;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.fundamentaldata.response.CompanyOverviewResponse;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("api")
@AnonymousAllowed
public class APITestView extends HorizontalLayout {



    public APITestView() {
        var description = new Paragraph("Diese Seite dient nur als Testzweck und sollte nicht verwendet werden.");
        var testData =  AlphaVantage
                .api()
                .economicIndicator()
                .inflation()
                .fetchSync();

        var printData = new Paragraph(testData.toString());

        add(description, printData);
    }
}
