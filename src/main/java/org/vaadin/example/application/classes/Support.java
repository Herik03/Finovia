package org.vaadin.example.application.classes;

import org.springframework.stereotype.Service;
import org.vaadin.example.application.models.SupportRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class Support {

    private final List<SupportRequest> supportRequests = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Erstellt eine neue Support-Anfrage
     * @param category Kategorie der Anfrage
     * @param description Beschreibung des Problems
     * @return Die erstellte Anfrage
     */
    public SupportRequest createRequest(String category, String description) {
        SupportRequest request = new SupportRequest(
                category,
                description,
                "Offen",
                LocalDate.now().format(formatter)
        );
        supportRequests.add(request);
        return request;
    }

    /**
     * Gibt alle Support-Anfragen zurück
     * @return Liste aller Support-Anfragen
     */
    public List<SupportRequest> getAllRequests() {
        return new ArrayList<>(supportRequests);
    }

    /**
     * Aktualisiert den Status einer Anfrage
     * @param requestId ID der Anfrage
     * @param newStatus Neuer Status
     * @return true wenn erfolgreich, false wenn Anfrage nicht gefunden
     */
    public boolean updateRequestStatus(int requestId, String newStatus) {
        if (requestId >= 0 && requestId < supportRequests.size()) {
            supportRequests.get(requestId).setStatus(newStatus);
            return true;
        }
        return false;
    }
    
    /**
     * Aktualisiert den Status einer Anfrage
     * @param request Die Anfrage
     * @param newStatus Neuer Status
     */
    public void updateRequestStatus(SupportRequest request, String newStatus) {
        request.setStatus(newStatus);
    }
    
    /**
     * Findet eine Anfrage anhand ihrer Ticket-ID
     * @param ticketId Ticket-ID der Anfrage
     * @return Die gefundene Anfrage oder null
     */
    public SupportRequest findByTicketId(String ticketId) {
        for (SupportRequest request : supportRequests) {
            if (ticketId.equals(request.getTicketId())) {
                return request;
            }
        }
        return null;
    }
    
    /**
     * Fügt einen Kommentar zu einer Anfrage hinzu
     * @param request Die Anfrage
     * @param comment Der Kommentar
     */
    public void addCommentToRequest(SupportRequest request, String comment) {
        request.addComment(comment);
    }
    
    /**
     * Löscht eine Support-Anfrage
     *
     * @param requestId ID der Anfrage
     */
    public void deleteRequest(int requestId) {
        if (requestId >= 0 && requestId < supportRequests.size()) {
            supportRequests.remove(requestId);
        }
    }
}