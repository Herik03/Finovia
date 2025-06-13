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
 * @author Henrik Dollmann
 * @version 1.0
 */
@Entity
@Table(name = "Watchlist")
@NoArgsConstructor
public class Watchlist {

    /**
     * Eindeutige ID der Watchlist (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    /**
     * Name der Watchlist.
     */
    @Getter
    @Setter
    private String name;

    /**
     * Der Nutzer, dem die Watchlist zugeordnet ist.
     */
    @Getter
    @Setter
    @OneToOne(mappedBy = "watchlist")
    private Nutzer nutzer;

    /**
     * Liste der Wertpapiere, die in der Watchlist beobachtet werden.
     */
    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "watchlist_wertpapier",
            joinColumns = @JoinColumn(name = "watchlist_id"),
            inverseJoinColumns = @JoinColumn(name = "wertpapier_id")
    )
    private List<Wertpapier> wertpapiere = new ArrayList<>();

    /**
     * Konstruktor für eine neue Watchlist.
     *
     * @param name Name der Watchlist
     */
    public Watchlist(String name) {
        this.name = name;
    }

    /**
     * Fügt ein Wertpapier zur Watchlist hinzu.
     *
     * @param wertpapier Das hinzuzufügende Wertpapier
     * @return true, wenn das Wertpapier hinzugefügt wurde, false, wenn es bereits enthalten war
     */
    public boolean addWertpapier(Wertpapier wertpapier) {
        if (!wertpapiere.contains(wertpapier)) {
            return wertpapiere.add(wertpapier);
        }
        return false;
    }

    /**
     * Entfernt ein Wertpapier aus der Watchlist.
     *
     * @param wertpapier Das zu entfernende Wertpapier
     * @return true, wenn das Wertpapier entfernt wurde, false, wenn es nicht gefunden wurde
     */
    public boolean removeWertpapier(Wertpapier wertpapier) {
        return wertpapiere.remove(wertpapier);
    }

    /**
     * Vergleicht diese Watchlist mit einem anderen Objekt auf Gleichheit anhand der ID.
     *
     * @param o Das zu vergleichende Objekt
     * @return true, wenn die IDs übereinstimmen, sonst false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Watchlist watchlist = (Watchlist) o;
        return Objects.equals(id, watchlist.id);
    }

    /**
     * Berechnet den Hashcode der Watchlist basierend auf der ID.
     *
     * @return Hashcode der Watchlist
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Gibt eine String-Repräsentation der Watchlist zurück.
     *
     * @return String mit ID, Name und Anzahl der Wertpapiere
     */
    @Override
    public String toString() {
        return "Watchlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", anzahlWertpapiere=" + wertpapiere.size() +
                '}';
    }
}
