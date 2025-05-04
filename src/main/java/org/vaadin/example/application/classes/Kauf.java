package org.vaadin.example.application.classes;

import java.sql.Date;

public class Kauf extends Transaktion {

    public Kauf(Date datum, double gebuehren, double kurs, int stueckzahl)
    {
        super(datum,gebuehren,kurs,stueckzahl);
    }
}
