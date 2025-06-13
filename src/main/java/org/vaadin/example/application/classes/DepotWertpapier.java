package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.*;

/**
 * Repräsentiert die Beziehung zwischen einem Depot und einem darin gehaltenen Wertpapier.
 * Enthält Informationen über die Anzahl der gehaltenen Wertpapiere und den Einstandspreis.
 *
 * Wird als Join-Entity zwischen {@link Depot} und {@link Wertpapier} verwendet.
 *
 * @author Sören Heß, Jan Schwarzer
 */
@Entity
@Table(name = "DepotWertpapier")
@NoArgsConstructor
public class DepotWertpapier {
    /**
     * Eindeutige ID des DepotWertpapiers (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    /**
     * Das zugehörige Depot.
     */
    @ManyToOne
    @JoinColumn(name = "depot_id")
    @Getter @Setter
    private Depot depot;

    /**
     * Das zugehörige Wertpapier.
     */
    @ManyToOne
    @JoinColumn(name = "wertpapier_id")
    @Getter @Setter
    private Wertpapier wertpapier;

    /**
     * Anzahl der gehaltenen Wertpapiere.
     */
    @Getter @Setter
    private int anzahl;

    /**
     * Einstandspreis pro Wertpapier (kann null sein).
     */
    @Getter @Setter
    @Column(nullable = true)
    private Double einstandspreis;

    /**
     * Konstruktor zum Erstellen eines DepotWertpapiers ohne Einstandspreis.
     *
     * @param depot Das zugehörige Depot
     * @param wertpapier Das zugehörige Wertpapier
     * @param anzahl Anzahl der gehaltenen Wertpapiere
     */
    public DepotWertpapier(Depot depot, Wertpapier wertpapier, int anzahl) {
        this.depot = depot;
        this.wertpapier = wertpapier;
        this.anzahl = anzahl;
        if (depot != null) {
            depot.getDepotWertpapiere().add(this);
        }
    }

    /**
     * Konstruktor zum Erstellen eines DepotWertpapiers mit Einstandspreis.
     *
     * @param depot Das zugehörige Depot
     * @param wertpapier Das zugehörige Wertpapier
     * @param anzahl Anzahl der gehaltenen Wertpapiere
     * @param einstandspreis Einstandspreis pro Wertpapier
     */
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
