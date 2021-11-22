package ru.hardy.techmonitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hardy.techmonitor.domain.Employee;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query("from Employee e " +
            "where " +
            "   concat(e.lastName, ' ', e.firstName, ' ', e.patronymic) like concat('%', :name, '%')")
    List<Employee> findByName(@Param("name") String name);
}
