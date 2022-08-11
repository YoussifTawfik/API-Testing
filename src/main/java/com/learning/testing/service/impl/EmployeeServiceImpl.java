package com.learning.testing.service.impl;

import com.learning.testing.entity.Employee;
import com.learning.testing.exception.EmployeeAlreadyExists;
import com.learning.testing.repository.EmployeeRepository;
import com.learning.testing.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        Employee savedEmployee=employeeRepository.getByEmail(employee.getEmail());
        if (savedEmployee!=null){
            throw new EmployeeAlreadyExists("Employee Already Exists with given email: "+employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Optional<Employee> getByEmail(String email) {
        return Optional.of(employeeRepository.getByEmail(email));
    }

}
