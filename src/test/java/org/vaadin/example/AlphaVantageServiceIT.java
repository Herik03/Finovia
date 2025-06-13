package org.vaadin.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.application.services.AlphaVantageService;

/**
 * Integrationstest für {@link AlphaVantageService}, der vorbereitete Daten verwendet.
 */
public class AlphaVantageServiceIT {

    private AlphaVantageService service;

    @BeforeEach
    @Autowired
    public void setup(AlphaVantageService service) {
        this.service = service;
    }

    /**
     * Testet, ob ein Kurswert für ein gültiges Symbol zurückgegeben wird.
     * Hier wird AlphaVantageService.getAktuellerKurs() real aufgerufen.
     */
    @Test
    public void testGetAktuellerKurs_withValidSymbol_returnsValue() {
        double kurs = service.getAktuellerKurs("AAPL");
        Assertions.assertTrue(kurs > 0, "Kurs sollte größer als 0 sein");
    }

    /**
     * Testet Verhalten bei ungültigem Symbol – Rückgabewert soll 0.0 sein.
     */
    @Test
    public void testGetAktuellerKurs_withInvalidSymbol_returnsZero() {
        double kurs = service.getAktuellerKurs("INVALID123");
        Assertions.assertEquals(0.0, kurs);
    }

    /**
     * Testet prozentuale Veränderung für ein reales Symbol.
     */
    @Test
    public void testGetProzentualeAenderung_withValidSymbol() {
        double change = service.getProzentualeAenderung24h("AAPL");
        Assertions.assertTrue(change >= 0 || change < 0); // Hauptsache kein Fehler/NaN
    }

    /**
     * Testet Verhalten bei leerem Symbol.
     */
    @Test
    public void testGetAktuellerKurs_withEmptySymbol_returnsZero() {
        double kurs = service.getAktuellerKurs("");
        Assertions.assertEquals(0.0, kurs);
    }
}