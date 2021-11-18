package ru.hardy.techmonitor.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Testdomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String teststr1;
    private String teststr2;
    private String teststr3;

    private int testint1;

}
