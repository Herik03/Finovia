package org.vaadin.example.application.services;

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
 * @author Batuhan
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
     *
     * @param symbol       Das Tickersymbol der Aktie (z. B. AAPL)
     * @param stueckzahl   Anzahl der zu kaufenden Aktien
     * @param handelsplatz Börsenplatz des Kaufs
     * @return Ein neues Aktie-Objekt oder null bei Fehler
     */
    public Aktie kaufeAktie(String symbol, int stueckzahl, String handelsplatz) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0) {
            return null;
        }

        // Abrufen des aktuellen Aktienkurses vom API-Service
        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            return null;
        }

        double kurs = quote.getPrice();  // Kurs von der API
        double gebuehren = 2.50;         // Standardgebühren (optional anpassen)

        // Erstellen der Kauf-Transaktion
        Kauf kauf = new Kauf(
                handelsplatz,
                LocalDate.now(),
                gebuehren,
                kurs,
                stueckzahl,
                null,
                null
        );

        List<Transaktion> transaktionen = new ArrayList<>();
        transaktionen.add(kauf);

        // Erstellen des Aktie-Objekts mit vorhandenem Konstruktor
        Aktie aktie = new Aktie(
                "Unternehmen: " + quote.getSymbol(), // unternehmensname
                "",     // description
                "",     // exchange
                "",     // currency
                "",     // country
                "",     // sector
                "",     // industry
                0L,     // marketCap
                0L,     // ebitda
                0.0,    // pegRatio
                0.0,    // bookValue
                0.0,    // dividendPerShare
                0.0,    // dividendYield
                0.0,    // eps
                0.0,    // forwardPE
                0.0,    // beta
                0.0,    // yearHigh
                0.0,    // yearLow
                null    // dividendDate
        );

        // Setzen der geerbten Attribute aus Wertpapier
        aktie.setIsin("ISIN: " + quote.getSymbol());
        aktie.setName(quote.getSymbol());
        aktie.setWertpapierId(quote.getSymbol().hashCode());
        aktie.setTransaktionen(transaktionen);
        aktie.setKurse(new ArrayList<>()); // Hier kannst du später auch historische Kurse setzen, falls gewünscht

        // Verknüpfe die Transaktion mit der Aktie
        kauf.setWertpapier(aktie);

        return aktie;
    }
}
