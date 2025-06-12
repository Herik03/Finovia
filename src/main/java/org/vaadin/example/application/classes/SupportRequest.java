package org.vaadin.example.application.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert eine Support-Anfrage mit Kategorie, Beschreibung, Status, Erstellungsdatum,
 * Ticket-ID, Kommentaren und Anhängen.
 *
 * Bietet Methoden zum Hinzufügen von Kommentaren und Anhängen.
 *
 * @author Ben Hübert
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportRequest {
    /**
     * Kategorie der Support-Anfrage (z. B. "Technisch", "Allgemein").
     */
    private String category;

    /**
     * Beschreibung des Problems oder Anliegens.
     */
    private String description;

    /**
     * Aktueller Status der Anfrage (z. B. "Offen", "In Bearbeitung", "Geschlossen").
     */
    private String status;

    /**
     * Erstellungsdatum der Anfrage im Format "yyyy-MM-dd".
     */
    private String creationDate;

    /**
     * Eindeutige Ticket-ID der Anfrage.
     */
    private String ticketId;

    /**
     * Liste der Kommentare zur Anfrage.
     */
    private List<String> comments = new ArrayList<>();

    /**
     * Liste der Anhänge (z. B. Dateipfade) zur Anfrage.
     */
    private List<String> attachments = new ArrayList<>();

    /**
     * Konstruktor ohne die optionalen Felder (Ticket-ID, Kommentare, Anhänge).
     *
     * @param category      Kategorie der Anfrage
     * @param description   Beschreibung des Problems
     * @param status        Status der Anfrage
     * @param creationDate  Erstellungsdatum
     */
    public SupportRequest(String category, String description, String status, String creationDate) {
        this.category = category;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
    }

    /**
     * Fügt der Anfrage einen Kommentar hinzu.
     *
     * @param comment Der hinzuzufügende Kommentar
     */
    public void addComment(String comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }

    /**
     * Fügt der Anfrage einen Anhang hinzu.
     *
     * @param attachmentPath Pfad zum Anhang
     */
    public void addAttachment(String attachmentPath) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachmentPath);
    }
}


