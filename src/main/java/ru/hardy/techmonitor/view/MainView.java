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
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.hardy.techmonitor.service.MenuAuthService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Route("")
public class MainView extends AppLayout {
    private final Tabs menu;
    private H1 viewTitle;
    private MenuAuthService menuAuthService;

    public MainView(MenuAuthService menuAuthService) {
        this.menuAuthService = menuAuthService;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.add(new DrawerToggle());

        viewTitle = new H1();
        layout.add(viewTitle);

        layout.add(new Image("images/user.svg", "Avatar"));

        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();

        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        // Have a drawer header with an application logo
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
//        logoLayout.add(new Image("images/logo.png", "My Project logo"));
        logoLayout.add(new H1("My Project"));

        // Display the logo and the menu in the drawer
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        if (CheckRole().contains("USER")) {
            tabs.add(createTab("Home", HomeView.class));
            tabs.add(createTab("Logout", LogoutView.class));
        }
        if (CheckRole().contains("ADMIN")) {
            tabs.add(createTab("Admin", AdminView.class));
        }
        return tabs;
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    public List<String> CheckRole() {
        List<String> result = new ArrayList();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("USER"))) {
            result.add("USER");
        }
        if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN"))) {
            result.add("ADMIN");
        }
        return result;
    }
}

