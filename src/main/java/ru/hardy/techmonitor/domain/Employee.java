package ru.hardy.techmonitor.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    private String patronymic;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacation> vacation = new ArrayList<>();

}
