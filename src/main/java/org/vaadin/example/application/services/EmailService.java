package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import org.vaadin.example.application.classes.SupportRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service-Klasse für den Versand und die Protokollierung von E-Mails.
 *
 * Bietet Methoden zum asynchronen Versand von E-Mails sowie zur Simulation und Protokollierung
 * von Support-Anfragen. Hält ein Log der letzten 100 E-Mails.
 *
 * @author Sören Heß, Ben Hübert
 */
@Service
public class EmailService {

    /** MailSender-Instanz für den E-Mail-Versand */
    private final JavaMailSender mailSender;

    /** Datumsformatierer für Zeitstempel in E-Mails */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    /** Liste zur Protokollierung der versendeten E-Mails (max. 100 Einträge) */
    private final List<String> emailLog = new ArrayList<>();

    /**
     * Konstruktor für EmailService.
     *
     * @param mailSender JavaMailSender-Instanz für den E-Mail-Versand
     */
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Versendet eine E-Mail asynchron.
     *
     * @param to      Empfängeradresse
     * @param subject Betreff der E-Mail
     * @param text    Inhalt der E-Mail
     */
    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("finovia.trading@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    /**
     * Initialisiert den EmailService nach Bean-Konstruktion.
     * Gibt eine Initialisierungsnachricht auf der Konsole aus.
     */
    @PostConstruct
    public void init() {
        System.out.println("EmailService initialisiert");
    }

    /**
     * Simuliert den E-Mail-Versand einer Support-Anfrage.
     * Erstellt eine Ticket-ID, generiert den E-Mail-Inhalt und protokolliert die Anfrage.
     *
     * @param request   SupportRequest-Objekt mit den Anfragedaten
     * @param userEmail E-Mail-Adresse des anfragenden Nutzers
     * @return true, wenn die Anfrage erfolgreich simuliert wurde, sonst false
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
            return false;
        }
    }

    /**
     * Erstellt den E-Mail-Inhalt für eine Support-Anfrage.
     *
     * @param request   SupportRequest-Objekt mit den Anfragedaten
     * @param userEmail E-Mail-Adresse des Nutzers
     * @param ticketId  Generierte Ticket-ID
     * @return String mit dem formatierten E-Mail-Inhalt
     */
    private String createEmailContent(SupportRequest request, String userEmail, String ticketId) {
        String content = "=== SIMULIERTE SUPPORT-ANFRAGE ===\n" +
                "Von: " + userEmail + "\n" +
                "An: support@finovia.de\n" +
                "Betreff: Support-Anfrage: " + request.getCategory() + "\n" +
                "Datum: " + LocalDateTime.now().format(formatter) + "\n\n" +
                "Ticket-ID: " + ticketId + "\n" +
                "Kategorie: " + request.getCategory() + "\n" +
                "Status: " + request.getStatus() + "\n" +
                "Erstellt am: " + request.getCreationDate() + "\n\n" +
                "Beschreibung:\n" + request.getDescription() + "\n" +
                "==============================\n";
        return content;
    }

    /**
     * Fügt eine E-Mail zum Log hinzu und gibt sie auf der Konsole aus.
     * Das Log ist auf 100 Einträge begrenzt.
     *
     * @param emailContent Inhalt der zu protokollierenden E-Mail
     */
    private void logEmail(String emailContent) {
        emailLog.add(emailContent);
        // Begrenze die Größe des Logs auf 100 Einträge
        if (emailLog.size() > 100) {
            emailLog.remove(0);
        }
        System.out.println(emailContent);
    }
}