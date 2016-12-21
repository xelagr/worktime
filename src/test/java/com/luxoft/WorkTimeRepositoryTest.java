package com.luxoft;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.luxoft.model.CustomDate;
        import com.luxoft.repository.WorkTimeRepository;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.context.junit4.SpringRunner;

        import java.util.Arrays;

/**
 * Created by user on 21.12.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class WorkTimeRepositoryTest {
    @Autowired
    private WorkTimeRepository repository;

    @Test public void testRepo() throws Exception {
        ObjectMapper m = new ObjectMapper();
        System.out.println(m.writerWithDefaultPrettyPrinter().writeValueAsString(repository.findByEmployeeId(1L)));

        System.out.println(m.writerWithDefaultPrettyPrinter().writeValueAsString(repository.getEmployeesWorkTimes(Arrays.asList(1L), new CustomDate(2016, 12, 21), new CustomDate(2016, 12, 22))));
    }
}