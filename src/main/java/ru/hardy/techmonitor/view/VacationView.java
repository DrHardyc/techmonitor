package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.hardy.techmonitor.domain.Employee;
import ru.hardy.techmonitor.domain.Vacation;
import ru.hardy.techmonitor.repo.EmployeeRepo;

import java.util.List;

@Route
public class VacationView extends VerticalLayout {
    private EmployeeRepo employeeRepo;

    private DatePicker datebegin = new DatePicker();
    private DatePicker dateend = new DatePicker();
    private Button btnNew = new Button("Добавить период", VaadinIcon.PLUS.create());


    public VacationView(){
        //Employee employee = employeeRepo.findByName("Михаил");
        //Employee employee = employeeRepo.findById();


//        List<Vacation> vacation = employee.getVacation();
//        vacation.get(0);


        add(new H1("Выбор периода отпуска"),
                new Label("c"), datebegin,
                new Label("по"), dateend,
                btnNew);

    }
}
