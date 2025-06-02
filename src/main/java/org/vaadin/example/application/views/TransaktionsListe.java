package org.vaadin.example.application.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Transaktion;
import org.vaadin.example.application.services.AktienKaufService;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route("transaktionen")
@PermitAll
public class TransaktionsListe extends AbstractSideNav {

    private final SecurityService securityService;

    public TransaktionsListe(@Autowired AktienKaufService aktienKaufService, SecurityService securityService) {
        super(securityService);
        this.securityService = securityService;

        H2 headline = new H2("Transaktionshistorie");
        headline.getStyle()
                .set("margin-top", "0")
                .set("margin-bottom", "1rem");

        Grid<Transaktion> grid = new Grid<>(Transaktion.class, false);

        DecimalFormat kursFormat = new DecimalFormat("#,##0.00");
        DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        grid.addColumn(t -> t.getWertpapier().getName()).setHeader("Name");
        grid.addColumn(Transaktion::getStückzahl).setHeader("Anzahl");
        grid.addColumn(t -> kursFormat.format(t.getKurs())).setHeader("Kaufpreis (€)");
        grid.addColumn(t -> kursFormat.format(t.getGebühren())).setHeader("Gebühren (€)");
        grid.addColumn(t -> kursFormat.format(t.getStückzahl() * t.getKurs() + t.getGebühren()))
                .setHeader("Gesamtpreis (€)");
        grid.addColumn(t -> t.getDatum().format(datumFormat)).setHeader("Kaufdatum");
        grid.addColumn(Transaktion::getHandelsplatz).setHeader("Handelsplatz");

        List<Transaktion> transaktionen = aktienKaufService.getAllTransaktionen();
        grid.setItems(transaktionen);

        addToMainContent(headline, grid);
    }
}
