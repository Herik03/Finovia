package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Getter
    @ManyToOne
    @JoinColumn(name = "nutzer_id")
    private Nutzer besitzer;

    @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DepotWertpapier> depotWertpapiere = new ArrayList<>();

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

    public void setBesitzer(Nutzer nutzer) {
        this.besitzer = nutzer;
        if (nutzer != null && !nutzer.getDepots().contains(this)) {
            nutzer.getDepots().add(this);
        }
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
}