package org.vaadin.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.repositories.*;
import org.vaadin.example.application.services.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Integrationstest für {@link AktienKaufService}.
 * <p>
 * Die Klasse testet zentrale Methoden der Aktienkauf-Logik mithilfe von Mockito.
 *  Mockito wird verwendet, um abhängige Komponenten wie Datenbank-Repositories oder externe API-Services zu simulieren
 *  und so die Business-Logik isoliert und reproduzierbar testen zu können.
 * Es werden sowohl "Happy Paths" als auch Fehlerfälle abgedeckt:
 * <ul>
 *     <li>Erfolgreiche Käufe</li>
 *     <li>Ungültige Eingaben</li>
 *     <li>Externe Fehler durch Kursservice</li>
 *     <li>Transaktionsabruf</li>
 *     <li>Kursabruf</li>
 * </ul>
 * </p>
 * Die Repositories und der externe AlphaVantageService werden isoliert über Mocks ersetzt.
 */
@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class AktienKaufServiceIT {

    @Mock
    private AlphaVantageService alphaVantageService;

    @Mock
    private DepotRepository depotRepository;

    @Mock
    private TransaktionRepository transaktionRepository;

    @Mock
    private AktieRepository aktieRepository;

    @InjectMocks
    private AktienKaufService service;

    private Depot depot;
    private Nutzer nutzer;

    /**
     * Initialisiert ein Beispieldepot und Nutzer vor jedem Test.
     */
    @BeforeEach
    void setup() {
        nutzer = new Nutzer();
        depot = new Depot("TestDepot", nutzer);
    }

    /**
     * Testet erfolgreichen Aktienkauf mit gültigen Eingaben.
     * Es wird geprüft, ob alle relevanten Speicher- und Verknüpfungsprozesse aufgerufen werden.
     */
    @Test
    void testErfolgreicherKauf() {
        String symbol = "AAPL";
        StockQuote quote = new StockQuote(symbol, 150.0, 0.0);
        Aktie aktie = new Aktie();
        aktie.setSymbol(symbol);

        when(alphaVantageService.getCurrentStockQuote(symbol)).thenReturn(quote);
        when(alphaVantageService.getFundamentalData(symbol)).thenReturn(aktie);
        when(aktieRepository.findBySymbol(symbol)).thenReturn(null);

        Aktie result = service.kaufeAktie(symbol, 10, "NASDAQ", depot, nutzer);

        assertNotNull(result);
        verify(transaktionRepository).save(any(Kauf.class));
        verify(aktieRepository).save(any(Aktie.class));
        verify(depotRepository).save(any(Depot.class));
    }

    /**
     * Testet Verhalten bei ungültigem (leerem oder null) Symbol.
     * Erwartet wird ein Abbruch der Kauflogik mit null.
     */
    @Test
    void testUngueltigesSymbol() {
        assertNull(service.kaufeAktie("", 10, "NASDAQ", depot, nutzer));
        assertNull(service.kaufeAktie(null, 10, "NASDAQ", depot, nutzer));
    }

    /**
     * Testet Verhalten bei ungültiger Stückzahl (0 oder negativ).
     */
    @Test
    void testUngueltigeStueckzahl() {
        assertNull(service.kaufeAktie("AAPL", 0, "NASDAQ", depot, nutzer));
        assertNull(service.kaufeAktie("AAPL", -1, "NASDAQ", depot, nutzer));
    }

    /**
     * Testet Verhalten, wenn kein Depot übergeben wurde.
     */
    @Test
    void testDepotNull() {
        assertNull(service.kaufeAktie("AAPL", 10, "NASDAQ", null, nutzer));
    }

    /**
     * Testet Verhalten, wenn der Kursdienst keinen Kurs zurückliefert.
     */
    @Test
    void testKeinKursVonAlphaVantage() {
        when(alphaVantageService.getCurrentStockQuote("AAPL")).thenReturn(null);
        assertNull(service.kaufeAktie("AAPL", 10, "NASDAQ", depot, nutzer));
    }

    /**
     * Testet Verhalten, wenn keine fundamentalen Aktiendaten zurückgegeben werden.
     */
    @Test
    void testKeinFundamentaldaten() {
        when(alphaVantageService.getCurrentStockQuote("AAPL")).thenReturn(new StockQuote("AAPL", 123.45, 0.0));
        when(alphaVantageService.getFundamentalData("AAPL")).thenReturn(null);
        assertNull(service.kaufeAktie("AAPL", 10, "NASDAQ", depot, nutzer));
    }

    /**
     * Testet erfolgreichen Kursabruf bei gültigem Symbol.
     */
    @Test
    void testKursAbrufErfolgreich() {
        when(alphaVantageService.getCurrentStockQuote("AAPL"))
                .thenReturn(new StockQuote("AAPL", 99.99, 0.0));
        assertEquals(99.99, service.getKursFürSymbol("AAPL"));
    }

    /**
     * Testet Kursabruf bei ungültigem oder leerem Symbol bzw. fehlender Antwort.
     */
    @Test
    void testKursAbrufFehlerhaft() {
        assertEquals(0.0, service.getKursFürSymbol(""));
        assertEquals(0.0, service.getKursFürSymbol(null));
        when(alphaVantageService.getCurrentStockQuote("AAPL")).thenReturn(null);
        assertEquals(0.0, service.getKursFürSymbol("AAPL"));
    }

    /**
     * Testet Rückgabe leerer Transaktionsliste, wenn keine Einträge vorhanden sind.
     */
    @Test
    void testGetAllTransaktionenLeer() {
        when(transaktionRepository.findAll()).thenReturn(List.of());
        assertTrue(service.getAllTransaktionen().isEmpty());
    }

    /**
     * Testet Rückgabe einer gefüllten Transaktionsliste.
     */
    @Test
    void testGetAllTransaktionenGefuellt() {
        Kauf kauf = new Kauf();
        when(transaktionRepository.findAll()).thenReturn(List.of(kauf));
        assertEquals(1, service.getAllTransaktionen().size());
    }
}
