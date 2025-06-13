package org.vaadin.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.application.classes.Nutzer;
import java.util.Optional;

/**
 * Repository-Interface für {@link Nutzer}-Entitäten.
 *
 * Bietet CRUD-Operationen sowie benutzerdefinierte Methoden zur Suche
 * und Überprüfung von Nutzern anhand von Username, E-Mail oder Reset-Token.
 *
 * Wird von Spring automatisch als Bean erkannt und implementiert.
 *
 * @author Sören Heß
 */
@Repository
public interface NutzerRepository extends JpaRepository<Nutzer, Long> {
    /**
     * Sucht einen Nutzer anhand des Usernames.
     *
     * @param username Der Username des Nutzers
     * @return Das gefundene {@link Nutzer}-Objekt oder null, falls nicht vorhanden
     */
    Nutzer findByUsername(String username);

    /**
     * Sucht einen Nutzer anhand der E-Mail-Adresse.
     *
     * @param email Die E-Mail-Adresse des Nutzers
     * @return Optional mit dem gefundenen {@link Nutzer}, oder leer, falls nicht vorhanden
     */
    Optional<Nutzer> findByEmail(String email);

    /**
     * Sucht einen Nutzer anhand des Reset-Tokens (z.B. für Passwort-Reset).
     *
     * @param resetToken Das Reset-Token des Nutzers
     * @return Optional mit dem gefundenen {@link Nutzer}, oder leer, falls nicht vorhanden
     */
    Optional<Nutzer> findByResetToken(String resetToken);

    /**
     * Prüft, ob ein Nutzer mit dem angegebenen Username existiert.
     *
     * @param username Der Username, der überprüft werden soll
     * @return true, wenn ein Nutzer mit diesem Username existiert, sonst false
     */
    boolean existsByUsername(String username);

    /**
     * Prüft, ob ein Nutzer mit der angegebenen E-Mail-Adresse existiert.
     *
     * @param email Die E-Mail-Adresse, die überprüft werden soll
     * @return true, wenn ein Nutzer mit dieser E-Mail existiert, sonst false
     */
    boolean existsByEmail(String email);
}
