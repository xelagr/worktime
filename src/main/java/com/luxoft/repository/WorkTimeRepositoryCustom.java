package com.luxoft.repository;

import com.luxoft.model.CustomDate;
import com.luxoft.model.EmployeeWorkTime;
import com.luxoft.model.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface WorkTimeRepositoryCustom {
    List<EmployeeWorkTime> getEmployeesWorkTimes(List<Long> ids, CustomDate from, CustomDate to);
}