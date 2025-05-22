package org.vaadin.example.application.classes;

import org.springframework.stereotype.Service;
import org.vaadin.example.application.models.SupportRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class Support {

    private final List<SupportRequest> supportRequests = new ArrayList<>();

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
                LocalDate.now().toString()
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
}
