package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;

public class AdminView extends Div {

    public AdminView() {

        add(new H1("Страница Админа"));
    }
}
