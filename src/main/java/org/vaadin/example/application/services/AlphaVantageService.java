package org.vaadin.example.application.services;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.models.SearchResult;
import org.vaadin.example.application.models.StockQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service für die Kommunikation mit der AlphaVantage API.
 * <p>
 * Dieser Service stellt Methoden bereit, um auf Finanzmarktdaten über die AlphaVantage API
 * zuzugreifen. Die Hauptfunktionalitäten umfassen:
 * <ul>
 *     <li>Abrufen von aktuellen Aktienkursen für ein bestimmtes Symbol</li>
 *     <li>Suche nach Wertpapieren basierend auf Suchbegriffen</li>
 * </ul>
 * <p>
 * Der API-Schlüssel wird aus den Umgebungsvariablen über Dotenv geladen.
 * 
 * @author Sören Heß
 * @version 1.3
 * @see StockQuote
 * @see SearchResult
 */
@Service
public class AlphaVantageService {

    private final String API_KEY = Dotenv.load().get("API_KEY");
    private final String BASE_URL = "https://www.alphavantage.co/query";
    private final ObjectMapper obejctMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Logger logger = LoggerFactory.getLogger(AlphaVantageService.class);

    /**
     Ruft Kursinformationen im Vergleich zum Vortag auf.

     @param symbol Das Börsensymbol des Wertpapiers (z.B. "AAPL" für Apple Inc.)
     @return Ein StockQuote-Objekt mit den aktuellen Kursdaten, oder null, wenn die Anfrage fehlschlägt
     oder keine Daten verfügbar sind
     */
    public StockQuote getCurrentStockQuote(String symbol) {
        var response = AlphaVantage.api().timeSeries().quote().forSymbol(symbol).fetchSync();

        if (response == null || response.getErrorMessage() != null) return null;

        return new StockQuote(response.getSymbol(), response.getPrice(), response.getChangePercent());
    }

    //TODO: Intraday -> Liefert Handelsdaten des aktuellen Tages im 5 Minuten Intervall

    /**
     * Ruft die täglichen Kursdaten der letzten 20 Jahre für ein bestimmtes Wertpapiersymbol von der AlphaVantage API ab.
     *
     * @param symbol Das Börsensymbol des Wertpapiers (z.B. "AAPL" für Apple Inc.)
     * @return Eine Liste von Kurs-Objekten mit den historischen Kursdaten, sortiert nach Datum
     * @throws APIException Wenn ein Fehler bei der API-Kommunikation auftritt oder keine Daten verfügbar sind
     */
    public List<Kurs> getDailySeries(String symbol) {
        var response = AlphaVantage.api()
                .timeSeries()
                .daily()
                .forSymbol(symbol)
                .outputSize(OutputSize.FULL)
                .fetchSync();

        if (response.getErrorMessage() != null) {
            logger.error("Fehler beim Abrufen der täglichen Kursdaten: {}", response.getErrorMessage());
            throw new APIException("Fehler beim Abrufen der täglichen Kursdaten: " + response.getErrorMessage());
        }

        return response.getStockUnits()
                .stream()
                .map( data -> new Kurs(
                        symbol,
                        LocalDate.parse(data.getDate()),
                        data.getOpen(),
                        data.getClose(),
                        data.getHigh(),
                        data.getLow()
                        ))
                .collect(Collectors.toList());
    }

    /**
     * Ruft die wöchentlichen Kursdaten für ein bestimmtes Wertpapiersymbol von der AlphaVantage API ab.
     * Eignet sich vor allem für die Analyse von längerfristigen Trends.
     *
     * @param symbol Das Börsensymbol des Wertpapiers (z.B. "AAPL" für Apple Inc.)
     * @return Eine Liste von Kurs-Objekten mit den wöchentlichen Kursdaten
     * @throws APIException Wenn ein Fehler bei der API-Kommunikation auftritt oder keine Daten verfügbar sind
     */
    public List<Kurs> getWeeklySeries(String symbol) {
        var response = AlphaVantage.api()
                .timeSeries()
                .weekly()
                .forSymbol(symbol)
                .fetchSync();

        if (response.getErrorMessage() != null) {
            logger.error("Fehler beim Abrufen der wöchentlichen Kursdaten: {}", response.getErrorMessage());
            throw new APIException("Fehler beim Abrufen der wöchentlichen Kursdaten: " + response.getErrorMessage());
        }

        return response.getStockUnits()
                .stream()
                .map(data -> new Kurs(
                        symbol,
                        LocalDate.parse(data.getDate()),
                        data.getOpen(),
                        data.getClose(),
                        data.getHigh(),
                        data.getLow()
                ))
                .collect(Collectors.toList());
    }

    //TODO: Monthly -> Liefert monatliche Kursdaten

    //TODO: Dividends -> Liefert Dividendenhistorie eines Wertpapiers

    //TODO: Stammdaten -> Liefert die Stammdaten eines Wertpapiers

    /**
     * Durchsucht Aktien und Wertpapiere basierend auf dem übergebenen Keyword über die AlphaVantage API.
     *
     * @param keyword Das Suchwort für die Wertpapiersuche
     * @return Eine Liste von SearchResult-Objekten mit den gefundenen Wertpapieren
     * @throws APIException Wenn ein Fehler bei der API-Kommunikation auftritt
     */
    public List<SearchResult> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            String url = UriComponentsBuilder.fromUriString(BASE_URL)
                    .queryParam("function", "SYMBOL_SEARCH")
                    .queryParam("keywords", keyword)
                    .queryParam("apikey", API_KEY)
                    .build()
                    .encode()
                    .toUriString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            //Prüfen von HTTP Statuscode
            if (response.statusCode() != 200) {
                throw new APIException("API antwortete mit code: " + response.statusCode());
            }

            JsonNode root = obejctMapper.readTree(response.body());

            //Prüfen auf Fehler in der API-Antwort
            if (root.has("Error Message")) {
                throw new APIException("API Anfrage fehlgeschlagen: " + root.get("Error Message").asText());
            }

            if(root.has("Note")){
                logger.warn("AlphaVantage Note: {}", root.get("Note").asText());
            }

            JsonNode matches = root.get("bestMatches");
            if (matches == null || !matches.isArray()) {
                return Collections.emptyList();
            }

            return StreamSupport.stream(matches.spliterator(), false)
                    .map(this::mapToSearchResult)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Netzwerk- oder JSON-Parsing-fehler: {}", e.getMessage());
            throw new APIException("Verbindung zur API fehlgeschlagen", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new APIException("API-Anfrage wurde unterbrochen", e);
        } catch (Exception e) {
            throw new APIException("Fehler bei der Symbolsuche: ", e);
        }
    }

    /**
     * Hilfsmethode zur Umwandlung eines JSON-Knotens in ein SearchResult-Objekt.
     */
    private SearchResult mapToSearchResult(JsonNode node) {
        try {
            return new SearchResult(
                    getTextSafely(node, "1. symbol"),
                    getTextSafely(node, "2. name"),
                    getTextSafely(node, "4. region"),
                    getTextSafely(node, "8. currency")
            );
        } catch (Exception e) {
            logger.warn("Konnte Suchergebnis nicht parsen: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Extrahiert sicher einen Textwert aus einem JsonNode. Gibt einen leeren String zurück,
     * wenn der Knoten nicht existiert oder kein Text ist.
     */
    private String getTextSafely(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asText() : "";
    }

    /**
     * Benutzerdefinierte Exception für API-bezogene Fehler.
     */
    public static class APIException extends RuntimeException {
        public APIException(String message) {
            super(message);
        }

        public APIException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}