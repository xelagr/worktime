package com.luxoft;

import com.luxoft.helper.DefaultTestSettings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DefaultTestSettings
public class ControllersTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetJson() throws Exception {
		mockMvc.perform(get("/employees")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());
	}

    @Test
    public void testAllEmployees() throws Exception {
        mockMvc.perform(get("/employees")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].name").value("Dmitry Loshchinin"))
                .andExpect(jsonPath("$[0].employees.size()").value(1))
                .andExpect(jsonPath("$[0].employees[0].name").value("Alexander Tsvetkov"))
                .andExpect(jsonPath("$[0].employees[0].employees.size()").value(2))
                .andExpect(jsonPath("$[0].employees[0].employees[0].name").value("Alexander Avdeichik"))
                .andExpect(jsonPath("$[0].employees[0].employees[1].name").value("Aleksei Grishkov"));
    }

	@Test
	public void testGetEmployeesById() throws Exception {
		mockMvc.perform(get("/employees/1")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dmitry Loshchinin"))
				.andExpect(jsonPath("$.employees.size()").value(1))
                .andExpect(jsonPath("$.employees[0].name").value("Alexander Tsvetkov"))
                .andExpect(jsonPath("$.employees[0].employees.size()").value(2))
                .andExpect(jsonPath("$.employees[0].employees[0].name").value("Alexander Avdeichik"))
                .andExpect(jsonPath("$.employees[0].employees[1].name").value("Aleksei Grishkov"));
	}

	@Test
    public void testNoEmployeeFound() throws Exception {
        mockMvc.perform(get("/100/employees")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
