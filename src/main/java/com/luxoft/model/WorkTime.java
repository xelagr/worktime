package com.luxoft.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Aleksei Grishkov on 15.12.2016.
 */
@Entity
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate firstEntry;

    private LocalDate lastExit;

    private LocalDate totalOfficeTime;

    private LocalDate pureOfficeTime;

    private LocalDate currentDay;

    @OneToOne
    private Employee employee;

    public WorkTime(LocalDate currentDay, Employee employee, LocalDate firstEntry, LocalDate lastExit, LocalDate totalOfficeTime, LocalDate pureOfficeTime) {
        this.firstEntry = firstEntry;
        this.lastExit = lastExit;
        this.totalOfficeTime = totalOfficeTime;
        this.pureOfficeTime = pureOfficeTime;
        this.employee = employee;
        this.currentDay = currentDay;
    }

    protected WorkTime() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFirstEntry() {
        return firstEntry;
    }

    public LocalDate getLastExit() {
        return lastExit;
    }

    public LocalDate getTotalOfficeTime() {
        return totalOfficeTime;
    }

    public LocalDate getPureOfficeTime() {
        return pureOfficeTime;
    }

    public LocalDate getCurrentDay() {
        return currentDay;
    }

    public Employee getEmployee() {
        return employee;
    }
}