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
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.classes.Transaktion;
import org.vaadin.example.application.repositories.TransaktionRepository;
import org.vaadin.example.application.services.NutzerService;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Ansicht für die Anzeige der Transaktionshistorie eines Nutzers.
 * Stellt eine Liste aller Transaktionen dar, die der aktuell angemeldete Nutzer durchgeführt hat.
 * Die Transaktionen werden in einem Grid angezeigt, das Spalten für Name, Stückzahl,
 * Kaufpreis, Gebühren, Gesamtpreis, Kaufdatum und Handelsplatz enthält.
 */
@Route("transaktionen")
@PageTitle("Transaktionen")
@PermitAll
public class TransaktionsListe extends AbstractSideNav {

    private final SecurityService securityService;
    private final TransaktionRepository transaktionRepository;
    private final NutzerService nutzerService;

    /**
     * Konstruktor, der die Transaktionsliste initialisiert.
     * Lädt die Transaktionen des aktuell angemeldeten Nutzers und zeigt sie in einem Grid an.
     *
     * @param transaktionRepository Repository
     * für Transaktionen, um die Datenbank abzufragen.
     * @param securityService Service
     * für Sicherheitsoperationen, um den aktuell angemeldeten Nutzer zu identifizieren.
     */
    @Autowired
    public TransaktionsListe(TransaktionRepository transaktionRepository, SecurityService securityService, NutzerService nutzerService) {
        super(securityService);
        this.securityService = securityService;
        this.transaktionRepository = transaktionRepository;
        this.nutzerService = nutzerService;

        H2 headline = new H2("Meine Käufe");
        headline.addClassNames(LumoUtility.Margin.Bottom.MEDIUM);

        Grid<Kauf> grid = new Grid<>(Kauf.class, false);

        DecimalFormat kursFormat = new DecimalFormat("#,##0.00");
        DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Spalten für das Grid definieren
        grid.addColumn(t -> t.getWertpapier().getName()).setHeader("Name");
        grid.addColumn(Transaktion::getStückzahl).setHeader("Anzahl");
        grid.addColumn(t -> kursFormat.format(t.getKurs())).setHeader("Kaufpreis (€)");
        grid.addColumn(t -> kursFormat.format(t.getGebühren())).setHeader("Gebühren (€)");
        grid.addColumn(t -> kursFormat.format(t.getStückzahl() * t.getKurs() + t.getGebühren()))
                .setHeader("Gesamtpreis (€)");
        grid.addColumn(t -> t.getDatum().format(datumFormat)).setHeader("Kaufdatum");
        grid.addColumn(Kauf::getHandelsplatz).setHeader("Handelsplatz");

        // Aktuellen Benutzer identifizieren
        UserDetails userDetails = securityService.getAuthenticatedUser();

        // Wenn ein Nutzer angemeldet ist, Transaktionen laden
        if (userDetails != null) {
            try {
                // Nur Transaktionen des aktuellen Nutzers laden
                String username = userDetails.getUsername();
                Nutzer user = nutzerService.getAngemeldeterNutzer();
                List<Kauf> nutzerTransaktionen = transaktionRepository.findTransaktionenByNutzer(user)
                        .stream()
                        .filter(transaktion -> transaktion instanceof Kauf) // Prüft den Typ
                        .map(transaktion -> (Kauf) transaktion) // Castet zu Kauf
                        .toList(); // Sammelt in neue Liste
                grid.setItems(nutzerTransaktionen);

                // Hinweis zur erfolgreichen Filterung
                Notification.show("Ihre Transaktionen werden angezeigt",
                                  3000, Notification.Position.BOTTOM_START)
                           .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            } catch (Exception e) {
                Notification.show("Fehler beim Abrufen der Transaktionen: " + e.getMessage(),
                                  5000, Notification.Position.MIDDLE)
                           .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            grid.setItems(new ArrayList<>());
        }

        addToMainContent(headline, grid);
    }
}
