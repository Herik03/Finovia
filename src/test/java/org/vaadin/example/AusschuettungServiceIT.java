package org.vaadin.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vaadin.example.application.classes.Ausschuettung;
import org.vaadin.example.application.classes.Wertpapier;
import org.vaadin.example.application.repositories.AusschuettungRepository;
import org.vaadin.example.application.services.AusschuettungService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Testklasse für {@link AusschuettungService}, die das Verhalten der Methode {@code saveAusschuettung}
 * überprüft. Es wird sichergestellt, dass Ausschüttungen korrekt gespeichert werden und
 * fehlerhafte Eingaben wie {@code null} zu einer {@link NullPointerException} führen.
 *
 * <p>Die Tests basieren auf Mockito zur Simulation der Repository-Schicht,
 * wodurch externe Seiteneffekte vermieden und gezielt kontrolliert werden können.</p>
 *
 * @author Jan
 */
@ExtendWith(MockitoExtension.class)
public class AusschuettungServiceIT {
    @Mock
    private AusschuettungRepository ausschuettungRepository;

    @InjectMocks
    private AusschuettungService ausschuettungService;

    /**
     * Interne Dummy-Klasse zur Instanziierung von {@link Ausschuettung}, da diese abstrakt ist.
     */
    static class TestAusschuettung extends Ausschuettung {
        public TestAusschuettung() {
            super(100.0, LocalDate.now(), 25.0, mock(Wertpapier.class));
        }
    }

    /**
     * Testet, ob bei gültiger Eingabe {@link Ausschuettung} das Repository genau einmal
     * mit dem übergebenen Objekt aufgerufen wird.
     */
    @Test
    void testSaveAusschuettung_shouldCallRepositoryOnce() {
        Ausschuettung aussch = new TestAusschuettung();

        ausschuettungService.saveAusschuettung(aussch);

        verify(ausschuettungRepository, times(1)).save(aussch);
    }

    /**
     * Testet, ob bei {@code null} als Eingabe eine {@link NullPointerException} geworfen wird
     * und kein Repository-Aufruf erfolgt.
     */
    @Test
    void testSaveAusschuettung_nullInput_throwsException() {
        assertThrows(NullPointerException.class, () -> {
            ausschuettungService.saveAusschuettung(null);
        });

        verify(ausschuettungRepository, never()).save(any());
    }
}
