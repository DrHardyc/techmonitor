package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@Route("home")
@PermitAll
public class HomeView extends Div {

    @Autowired
    public HomeView(){
        add(new H1("Домашняя страница"));
    }
}
