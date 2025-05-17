package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.button.ButtonVariant;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Dividende;

import java.time.format.DateTimeFormatter;

public class DividendenPanel extends VerticalLayout {

    private final Grid<Dividende> grid = new Grid<>(Dividende.class, false);
    private final Depot depot;

    public DividendenPanel(Depot depot) {
        this.depot = depot;
        setPadding(true);
        setSpacing(false);
        setWidth("400px");
        setHeightFull();
        getStyle().set("border-left", "1px solid #ccc");

        H4 title = new H4("Letzte Dividenden");

        configureGrid();

        Button refreshBtn = new Button("Dividenden prüfen", VaadinIcon.MONEY.create());
        refreshBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshBtn.addClickListener(e -> {
            depot.pruefeUndBucheDividenden(java.time.LocalDate.now());
            updateGrid();
            Notification.show("Dividenden geprüft");
        });

        add(title, grid, refreshBtn);
        updateGrid();
    }

    private void configureGrid() {
        grid.addColumn(d -> d.getDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).setHeader("Datum");
        grid.addColumn(Dividende::getAktienAnzahl).setHeader("Anzahl");
        grid.addColumn(Dividende::getBetrag).setHeader("Netto (€)");
        grid.setHeight("250px");
        grid.setAllRowsVisible(true);
    }

    private void updateGrid() {
        grid.setItems(depot.getDividendenHistorie());
    }
}
