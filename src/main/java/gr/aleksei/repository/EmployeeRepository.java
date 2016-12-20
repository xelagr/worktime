package gr.aleksei.repository;

import gr.aleksei.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Aleksei Grishkov on 15.12.2016.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);
    Optional<Employee> findByLastName(String lastName);
}
