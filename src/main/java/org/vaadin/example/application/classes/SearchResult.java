package org.vaadin.example.application.classes;

import org.vaadin.example.application.classes.enums.SearchResultTypeEnum;

/**
 * Repräsentiert ein Suchergebnis für ein Wertpapier oder einen Finanzmarkt-Eintrag.
 * <p>
 * Enthält Informationen wie Symbol, Name, Region, Währung und Typ des Suchergebnisses.
 * Wird typischerweise für die Anzeige von Suchvorschlägen oder Suchergebnissen verwendet.
 *
 * @param symbol   Das Symbol des Suchergebnisses (z. B. Ticker-Symbol).
 * @param name     Der Name des Suchergebnisses (z. B. Firmenname).
 * @param region   Die Region, zu der das Suchergebnis gehört (z. B. "Deutschland", "USA").
 * @param currency Die Währung, in der das Wertpapier gehandelt wird.
 * @param type     Der Typ des Suchergebnisses (z. B. Aktie, ETF, Anleihe).
 * @author Sören Heß
 */
public record SearchResult(String symbol, String name, String region, String currency, SearchResultTypeEnum type) {

}
