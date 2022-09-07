package com.learning.testing.entity;

import lombok.*;
import javax.persistence.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Entity
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME",nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME",nullable = false)
    private String lastName;

    @Column(name = "EMAIL",nullable = false)
    private String email;

    @Column(name = "SALARY",columnDefinition = "Decimal(7,2) default '2500.0'")
    private Double salary;
}
