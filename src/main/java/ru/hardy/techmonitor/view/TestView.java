package ru.hardy.techmonitor.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
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
