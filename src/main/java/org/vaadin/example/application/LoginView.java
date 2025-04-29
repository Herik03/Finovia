package org.vaadin.example.application;
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

import java.awt.*;

@Route("login")
@PageTitle( "Login")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private final LoginForm loginForm ;
    private final Button button;
    private final Button forgotPasswordButton;
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

        add(new H1("Login Finovia"),loginForm,horizontalLayout);



    }
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
