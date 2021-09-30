package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hardy.techmonitor.domain.Employee;
import ru.hardy.techmonitor.repo.EmployeeRepo;

@Route("vacation/:empID?/details")
public class VacationView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private EmployeeRepo employeeRepo;

    private Long empID;
    private Label label= new Label("");
    private DatePicker datebegin = new DatePicker();
    private DatePicker dateend = new DatePicker();
    private Button btnNew = new Button("Добавить период", VaadinIcon.PLUS.create());

    @Autowired
    public VacationView(){

       add(new H1("Выбор периода отпуска"),
                new Label("c"), datebegin,
                new Label("по"), dateend,
                btnNew, label);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        empID = Long.parseLong(event.getRouteParameters().get("empID").get());

        Employee employee = employeeRepo.findById(empID).get();
        label.setText(employee.getSurname() + " " + employee.getName() + " " + employee.getPatronymic());
    }
}
