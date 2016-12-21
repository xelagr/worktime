package com.luxoft.model;

import java.util.Collection;

/**
 * Created by user on 21.12.2016.
 */
public class EmployeeWorkTime {
    private Long employeeId;
    private String employeeName;
    private Collection<WorkTime> workTimes;

    public EmployeeWorkTime() {
    }

    public EmployeeWorkTime(Long employeeId, String employeeName, Collection<WorkTime> workTimes) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.workTimes = workTimes;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Collection<WorkTime> getWorkTimes() {
        return workTimes;
    }
}