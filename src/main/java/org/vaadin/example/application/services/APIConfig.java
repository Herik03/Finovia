package org.vaadin.example.application.services;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 * Service-Klasse zur Initialisierung der AlphaVantage-API-Konfiguration.
 *
 * Lädt den API-Key aus einer .env-Datei und konfiguriert die AlphaVantage-API
 * beim Start der Anwendung.
 *
 * @author Sören Heß
 */
@Service
public class APIConfig {

    /**
     * Initialisiert die AlphaVantage-API mit Konfiguration aus der .env-Datei.
     *
     * Wird nach der Konstruktion des Beans automatisch aufgerufen.
     * Liest den API-Key, setzt das Timeout und initialisiert die API.
     */
    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");

        Config cfg = Config.builder()
                .key(apiKey)
                .timeOut(10)
                .build();

        AlphaVantage.api().init(cfg);
    }
}