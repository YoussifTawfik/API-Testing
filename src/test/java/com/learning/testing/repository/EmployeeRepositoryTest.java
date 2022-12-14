package com.learning.testing.repository;

import com.learning.testing.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * @DataJpaTest:
 * scan all classes that annotated with entity or repository
 * roll back all transaction when finish testing the method
 * */
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Youssif")
                .lastName("Tawfik")
                .email("youssif.tawfik96@gmail.com")
                .salary(4000.0)
                .build();
    }

    @Test
    @DisplayName("JUnit test for Save Employee")
    public void givenEmployee_whenSave_thenReturnSavedEmployee() {

        // given - setup

        //when - action
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("JUnit test for Find All")
    public void givenEmployees_whenFindAll_thenReturnAllEmployees() {

        // given - setup
        Employee employee2 = Employee.builder()
                .firstName("Martin")
                .lastName("Cuber")
                .email("martin@gmail.com")
                .salary(3000.0)
                .build();
        List<Employee> employees = List.of(employee, employee2);
        employeeRepository.saveAll(employees);

        //when - action
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit test for Find By Email")
    public void givenEmail_whenFindByEmail_thenReturnEmployeeObject() {

        // given - setup
//        Employee employee = Employee.builder()
//                .firstName("Youssif")
//                .lastName("Tawfik")
//                .email("youssif.tawfik96@gmail.com")
//                .salary(4000.0)
//                .build();
        employeeRepository.save(employee);

        //when - action
        Employee retrievedEmployee = employeeRepository.getByEmail(employee.getEmail());

        //then - verify
        assertThat(retrievedEmployee).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for Update Employee")
    public void givenEmployee_whenUpdate_thenReturnUpdatedEmployee() {

        // given - setup
        employeeRepository.save(employee);

        //when - action
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();
        employeeDb.setFirstName("Smith");
        employeeDb.setEmail("smith@gmail.com");
        Employee updatedEmployee=employeeRepository.save(employeeDb);

        //then - verify
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Smith");
        assertThat(updatedEmployee.getEmail()).isEqualTo(employeeDb.getEmail());
    }

    @Test
    @DisplayName("JUnit test for delete employee")
    public void givenEmployee_whenDelete_thenAssertNotFound(){

        // given - setup
        employeeRepository.save(employee);
        // when - action
        employeeRepository.delete(employee);
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        // then - verify
        assertThat(deletedEmployee).isEmpty();
    }


    @Test
    @DisplayName("JUnit test for Test JPQL Operation")
    public void givenEmployee_whenGetByFirstName_thenReturnEmployeeObject(){

        // given - setup
        employeeRepository.save(employee);
        // when - action
        String firstName="Youssif";
        Employee employeeDb=employeeRepository.getByFirstName(firstName);
        // then - verify
        assertThat(employeeDb).isNotNull();
    }


}
