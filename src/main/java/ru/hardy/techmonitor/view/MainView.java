package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {


    @Autowired
    public MainView() {
        Label label = new Label("Главная страница!");
        add(label);

    }


}
