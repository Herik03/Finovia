package org.vaadin.example.application.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * Die Login-Ansicht der Finovia-Anwendung.
 * <p>
 * Diese Klasse stellt die Login-Seite der Anwendung dar und enthält:
 * <ul>
 *   <li>Ein Login-Formular für die Benutzerauthentifizierung</li>
 *   <li>Einen Button zur Registrierung neuer Benutzer</li>
 *   <li>Einen Button für das Zurücksetzen vergessener Passwörter</li>
 * </ul>
 * <p>
 * Die Klasse implementiert das BeforeEnterListener-Interface, um Fehler bei der Anmeldung zu verarbeiten.
 * 
 * @author Finovia Team
 * @version 1.0
 */
@Route("login")
@PageTitle( "Login")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    /** Das Login-Formular für die Benutzerauthentifizierung */
    private final LoginForm loginForm;

    /** Der Button zur Registrierung neuer Benutzer */
    private final Button button;

    /** Der Button zum Zurücksetzen vergessener Passwörter */
    private final Button forgotPasswordButton;

    /**
     * Konstruktor für die LoginView.
     * <p>
     * Initialisiert das Login-Formular und die Buttons für die Registrierung und
     * das Zurücksetzen vergessener Passwörter. Die Komponenten werden in einem
     * zentrierten Layout angeordnet.
     */
    public LoginView() {
        addClassNames(LumoUtility.Display.FLEX,LumoUtility.JustifyContent.CENTER,LumoUtility.AlignItems.CENTER);
        setSizeFull();
      loginForm=new LoginForm();
        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);
        button=new Button("Registrieren",e->UI.getCurrent().navigate("register"));
        forgotPasswordButton=new Button("Passwort vergessen?",e->UI.getCurrent().navigate("passwortvergessen"));
        loginForm.addForgotPasswordListener(e->UI.getCurrent().navigate("passwortvergessen"));

        HorizontalLayout horizontalLayout=new HorizontalLayout(button,forgotPasswordButton);

        add(new H1("Finovia - Deine Sparplattform!"),loginForm,horizontalLayout);



    }
    /**
     * Wird aufgerufen, bevor die Ansicht angezeigt wird.
     * <p>
     * Überprüft, ob ein Fehler bei der Anmeldung aufgetreten ist, und zeigt
     * gegebenenfalls eine Fehlermeldung im Login-Formular an.
     *
     * @param beforeEnterEvent Das Event, das vor dem Betreten der Ansicht ausgelöst wird
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
