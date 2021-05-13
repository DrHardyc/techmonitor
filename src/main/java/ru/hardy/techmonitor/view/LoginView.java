package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.hardy.techmonitor.service.AuthService;


@Route("login")
@StyleSheet("css/login-view.css")
public class LoginView extends Div {

    public LoginView(AuthService authService){
        setId("login-view");
        var username = new TextField("Имя пользователя");
        var password = new PasswordField("Пароль");

        add(
                new H1("Добро пожаловать"),
                username,
                password,

                new Button("Вход", event -> {
                    try {
                        authService.authenticate(username.getValue(), password.getValue());
                        UI.getCurrent().navigate("home");
                    } catch (AuthService.AuthException e) {
                        Notification.show("Неверное имя пользователя или пароль");
                    }
                })
        );
    }
}
