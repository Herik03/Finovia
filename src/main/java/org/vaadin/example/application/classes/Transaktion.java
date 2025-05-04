package org.vaadin.example.application.classes;

import java.sql.Date;

public class Transaktion
{
  private Date datum;
  private double gebuehren;
  private double kurs;
  private int stueckzahl;

  public Transaktion(Date datum, double gebuehren, double kurs, int stueckzahl)
  {
    this.datum = datum;
    this.gebuehren = gebuehren;
    this.kurs = kurs;
    this.stueckzahl = stueckzahl;
  }

}
