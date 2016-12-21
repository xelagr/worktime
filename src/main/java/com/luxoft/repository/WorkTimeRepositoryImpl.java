package com.luxoft.repository;

import com.luxoft.model.CustomDate;
import com.luxoft.model.EmployeeWorkTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class WorkTimeRepositoryImpl implements WorkTimeRepositoryCustom {
    @Autowired
    WorkTimeRepository workTimeRepository;

    @Override
    public List<EmployeeWorkTime> getEmployeesWorkTimes(List<Long> ids, CustomDate from, CustomDate to) {
        List<EmployeeWorkTime> employeeWorkTimes = new ArrayList<>();
        ids.forEach(id -> employeeWorkTimes.add(new EmployeeWorkTime(id, workTimeRepository.findByEmployeeId(id))));
        return employeeWorkTimes;
    }
}