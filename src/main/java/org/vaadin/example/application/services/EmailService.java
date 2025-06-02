package org.vaadin.example.application.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Support;
import org.vaadin.example.application.models.SupportRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private Support supportService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private final List<String> emailLog = new ArrayList<>();

    @PostConstruct
    public void init() {
        System.out.println("EmailService initialisiert");
    }

    /**
     * Simuliert den E-Mail-Versand einer Support-Anfrage
     */
    public boolean sendSupportRequest(SupportRequest request, String userEmail) {
        try {
            // Eindeutige Ticket-ID generieren
            String ticketId = "SUP-" + System.currentTimeMillis();
            request.setTicketId(ticketId);

            // Nachricht erstellen
            String emailContent = createEmailContent(request, userEmail, ticketId);

            // Nachricht protokollieren
            logEmail(emailContent);

            System.out.println("Support-Anfrage erfolgreich erstellt mit Ticket-ID: " + ticketId);
            return true;
        } catch (Exception e) {
            System.err.println("Fehler beim Erstellen der Support-Anfrage: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Erstellt den E-Mail-Inhalt
     */
    private String createEmailContent(SupportRequest request, String userEmail, String ticketId) {
        StringBuilder content = new StringBuilder();
        content.append("=== SIMULIERTE SUPPORT-ANFRAGE ===\n");
        content.append("Von: ").append(userEmail).append("\n");
        content.append("An: support@finovia.de\n");
        content.append("Betreff: Support-Anfrage: ").append(request.getCategory()).append("\n");
        content.append("Datum: ").append(LocalDateTime.now().format(formatter)).append("\n\n");
        content.append("Ticket-ID: ").append(ticketId).append("\n");
        content.append("Kategorie: ").append(request.getCategory()).append("\n");
        content.append("Status: ").append(request.getStatus()).append("\n");
        content.append("Erstellt am: ").append(request.getCreationDate()).append("\n\n");
        content.append("Beschreibung:\n").append(request.getDescription()).append("\n");
        content.append("==============================\n");
        return content.toString();
    }

    /**
     * Fügt eine E-Mail zum Log hinzu
     */
    private void logEmail(String emailContent) {
        emailLog.add(emailContent);
        // Begrenze die Größe des Logs auf 100 Einträge
        if (emailLog.size() > 100) {
            emailLog.remove(0);
        }
        System.out.println(emailContent);
    }

    /**
     * Gibt das E-Mail-Log zurück
     */
    public List<String> getEmailLog() {
        return new ArrayList<>(emailLog);
    }


    /**
     * Manuelles Hinzufügen einer Antwort zu einer Support-Anfrage
     */
    public void addResponse(SupportRequest request, String responseText) {
        String currentTime = LocalDateTime.now().format(formatter);
        String formattedResponse = "Antwort von support@finovia.de am " + currentTime + ":\n\n" + responseText;

        supportService.addCommentToRequest(request, formattedResponse);
        System.out.println("Manuelle Antwort auf Ticket " + request.getTicketId() + " hinzugefügt.");
    }

    /**
     * Schließt eine Support-Anfrage
     */
    public void closeRequest(SupportRequest request, String reason) {
        String currentTime = LocalDateTime.now().format(formatter);
        String closingMessage = "Ticket geschlossen am " + currentTime + " mit Begründung: " + reason;

        supportService.addCommentToRequest(request, closingMessage);
        supportService.updateRequestStatus(request, "Geschlossen");
        System.out.println("Ticket " + request.getTicketId() + " manuell geschlossen.");
    }
}
