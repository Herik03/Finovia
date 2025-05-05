package org.vaadin.example.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Nutzer-Klasse, die die Eigenschaften und Methoden eines Nutzers repräsentiert.
 * Diese Klasse implementiert das Beobachter-Interface, um Benachrichtigungen
 * über Änderungen an Supportanfragen zu erhalten.
 */
//        von Ben
public class Nutzer implements Beobachter {

    private int id;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;
    private String username;
    private Depot depot;  //evtl depotliste
    private Watchlist watchlist;

    // Liste der Benachrichtigungen für den Nutzer
    private List<String> benachrichtigungen = new ArrayList<>();

    /**
     * Konstruktor für einen neuen Nutzer
     *
     * @param vorname Der Vorname des Nutzers
     * @param nachname Der Nachname des Nutzers
     * @param email Die E-Mail-Adresse des Nutzers
     * @param passwort Das Passwort des Nutzers
     * @param benutzername Der Benutzername des Nutzers
     */
    public Nutzer(String vorname, String nachname, String email, String passwort, String benutzername) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.passwort = passwort;
        this.username = benutzername;
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
        String benachrichtigung = "";

        switch (ereignisTyp) {
            case "ANTWORT_AKTUALISIERT":
                benachrichtigung = "Neue Antwort auf Ihre Supportanfrage (ID: " +
                        supportanfrage.getSupportRequestId() + "): " +
                        supportanfrage.getAntwort();
                break;
            case "STATUS_GEÄNDERT":
                benachrichtigung = "Der Status Ihrer Supportanfrage (ID: " +
                        supportanfrage.getSupportRequestId() + ") wurde auf '" +
                        supportanfrage.getStatus() + "' geändert.";
                break;
            default:
                benachrichtigung = "Aktualisierung bei Ihrer Supportanfrage (ID: " +
                        supportanfrage.getSupportRequestId() + ")";
        }

        // Benachrichtigung zur Liste hinzufügen
        benachrichtigungen.add(benachrichtigung);

        // Hier könnte zusätzlich eine E-Mail-Benachrichtigung verschickt werden
        // oder andere Aktionen durchgeführt werden
        System.out.println("Benachrichtigung an " + this.username + ": " + benachrichtigung);
    }

    // Getter für Benachrichtigungen
    public List<String> getBenachrichtigungen() {
        return new ArrayList<>(benachrichtigungen);
    }

    public void benachrichtigungLoeschen(int index) {
        if (index >= 0 && index < benachrichtigungen.size()) {
            benachrichtigungen.remove(index);
        }
    }

    public void allesBenachrichtigungenLoeschen() {
        benachrichtigungen.clear();
    }

    // Existierende Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
