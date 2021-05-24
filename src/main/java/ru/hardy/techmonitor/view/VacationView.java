package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.hardy.techmonitor.domain.Employee;
import ru.hardy.techmonitor.domain.Vacation;
import ru.hardy.techmonitor.repo.EmployeeRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Route("vacation")
public class VacationView extends VerticalLayout {
    private EmployeeRepo employeeRepo;

    private DatePicker datebegin = new DatePicker();
    private DatePicker dateend = new DatePicker();
    private Button btnNew = new Button("Добавить часть отпуска", VaadinIcon.PLUS.create());
    private H1 h1 = new H1("0");
    private Button btnGetValue = new Button("Получить значения");

    public VacationView(){
        Employee employee = new Employee();


        add(btnNew, h1, btnGetValue);

    }

}
