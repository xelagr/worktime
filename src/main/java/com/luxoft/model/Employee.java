package com.luxoft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Aleksei Grishkov on 15.12.2016.
 */
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String program;

    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
    private Collection<Employee> employees;

    @OneToMany(targetEntity = WorkTime.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<WorkTime> workTimes;

    public Employee(String name, String program, Employee manager) {
        this.name = name;
        this.program = program;
        this.manager = manager;
        this.workTimes = new ArrayList<>();
    }

    protected Employee() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProgram() {
        return program;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public void addWorkTime(WorkTime workTime) {
        this.workTimes.add(workTime);
    }

    public Collection<Employee> getEmployees() {
        return employees;
    }

    @JsonIgnore
    public Employee getManager() {
        return manager;
    }

    @JsonIgnore
    public Collection<WorkTime> getWorkTimes() {
        return workTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}