package org.vaadin.example.application.classes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Observer-Interface
interface Beobachter {
    void aktualisieren(String ereignisTyp, Supportanfrage supportanfrage);
}
//        von Ben
// Observable/Subject-Klasse
public class Supportanfrage {

    // Getter und Setter mit Benachrichtigungen
    @Setter @Getter
    private int supportRequestId;
    @Getter @Setter
    private String nachricht;
    @Getter
    private String antwort;
    @Getter
    private String status;
    @Setter @Getter
    private LocalDateTime erstellungsdatum;
    @Setter @Getter
    private Nutzer ersteller;

    // Liste der Beobachter (normalerweise nur der Ersteller und ggf. Support-Mitarbeiter)
    private List<Beobachter> beobachter = new ArrayList<>();

    // Konstruktor für neue Supportanfrage
    public Supportanfrage(String nachricht, Nutzer ersteller) {
        this.nachricht = nachricht;
        this.ersteller = ersteller;
        this.status = "Offen";
        this.erstellungsdatum = LocalDateTime.now();

        // Wenn der Nutzer das Beobachter-Interface implementiert,
        // automatisch als Beobachter hinzufügen
        if (ersteller != null) {
            beobachterHinzufuegen((Beobachter) ersteller);
        }
    }

    // Konstruktor mit allen Attributen
    public Supportanfrage(int id, String nachricht, String antwort, String status,
                          LocalDateTime erstellungsdatum, Nutzer ersteller) {
        this.supportRequestId = id;
        this.nachricht = nachricht;
        this.antwort = antwort;
        this.status = status;
        this.erstellungsdatum = erstellungsdatum;
        this.ersteller = ersteller;

        // Wenn der Nutzer das Beobachter-Interface implementiert,
        // automatisch als Beobachter hinzufügen
        if (ersteller != null) {
            beobachterHinzufuegen((Beobachter) ersteller);
        }
    }

    // Methoden für das Observer-Pattern
    public void beobachterHinzufuegen(Beobachter beobachter) {
        if (!this.beobachter.contains(beobachter)) {
            this.beobachter.add(beobachter);
        }
    }

    public void beobachterEntfernen(Beobachter beobachter) {
        this.beobachter.remove(beobachter);
    }

    private void beobachterBenachrichtigen(String ereignisTyp) {
        for (Beobachter beobachter : this.beobachter) {
            beobachter.aktualisieren(ereignisTyp, this);
        }
    }

    public void setAntwort(String antwort) {
        String alterStatus = this.status;
        this.antwort = antwort;

        // Wenn eine Antwort gesetzt wird, ändere den Status auf "Beantwortet"
        if (antwort != null && !antwort.isEmpty()) {
            this.status = "Beantwortet";
        }

        // Benachrichtigen über neue Antwort
        beobachterBenachrichtigen("ANTWORT_AKTUALISIERT");

        // Wenn sich der Status geändert hat, auch darüber benachrichtigen
        if (!alterStatus.equals(this.status)) {
            beobachterBenachrichtigen("STATUS_GEÄNDERT");
        }
    }

    public void setStatus(String status) {
        if (!this.status.equals(status)) {
            String alterStatus = this.status;
            this.status = status;
            beobachterBenachrichtigen("STATUS_GEÄNDERT");
        }
    }

}
