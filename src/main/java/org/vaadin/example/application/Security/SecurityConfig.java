package org.vaadin.example.application.Security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.vaadin.example.application.views.LoginView;

/**
 * Konfiguration der Sicherheitseinstellungen für die Vaadin-Anwendung.
 * Diese Klasse definiert, wie die Authentifizierung und Autorisierung
 * innerhalb der Anwendung gehandhabt wird.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    /**
     * Konfiguriert den PasswordEncoder, der für die Verschlüsselung von Passwörtern verwendet wird.
     * In diesem Fall wird BCrypt verwendet, um Passwörter sicher zu speichern.
     *
     * @return Ein BCryptPasswordEncoder-Objekt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Konfiguriert den AuthenticationManager, der für die Authentifizierung von Nutzern verwendet wird.
     * Er nutzt den DaoAuthenticationProvider, um Nutzerinformationen zu laden und Passwörter zu überprüfen.
     *
     * @param userDetailsService Der UserDetailsService, der die Nutzerinformationen bereitstellt.
     * @return Ein konfigurierter AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    /**
     * Konfiguriert das SecurityContextRepository, das verwendet wird, um den Sicherheitskontext
     * in der HTTP-Session zu speichern. Dies ermöglicht die Beibehaltung des Authentifizierungsstatus
     * über verschiedene Anfragen hinweg.
     *
     * @return Ein HttpSessionSecurityContextRepository-Objekt.
     */
    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    /**
     * Konfiguriert die HTTP-Sicherheitsregeln für die Anwendung.
     * Hier wird festgelegt, welche Endpunkte öffentlich zugänglich sind
     * und wie die Authentifizierung und Autorisierung gehandhabt werden.
     *
     * @param http Das HttpSecurity-Objekt, das konfiguriert wird.
     * @throws Exception Wenn ein Fehler bei der Konfiguration auftritt.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
        );

        super.configure(http);

        setLoginView(http, LoginView.class);
        http.formLogin(form -> form.defaultSuccessUrl("/uebersicht", true));
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );
    }
}
