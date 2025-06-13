package org.vaadin.example.application.services;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.vaadin.example.application.classes.*;
import org.vaadin.example.application.classes.SearchResult;
import org.vaadin.example.application.classes.StockQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.example.application.classes.enums.SearchResultTypeEnum;
import org.vaadin.example.application.repositories.KursRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * @author Sören Heß, Jan Schwarzer, Henrik Dollmann
 * @version 1.3
 * @see StockQuote
 * @see SearchResult
 */
@Service
public class AlphaVantageService {

    private final String API_KEY = Dotenv.load().get("API_KEY");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Logger logger = LoggerFactory.getLogger(AlphaVantageService.class);

    private final KursRepository kursRepository;

    /**
     * Konstruktor für AlphaVantageService.
     *
     * @param kursRepository Repository für Kursdatenbankzugriffe
     */
    @Autowired
    public AlphaVantageService(KursRepository kursRepository) {
        this.kursRepository = kursRepository;
    }
    

    /**
     * Prüft, ob das übergebene Symbol ein gültiges AlphaVantage-Symbol ist.
     * Fiktive Symbole (z. B. "ETF", "BND") werden ausgeschlossen.
     *
     * @param symbol Das zu prüfende Symbol
     * @return true, wenn das Symbol gültig ist, sonst false
     */
    private boolean isValidAlphaVantageSymbol(String symbol) {
        // Fiktive Symbole nicht zulassen
        return !(symbol.startsWith("ETF") || symbol.startsWith("BND"));
    }

