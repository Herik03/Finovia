package org.vaadin.example.application.services;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.classes.Transaktion;
import org.vaadin.example.application.models.StockQuote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service-Klasse zur Durchführung von Aktienkäufen.
 * Diese Klasse kommuniziert mit dem AlphaVantageService,
 * um aktuelle Kursinformationen abzurufen und darauf basierend
 * ein Kaufobjekt sowie eine neue Aktie zu erstellen.
 *
 * @author Batuhan Güvercin
 */
@Service
public class AktienKaufService {
    private final AlphaVantageService alphaVantageService;

    public AktienKaufService(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

/**
 * Führt den Kauf einer Aktie durch.
 *
 * Diese Methode:
 * - prüft die Eingaben
 * - ruft den aktuellen Kurs der gewünschten Aktie ab
 * - erstellt eine Kauf-Transaktion
 * - erstellt ein neues Aktie-Objekt mit der Kaufhistorie
 * - verknüpft die Transaktion mit der Aktie
 */
    public Aktie kaufeAktie(String symbol, int stueckzahl, String handelsplatz){
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0) {
            return null;
        }

        // Abrufen des aktuellen Aktienkurses vom API-Service
        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if(quote == null){
            return null;
        }

        double kurs = quote.getPrice();
        double gebühren = 2.50;

        // Erstellen eines neuen Kaufobjekts mit den Transaktionsdaten
        Kauf kauf = new Kauf(
                handelsplatz,
                LocalDate.now(),
                gebühren,
                kurs,
                stueckzahl,
                null,
                null
        );

        // Anlegen der Transaktionsliste und Hinzufügen des Kaufs
        List<Transaktion> transaktionen = new ArrayList<>();
        transaktionen.add(kauf);

        // Erstellen des Aktie-Objekts inklusive Transaktion
        Aktie aktie = new Aktie(
                stueckzahl,
                "Unternehmen: " + quote.getSymbol(),
                new ArrayList<>(),
                "ISIN: " + quote.getSymbol(),
                quote.getSymbol(),
                quote.getSymbol().hashCode(),
                transaktionen,
                new ArrayList<>()
        );

        kauf.setWertpapier(aktie);

        return aktie;
    }
}
