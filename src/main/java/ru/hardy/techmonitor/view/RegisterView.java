package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.hardy.techmonitor.service.UserService;

@Route("register")
public class RegisterView extends Composite {

    private final UserService userService;

    public RegisterView(UserService authService) {
        this.userService = authService;
    }

    @Override
    protected Component initContent() {
        TextField username = new TextField("Username");
        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Confirm password");
        return new VerticalLayout(
                new H2("Register"),
                username,
                password1,
                password2,
                new Button("Send", event -> register(
                        username.getValue(),
                        password1.getValue(),
                        password2.getValue()
                ))
        );
    }

    private void register(String username, String password1, String password2) {
        if (username.trim().isEmpty()) {
            Notification.show("Enter a username");
        } else if (password1.isEmpty()) {
            Notification.show("Enter a password");
        } else if (!password1.equals(password2)) {
            Notification.show("Passwords don't match");
        } else {
            userService.addUser(username, password1);
            Notification.show("Check your email.");
        }
    }
}
