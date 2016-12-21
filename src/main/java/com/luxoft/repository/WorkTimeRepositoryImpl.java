package com.luxoft.repository;

import com.luxoft.model.CustomDate;
import com.luxoft.model.Employee;
import com.luxoft.model.EmployeeWorkTime;
import com.luxoft.model.WorkTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class WorkTimeRepositoryImpl implements WorkTimeRepositoryCustom {
    @Autowired
    WorkTimeRepository workTimeRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @PersistenceContext
    EntityManager em;

    @Override
    public List<EmployeeWorkTime> getEmployeesWorkTimes(List<Long> ids, CustomDate from, CustomDate to) {
        List<EmployeeWorkTime> employeeWorkTimes = new ArrayList<>();
        ids.forEach(id -> {
            Employee e = employeeRepository.findById(id).get();
            employeeWorkTimes.add(new EmployeeWorkTime(id, e.getName(),
                    getWorkTimes(id, from, to)));
        });
        return employeeWorkTimes;
    }

    private List<WorkTime> getWorkTimes(Long id, CustomDate from, CustomDate to) {

        String hql = "FROM WorkTime w LEFT JOIN FETCH w.employee e" +
                " LEFT JOIN FETCH w.date d" +
                " where e.id = :id" +
                " and d.date between :f and :t";

        Query q = em.createQuery(hql);
        q.setParameter("id", id);
        q.setParameter("f", from.getDate());
        q.setParameter("t", to.getDate());

        List result = q.getResultList();
        if(null == result) {
            return new ArrayList<>();
        }
        return (List<WorkTime>)result;
    }
}