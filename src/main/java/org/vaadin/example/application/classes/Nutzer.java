package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;


/**
 * Nutzer-Klasse, die die Eigenschaften und Methoden eines Nutzers repräsentiert.
 * Diese Klasse implementiert das Beobachter-Interface, um Benachrichtigungen
 * über Änderungen an Supportanfragen zu erhalten.
 *
 * @author Sören, Ben
 * @version 2.0
 */
@Entity
@Table(name = "Nutzer")
public class Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(unique = true, nullable = false)
    @Getter @Setter
    private String username;


    @Column(nullable = false)
    @Getter @Setter
    private String passwort;

    @Column(unique = true)
    @Getter @Setter
    private String steuerId;

    @Getter @Setter
    private String vorname;

    @Getter @Setter
    private String nachname;

    @Getter @Setter
    private String email;

    @Setter @Getter
    private String resetToken = "";

    @Setter @Getter
    private Date resetTokenExpiration;

    @ElementCollection(fetch = FetchType.EAGER)
    @Getter @Setter
    private Collection<String> roles;

    @Getter
    @OneToMany(mappedBy = "besitzer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Depot> depots = new ArrayList<>();

    @Getter @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "watchlist_id")
    private Watchlist watchlist;

    // Registrierungsdatum hinzugefügt
    @Getter
    private final LocalDateTime registrierungsDatum;

    // Konstruktor für JPA
    public Nutzer() {
        this.registrierungsDatum = LocalDateTime.now();
    }

    /**
     * Konstruktor für einen neuen Nutzer mit Watchlist
     *
     * @param vorname Der Vorname des Nutzers
     * @param nachname Der Nachname des Nutzers
     * @param email Die E-Mail-Adresse des Nutzers
     * @param passwort Das Passwort des Nutzers
     * @param benutzername Der Benutzername des Nutzers
     * @param watchlist Die Watchlist des Nutzers
     */
    public Nutzer(String vorname, String nachname, String email, String passwort, String benutzername,String steuerId,Watchlist watchlist) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.passwort = passwort;
        this.username = benutzername;
        this.steuerId = steuerId;
        this.watchlist = watchlist;
        this.registrierungsDatum = LocalDateTime.now();
    }

    /**
     * Konstruktor für einen neuen Nutzer ohne Watchlist
     *
     * @param vorname Der Vorname des Nutzers
     * @param nachname Der Nachname des Nutzers
     * @param email Die E-Mail-Adresse des Nutzers
     * @param passwort Das Passwort des Nutzers
     * @param benutzername Der Benutzername des Nutzers
     */
    public Nutzer(String vorname, String nachname, String email, String passwort, String benutzername,String steuerId) {
        this(vorname, nachname, email, passwort, benutzername, steuerId, null);
    }

    /**
     * Fügt ein Depot zur Liste der Depots des Nutzers hinzu
     *
     * @param depot Das hinzuzufügende Depot
     * @throws IllegalArgumentException wenn das Depot null ist
     */
    public void depotHinzufuegen(Depot depot) {
        Objects.requireNonNull(depot, "Depot darf nicht null sein");
        if (!depots.contains(depot)) {
            depots.add(depot);
            depot.setBesitzer(this);
        }
    }

    /**
     * Entfernt ein Depot aus der Liste der Depots des Nutzers
     *
     * @param depot Das zu entfernende Depot
     * @return true, wenn das Depot entfernt wurde, false wenn es nicht gefunden wurde
     */
    public boolean depotEntfernen(Depot depot) {
        if (depots.remove(depot)) {
            depot.setBesitzer(null);
            return true;
        }
        return false;
    }

    /**
     * Gibt die Steuer-ID zurück oder einen leeren String, wenn die Steuer-ID null ist.
     *
     * @return Steuer-ID oder leerer String
     */
    public String getSteuerIdOrEmpty() {
        return steuerId != null ? steuerId : "";
    }

    /**
     * Gibt den Benutzernamen zurück oder einen leeren String, wenn der Benutzername null ist.
     * Dies sollte nicht vorkommen, da das Feld als nicht-null markiert ist, aber zur Sicherheit.
     *
     * @return Benutzername oder leerer String
     */
    public String getUsernameOrEmpty() {
        return username != null ? username : "";
    }

    /**
     * Gibt den vollständigen Namen des Nutzers zurück
     *
     * @return Vorname und Nachname als ein String
     */
    public String getVollerName() {
        return vorname + " " + nachname;
    }

    /**
     * Gibt den Vornamen zurück oder einen leeren String, wenn der Vorname null ist.
     *
     * @return Vorname oder leerer String
     */
    public String getVornameOrEmpty() {
        return vorname != null ? vorname : "";
    }

    /**
     * Gibt den Nachnamen zurück oder einen leeren String, wenn der Nachname null ist.
     *
     * @return Nachname oder leerer String
     */
    public String getNachnameOrEmpty() {
        return nachname != null ? nachname : "";
    }

    /**
     * Gibt die E-Mail-Adresse zurück oder einen leeren String, wenn die E-Mail null ist.
     *
     * @return E-Mail oder leerer String
     */
    public String getEmailOrEmpty() {
        return email != null ? email : "";
    }

    /**
     * Formatiert das Registrierungsdatum in ein lesbares Format.
     * Gibt einen leeren String zurück, wenn das Datum null ist.
     *
     * @param pattern Das Formatmuster (z. B. "dd.MM.yyyy HH:mm")
     * @return Formatiertes Datum oder leerer String
     */
    public String getFormattedRegistrierungsDatum(String pattern) {
        if (registrierungsDatum == null) {
            return "";
        }
        return registrierungsDatum.format(java.time.format.DateTimeFormatter.ofPattern(pattern));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutzer nutzer = (Nutzer) o;
        return id.equals(nutzer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Nutzer{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", anzahlDepots=" + depots.size() +
                '}';
    }
    public Long getNutzerId() {
        return id;
    }

}
