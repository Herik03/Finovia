package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Nutzer-Klasse, die die Eigenschaften und Methoden eines Nutzers repräsentiert.
 * Diese Klasse implementiert das Beobachter-Interface, um Benachrichtigungen
 * über Änderungen an Supportanfragen zu erhalten.
 */
//        von Ben, Sören

@Entity
@Table(name = "Depot")
@NoArgsConstructor
public class Depot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long depotId;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "nutzer_id")
    private Nutzer besitzer;

    @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<DepotWertpapier> depotWertpapiere = new ArrayList<>();

    /**
     * -- SETTER --
     *  Setter für Saldo (Cash Balance).
     */
    @Setter
    @Getter
    private double saldo = 0.0;

    /**
     * Konstruktor für ein neues Depot
     *
     * @param name Der Name des Depots
     * @param besitzer Der Besitzer des Depots
     */
    public Depot(String name, Nutzer besitzer) {
        this.name = name;
        this.besitzer = besitzer;
    }

    public void wertpapierHinzufuegen(Wertpapier wertpapier, int anzahl, double kaufpreisProStueck) {
        for (DepotWertpapier dw : depotWertpapiere) {
            if (dw.getWertpapier().equals(wertpapier)) {
                // Bisherige Werte:
                int bisherigeAnzahl = dw.getAnzahl();
                double bisherigerEinstandspreis = dw.getEinstandspreis() != null ? dw.getEinstandspreis() : 0.0;

                // Gesamtwert vorher und neuer Kaufwert:
                double gesamtwertAlt = bisherigerEinstandspreis * bisherigeAnzahl;
                double gesamtwertNeu = kaufpreisProStueck * anzahl;

                int neueAnzahl = bisherigeAnzahl + anzahl;
                double neuerEinstandspreis = (gesamtwertAlt + gesamtwertNeu) / neueAnzahl;

                dw.setAnzahl(neueAnzahl);
                dw.setEinstandspreis(neuerEinstandspreis);
                dw.setDepot(this);
                return;
            }
        }
        // Neu anlegen, Einstandspreis direkt setzen
        depotWertpapiere.add(new DepotWertpapier(this, wertpapier, anzahl, kaufpreisProStueck));
    }

    public boolean wertpapierEntfernen(Wertpapier wertpapier, int anzahl) {
        if (wertpapier == null || anzahl <= 0) {
            return false;  // Ungültige Eingabe
        }

        for (DepotWertpapier dw : depotWertpapiere) {
            if (dw.getWertpapier().equals(wertpapier)) {
                if (dw.getAnzahl() > anzahl) {
                    // Reduziere die Anzahl
                    dw.setAnzahl(dw.getAnzahl() - anzahl);
                    return true;
                } else if (dw.getAnzahl() == anzahl) {
                    // Entferne das Wertpapier vollständig, wenn alle verkauft sind
                    depotWertpapiere.remove(dw);
                    return true;
                } else {
                    // Nicht genug Wertpapiere im Depot
                    return false;
                }
            }
        }

        // Wertpapier nicht gefunden
        return false;
    }

    public List<DepotWertpapier> getDepotWertpapiere() {
        return new ArrayList<>(depotWertpapiere);
    }

    public List<Wertpapier> getWertpapiere() {
        return depotWertpapiere.stream().map(DepotWertpapier::getWertpapier).toList();
    }

    /**
     * Gibt eine Liste aller historischen Dividenden (vom Typ {@link Dividende}) im Depot zurück.
     *
     * Diese Methode berücksichtigt nur Dividenden aus der internen Ausschüttungsliste der Wertpapiere
     * und filtert nach dem konkreten Typ {@code Dividende}.
     *
     * @return Liste aller Dividenden im Depot
     */
    public List<Dividende> getDividendenHistorie() {
        List<Dividende> alleDividenden = new ArrayList<>();

        for (Wertpapier wp : getWertpapiere()) {
            List<Dividende> dividendenDesWertpapiers = wp.getAusschuettungen()
                    .stream()
                    .filter(a -> a instanceof Dividende)
                    .map(a -> (Dividende) a)
                    .toList();
            alleDividenden.addAll(dividendenDesWertpapiers);
        }

        return alleDividenden;
    }

    /**
     * Gibt eine Liste aller historischen Zinszahlungen (vom Typ {@link Zinszahlung}) im Depot zurück.
     *
     * Diese Methode durchsucht alle Wertpapiere des Depots und extrahiert die zugehörigen Ausschüttungen,
     * sofern sie vom Typ {@code Zinszahlung} sind.
     *
     * @return Liste aller Zinszahlungen im Depot
     */
    public List<Zinszahlung> getZinszahlungenHistorie() {
        return getWertpapiere().stream()
                .flatMap(wp -> wp.getAusschuettungen().stream())
                .filter(a -> a instanceof Zinszahlung)
                .map(a -> (Zinszahlung) a)
                .collect(Collectors.toList());
    }

    /**
     * Gibt eine Liste aller historischen ETF-Dividenden (vom Typ {@link ETFDividende}) im Depot zurück.
     *
     * Diese Methode durchsucht alle Wertpapiere des Depots und filtert deren Ausschüttungen
     * auf solche, die vom Typ {@code ETFDividende} sind.
     *
     * @return Liste aller ETF-Dividenden im Depot
     */
    public List<ETFDividende> getETFDividendeHistorie() {
        return getWertpapiere().stream()
                .flatMap(wp -> wp.getAusschuettungen().stream())
                .filter(a -> a instanceof ETFDividende)
                .map(a -> (ETFDividende) a)
                .collect(Collectors.toList());
    }

    /**
     * Gibt eine kombinierte Liste aller Ausschüttungen des Depots zurück, bestehend aus:
     * <ul>
     *     <li>{@link Dividende}</li>
     *     <li>{@link Zinszahlung}</li>
     *     <li>{@link ETFDividende}</li>
     * </ul>
     *
     * Diese Methode dient der einheitlichen Darstellung aller Ausschüttungstypen im UI.
     *
     * @return Liste aller Ausschüttungen im Depot
     */
    public List<Ausschuettung> getAlleAusschuettungen() {
        List<Ausschuettung> result = new ArrayList<>();
        result.addAll(getDividendenHistorie());
        result.addAll(getZinszahlungenHistorie());
        result.addAll(getETFDividendeHistorie());
        return result;
    }

    /**
     * Prüft für den aktuellen Tag, ob neue Ausschüttungen (Dividenden, Zinsen, ETF-Dividenden) fällig sind
     * und bucht diese bei Bedarf ins Depot ein.
     *
     * Dabei wird je nach Wertpapierklasse geprüft:
     * <ul>
     *     <li>Aktien: über {@code getDividendDate()} und {@code getDividendPerShare()}</li>
     *     <li>Anleihen: gegen gespeicherte {@link Zinszahlung} mit passendem Datum</li>
     *     <li>ETFs: gegen gespeicherte {@link ETFDividende} mit passendem Datum</li>
     * </ul>
     *
     * Die gebuchten Netto-Beträge werden dem {@code saldo} des Depots gutgeschrieben.
     *
     * @param aktuellesDatum Das Datum, für das Ausschüttungen geprüft und gebucht werden sollen
     */
    public void pruefeUndBucheAusschuettungen(LocalDate aktuellesDatum) {
        for (Wertpapier wp : getWertpapiere()) {

            // === Dividende (von API-Daten) ===
            if (wp instanceof Aktie aktie && aktie.getDividendDate() != null) {
                if (aktuellesDatum.equals(aktie.getDividendDate())) {
                    int anzahl = getDepotWertpapierFor(aktie) != null ? getDepotWertpapierFor(aktie).getAnzahl() : 0;
                    if (anzahl > 0 && aktie.getDividendPerShare() > 0.0) {
                        double brutto = aktie.getDividendPerShare() * anzahl;
                        double steuer = brutto * 0.25;
                        double netto = brutto - steuer;

                        Dividende dividende = new Dividende(
                                anzahl,
                                aktie.getDividendYield(),
                                netto,
                                aktuellesDatum,
                                steuer,
                                aktie
                        );

                        aktie.addAusschuettung(dividende);
                        saldo += netto;
                    }
                }
            }

            // === Zinszahlung (aus DB-Daten) ===
            else if (wp instanceof Anleihe anleihe) {
                for (Ausschuettung a : anleihe.getAusschuettungen()) {
                    if (a instanceof Zinszahlung zins && aktuellesDatum.equals(zins.getDatum())) {
                        int anzahl = getDepotWertpapierFor(anleihe) != null ? getDepotWertpapierFor(anleihe).getAnzahl() : 0;
                        if (anzahl > 0) {
                            double brutto = zins.getZinssatz() * anzahl;
                            double steuer = brutto * 0.25;
                            double netto = brutto - steuer;

                            Zinszahlung gezahlt = new Zinszahlung(
                                    anzahl,
                                    zins.getZinssatz(),
                                    netto,
                                    aktuellesDatum,
                                    steuer,
                                    anleihe,
                                    zins.getFrequenz()
                            );

                            anleihe.addAusschuettung(gezahlt);
                            saldo += netto;
                        }
                    }
                }
            }

            // === ETF-Dividende (aus DB-Daten) ===
            else if (wp instanceof ETF etf) {
                for (Ausschuettung a : etf.getAusschuettungen()) {
                    if (a instanceof ETFDividende etfDiv && aktuellesDatum.equals(etfDiv.getDatum())) {
                        int anzahl = getDepotWertpapierFor(etf) != null ? getDepotWertpapierFor(etf).getAnzahl() : 0;
                        if (anzahl > 0) {
                            double brutto = etfDiv.getBetrag() * anzahl;
                            double steuer = brutto * 0.25;
                            double netto = brutto - steuer;

                            ETFDividende gezahlt = new ETFDividende(
                                    anzahl,
                                    etfDiv.getBetrag(),
                                    netto,
                                    aktuellesDatum,
                                    steuer,
                                    etf,
                                    etfDiv.getFrequenz()
                            );

                            etf.addAusschuettung(gezahlt);
                            saldo += netto;
                        }
                    }
                }
            }
        }
    }


    /**
     * Liefert das DepotWertpapier zum übergebenen Wertpapier oder null wenn nicht vorhanden.
     */
    public DepotWertpapier getDepotWertpapierFor(Wertpapier wertpapier) {
        for (DepotWertpapier dw : depotWertpapiere) {
            if (dw.getWertpapier().equals(wertpapier)) {
                return dw;
            }
        }
        return null;
    }

    public int getAnzahlAktien(String symbol) {
        for (DepotWertpapier dw : depotWertpapiere) {
            if (dw.getWertpapier() instanceof Aktie aktie && aktie.getSymbol().equals(symbol)) {
                return dw.getAnzahl();
            }
        }
        return 0;
    }

}
