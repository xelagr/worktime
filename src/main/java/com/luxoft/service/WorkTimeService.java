package com.luxoft.service;

import com.luxoft.exception.EmployeeNotFoundException;
import com.luxoft.model.CustomDate;
import com.luxoft.model.Employee;
import com.luxoft.model.EmployeeWorkTime;
import com.luxoft.repository.EmployeeRepository;
import com.luxoft.repository.WorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by user on 21.12.2016.
 */
@Service
public class WorkTimeService {
    @Autowired
    WorkTimeRepository workTimeRepository;

    @Autowired
    public EmployeeRepository employeeRepository;

    public List<EmployeeWorkTime> getEmployeeWorkTimes(Long id, CustomDate from, CustomDate to) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        List<Long> ids = new ArrayList<>();
        ids.add(employee.getId());

        Queue<Employee> employees = new LinkedList<>();
        employees.addAll(employee.getEmployees());

        while (!employees.isEmpty()) {
            Employee e = employees.poll();
            ids.add(e.getId());
            employees.addAll(e.getEmployees());
        }

        return workTimeRepository.getEmployeesWorkTimes(ids, from, to);
    }
}