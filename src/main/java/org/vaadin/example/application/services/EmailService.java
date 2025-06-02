package org.vaadin.example.application.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Support;
import org.vaadin.example.application.classes.SupportRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class EmailService {

    @Autowired
    private Support supportService;
    
    private final Timer timer = new Timer(true);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private final List<String> emailLog = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Plane automatische Support-Antworten alle 2 Minuten
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                simulateResponses();
            }
        }, 60000, 120000); // 1 Minute initiale Verzögerung, dann alle 2 Minuten
        
        System.out.println("EmailService initialisiert - Lokale Simulation aktiviert");
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
     * Simuliert automatische Antworten auf Support-Anfragen
     */
    private void simulateResponses() {
        try {
            List<SupportRequest> requests = supportService.getAllRequests();
            for (SupportRequest request : requests) {
                // Nur auf offene Anfragen antworten, die eine Ticket-ID haben und noch keine Kommentare
                if ("Offen".equals(request.getStatus()) && 
                    request.getTicketId() != null && 
                    (request.getComments() == null || request.getComments().isEmpty())) {
                    
                    // Simulierte Antwort generieren
                    String response = generateResponse(request);
                    
                    // Kommentar zur Anfrage hinzufügen
                    supportService.addCommentToRequest(request, response);
                    
                    // Status auf "In Bearbeitung" setzen
                    supportService.updateRequestStatus(request, "In Bearbeitung");
                    
                    System.out.println("Automatische Antwort auf Ticket " + request.getTicketId() + " generiert.");
                    
                    // Wenn die Anfrage in der Kategorie "Allgemeine Frage" ist, nach einer weiteren Minute als "Geschlossen" markieren
                    if ("Allgemeine Frage".equals(request.getCategory())) {
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                String closingResponse = "Antwort von support@finovia.de am " + 
                                                        LocalDateTime.now().format(formatter) + 
                                                        ":\n\nVielen Dank für Ihre Anfrage. Da wir keine weitere Rückmeldung erhalten haben, " +
                                                        "schließen wir dieses Ticket. Falls Sie weitere Hilfe benötigen, " +
                                                        "zögern Sie nicht, eine neue Anfrage zu stellen.";
                                supportService.addCommentToRequest(request, closingResponse);
                                supportService.updateRequestStatus(request, "Geschlossen");
                                System.out.println("Ticket " + request.getTicketId() + " automatisch geschlossen.");
                            }
                        }, 60000); // Nach 1 Minute schließen
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler bei der Simulation von Antworten: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Generiert eine automatische Antwort basierend auf der Kategorie der Anfrage
     */
    private String generateResponse(SupportRequest request) {
        String currentTime = LocalDateTime.now().format(formatter);
        String responseText = "Antwort von support@finovia.de am " + currentTime + ":\n\n";
        
        switch (request.getCategory()) {
            case "Allgemeine Frage":
                responseText += "Vielen Dank für Ihre Anfrage. Unser Team wird sich innerhalb der nächsten 24 Stunden mit einer Antwort bei Ihnen melden. " +
                              "Für allgemeine Fragen empfehlen wir auch einen Blick in unsere FAQ-Sektion.";
                break;
            case "Technisches Problem":
                responseText += "Wir haben Ihre Meldung zu einem technischen Problem erhalten. " +
                              "Für eine schnellere Bearbeitung wäre es hilfreich, wenn Sie uns folgende Informationen mitteilen könnten:\n" +
                              "- Welchen Browser/Gerät nutzen Sie?\n" +
                              "- Können Sie das Problem reproduzieren?\n" +
                              "- Seit wann tritt das Problem auf?\n\n" +
                              "Ein Techniker wird sich in Kürze mit Ihnen in Verbindung setzen.";
                break;
            case "Depotproblem":
                responseText += "Ihre Anfrage zu einem Depotproblem ist bei uns eingegangen. " +
                              "Unsere Fachabteilung für Depotfragen wird sich schnellstmöglich mit Ihnen in Verbindung setzen. " +
                              "Bitte beachten Sie, dass die Bearbeitung in Einzelfällen bis zu 48 Stunden dauern kann.";
                break;
            case "Konto & Sicherheit":
                responseText += "Wir haben Ihre Anfrage zu Konto & Sicherheit erhalten. Dieser Bereich hat für uns höchste Priorität. " +
                              "Ein Mitarbeiter unserer Sicherheitsabteilung wird sich innerhalb der nächsten Stunden bei Ihnen melden. " +
                              "Bitte beachten Sie, dass wir aus Sicherheitsgründen zusätzliche Verifikationsschritte durchführen müssen.";
                break;
            default:
                responseText += "Vielen Dank für Ihre Anfrage. Wir haben Ihre Nachricht erhalten und werden uns in Kürze mit Ihnen in Verbindung setzen. " +
                              "Unser Support-Team ist werktags von 9:00 bis 18:00 Uhr für Sie da.";
        }
        
        return responseText;
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