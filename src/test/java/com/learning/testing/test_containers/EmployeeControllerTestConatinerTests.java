package com.learning.testing.test_containers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.testing.entity.Employee;
import com.learning.testing.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class EmployeeControllerTestConatinerTests {

    @Container
    private static MySQLContainer mySQLContainer=new MySQLContainer("mysql:latest")
            .withUsername("username")
            .withPassword("password")
            .withDatabaseName("dbName");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;


    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll();
        employee = Employee.builder()
                .firstName("Youssif")
                .lastName("Tawfik")
                .email("youssif@gmail.com")
                .salary(4000.0)
                .build();
    }


    @Test
    public void givenEmployee_whenSave_thenReturnSavedEmployee() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(employee.getLastName())));
    }

    @Test
    public void givenEmployeesList_whenGetAll_thenReturnListOfEmployees() throws Exception {
        // given
        Employee employee1=Employee.builder()
                .firstName("Will")
                .lastName("Smith")
                .email("smith@hotmail.com")
                .salary(6000.0)
                .build();
        List<Employee> employeeList=List.of(employee,employee1);
        employeeRepository.saveAll(employeeList);
        // when
        ResultActions result=mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(employeeList.size())));
    }

    @Test
    public void givenEmployeeEmail_whenGetByEmail_thenReturnEmployee() throws Exception {

        // given - setup
        employeeRepository.save(employee);
        // when - action or behavior
        ResultActions response=mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/employees/{email}",employee.getEmail()));
        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(employee.getFirstName())));

    }
}
