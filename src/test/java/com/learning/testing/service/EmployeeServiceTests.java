package com.learning.testing.service;

import com.learning.testing.entity.Employee;
import com.learning.testing.exception.EmployeeAlreadyExists;
import com.learning.testing.repository.EmployeeRepository;
import com.learning.testing.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    /*
    * We have two ways to mock the dependencies
    * First one: Using Mockito.mock() method as we have done in the setup() method
    * Second one: Use @Mock and @InjectMocks annotations but remember to use @ExtendWith(MockitoExtension.class) over the class level
    * */

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;



    @BeforeEach
    public void setup(){
//        employeeRepository= Mockito.mock(EmployeeRepository.class);
//        employeeService=new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .firstName("Youssif")
                .lastName("Tawfik")
                .email("youssif.tawfik96@gmail.com")
                .salary(4000.0)
                .build();
    }


    @Test
    @DisplayName("Add Employee")
    public void givenEmployee_whenAddEmployee_thenReturnSavedEmployee(){
        // given - setup

        // We need to mock all repository methods that used inside addEmployee() method in the service
        BDDMockito.given(employeeRepository.getByEmail(employee.getEmail())).willReturn(null);
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when - action
        Employee savedEmployee=employeeService.addEmployee(employee);

        // then - verify
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    @Test
    @DisplayName("Add Employee Throws Exception")
    public void givenExistingEmail_whenAddEmployee_thenThrowsException(){
        // given - setup
        BDDMockito.given(employeeRepository.getByEmail(employee.getEmail())).willReturn(employee);

        // when - action
        org.junit.jupiter.api.Assertions.assertThrows(EmployeeAlreadyExists.class,
                ()-> employeeService.addEmployee(employee));

        // then - verify the output

        // verify that employeeRepository.save() never be called, because it will not be called if the exception has been thrown
        Mockito.verify(employeeRepository,Mockito.never()).save(Mockito.any(Employee.class));
    }

    @Test
    @DisplayName("Get All Employees")
    public void givenEmployeeList_whenGetAll_thenReturnAllEmployees(){
        // given - setup
        Employee employee2 = Employee.builder()
                .firstName("Tony")
                .lastName("Stark")
                .email("stark@gmail.com")
                .salary(5000.0)
                .build();
        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee,employee2));
        // when - action
        List<Employee> employeeList=employeeService.getAllEmployees();
        // then - verify
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Get All Employees (Empty List)")
    public void givenEmptyList_whenGetAll_thenEmptyEmployeeList(){
        // given - setup
        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        // when - action
        List<Employee> employeeList=employeeService.getAllEmployees();
        // then - verify
        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("Update Employee")
    public void givenEmployee_whenUpdate_thenReturnUpdatedEmployee(){
        // given - setup
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("test@gmail.com");
        employee.setFirstName("Test");
        // when - action
        Employee updatedEmployee=employeeService.updateEmployee(employee);
        // then - verify
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("test@gmail.com");
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Test");

    }

    @Test
    @DisplayName("Delete Employee")
    public void givenEmployeeId_whenDelete_thenDeleteEmployee(){
        Long employeeId=1L;
        // given - setup
        // We use willDoNothing() to test the methods that return nothing (void)
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(employeeId);
        // when - action
        employeeRepository.deleteById(employeeId);
        // then - verify output
        // verify that deleteById(employeeId) has been called just one time
        Mockito.verify(employeeRepository,Mockito.times(1)).deleteById(employeeId);

    }
}
