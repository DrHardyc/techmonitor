package ru.hardy.techmonitor.service;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.hardy.techmonitor.domain.Employee;
import ru.hardy.techmonitor.domain.Vacation;
import ru.hardy.techmonitor.repo.EmployeeRepo;
import ru.hardy.techmonitor.view.VacationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@SpringComponent
@UIScope
public class EmployeeService extends VerticalLayout implements KeyNotifier {
    private final EmployeeRepo employeeRepo;

    private Employee employee;

    private final TextField firstName = new TextField("", "Имя");
    private final TextField lastName = new TextField("", "Фамилия");
    private final TextField patronymic = new TextField("", "Отчество");

    private Button save = new Button("Сохранить");
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить");

    private HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);

    private Binder<Employee> binder = new Binder<>(Employee.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;

        add(lastName, firstName, patronymic, buttons);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editEmployee(employee));
        setVisible(false);
    }

    private void save() {
        employeeRepo.save(employee);
        changeHandler.onChange();
    }

    private void delete() {
        employeeRepo.delete(employee);
        changeHandler.onChange();
    }

    public void editEmployee(Employee emp) {
        if (emp == null) {
            setVisible(false);
            return;
        }

        if (emp.getId() != null) {
            this.employee = employeeRepo.findById(emp.getId()).orElse(emp);
        } else {
            this.employee = emp;
        }

        binder.setBean(this.employee);

        setVisible(true);

        lastName.focus();
    }

    public void addVacation(Employee employee, Vacation vacation) {
        employee.getVacations().add(vacation);
        employeeRepo.save(employee);
    }
}
