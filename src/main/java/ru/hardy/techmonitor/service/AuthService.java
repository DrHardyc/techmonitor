package ru.hardy.techmonitor.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hardy.techmonitor.domain.Role;
import ru.hardy.techmonitor.domain.User;
import ru.hardy.techmonitor.repo.UserRepo;
import ru.hardy.techmonitor.view.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AuthService {

    public record AuthorizeRoutes(String route, String name, Class<? extends Component> view){

    }

    public class AuthException extends Exception{

    }

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    public void authenticate(String username, String password) throws AuthException {
        User user = userRepo.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())){
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRoles());
        } else {
            throw new AuthException();
        }
    }

    private void createRoutes(Set<Role> roles) {
        getAuthorizedRoutes(roles).stream()
                .forEach(route ->
                        RouteConfiguration.forSessionScope().setRoute(route.route, route.view, MainView.class));
    }

    public List<AuthorizeRoutes> getAuthorizedRoutes(Set<Role> roles){
        var routes = new ArrayList<AuthorizeRoutes>();

        if(roles.contains(Role.USER)){
            routes.add(new AuthorizeRoutes("home", "Home", HomeView.class));
            routes.add(new AuthorizeRoutes("employee", "Employee", EmployeeView.class));
            routes.add(new AuthorizeRoutes("vocation", "Vocation", VacationView.class));
            routes.add(new AuthorizeRoutes("logout", "Logout", LogoutView.class));

        }

        if(roles.contains(Role.ADMIN)) {
            routes.add(new AuthorizeRoutes("admin", "Admin", AdminView.class));
        }

        return routes;
    }
}
