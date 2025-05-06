package org.vaadin.example.application.classes;

import java.sql.Date;

public class Transaktion
{
  private Date datum;
  private double gebuehren;
  private double kurs;
  private int stueckzahl;

  public Date getDatum() {
    return datum;
  }

  public void setDatum(Date datum) {
    this.datum = datum;
  }

  public int getStueckzahl() {
    return stueckzahl;
  }

  public void setStueckzahl(int stueckzahl) {
    this.stueckzahl = stueckzahl;
  }

  public double getKurs() {
    return kurs;
  }

  public void setKurs(double kurs) {
    this.kurs = kurs;
  }

  public double getGebuehren() {
    return gebuehren;
  }

  public void setGebuehren(double gebuehren) {
    this.gebuehren = gebuehren;
  }

  public Transaktion(Date datum, double gebuehren, double kurs, int stueckzahl)
  {
    this.datum = datum;
    this.gebuehren = gebuehren;
    this.kurs = kurs;
    this.stueckzahl = stueckzahl;
  }

}
