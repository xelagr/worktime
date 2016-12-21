package com.luxoft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Aleksei Grishkov on 15.12.2016.
 */
@Entity
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime firstEntry;

    private LocalTime lastExit;

    private LocalTime totalOfficeTime;

    private LocalTime pureOfficeTime;

    @OneToOne(targetEntity = CustomDate.class, cascade = CascadeType.ALL)
    private CustomDate date;

    @JsonIgnore
    @ManyToOne(targetEntity = Employee.class)
    private Employee employee;

    public WorkTime(CustomDate date, Employee employee,
                    LocalTime firstEntry, LocalTime lastExit,
                    LocalTime totalOfficeTime, LocalTime pureOfficeTime) {
        this.firstEntry = firstEntry;
        this.lastExit = lastExit;
        this.totalOfficeTime = totalOfficeTime;
        this.pureOfficeTime = pureOfficeTime;
        this.employee = employee;
        this.date = date;
    }

    protected WorkTime() {
    }

    public Long getId() {
        return id;
    }

    public LocalTime getFirstEntry() {
        return firstEntry;
    }

    public LocalTime getLastExit() {
        return lastExit;
    }

    public LocalTime getTotalOfficeTime() {
        return totalOfficeTime;
    }

    public LocalTime getPureOfficeTime() {
        return pureOfficeTime;
    }

    public CustomDate getDate() {
        return date;
    }

    public Employee getEmployee() {
        return employee;
    }
}