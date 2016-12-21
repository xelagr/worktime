package com.luxoft.model;

import java.util.Collection;

/**
 * Created by user on 21.12.2016.
 */
public class EmployeeWorkTime {
    private Long employeeId;
    private Collection<WorkTime> workTimes;

    public EmployeeWorkTime() {
    }

    public EmployeeWorkTime(Long employeeId, Collection<WorkTime> workTimes) {
        this.employeeId = employeeId;
        this.workTimes = workTimes;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Collection<WorkTime> getWorkTimes() {
        return workTimes;
    }
}