package com.luxoft;

import com.luxoft.model.CustomDate;
import com.luxoft.model.Employee;
import com.luxoft.model.WorkTime;
import com.luxoft.repository.EmployeeRepository;
import com.luxoft.repository.WorkTimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalTime;

@SpringBootApplication
public class WorkTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkTimeApplication.class, args);
    }

    @Bean
    @Profile("dev")
    CommandLineRunner init(EmployeeRepository employeeRepository, WorkTimeRepository workTimeRepository) {
        return (args) -> {
            Employee bigBoss = employeeRepository.save(new Employee("Dmitry", "Loshchinin", "Luxoft", null));
            Employee manager1 = employeeRepository.save(new Employee("Alexander", "Tsvetkov", "Credits", bigBoss));
            employeeRepository.save(new Employee("Alexander", "Avdeichik", "Credits", manager1));
            employeeRepository.save(new Employee("Aleksei", "Grishkov", "Credits", manager1));


            WorkTime bigBossWT = workTimeRepository.save(
                    new WorkTime(new CustomDate(2016, 12, 21),
                            bigBoss,
                            LocalTime.of(8, 0, 0),
                            LocalTime.of(16, 0, 0),
                            LocalTime.of(8, 0, 0),
                            LocalTime.of(7, 0, 0)));
        };
    }
}
