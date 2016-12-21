package com.luxoft.controller;

import com.luxoft.exception.EmployeeNotFoundException;
import com.luxoft.model.Employee;
import com.luxoft.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by Aleksei Grishkov on 16.12.2016.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Employee> getAllEmployees() {
        //TODO add validation
        return employeeRepository.findAll();
    }

    @RequestMapping(path = "/{employeeId}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable Long employeeId) {
        //TODO add validation
        Optional<Employee> manager = employeeRepository.findById(employeeId);
        return manager.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }
}
