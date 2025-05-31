package org.vaadin.example.application.views;

import com.vaadin.flow.component.dialog.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.classes.*;

@Component
public class WertpapierDetailViewFactory {

    private final AktienView aktienView;
    private final ETFView etfView;
    private final AnleiheView anleiheView;

    @Autowired
    public WertpapierDetailViewFactory(AktienView aktienView, ETFView etfView, AnleiheView anleiheView) {
        this.aktienView = aktienView;
        this.etfView = etfView;
        this.anleiheView = anleiheView;
    }

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
