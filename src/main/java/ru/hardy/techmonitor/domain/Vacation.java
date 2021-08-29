package ru.hardy.techmonitor.domain;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate dateBegin;
    private LocalDate dateEnd;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vacation")
    private Set<Employee> employee;
}
