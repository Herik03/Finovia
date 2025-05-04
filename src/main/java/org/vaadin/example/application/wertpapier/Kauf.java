package org.vaadin.example.application.wertpapier;

import java.time.LocalDate;

public class Kauf extends Transaktion{
    private String handelsplatz;

    public Kauf(String handelsplatz, LocalDate datum, double gebühren, double kurs, int stückzahl, Wertpapier wertpapier, Ausschuettung ausschüttung) {
        super(datum, gebühren, kurs, stückzahl, wertpapier, ausschüttung);
        this.handelsplatz = handelsplatz;
    }

    public String getHandelsplatz() {
        return handelsplatz;
    }

    public void setHandelsplatz(String handelsplatz) {
        this.handelsplatz = handelsplatz;
    }
}
