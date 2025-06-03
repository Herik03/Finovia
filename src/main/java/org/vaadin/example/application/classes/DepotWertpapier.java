package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DepotWertpapier")
@NoArgsConstructor
public class DepotWertpapier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "depot_id")
    @Getter @Setter
    private Depot depot;

    @ManyToOne
    @JoinColumn(name = "wertpapier_id")
    @Getter @Setter
    private Wertpapier wertpapier;

    @Getter @Setter
    private int anzahl; // Anzahl der gehaltenen Wertpapiere

    @Getter @Setter
    @Column(nullable = true)
    private Double einstandspreis;


    public DepotWertpapier(Depot depot, Wertpapier wertpapier, int anzahl) {
        this.depot = depot;
        this.wertpapier = wertpapier;
        this.anzahl = anzahl;
        if (depot != null) {
            depot.getDepotWertpapiere().add(this);
        }
    }
    public DepotWertpapier(Depot depot, Wertpapier wertpapier, int anzahl, Double einstandspreis) {
        this.depot = depot;
        this.wertpapier = wertpapier;
        this.anzahl = anzahl;
        this.einstandspreis = einstandspreis;
        if (depot != null) {
            depot.getDepotWertpapiere().add(this);
        }
    }
}
