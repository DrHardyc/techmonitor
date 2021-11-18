package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import ru.hardy.techmonitor.service.UserService;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Map;

@Route("activate")
@AnonymousAllowed
public class ActivationView extends Composite implements BeforeEnterObserver {

    private VerticalLayout layout;
    private final UserService userService;

    public ActivationView(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Component initContent(){
        layout = new VerticalLayout();
        return layout;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Map<String, List<String>> params = event.getLocation().getQueryParameters().getParameters();
        String code = params.get("code").get(0);
        try {
            userService.activate(code);
            layout.add(
                    new Text("Аккаунт активирован."),
                    new RouterLink("Войти", LoginView.class)
                    );
        } catch (AuthException e) {
            layout.add(new Text("Неправильная ссылка."));
        }
    }
}
