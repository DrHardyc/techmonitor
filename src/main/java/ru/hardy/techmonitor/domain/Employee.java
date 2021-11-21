package ru.hardy.techmonitor.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "employee")
    @EqualsAndHashCode.Exclude
    private Set<Vacation> vacation;

    private String name;
    private String surname;
    private String patronymic;



}
