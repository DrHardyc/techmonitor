package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.SessionScope;
import ru.hardy.techmonitor.domain.Employee;
import ru.hardy.techmonitor.domain.Vacation;
import ru.hardy.techmonitor.repo.EmployeeRepo;
import ru.hardy.techmonitor.service.EmployeeService;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route("vacation/:empID?/details")
@PermitAll
public class VacationView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EmployeeService employeeService;

    private Long empID;
    private final Label label= new Label("");
    private final DatePicker datebegin = new DatePicker();
    private final DatePicker dateend = new DatePicker();
    private final Grid<Vacation> vacationGrid = new Grid<>();

    @Autowired
    public VacationView(){

        vacationGrid.addColumn(Vacation::getDateBegin).setHeader("Дата начала");
        vacationGrid.addColumn(Vacation::getDateEnd).setHeader("Дата окончания");

        Button btnNew = new Button("Добавить период", VaadinIcon.PLUS.create());

        btnNew.addClickListener(e -> addVacation());

        ContextMenu contextMenu = new ContextMenu(vacationGrid);

        Label message = new Label("-");

        contextMenu.addItem(new H5("First menu item"),
                event -> message.setText("Clicked on the first item"));

        MenuItem subMenuItem = contextMenu.addItem("SubMenu Item");
        SubMenu subMenu = subMenuItem.getSubMenu();

        Checkbox checkbox = new Checkbox("Checkbox");
        subMenu.addItem(checkbox, event -> message.setText(
                "Clicked on checkbox with value: " + checkbox.getValue()));

        subMenu.addItem("TextItem",
                event -> message.setText("Clicked on text item"));

// Components can also be added to the submenu overlay
// without creating menu items with add()
        subMenu.addComponentAtIndex(1, new Hr());
        subMenu.add(new Label("This is not a menu item"));

        add(new H1("Выбор периода отпуска"),
                vacationGrid,
                new Label("c"), datebegin,
                new Label("по"), dateend,
                btnNew, label, message);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        empID = Long.parseLong(event.getRouteParameters().get("empID").get());

        Employee employee = employeeRepo.findById(empID).get();
        label.setText(employee.getLastName() + " " + employee.getFirstName() + " " + employee.getPatronymic());

        vacationGrid.setItems(employee.getVacations());
    }

    private void addVacation(){
        Employee employee = employeeRepo.findById(empID).get();
        Vacation vacation = new Vacation();
        vacation.setEmployee(employee);
        vacation.setDateBegin(datebegin.getValue());
        vacation.setDateEnd(dateend.getValue());

        employeeService.addVacation(employee, vacation);
    }
}
