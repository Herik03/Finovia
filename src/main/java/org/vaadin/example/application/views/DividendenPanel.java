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

/**
 * Das {@code DividendenPanel} stellt eine UI-Komponente dar, die die zuletzt erhaltenen
 * Ausschüttungen (Dividenden, Zinszahlungen, ETF-Dividenden) eines Depots anzeigt.
 * Es enthält eine Tabelle zur Übersicht und einen Button zur Prüfung neuer Ausschüttungen.
 */
public class DividendenPanel extends VerticalLayout {

    /**
     * Tabelle zur Anzeige der Ausschüttungen im Depot.
     */
    private final Grid<Ausschuettung> grid = new Grid<>(Ausschuettung.class, false);

    /**
     * Referenz auf das anzuzeigende Depot.
     */
    private final Depot depot;

    /**
     * Erstellt ein neues {@code DividendenPanel} für das übergebene Depot.
     *
     * @param depot Das Depot, dessen Ausschüttungen angezeigt und geprüft werden sollen.
     */
    public DividendenPanel(Depot depot) {
        this.depot = depot;
        setPadding(true);
        setSpacing(false);
        setWidth("400px");
        setHeightFull();
        getStyle().set("border-left", "1px solid #ccc");

        H4 title = new H4("Letzte Dividenden");

        configureGrid();

        // Button zum Prüfen und Buchen neuer Ausschüttungen
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

    /**
     * Konfiguriert die Tabellenspalten für die Darstellung von Ausschüttungen.
     * Es werden Datum, Anzahl der Anteile, Nettoauszahlung und Typ (Aktie, Anleihe, ETF) angezeigt.
     */
    private void configureGrid() {
        grid.addColumn(a -> a.getDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .setHeader("Datum");

        // Spalte für die Anzahl der Anteile, die je nach Ausschüttungstyp variiert
        grid.addColumn(a -> {
            if (a instanceof Dividende d) {
                return d.getAktienAnzahl();
            } else if (a instanceof Zinszahlung z) {
                return z.getAnleihenAnzahl();
            } else if (a instanceof ETFDividende e) {
                return e.getEtfAnteile();
            }
            return "-";
        }).setHeader("Anzahl");

        // Spalte für den Nettoauszahlungsbetrag, der je nach Ausschüttungstyp variiert
        grid.addColumn(a -> {
            if (a instanceof Dividende d) {
                return String.format("%.2f", d.getBetrag());
            } else if (a instanceof Zinszahlung z) {
                return String.format("%.2f", z.getBetrag());
            } else if (a instanceof ETFDividende e) {
                return String.format("%.2f", e.getBetrag());
            }
            return "-";
        }).setHeader("Netto (€)");

        // Spalte für den Typ der Ausschüttung (Aktie, Anleihe, ETF)
        grid.addColumn(a -> {
            if (a instanceof Dividende) return "Aktie";
            if (a instanceof Zinszahlung) return "Anleihe";
            if (a instanceof ETFDividende) return "ETF";
            return "-";
        }).setHeader("Typ");

        grid.setHeight("250px");
        grid.setAllRowsVisible(true);
    }

    /**
     * Aktualisiert die Tabelle mit allen bisher gebuchten Ausschüttungen des Depots.
     */
    private void updateGrid() {
        grid.setItems(depot.getAlleAusschuettungen());
    }
}
