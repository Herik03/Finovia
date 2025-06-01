package org.vaadin.example.application.views;

import com.vaadin.flow.component.dialog.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.*;

/**
 * Factory-Klasse zur Erstellung von Detail-Dialogen für verschiedene Wertpapier-Typen.
 * <p>
 * Diese Klasse entscheidet anhand des konkreten Typs eines {@link Wertpapier}-Objekts,
 * welche spezialisierte View verwendet wird, um den zugehörigen {@link Dialog} zu erzeugen.
 */
@Component
public class WertpapierDetailViewFactory {

    /** View zur Darstellung von Aktien-Details. */
    private final AktienView aktienView;

    /** View zur Darstellung von ETF-Details. */
    private final ETFView etfView;

    /** View zur Darstellung von Anleihe-Details. */
    private final AnleiheView anleiheView;

    /**
     * Konstruktor zur Initialisierung der Detail-Views.
     *
     * @param aktienView   View-Komponente für Aktien
     * @param etfView      View-Komponente für ETFs
     * @param anleiheView  View-Komponente für Anleihen
     */
    @Autowired
    public WertpapierDetailViewFactory(AktienView aktienView, ETFView etfView, AnleiheView anleiheView) {
        this.aktienView = aktienView;
        this.etfView = etfView;
        this.anleiheView = anleiheView;
    }

    /**
     * Gibt einen passenden Detaildialog für das übergebene {@link Wertpapier} zurück.
     * <p>
     * Der konkrete Typ (Aktie, ETF oder Anleihe) wird zur Laufzeit erkannt
     * und an die entsprechende View weitergeleitet.
     *
     * @param wertpapier Das anzuzeigende Wertpapier
     * @return Ein {@link Dialog} mit den Wertpapierdetails
     * @throws IllegalArgumentException Wenn der Wertpapier-Typ nicht erkannt wird
     */
    public Dialog getDetailsDialog(Wertpapier wertpapier) {
        if (wertpapier instanceof Aktie aktie) {
            return aktienView.createDetailsDialog(aktie);
        } else if (wertpapier instanceof ETF etf) {
            return etfView.createDetailsDialog(etf);
        } else if (wertpapier instanceof Anleihe anleihe) {
            return anleiheView.createDetailsDialog(anleihe);
        } else {
            throw new IllegalArgumentException("Unbekannter Wertpapier-Typ");
        }
    }
}
