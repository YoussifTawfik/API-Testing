package com.learning.testing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.testing.entity.Employee;
import com.learning.testing.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee=Employee.builder()
                .firstName("Youssif")
                .lastName("Tawfik")
                .email("youssif@gmail.com")
                .salary(5000.0).build();
    }


    @Test
    public void givenEmployee_whenSave_thenReturnSavedEmployee() throws Exception {
        // given
        BDDMockito.given(employeeService.addEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

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
        BDDMockito.given(employeeService.getAllEmployees())
                .willReturn(employeeList);
        // when
       ResultActions result=mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(employeeList.size())));
    }


}
