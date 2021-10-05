package ru.hardy.techmonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hardy.techmonitor.domain.Role;
import ru.hardy.techmonitor.domain.User;
import ru.hardy.techmonitor.repo.UserRepo;

import javax.security.auth.message.AuthException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<Role> roleNames = new ArrayList<>(user.getRoles());
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        for (Role role : roleNames){
            GrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            grantList.add(authority);
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantList);

        return userDetails;
    }

    public boolean addUser(String username, String password) {
        userRepo.save(username, passwordEncoder.encode(password), Role.USER);

        if (userFromDb != null) {
            return false;
        }

        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@example.com");
        message.setSubject("Confirmation email");
        message.setText(text);
        message.setTo(email);
        mailSender.send(message);

        return true;
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
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
