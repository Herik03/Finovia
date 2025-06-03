package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.button.ButtonVariant;
import org.vaadin.example.application.classes.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DividendenPanel extends VerticalLayout {

    private final Grid<Ausschuettung> grid = new Grid<>(Ausschuettung.class, false);
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

        Button refreshBtn = new Button("Ausschüttungen prüfen", VaadinIcon.MONEY.create());
        refreshBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshBtn.addClickListener(e -> {
            depot.pruefeUndBucheAusschuettungen(LocalDate.now());
            updateGrid();
            Notification.show("Ausschüttungen geprüft");
        });

        add(title, grid, refreshBtn);
        updateGrid();
    }

    private void configureGrid() {
        grid.addColumn(a -> a.getDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .setHeader("Datum");

        grid.addColumn(a -> {
            if (a instanceof Dividende d) {
                return d.getAktienAnzahl();
            } else if (a instanceof Zinszahlung z) {
                return z.getAnleihenAnzahl();
            }
            return "-";
        }).setHeader("Anzahl");

        grid.addColumn(a -> {
            if (a instanceof Dividende d) {
                return String.format("%.2f", d.getBetrag());
            } else if (a instanceof Zinszahlung z) {
                return String.format("%.2f", z.getBetrag());
            }
            return "-";
        }).setHeader("Netto (€)");

        grid.setHeight("250px");
        grid.setAllRowsVisible(true);
    }

    private void updateGrid() {
        grid.setItems(depot.getAlleAusschuettungen());
    }

}
