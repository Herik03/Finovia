package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.Aktie;
import org.vaadin.example.application.classes.Depot;
import org.vaadin.example.application.classes.Kauf;
import org.vaadin.example.application.classes.Transaktion;
import org.vaadin.example.application.classes.StockQuote;
import org.vaadin.example.application.repositories.AktieRepository;
import org.vaadin.example.application.repositories.DepotRepository;
import org.vaadin.example.application.repositories.TransaktionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AktienKaufService {

    private final AlphaVantageService alphaVantageService;
    private final DepotRepository depotRepository;
    private final TransaktionRepository transaktionRepository;
    private final AktieRepository aktieRepository;

    @Autowired
    public AktienKaufService(AlphaVantageService alphaVantageService,
                             DepotRepository depotRepository,
                             TransaktionRepository transaktionRepository,
                             AktieRepository aktieRepository) {
        this.alphaVantageService = alphaVantageService;
        this.depotRepository = depotRepository;
        this.transaktionRepository = transaktionRepository;
        this.aktieRepository = aktieRepository;
    }

    /**
     * Führt den Kauf einer Aktie durch, speichert die Kauf-Transaktion und fügt die Aktie dem Depot hinzu.
     *
     * @param symbol       Das Tickersymbol der Aktie (z.B. AAPL)
     * @param stueckzahl   Anzahl der zu kaufenden Aktien
     * @param handelsplatz Börsenplatz des Kaufs
     * @param depot        Depot, dem die Aktie hinzugefügt werden soll
     * @return Das gekaufte Aktie-Objekt oder null bei Fehler
     */
    @Transactional
    public Aktie kaufeAktie(String symbol, int stueckzahl, String handelsplatz, Depot depot) {
        if (symbol == null || symbol.isBlank() || stueckzahl <= 0 || depot == null) {
            return null;
        }

        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            return null;
        }

        double kurs = quote.getPrice();
        double gebuehren = 2.50;

        Kauf kauf = new Kauf(handelsplatz, LocalDate.now(), gebuehren, kurs, stueckzahl, null, null);

        List<Transaktion> transaktionen = new ArrayList<>();
        transaktionen.add(kauf);

        Aktie aktie = new Aktie(
                quote.getSymbol(),
                "Unternehmen: " + quote.getSymbol(),
                "",
                "",
                "",
                "",
                "",
                "",
                0L,
                0L,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                null
        );

        aktie.setName(quote.getSymbol());
        aktie.setSymbol(quote.getSymbol());
        aktie.setUnternehmensname("Unternehmen: " + quote.getSymbol());
        aktie.setTransaktionen(transaktionen);
        aktie.setKurse(new ArrayList<>());

        // 1. Aktie speichern, bevor sie in der Transaktion verwendet wird
        aktie = aktieRepository.save(aktie);

        // 2. Verknüpfung Kauf -> Aktie
        kauf.setWertpapier(aktie);

        // 3. Kauf-Transaktion speichern
        transaktionRepository.save(kauf);

        // 4. Aktie dem Depot hinzufügen
        depot.wertpapierHinzufuegen(aktie, stueckzahl);

        // 5. Depot speichern
        depotRepository.save(depot);

        return aktie;
    }

    public double getKursFürSymbol(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol darf nicht leer sein.");
        }

        StockQuote quote = alphaVantageService.getCurrentStockQuote(symbol);
        if (quote == null) {
            throw new RuntimeException("Kein Kurs für Symbol gefunden: " + symbol);
        }

        return quote.getPrice();
    }

    public List<Transaktion> getAllTransaktionen() {
        return transaktionRepository.findAll();
    }
}
