package gr.aleksei;

import gr.aleksei.helper.DefaultTestSettings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Aleksei Grishkov on 19.12.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//TODO tests real http requests to real web server
//TODO currently do nothing, extend if needed
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetEmployeesById() throws Exception {
        System.out.println(restTemplate.getForObject("http://localhost:" + port + "/1/employees", String.class));
    }
}
