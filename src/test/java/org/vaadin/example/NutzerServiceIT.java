package org.vaadin.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.repositories.NutzerRepository;
import org.vaadin.example.application.services.NutzerService;

public class NutzerServiceIT {

    @Mock
    private NutzerRepository nutzerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private NutzerService nutzerService;

    private Nutzer testNutzer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testNutzer = new Nutzer();
        testNutzer.setUsername("testuser");
        testNutzer.setPasswort("plaintextpasswort");
        testNutzer.setEmail("test@example.com");
        testNutzer.setRoles(null); // Keine Rollen gesetzt
    }

    /**
     * Testet, ob speichereNutzer das Passwort korrekt hasht und
     * die Standardrolle "USER" setzt, wenn keine Rollen vorhanden sind.
     * Außerdem wird geprüft, ob nutzerRepository.save() aufgerufen wird.
     */
    @Test
    public void testSpeichereNutzer() {
        // Mock PasswordEncoder um ein "gehashtes" Passwort zurückzugeben
        when(passwordEncoder.encode(any())).thenReturn("gehashtesPasswort");
        // Mock Save Methode - gibt den Nutzer zurück (optional, da void Methode nicht zwingend)
        when(nutzerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Aufruf der void Methode - hier kein Rückgabewert
        nutzerService.speichereNutzer(testNutzer);

        // Passwort wurde gehasht und gesetzt
        assertEquals("gehashtesPasswort", testNutzer.getPasswort());

        // Standardrolle "USER" wurde gesetzt
        assertNotNull(testNutzer.getRoles());
        assertTrue(testNutzer.getRoles().contains("USER"));

        // Nutzer wurde gespeichert
        verify(nutzerRepository, times(1)).save(testNutzer);
    }

    /**
     * Testet getNutzerById mit vorhandenem Nutzer.
     */
    @Test
    public void testGetNutzerById_Found() {
        when(nutzerRepository.findById(1L)).thenReturn(Optional.of(testNutzer));

        Optional<Nutzer> result = nutzerService.getNutzerById(1L);

        assertTrue(result.isPresent());
        assertEquals(testNutzer, result.get());
    }

    /**
     * Testet getNutzerById mit nicht vorhandenem Nutzer.
     */
    @Test
    public void testGetNutzerById_NotFound() {
        when(nutzerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Nutzer> result = nutzerService.getNutzerById(1L);

        assertFalse(result.isPresent());
    }

    /**
     * Testet getNutzerByUsername mit vorhandenem Nutzer.
     */
    @Test
    public void testGetNutzerByUsername_Found() {
        when(nutzerRepository.findByUsername("testuser")).thenReturn(testNutzer);

        Nutzer result = nutzerService.getNutzerByUsername("testuser");

        assertNotNull(result);
        assertEquals(testNutzer, result);
    }

    /**
     * Testet getNutzerByUsername mit nicht vorhandenem Nutzer.
     */
    @Test
    public void testGetNutzerByUsername_NotFound() {
        when(nutzerRepository.findByUsername("unknown")).thenReturn(null);

        Nutzer result = nutzerService.getNutzerByUsername("unknown");

        assertNull(result);
    }

    /**
     * Testet getAllNutzer.
     */
    @Test
    public void testGetAllNutzer() {
        List<Nutzer> nutzerListe = List.of(testNutzer);
        when(nutzerRepository.findAll()).thenReturn(nutzerListe);

        List<Nutzer> result = nutzerService.getAllNutzer();

        assertEquals(nutzerListe.size(), result.size());
        assertEquals(testNutzer, result.get(0));
    }

    /**
     * Testet usernameExists für vorhandenen und nicht vorhandenen Nutzernamen.
     */
    @Test
    public void testUsernameExists() {
        when(nutzerRepository.existsByUsername("testuser")).thenReturn(true);
        when(nutzerRepository.existsByUsername("unknown")).thenReturn(false);

        assertTrue(nutzerService.usernameExists("testuser"));
        assertFalse(nutzerService.usernameExists("unknown"));
    }

    /**
     * Testet emailExists für vorhandene und nicht vorhandene E-Mail.
     */
    @Test
    public void testEmailExists() {
        when(nutzerRepository.existsByEmail("test@example.com")).thenReturn(true);
        when(nutzerRepository.existsByEmail("unknown@example.com")).thenReturn(false);

        assertTrue(nutzerService.emailExists("test@example.com"));
        assertFalse(nutzerService.emailExists("unknown@example.com"));
    }

    /**
     * Testet loescheNutzer.
     */
    @Test
    public void testLoescheNutzer() {
        nutzerService.loescheNutzer(testNutzer);

        verify(nutzerRepository, times(1)).delete(testNutzer);
    }

    /**
     * Testet authenticate mit korrektem Passwort.
     */
    @Test
    public void testAuthenticate_Success() {
        when(nutzerRepository.findByUsername("testuser")).thenReturn(testNutzer);
        when(passwordEncoder.matches("plaintextpasswort", testNutzer.getPasswort())).thenReturn(true);

        boolean authenticated = nutzerService.authenticate("testuser", "plaintextpasswort");

        assertTrue(authenticated);
    }

    /**
     * Testet authenticate mit falschem Passwort.
     */
    @Test
    public void testAuthenticate_WrongPassword() {
        when(nutzerRepository.findByUsername("testuser")).thenReturn(testNutzer);
        when(passwordEncoder.matches("falschesPasswort", testNutzer.getPasswort())).thenReturn(false);

        boolean authenticated = nutzerService.authenticate("testuser", "falschesPasswort");

        assertFalse(authenticated);
    }

    /**
     * Testet authenticate mit nicht vorhandenem Nutzer.
     */
    @Test
    public void testAuthenticate_UserNotFound() {
        when(nutzerRepository.findByUsername("unknown")).thenReturn(null);

        boolean authenticated = nutzerService.authenticate("unknown", "anyPasswort");

        assertFalse(authenticated);
    }
}
