package org.vaadin.example.application.classes.enums;

/**
 * Das {@code SearchResultTypeEnum} definiert die möglichen Typen von Wertpapieren,
 * die bei einer Suchanfrage in der Anwendung unterschieden werden können.
 *
 * Dieses Enum wird verwendet, um die jeweilige Kategorie eines Suchergebnisses zu klassifizieren.
 *
 * @author Sören Heß
 */
public enum SearchResultTypeEnum {

    /**
     * Das Suchergebnis ist eine Aktie.
     */
    AKTIE,

    /**
     * Das Suchergebnis ist ein Exchange Traded Fund (ETF).
     */
    ETF,

    /**
     * Das Suchergebnis ist eine Anleihe.
     */
    ANLEIHE,

    /**
     * Der Typ des Suchergebnisses konnte nicht bestimmt werden.
     */
    UNBEKANNT
}
