package com.learning.testing.service;

import com.learning.testing.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long employeeId);
}
