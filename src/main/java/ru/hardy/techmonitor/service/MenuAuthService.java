package ru.hardy.techmonitor.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.hardy.techmonitor.domain.Role;
import ru.hardy.techmonitor.repo.UserRepo;
import ru.hardy.techmonitor.view.AdminView;
import ru.hardy.techmonitor.view.HomeView;
import ru.hardy.techmonitor.view.LogoutView;
import ru.hardy.techmonitor.view.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MenuAuthService {
    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {

    }

    public class AuthException extends Exception {

    }

    private final UserRepo userRepository;

    public MenuAuthService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    private void createRoutes(Set<Role> role) {
        getAuthorizedRoutes().stream()
                .forEach(route ->
                        RouteConfiguration.forSessionScope().setRoute(
                                route.route, route.view, MainView.class));
    }


    public List<AuthorizedRoute> getAuthorizedRoutes() {

        var routes = new ArrayList<AuthorizedRoute>();

//        if (CheckRole().equals("USER")) {
//            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
//            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
//
//        } else if (CheckRole().equals("ADMIN")) {
//            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
//            routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
//            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
//        }

        return routes;
    }


}
