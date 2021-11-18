package ru.hardy.techmonitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hardy.techmonitor.domain.Testdomain;

public interface TestRepo extends JpaRepository<Testdomain, Long> {
    @Query("from Testdomain e " +
            "where " +
            "   concat(e.testint1, ' ', e.teststr2, ' ', e.teststr3) like concat('%', :name, '%')")
    Testdomain findByName(@Param("name") String name);


}
