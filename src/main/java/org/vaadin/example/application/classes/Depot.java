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

    @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private final List<DepotWertpapier> depotWertpapiere = new ArrayList<>();

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

    public void wertpapierHinzufuegen(Wertpapier wertpapier, int anzahl) {
        for (DepotWertpapier dw : depotWertpapiere) {
            if (dw.getWertpapier().equals(wertpapier)) {
                dw.setAnzahl(dw.getAnzahl() + anzahl);
                dw.setDepot(this);
                return;
            }
        }
        depotWertpapiere.add(new DepotWertpapier(this, wertpapier, anzahl));
    }

    public boolean wertpapierEntfernen(Wertpapier wertpapier, int anzahl) {
        for (DepotWertpapier dw : depotWertpapiere) {
            if (dw.getWertpapier().equals(wertpapier)) {
                if (dw.getAnzahl() > anzahl) {
                    dw.setAnzahl(dw.getAnzahl() - anzahl);
                } else {
                    dw.setDepot(null);
                    depotWertpapiere.remove(dw);
                }
                return true;
            }
        }
        return false;
    }

    public List<DepotWertpapier> getDepotWertpapiere() {
        return new ArrayList<>(depotWertpapiere);
    }

    public List<Wertpapier> getWertpapiere() {
        return depotWertpapiere.stream().map(DepotWertpapier::getWertpapier).toList();
    }

    public List<Dividende> getDividendenHistorie() {
        for (Wertpapier wp : getWertpapiere()) {
            return wp.getAusschuettungen()
                    .stream()
                    .filter(a -> a instanceof Dividende)
                    .map(a -> (Dividende) a)
                    .toList();
        }
        return new ArrayList<>();
    }

    public List<Zinszahlung> getZinszahlungenHistorie() {
        return getWertpapiere().stream()
                .flatMap(wp -> wp.getAusschuettungen().stream())
                .filter(a -> a instanceof Zinszahlung)
                .map(a -> (Zinszahlung) a)
                .collect(Collectors.toList());
    }

    public List<Ausschuettung> getAlleAusschuettungen() {
        List<Ausschuettung> result = new ArrayList<>();
        result.addAll(getDividendenHistorie());     // ← bereits vorhandene API-Logik
        result.addAll(getZinszahlungenHistorie());  // ← aus der DB
        return result;
    }

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

    /**
     * Setter für Saldo (Cash Balance).
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
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