    /**
     * Gibt lokale Kursdaten für ein bestimmtes Symbol zurück.
     *
     * @param symbol Das Wertpapiersymbol
     * @return Liste von Kurs-Objekten aus der lokalen Datenbank
     */
    public List<Kurs> getLocalKurse(String symbol) {
        return kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol);
    }

    /**
     * Wandelt ein Datums-String in ein LocalDateTime-Objekt um.
     * Unterstützt sowohl reine Datumsangaben als auch Datumsangaben mit Uhrzeit.
     *
     * @param dateStr Das Datums-String
     * @return Das geparste LocalDateTime-Objekt
     */
    private LocalDateTime parseDateSmart(String dateStr) {
        if (dateStr.length() == 10) {
            // Nur Datum → Uhrzeit 00:00:00 ergänzen
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        } else {
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    /**
     * Ruft Kursinformationen im Vergleich zum Vortag auf.
     *
     * @param symbol Das Börsensymbol des Wertpapiers (z.B. "AAPL" für Apple Inc.)
     * @return Ein StockQuote-Objekt mit den aktuellen Kursdaten, oder null, wenn die Anfrage fehlschlägt
     *         oder keine Daten verfügbar sind
     */
    public StockQuote getCurrentStockQuote(String symbol) {
        var response = AlphaVantage.api().timeSeries().quote().forSymbol(symbol).fetchSync();

        if (response == null || response.getErrorMessage() != null) return null;

        return new StockQuote(response.getSymbol(), response.getPrice(), response.getChangePercent());
    }

    /**
     * Ruft die Intraday-Kursdaten im 5-Minuten-Intervall für ein bestimmtes Wertpapiersymbol von der AlphaVantage API ab.
     * Diese Methode eignet sich besonders für die Analyse, von kurzfristigen Kursbewegungen innerhalb eines Handelstages.
     *
     * @param symbol Das Börsensymbol des Wertpapiers (z.B. "AAPL" für Apple Inc.)
     * @return Eine Liste von Kurs-Objekten mit den Intraday-Kursdaten, die Öffnungs-, Schluss-, Höchst- und Tiefstkurse enthält
     * @throws APIException Wenn ein Fehler bei der API-Kommunikation auftritt oder keine Daten verfügbar sind
     */
    public List<Kurs> getIntradaySeries(String symbol) {
        if (!isValidAlphaVantageSymbol(symbol)) {
            logger.info("Lade lokale Kursdaten (Intraday) für fiktives Symbol: {}", symbol);
            return getLocalKurse(symbol);
        }

        var response = AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol(symbol)
                .interval(Interval.FIVE_MIN)
                .fetchSync();

        if (response.getErrorMessage() != null) {
            logger.error("Fehler beim Abrufen der Intraday-Kursdaten: {}", response.getErrorMessage());
            throw new APIException("Fehler beim Abrufen der Intraday-Kursdaten: " + response.getErrorMessage());
        }

        List<Kurs> result = response.getStockUnits()
                .stream()
                .map(data -> new Kurs(
                        symbol,
                        parseDateSmart(data.getDate()),
                        data.getOpen(),
                        data.getClose(),
                        data.getHigh(),
                        data.getLow()
                ))
                .collect(Collectors.toList());

        Collections.reverse(result);
        return result;
    }

    /**
     * Ruft die täglichen Kursdaten der letzten 20 Jahre für ein bestimmtes Wertpapiersymbol von der AlphaVantage API ab.
     *
     * @param symbol Das Börsensymbol des Wertpapiers (z.B. "AAPL" für Apple Inc.)
     * @return Eine Liste von Kurs-Objekten mit den historischen Kursdaten, sortiert nach Datum
     * @throws APIException Wenn ein Fehler bei der API-Kommunikation auftritt oder keine Daten verfügbar sind
     */
    public List<Kurs> getDailySeries(String symbol) {
        if (!isValidAlphaVantageSymbol(symbol)) {
            logger.info("Lade lokale Kursdaten (daily) für fiktives Symbol: {}", symbol);
            return getLocalKurse(symbol);
        }

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

        List<Kurs> result = response.getStockUnits()
                .stream()
                .map(data -> new Kurs(
                        symbol,
                        parseDateSmart(data.getDate()),
                        data.getOpen(),
                        data.getClose(),
                        data.getHigh(),
                        data.getLow()
                ))
                .collect(Collectors.toList());

        Collections.reverse(result);
        return result;
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
        if (!isValidAlphaVantageSymbol(symbol)) {
            logger.info("Lade lokale Kursdaten für fiktives Symbol: {}", symbol);
            return getLocalKurse(symbol);
        }

        var response = AlphaVantage.api()
                .timeSeries()
                .weekly()
                .forSymbol(symbol)
                .fetchSync();

        if (response.getErrorMessage() != null) {
            logger.error("Fehler beim Abrufen der wöchentlichen Kursdaten: {}", response.getErrorMessage());
            throw new APIException("Fehler beim Abrufen der wöchentlichen Kursdaten: " + response.getErrorMessage());
        }

        List<Kurs> result = response.getStockUnits()
                .stream()
                .map(data -> new Kurs(
                        symbol,
                        parseDateSmart(data.getDate()),
                        data.getOpen(),
                        data.getClose(),
                        data.getHigh(),
                        data.getLow()
                ))
                .collect(Collectors.toList());

        Collections.reverse(result);
        return result;
    }

    /**
     * Ruft die monatlichen Kursdaten für ein bestimmtes Wertpapiersymbol von der AlphaVantage API ab.
     *
     * @param symbol Das Börsensymbol des Wertpapiers
     * @return Liste von Kurs-Objekten mit monatlichen Kursdaten
     * @throws APIException Wenn ein Fehler bei der API-Kommunikation auftritt
     */
    public List<Kurs> getMonthlySeries(String symbol) {
        if (!isValidAlphaVantageSymbol(symbol)) {
            logger.info("Lade lokale Kursdaten (monthly) für fiktives Symbol: {}", symbol);
            return getLocalKurse(symbol);
        }

        var response = AlphaVantage.api()
                .timeSeries()
                .monthly()
                .forSymbol(symbol)
                .fetchSync();

        if (response.getErrorMessage() != null) {
            logger.error("Fehler beim Abrufen der monatlichen Kursdaten: {}", response.getErrorMessage());
            throw new APIException("Fehler beim Abrufen der wöchentlichen Kursdaten: " + response.getErrorMessage());
        }

        List<Kurs> result = response.getStockUnits()
                .stream()
                .map(data -> new Kurs(
                        symbol,
                        parseDateSmart(data.getDate()),
                        data.getOpen(),
                        data.getClose(),
                        data.getHigh(),
                        data.getLow()
                ))
                .collect(Collectors.toList());

        Collections.reverse(result);
        return result;
    }
    
    public double getProzentualeAenderung24h(String name) {
        var response = AlphaVantage.api()
                .timeSeries()
                .quote()
                .forSymbol(name)
                .fetchSync();

        if (response == null || response.getErrorMessage() != null) return 0;

        return response.getChangePercent();
    }

    /**
     * Ruft die fundamentalen Unternehmensdaten für ein bestimmtes Wertpapiersymbol von der AlphaVantage API ab.
     * Die Daten beinhalten wichtige Kennzahlen wie Marktkapitalisierung, Dividendeninformationen, Branche und andere
     * fundamentale Wirtschaftsdaten des Unternehmens.
     *
     * @param symbol Das Börsensymbol des Wertpapiers (z.B. "AAPL" für Apple Inc.)
     * @return Ein {@link Aktie}-Objekt mit den fundamentalen Unternehmensdaten
     * @throws APIException Wenn ein Fehler bei der API-Kommunikation auftritt oder keine Daten verfügbar sind
     */
    public Aktie getFundamentalData(String symbol) {
        var response = AlphaVantage.api()
                .fundamentalData()
                .companyOverview()
                .forSymbol(symbol)
                .fetchSync();

        if (response == null || response.getErrorMessage() != null) {
            logger.warn("Fehler oder leere Antwort beim Symbol {}: {}", symbol, response != null ? response.getErrorMessage() : "null");
            return null;
        }

        var overview = response.getOverview();

        if (overview == null || overview.getName() == null || overview.getMarketCapitalization() == null) {
            logger.warn("Unvollständige Daten erhalten für Symbol {}. Wird ignoriert.", symbol);
            return null;
        }

        LocalDate dividendDate = safeDate(overview.getDividendDate());

        return new Aktie(
                safeString(overview.getSymbol()),
                safeString(overview.getName()),
                safeString(overview.getDescription()),
                safeString(overview.getExchange()),
                safeString(overview.getCurrency()),
                safeString(overview.getCountry()),
                safeString(overview.getSector()),
                safeString(overview.getIndustry()),
                safeLong(overview.getMarketCapitalization()),
                safeLong(overview.getEBITDA()),
                safeDouble(overview.getPEGRatio()),
                safeDouble(overview.getBookValue()),
                safeDouble(overview.getDividendPerShare()),
                safeDouble(overview.getDividendYield()),
                safeDouble(overview.getEPS()),
                safeDouble(overview.getForwardPE()),
                safeDouble(overview.getBeta()),
                safeDouble(overview.getFiftyTwoWeekHigh()),
                safeDouble(overview.getFiftyTwoWeekLow()),
                dividendDate
        );
    }

    /**
     * Hilfsmethode: Gibt einen sicheren String zurück (leerer String bei null oder "None").
     *
     * @param s Der zu prüfende String
     * @return Der sichere String
     */
    private String safeString(String s) { return (s == null || s.equalsIgnoreCase("None")) ? "" : s; }

    /**
     * Hilfsmethode: Gibt einen sicheren Long-Wert zurück (0 bei null).
     *
     * @param l Der zu prüfende Long-Wert
     * @return Der sichere Long-Wert
     */
    private Long safeLong(Long l) { return (l == null) ? 0L : l; }

    /**
     * Hilfsmethode: Gibt einen sicheren Double-Wert zurück (0.0 bei null).
     *
     * @param d Der zu prüfende Double-Wert
     * @return Der sichere Double-Wert
     */
    private Double safeDouble(Double d) { return (d == null) ? 0.0 : d; }

    /**
     * Hilfsmethode: Parst ein Datum sicher, gibt null bei Fehler zurück.
     *
     * @param date Das zu parsende Datum als String
     * @return Das geparste LocalDate oder null
     */
    private LocalDate safeDate(String date) {
        if (date == null || date.isBlank() || date.equalsIgnoreCase("None")) return null;
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            logger.warn("Konnte DividendDate nicht parsen: {}", date);
            return null;
        }
    }

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
            String baseUrl = "https://www.alphavantage.co/query";
            String url = UriComponentsBuilder.fromUriString(baseUrl)
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

            JsonNode root = objectMapper.readTree(response.body());

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
                    .filter(result ->
                            "United States".equals(result.region()) &&
                                    "USD".equals(result.currency()))
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
     *
     * @param node Der JSON-Knoten mit Suchergebnisdaten
     * @return Ein SearchResult-Objekt oder null bei Fehler
     */
    private SearchResult mapToSearchResult(JsonNode node) {
        try {
            return new SearchResult(
                    getTextSafely(node, "1. symbol"),
                    getTextSafely(node, "2. name"),
                    getTextSafely(node, "4. region"),
                    getTextSafely(node, "8. currency"),
                    SearchResultTypeEnum.AKTIE // API liefert nur Aktien, daher immer AKTIE
            );
        } catch (Exception e) {
            logger.warn("Konnte Suchergebnis nicht parsen: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Extrahiert sicher einen Textwert aus einem JsonNode. Gibt einen leeren String zurück,
     * wenn der Knoten nicht existiert oder kein Text ist.
     *
     * @param node      Der JSON-Knoten
     * @param fieldName Der Feldname
     * @return Der extrahierte Text oder ein leerer String
     */
    private String getTextSafely(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asText() : "";
    }

    /**
     * Ruft den aktuellen Kurs für das angegebene Wertpapier ab.
     *
     * @param name Das Symbol des Wertpapiers
     * @return Der aktuelle Kurswert, oder 0 bei Fehler
     */
    public double getAktuellerKurs(String name) {
        var response = AlphaVantage.api()
                .timeSeries()
                .quote()
                .forSymbol(name)
                .fetchSync();

        if (response == null || response.getErrorMessage() != null) return 0;

        return response.getPrice();
    }

    /**
     * Benutzerdefinierte Exception für API-bezogene Fehler.
     */
    public static class APIException extends RuntimeException {
        /**
         * Konstruktor mit Fehlermeldung.
         *
         * @param message Die Fehlermeldung
         */
        public APIException(String message) {
            super(message);
        }

        /**
         * Konstruktor mit Fehlermeldung und Ursache.
         *
         * @param message Die Fehlermeldung
         * @param cause   Die zugrundeliegende Exception
         */
        public APIException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}