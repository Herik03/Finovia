package org.vaadin.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.services.KaufService;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route("meine-kauefe")
@PermitAll
public class MeineKauefeView extends VerticalLayout {

    public MeineKauefeView(@Autowired KaufService kaufService) {
        setPadding(true);
        setSpacing(true);

        // Erstelle den "Zurück"-Button mit einem Pfeil
        Button backButton = new Button(new Icon(VaadinIcon.ARROW_LEFT));
        backButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("uebersicht")));  // Navigiere zur Startseite (MainView)

        // HorizontalLayout für Button und Grid
        HorizontalLayout headerLayout = new HorizontalLayout(backButton);
        headerLayout.setAlignItems(Alignment.START);  // Button nach links ausrichten

        // Grid für die Käufe
        Grid<Kauf> grid = new Grid<>(Kauf.class, false);

        // Formatierer für Kurs und Datum
        DecimalFormat kursFormat = new DecimalFormat("#,##0.00");
        DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Spaltendefinitionen für das Grid
        grid.addColumn(k -> k.getWertpapier().getName()).setHeader("Name");
        grid.addColumn(Kauf::getStückzahl).setHeader("Anzahl");
        grid.addColumn(k -> kursFormat.format(k.getKurs())).setHeader("Kaufpreis (€)");
        grid.addColumn(k -> kursFormat.format(k.getGebühren())).setHeader("Gebühren (€)");
        grid.addColumn(k -> k.getDatum().format(datumFormat)).setHeader("Kaufdatum");
        grid.addColumn(Kauf::getHandelsplatz).setHeader("Handelsplatz");

        // Daten setzen
        List<Kauf> kauefe = kaufService.getAllKauefe();
        grid.setItems(kauefe);

        // Hinzufügen des Buttons und des Grids zur View
        add(headerLayout, grid);
    }
}
