package ru.hardy.techmonitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hardy.techmonitor.domain.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
