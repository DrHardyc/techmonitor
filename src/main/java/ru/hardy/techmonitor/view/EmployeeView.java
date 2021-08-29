package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.hardy.techmonitor.domain.Employee;
import ru.hardy.techmonitor.domain.Role;
import ru.hardy.techmonitor.domain.User;
import ru.hardy.techmonitor.repo.EmployeeRepo;
import ru.hardy.techmonitor.repo.UserRepo;
import ru.hardy.techmonitor.service.EmployeeService;

public class EmployeeView extends VerticalLayout {
    private final EmployeeRepo employeeRepo;
    private User user;
    private UserRepo userRepo;
    private final EmployeeService employeeService;

    private Grid<Employee> employeeGrid = new Grid<>();
    private final TextField filter = new TextField();
    private final Button addNewButton = new Button("Новый сотрудник", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);

    @Autowired
    public EmployeeView(EmployeeRepo employeeRepo, EmployeeService employeeService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user =  userRepo.findByUsername(auth.getName());
        this.employeeRepo = employeeRepo;
        this.employeeService = employeeService;

        filter.setPlaceholder("Поиск");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> fillList(field.getValue()));

        employeeGrid.addColumn(Employee::getSurname).setHeader("Фамилия");
        employeeGrid.addColumn(Employee::getName).setHeader("Имя");
        employeeGrid.addColumn(Employee::getPatronymic).setHeader("Отчество");
        employeeGrid.addColumn(Employee::getVacation).setHeader("Период отпуска");

        add(toolbar, employeeGrid, employeeService);

        employeeGrid
                .asSingleSelect()
                .addValueChangeListener(e -> employeeService.editEmployee(e.getValue()));

        addNewButton.addClickListener(e -> employeeService.editEmployee(new Employee()));


        if (user.getRoles().contains(Role.ADMIN)) {
            employeeService.setChangeHandler(() -> {
                employeeService.setVisible(false);
                fillList(filter.getValue());
            });
            fillList("");
        };
    }

    private void fillList(String name) {
        if (name.isEmpty()) {
            employeeGrid.setItems(this.employeeRepo.findAll());
        } else {
            employeeGrid.setItems(this.employeeRepo.findByName(name));
        }
    }
}