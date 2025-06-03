package org.vaadin.example.application.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.vaadin.example.application.views.DepotAnlegenView;

/**
 * Die `DepotAnlegenViewFactory`-Klasse ist dafür da ein Pop-Up für die Erstellung eines Depots zu erstellen.
 */
@Component
public class DepotAnlegenViewFactory {

    private final ApplicationContext applicationContext;

    /**
     * Konstruktor für die `DepotAnlegenViewFactory`-Klasse.
     *
     * @param applicationContext Der Spring {@link ApplicationContext}, der zum Abrufen von Bean-Instanzen verwendet wird.
     */
    @Autowired
    public DepotAnlegenViewFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Erstellt und gibt eine neue Instanz von {@link DepotAnlegenView} zurück.
     * Dies ist notwendig, da `DepotAnlegenView` den Scope `SCOPE_PROTOTYPE` hat
     * und somit jedes Mal eine neue Instanz benötigt wird, wenn der Dialog geöffnet wird.
     *
     * @return Eine neue, voll konfigurierte Instanz von {@link DepotAnlegenView}.
     */
    public DepotAnlegenView createDepotAnlegenView() {
        return applicationContext.getBean(DepotAnlegenView.class);
    }
}