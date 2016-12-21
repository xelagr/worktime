package com.luxoft.controller;

import com.luxoft.exception.EmployeeNotFoundException;
import com.luxoft.model.CustomDate;
import com.luxoft.model.Employee;
import com.luxoft.model.EmployeeWorkTime;
import com.luxoft.model.WorkTime;
import com.luxoft.repository.EmployeeRepository;
import com.luxoft.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @Autowired
    WorkTimeService workTimeService;


    @RequestMapping(path = "/worktimes/{employeeId}", method = RequestMethod.GET)
    public @ResponseBody List<EmployeeWorkTime> getEmployeeWorkTimes(@PathVariable Long employeeId, @RequestParam String from, @RequestParam String to) {
        LocalDate f = LocalDate.parse(from);
        LocalDate t = LocalDate.parse(to);
        return workTimeService.getEmployeeWorkTimes(employeeId,
                new CustomDate(f.getYear(), f.getMonthValue(), f.getDayOfMonth()),
                new CustomDate(t.getYear(), t.getMonthValue(), t.getDayOfMonth()));
    }
}
