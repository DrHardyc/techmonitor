package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import ru.hardy.techmonitor.domain.User;
import ru.hardy.techmonitor.service.MenuAuthService;

@Route("test")
public class TestView extends AppLayout {
    //private final Tabs menu;
    private H1 viewTitle;
    private MenuAuthService menuAuthService;

    public TestView(MenuAuthService menuAuthService) {
      addToDrawer(viewTitle = new H1("asdfasdfadsf"));
//        this.menuAuthService = menuAuthService;
//        setPrimarySection(Section.DRAWER);
//        addToNavbar(true, createHeaderContent());
//        menu = createMenu();
//        addToDrawer(createDrawerContent(menu));
    }

}
