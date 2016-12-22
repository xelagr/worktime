/*
 * Copyright 2001-2016, Deutsche Bank AG. All Rights Reserved.
 * Confidential and Proprietary Information of Deutsche Bank.
 *
 * @author: Alexey Grishkov
 * Created: 22.12.2016
 */
package com.luxoft;

import com.luxoft.model.CustomDate;
import com.luxoft.model.Employee;
import com.luxoft.model.WorkTime;
import com.luxoft.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalTime;

@Configuration
@EnableJpaRepositories
@Profile("test")
public class TestConfig {

    @Bean
    @Profile("test")
    CommandLineRunner init(EmployeeRepository employeeRepository) {
        return (args) -> {
            Employee bigBoss = new Employee("Dmitry Loshchinin", "Luxoft", null);
            bigBoss.addWorkTime(new WorkTime(new CustomDate(2016, 12, 21),
                    bigBoss,
                    LocalTime.of(8, 0, 0),
                    LocalTime.of(16, 0, 0),
                    LocalTime.of(8, 0, 0),
                    LocalTime.of(7, 0, 0)));

            bigBoss = employeeRepository.save(bigBoss);
            Employee manager1 = employeeRepository.save(new Employee("Alexander Tsvetkov", "Credits", bigBoss));
            employeeRepository.save(new Employee("Alexander Avdeichik", "Credits", manager1));
            employeeRepository.save(new Employee("Aleksei Grishkov", "Credits", manager1));
        };
    }
}
