package com.learning.testing.repository;

import com.learning.testing.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Employee getByEmail(String email);
}
