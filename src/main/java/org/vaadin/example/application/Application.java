package org.vaadin.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Die Hauptanwendungsklasse für die Finovia Trading Plattform.
 * Diese Klasse konfiguriert die Anwendung als Spring Boot Anwendung
 * und richtet die Progressive Web-App (PWA) Einstellungen ein.
 */
@SpringBootApplication
@PWA(name = "Finovia - Ihre Trading Plattform", shortName = "Finovia", iconPath = "icons/logo.png")
@Theme("finovia")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
