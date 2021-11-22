package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.hardy.techmonitor.domain.Employee;
import ru.hardy.techmonitor.repo.EmployeeRepo;
import ru.hardy.techmonitor.service.EmployeeService;

import javax.annotation.security.PermitAll;

@Route("employee")
@PermitAll
public class EmployeeView extends VerticalLayout {
    private final EmployeeRepo employeeRepo;

    private final Grid<Employee> employeeGrid = new Grid<>();
    private final TextField filter = new TextField();
    private final Button addNewButton = new Button("Новый сотрудник", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);

    private Long empID;

    @Autowired
    public EmployeeView(EmployeeRepo employeeRepo, EmployeeService employeeService) {
        this.employeeRepo = employeeRepo;

        filter.setPlaceholder("Поиск");
        filter.setValueChangeMode(ValueChangeMode.EAGER);

        filter.addValueChangeListener(field -> fillList(field.getValue()));

        employeeGrid.addColumn(Employee::getLastName).setHeader("Фамилия");
        employeeGrid.addColumn(Employee::getFirstName).setHeader("Имя");
        employeeGrid.addColumn(Employee::getPatronymic).setHeader("Отчество");

        add(toolbar, employeeGrid, employeeService);

        employeeGrid
                .asSingleSelect()
                .addValueChangeListener(e -> {
                    employeeService.editEmployee(e.getValue());
                    changeEmpID(e.getValue().getId());
                });
        employeeGrid.addItemDoubleClickListener(e -> UI.getCurrent().getPage().
                setLocation("vacation/" + empID + "/details"));

        addNewButton.addClickListener(e -> employeeService.editEmployee(new Employee()));

        employeeService.setChangeHandler(() -> {
            employeeService.setVisible(false);
            fillList(filter.getValue());
        });

        fillList("");






    }

    private void changeEmpID(Long empID) {
        this.empID = empID;
    }

    private void fillList(String name) {
        if (name.isEmpty()) {
            employeeGrid.setItems(this.employeeRepo.findAll());
        } else {
            employeeGrid.setItems(this.employeeRepo.findByName(name));
        }
    }
}