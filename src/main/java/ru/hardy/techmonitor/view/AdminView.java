package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route("admin")
@Secured("ADMIN")
public class AdminView extends Div {

    public AdminView() {

        add(new H1("Страница Админа"));
    }
}
