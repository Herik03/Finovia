package org.vaadin.example.application.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Nutzer-Klasse, die die Eigenschaften und Methoden eines Nutzers repräsentiert.
 * Diese Klasse implementiert das Beobachter-Interface, um Benachrichtigungen
 * über Änderungen an Supportanfragen zu erhalten.
 *
 * @author Sören, Ben
 * @version 2.0
 */
@Entity
@Table(name = "USER")
public class Nutzer implements Beobachter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Getter @Setter
    private String username;

    @Column(nullable = false)
    @Getter @Setter
    private String passwort;

    @Getter @Setter
    private String vorname;

    @Getter @Setter
    private String nachname;

    @Getter @Setter
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Getter @Setter
    private Collection<String> roles;

    @Getter
    private final List<Depot> depots = new ArrayList<>();

    @Getter @Setter
    private Watchlist watchlist;

    // Registrierungsdatum hinzugefügt
    @Getter
    private final LocalDateTime registrierungsDatum;

    // Liste der Benachrichtigungen für den Nutzer
    private final List<String> benachrichtigungen = new ArrayList<>();

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
    public Nutzer(String vorname, String nachname, String email, String passwort, String benutzername, Watchlist watchlist) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.passwort = passwort;
        this.username = benutzername;
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
    public Nutzer(String vorname, String nachname, String email, String passwort, String benutzername) {
        this(vorname, nachname, email, passwort, benutzername, null);
    }

    /**
     * Fügt ein Depot zur Liste der Depots des Nutzers hinzu
     *
     * @param depot Das hinzuzufügende Depot
     * @throws IllegalArgumentException wenn das Depot null ist
     */
    public void depotHinzufuegen(Depot depot) {
        Objects.requireNonNull(depot, "Depot darf nicht null sein");
        depots.add(depot);
    }

    /**
     * Entfernt ein Depot aus der Liste der Depots des Nutzers
     *
     * @param depot Das zu entfernende Depot
     * @return true wenn das Depot entfernt wurde, false wenn es nicht gefunden wurde
     */
    public boolean depotEntfernen(Depot depot) {
        return depots.remove(depot);
    }

    /**
     * Sucht ein Depot anhand seiner ID
     *
     * @param depotId Die ID des gesuchten Depots
     * @return Das gefundene Depot oder null, wenn keines gefunden wurde
     */
    public Depot findeDepotNachId(String depotId) {
        return depots.stream()
                .filter(depot -> depot.getDepotId().equals(depotId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Erstellt ein neues Depot für den Nutzer und fügt es zur Liste hinzu
     *
     * @param depotId Die ID des neuen Depots
     * @param name Der Name des neuen Depots
     * @return Das neu erstellte Depot
     */
    public Depot erstelleNeuesDepot(String depotId, String name) {
        Depot neuesDepot = new Depot(depotId, name, this);
        depotHinzufuegen(neuesDepot);
        return neuesDepot;
    }

    // Implementierung der Beobachter-Methode
    /**
     * Diese Methode wird aufgerufen, wenn eine Aktualisierung an einer
     * Supportanfrage erfolgt.
     *
     * @param ereignisTyp Der Typ des Ereignisses (z.B. Antwort aktualisiert, Status geändert)
     * @param supportanfrage Die betroffene Supportanfrage
     */
    @Override
    public void aktualisieren(String ereignisTyp, Supportanfrage supportanfrage) {
        String benachrichtigung = erstelleBenachrichtigungsText(ereignisTyp, supportanfrage);

        // Benachrichtigung zur Liste hinzufügen
        benachrichtigungen.add(benachrichtigung);

        // Hier könnte zusätzlich eine E-Mail-Benachrichtigung verschickt werden
        // oder andere Aktionen durchgeführt werden
        System.out.println("Benachrichtigung an " + this.username + ": " + benachrichtigung);
    }

    /**
     * Erstellt einen Benachrichtigungstext basierend auf dem Ereignistyp
     *
     * @param ereignisTyp Der Typ des Ereignisses
     * @param supportanfrage Die betroffene Supportanfrage
     * @return Der erstellte Benachrichtigungstext
     */
    private String erstelleBenachrichtigungsText(String ereignisTyp, Supportanfrage supportanfrage) {
        return switch (ereignisTyp) {
            case "ANTWORT_AKTUALISIERT" -> "Neue Antwort auf Ihre Supportanfrage (ID: " +
                    supportanfrage.getSupportRequestId() + "): " +
                    supportanfrage.getAntwort();
            case "STATUS_GEÄNDERT" -> "Der Status Ihrer Supportanfrage (ID: " +
                    supportanfrage.getSupportRequestId() + ") wurde auf '" +
                    supportanfrage.getStatus() + "' geändert.";
            default -> "Aktualisierung bei Ihrer Supportanfrage (ID: " +
                    supportanfrage.getSupportRequestId() + ")";
        };
    }

    /**
     * Gibt eine unveränderliche Liste aller Benachrichtigungen zurück
     *
     * @return Unveränderliche Liste der Benachrichtigungen
     */
    public List<String> getBenachrichtigungen() {
        return Collections.unmodifiableList(benachrichtigungen);
    }

    /**
     * Löscht eine Benachrichtigung an einem bestimmten Index
     *
     * @param index Der Index der zu löschenden Benachrichtigung
     * @throws IndexOutOfBoundsException wenn der Index außerhalb des gültigen Bereichs liegt
     */
    public void benachrichtigungLoeschen(int index) {
        benachrichtigungen.remove(index);
    }

    /**
     * Löscht alle Benachrichtigungen
     */
    public void allesBenachrichtigungenLoeschen() {
        benachrichtigungen.clear();
    }

    /**
     * Prüft, ob das übergebene Passwort mit dem gespeicherten Passwort übereinstimmt
     * In einer echten Anwendung würde hier eine Passwort-Hash-Überprüfung erfolgen
     *
     * @param passwortEingabe Das zu prüfende Passwort
     * @return true wenn das Passwort übereinstimmt, sonst false
     */
    public boolean pruefePasswort(String passwortEingabe) {
        return this.passwort.equals(passwortEingabe);
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
     * Filtert Benachrichtigungen nach einem Suchbegriff
     *
     * @param suchbegriff Der Suchbegriff
     * @return Liste der gefilterten Benachrichtigungen
     */
    public List<String> filtereNachrichtenNachSuchbegriff(String suchbegriff) {
        return benachrichtigungen.stream()
                .filter(nachricht -> nachricht.toLowerCase().contains(suchbegriff.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutzer nutzer = (Nutzer) o;
        return id == nutzer.id;
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
}
