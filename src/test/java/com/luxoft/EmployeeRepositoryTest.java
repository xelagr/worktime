package com.luxoft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.model.Employee;
import com.luxoft.model.WorkTime;
import com.luxoft.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 21.12.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    public void testRepo() throws Exception {
        Optional<Employee> employee = repository.findById(1L);
        assertTrue(employee.isPresent());
        Collection<WorkTime> workTimes = employee.get().getWorkTimes();
        assertNotNull(workTimes);
        assertThat(workTimes.size(), is(1));
    }
}
