package org.vaadin.example.application.classes.enums;

/**
 * Enum zur Definition der Zinsfrequenz eines Finanzprodukts.
 *
 * Mögliche Werte:
 * <ul>
 *   <li>JAHRLICH - Zinszahlung erfolgt einmal jährlich</li>
 *   <li>HALBJAEHRLICH - Zinszahlung erfolgt zweimal jährlich</li>
 *   <li>VIERTELJAEHRLICH - Zinszahlung erfolgt viermal jährlich</li>
 * </ul>
 *
 * Wird z. B. für Anleihen oder Sparprodukte verwendet, um die Häufigkeit der Zinsgutschrift zu bestimmen.
 *
 * @author Jan Schwarzer
 */

public enum Zinsfrequenz {
    JAHRLICH,
    HALBJAEHRLICH,
    VIERTELJAEHRLICH
}
