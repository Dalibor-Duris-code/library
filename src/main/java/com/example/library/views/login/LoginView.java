package com.example.library.views.login;

import com.example.library.repository.UserRepository;
import com.example.library.services.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "login")
@PageTitle("Login")
@CssImport("./styles/login-view.css")
public class LoginView extends Div {
    private UserRepository userRepository;
    public LoginView(AuthService authService, UserRepository userRepository) {
        this.userRepository = userRepository;
        setId("login-view");

        var password = new PasswordField("Heslo");
        var username = new TextField("Prihlasovacie meno");

        add(new H1("39 Pub"));

        add(
                new H1("Prihlásenie"),
                username,
                password,
                new Button("Login", event -> {
                    try {
                        authService.authericate(username.getValue(), password.getValue());
                        UI.getCurrent().navigate("home");
                    } catch (AuthService.AuthException e){
                        Notification.show("WrongCredencials");
                    }

                })
        );
    }
}
