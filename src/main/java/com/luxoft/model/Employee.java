package com.luxoft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Aleksei Grishkov on 15.12.2016.
 */
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String program;

    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private Collection<Employee> employees;

    @OneToMany(targetEntity = WorkTime.class)
    private Collection<WorkTime> workTime;

    public Employee(String firstName, String lastName, String program, Employee manager) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.program = program;
        this.manager = manager;
    }

    protected Employee() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProgram() {
        return program;
    }

    public Collection<Employee> getEmployees() {
        return employees;
    }

    @JsonIgnore
    public Employee getManager() {
        return manager;
    }

    public Collection<WorkTime> getWorkTime() {
        return workTime;
    }
}