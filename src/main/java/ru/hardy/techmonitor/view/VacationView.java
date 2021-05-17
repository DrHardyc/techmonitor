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
        var vacation = new ArrayList<Vacation>();

        AtomicInteger i = new AtomicInteger();
        btnNew.addClickListener(e -> setNewDatePicker(i.toString()));
        btnNew.addClickListener(e -> i.set(i.get() + 2));
        btnNew.addClickListener(e -> h1.setText(i.toString()));

        btnGetValue.addClickListener(e -> getValueNewDP());

        add(btnNew, h1, btnGetValue);

//        vacation.setDateEnd(dateend.getValue());
//        employee.setVacation(vacation);
//        employeeRepo.save(employee);
    }

    private void setNewDatePicker(String i){
        DatePicker dateBegin = new DatePicker();
        DatePicker dateend = new DatePicker();
        dateBegin.setId(i);
        dateend.setId(i + 1);
        add(dateBegin, dateend);
    }

    Component findComponentWithId(HasComponents root, String id) {
        for(Component child : root) {
            if(id.equals(child.getId())) {
                // found it!
                return child;
            } else if(child instanceof HasComponents) {
                // recursively go through all children that themselves have children
                return findComponentWithId((HasComponents) child, id);
            }
        }
        // none was found
        return null;
    }
}
