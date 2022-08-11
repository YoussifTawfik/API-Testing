package com.learning.testing.service;

import com.learning.testing.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long employeeId);

    Optional<Employee> getByEmail(String email);
}
