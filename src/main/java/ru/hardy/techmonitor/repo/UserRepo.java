package ru.hardy.techmonitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hardy.techmonitor.domain.User;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
