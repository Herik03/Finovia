package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repräsentiert eine Watchlist, in der ein Nutzer Wertpapiere speichern kann,
 * um deren Entwicklung zu beobachten.
 *
 * @author Henrik
 * @version 1.0
 */
@Entity
@Table(name = "Watchlist")
@NoArgsConstructor
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @OneToOne(mappedBy = "watchlist")
    private Nutzer nutzer;

    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "watchlist_wertpapier",
            joinColumns = @JoinColumn(name = "watchlist_id"),
            inverseJoinColumns = @JoinColumn(name = "wertpapier_id")
    )
    private List<Wertpapier> wertpapiere = new ArrayList<>();

    /**
     * Konstruktor für eine neue Watchlist
     *
     * @param name Name der Watchlist
     */
    public Watchlist(String name) {
        this.name = name;
    }

    /**
     * Fügt ein Wertpapier zur Watchlist hinzu
     *
     * @param wertpapier Das hinzuzufügende Wertpapier
     * @return true wenn das Wertpapier hinzugefügt wurde, false wenn es bereits enthalten war
     */
    public boolean addWertpapier(Wertpapier wertpapier) {
        if (!wertpapiere.contains(wertpapier)) {
            return wertpapiere.add(wertpapier);
        }
        return false;
    }

    /**
     * Entfernt ein Wertpapier aus der Watchlist
     *
     * @param wertpapier Das zu entfernende Wertpapier
     * @return true wenn das Wertpapier entfernt wurde, false wenn es nicht gefunden wurde
     */
    public boolean removeWertpapier(Wertpapier wertpapier) {
        return wertpapiere.remove(wertpapier);
    }

    /**
     * Prüft, ob ein bestimmtes Wertpapier in der Watchlist enthalten ist
     *
     * @param wertpapier Das zu prüfende Wertpapier
     * @return true wenn das Wertpapier in der Watchlist enthalten ist, sonst false
     */
    public boolean containsWertpapier(Wertpapier wertpapier) {
        return wertpapiere.contains(wertpapier);
    }

    /**
     * Leert die Watchlist (entfernt alle Wertpapiere)
     */
    public void clearWatchlist() {
        wertpapiere.clear();
    }

    /**
     * Setzt den Nutzer, dem diese Watchlist gehört
     *
     * @param nutzer Der Nutzer, dem die Watchlist gehören soll
     */
    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Watchlist watchlist = (Watchlist) o;
        return Objects.equals(id, watchlist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Watchlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", anzahlWertpapiere=" + wertpapiere.size() +
                '}';
    }
}
