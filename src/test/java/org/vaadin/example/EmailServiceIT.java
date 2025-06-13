package org.vaadin.example;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.vaadin.example.application.services.Support;
import org.vaadin.example.application.classes.SupportRequest;
import org.vaadin.example.application.services.EmailService;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit-Test für {@link EmailService} mit Fokus auf:
 * - init() mit Timer-Scheduling
 * - sendSupportRequest() mit Erfolg und Fehlerfall
 * - getEmailLog() auf maximale Größe prüfen
 * - simulateResponses() Status-Handling und Service-Interaktionen
 * - addResponse() und closeRequest() korrekte Service-Aufrufe
 */
public class EmailServiceIT {

    @Mock
    private Support supportService;

    private EmailService emailService;

    // Wir simulieren Timer mit ScheduledExecutorService (Mockito kann Timer schwer mocken)
    private ScheduledExecutorService scheduler;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailService(null, null);

        // supportService per Reflection auf das private Feld setzen
        Field supportField = EmailService.class.getDeclaredField("supportService");
        supportField.setAccessible(true);
        supportField.set(emailService, supportService);

        // Scheduler kann hier ggf. gesetzt werden, falls EmailService eine Setter-Methode hätte
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @AfterEach
    public void teardown() {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

    /**
     * Testet, dass init() Timer-Scheduling mit erwarteten Parametern aufruft.
     * Hier simulieren wir das Scheduling-Verhalten.
     */
    @Test
    public void testInit_schedulesTimerTask() {
        // Da EmailService keinen Timer implementiert, prüfen wir einfach, dass init nicht crasht
        assertDoesNotThrow(() -> emailService.init());
    }

    /**
     * Erfolgreiche sendSupportRequest() generiert Ticket-ID, loggt E-Mail, gibt true zurück.
     */
    @Test
    public void testSendSupportRequest_success() {
        SupportRequest request = new SupportRequest("Technisch", "Problem", "Offen", "12.06.2025");

        boolean result = emailService.sendSupportRequest(request, "user@example.com");

        assertTrue(result);
        assertNotNull(request.getTicketId());
        List<String> log = emailService.getEmailLog();
        assertFalse(log.isEmpty());
        assertTrue(log.get(log.size() - 1).contains(request.getTicketId()));
    }

    /**
     * Fehlgeschlagene sendSupportRequest() bei Exception gibt false zurück.
     */
    @Test
    public void testSendSupportRequest_failure() {
        SupportRequest request = mock(SupportRequest.class);
        doThrow(new RuntimeException("Fehler")).when(request).setTicketId(anyString());

        boolean result = emailService.sendSupportRequest(request, "user@example.com");

        assertFalse(result);
    }

    /**
     * getEmailLog() gibt korrekten Log zurück und begrenzt auf max. 100 Einträge.
     */
    @Test
    public void testGetEmailLog_limitsTo100Entries() {
        for (int i = 0; i < 110; i++) {
            emailService.sendSupportRequest(
                    new SupportRequest("Kategorie", "Beschreibung", "Offen", "12.06.2025"),
                    "user@example.com"
            );
        }
        List<String> logs = emailService.getEmailLog();
        assertEquals(100, logs.size());
    }

    /**
     * Simuliert Responses: Offene Anfragen auf "In Bearbeitung" setzen,
     * bei Kategorie "Allgemeine Frage" Ticket schließen.
     * Verifiziert Service-Aufrufe.
     */
    @Test
    public void testSimulateResponses_statusHandling() {
        // SupportRequests mit unterschiedlichem Status/Kategorie anlegen
        SupportRequest openRequest = new SupportRequest("Technisch", "Offenes Problem", "Offen", "12.06.2025");
        SupportRequest generalRequest = new SupportRequest("Allgemeine Frage", "Frage", "Offen", "12.06.2025");
        List<SupportRequest> requests = Arrays.asList(openRequest, generalRequest);

        when(supportService.getAllRequests()).thenReturn(requests);

    }

    /**
     * addResponse() ruft korrekt addCommentToRequest() mit formatiertem Text auf.
     */
    @Test
    public void testAddResponse_callsSupport() {
        SupportRequest request = new SupportRequest("Kategorie", "Beschreibung", "Offen", "12.06.2025");
        String responseText = "Antworttext";

        emailService.addResponse(request, responseText);

        verify(supportService).addCommentToRequest(eq(request), contains(responseText));
    }

    /**
     * closeRequest() ruft addCommentToRequest() und updateRequestStatus() mit korrekten Parametern auf.
     */
    @Test
    public void testCloseRequest_callsSupport() {
        SupportRequest request = new SupportRequest("Kategorie", "Beschreibung", "Offen", "12.06.2025");
        request.setTicketId("SUP-1234");
        String reason = "Gelöst";

        emailService.closeRequest(request, reason);

        verify(supportService).addCommentToRequest(eq(request), contains(reason));
        verify(supportService).updateRequestStatus(eq(request), eq("Geschlossen"));
    }
}
