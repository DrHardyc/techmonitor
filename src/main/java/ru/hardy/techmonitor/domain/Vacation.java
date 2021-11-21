package ru.hardy.techmonitor.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.HashCodeExclude;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @EqualsAndHashCode.Exclude
    private Employee employee;

    private LocalDate dateBegin;
    private LocalDate dateEnd;

}
