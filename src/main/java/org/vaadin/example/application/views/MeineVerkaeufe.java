package org.vaadin.example.application.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.example.application.Security.SecurityService;
import org.vaadin.example.application.classes.Transaktion;
import org.vaadin.example.application.classes.Verkauf;
import org.vaadin.example.application.repositories.TransaktionRepository;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("meineverkaeufe")
@PageTitle("Meine Verkäufe")
@PermitAll
public class MeineVerkaeufe extends AbstractSideNav {

    private final SecurityService securityService;
    private final TransaktionRepository transaktionRepository;

    @Autowired
    public MeineVerkaeufe(TransaktionRepository transaktionRepository, SecurityService securityService) {
        super(securityService);
        this.securityService = securityService;
        this.transaktionRepository = transaktionRepository;

        H2 headline = new H2("Verkaufsübersicht");
        headline.addClassNames(LumoUtility.Margin.Bottom.MEDIUM);

        Grid<Verkauf> grid = new Grid<>(Verkauf.class, false);

        DecimalFormat kursFormat = new DecimalFormat("#,##0.00");
        DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        grid.addColumn(v -> v.getWertpapier().getName()).setHeader("Name");
        grid.addColumn(Verkauf::getStückzahl).setHeader("Anzahl");
        grid.addColumn(v -> kursFormat.format(v.getKurs())).setHeader("Verkaufspreis (€)");
        grid.addColumn(v -> kursFormat.format(v.getGebühren())).setHeader("Gebühren (€)");
        grid.addColumn(v -> kursFormat.format(v.getStückzahl() * v.getKurs() - v.getGebühren()))
                .setHeader("Gesamtpreis (€)");
        grid.addColumn(v -> v.getDatum().format(datumFormat)).setHeader("Verkaufsdatum");

        // Optional: Handelsplatz, wenn vorhanden (muss ggf. angepasst werden)
        grid.addColumn(v -> "Verkauf").setHeader("Transaktionstyp");

        // Aktuellen Benutzer identifizieren
        UserDetails userDetails = securityService.getAuthenticatedUser();

        if (userDetails != null) {
            try {
                String username = userDetails.getUsername();
                // Nur Verkaufs-Transaktionen des aktuellen Nutzers laden
                List<Transaktion> alleTransaktionen = transaktionRepository.findTransaktionenByNutzerUsername(username);
                List<Verkauf> verkaufsTransaktionen = alleTransaktionen.stream()
                        .filter(t -> t instanceof Verkauf)
                        .map(t -> (Verkauf) t)
                        .collect(Collectors.toList());

                grid.setItems(verkaufsTransaktionen);

                Notification.show("Ihre Verkäufe werden angezeigt",
                                3000, Notification.Position.BOTTOM_START)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            } catch (Exception e) {
                Notification.show("Fehler beim Abrufen der Verkäufe: " + e.getMessage(),
                                5000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            grid.setItems(new ArrayList<>());
        }

        addToMainContent(headline, grid);
    }
}
