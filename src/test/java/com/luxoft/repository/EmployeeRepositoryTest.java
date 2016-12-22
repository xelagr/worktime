package com.luxoft.repository;

import com.luxoft.helper.DefaultTestSettings;
import com.luxoft.model.Employee;
import com.luxoft.model.WorkTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by user on 21.12.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DefaultTestSettings
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    public void testRepo() throws Exception {
        Optional<Employee> employee = repository.findById(1L);
        assertTrue(employee.isPresent());
        List<WorkTime> workTimes = new ArrayList<>(employee.get().getWorkTimes());
        assertNotNull(workTimes);
        assertThat(workTimes.size(), is(1));
        assertThat(workTimes.get(0).getDate().getDay(), is(21));
    }
}
