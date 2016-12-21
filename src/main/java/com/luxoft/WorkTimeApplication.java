package com.luxoft;

import com.luxoft.model.Employee;
import com.luxoft.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class WorkTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkTimeApplication.class, args);
	}

    @Bean
    @Profile("dev")
    CommandLineRunner init(EmployeeRepository employeeRepository) {
        return (args) -> {
            Employee bigBoss = employeeRepository.save(new Employee("Dmitry Loshchinin", "Luxoft", null));
            Employee manager1 = employeeRepository.save(new Employee("Alexander Tsvetkov", "Credits", bigBoss));
            employeeRepository.save(new Employee("Alexander Avdeichik", "Credits", manager1));
            employeeRepository.save(new Employee("Aleksei Grishkov", "Credits", manager1));
        };
    }
}
