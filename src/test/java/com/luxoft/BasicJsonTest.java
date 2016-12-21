package com.luxoft;

import com.luxoft.helper.DefaultTestSettings;
import com.luxoft.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Aleksei Grishkov on 18.12.2016.
 */
@RunWith(SpringRunner.class)
@JsonTest
@DefaultTestSettings
public class BasicJsonTest {

    @Autowired
    JacksonTester<Employee> employeeJacksonTester;

    @Test
    public void testSerialize() throws IOException {
        Employee employee = new Employee("Alex Grishkov", "Credits", null);
        System.out.println(employeeJacksonTester.write(employee).getJson());
        assertThat(employeeJacksonTester.write(employee)).isEqualToJson("employee.json", JSONCompareMode.STRICT);
    }
}
