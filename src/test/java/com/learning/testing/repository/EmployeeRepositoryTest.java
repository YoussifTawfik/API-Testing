package com.learning.testing.repository;

import com.learning.testing.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

/*
* @DataJpaTest:
* scan all classes that annotated with entity or repository
* roll back all transaction when finish testing the method
* */
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    @DisplayName("Save Employee Test Method")
    public void givenEmployee_whenSave_thenReturnSavedEmployee(){

        // given - setup
        Employee employee=Employee.builder()
                .firstName("Youssif")
                .lastName("Tawfik")
                .email("youssif.tawfik96@gmail.com")
                .salary(4000.0)
                .build();

        //when - action
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Find All Test Method")
    public void givenEmployees_whenFindAll_thenReturnAllEmployees(){

        // given - setup
        Employee employee1=Employee.builder()
                .firstName("Youssif")
                .lastName("Tawfik")
                .email("youssif.tawfik96@gmail.com")
                .salary(4000.0)
                .build();

        Employee employee2=Employee.builder()
                .firstName("Martin")
                .lastName("Cuber")
                .email("martin@gmail.com")
                .salary(3000.0)
                .build();
        List<Employee> employees=List.of(employee1,employee2);
        employeeRepository.saveAll(employees);

        //when - action
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit test for Find By Email")
    public void givenEmail_whenFindByEmail_thenReturnEmployeeObject(){

        // given - setup
        Employee employee=Employee.builder()
                .firstName("Youssif")
                .lastName("Tawfik")
                .email("youssif.tawfik96@gmail.com")
                .salary(4000.0)
                .build();
        employeeRepository.save(employee);

        //when - action
        Employee retrievedEmployee=employeeRepository.getByEmail(employee.getEmail());

        //then - verify
        assertThat(retrievedEmployee).isNotNull();
    }


}
