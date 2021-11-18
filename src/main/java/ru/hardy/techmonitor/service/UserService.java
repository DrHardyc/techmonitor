package ru.hardy.techmonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hardy.techmonitor.domain.Role;
import ru.hardy.techmonitor.domain.User;
import ru.hardy.techmonitor.repo.UserRepo;

import javax.security.auth.message.AuthException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<Role> roleNames = new ArrayList<>(user.getRoles());
        List<GrantedAuthority> grantList = new ArrayList();
        for (Role role : roleNames){
            GrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            grantList.add(authority);
        }

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantList);

        return userDetails;
    }

    public boolean addUser(String email, String username, String password) {
        User user = new User();
        String activationCode = user.getActivationCode();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setActive(false);
        user.setActivationCode(activationCode);

        User userFromDb = userRepo.findByUsername(username);
        if (userFromDb != null) {
            return false;
        }

        userRepo.save(user);

        String text = "Для активации Вашего аакаунта пройдите по ссылке " +
                "http://localhost:8080/activate?code=" + activationCode;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("post@cherminel.ru");
        message.setSubject("Подтверждение адерса электронной почты");
        message.setText(text);
        message.setTo(email);
        mailSender.send(message);

        return true;
    }

    public void activate(String activationCode) throws AuthException {
        User user = userRepo.getByActivationCode(activationCode);
        if (user != null) {
            user.setActive(true);
            userRepo.save(user);
        } else {
            throw new AuthException();
        }
    }


}
