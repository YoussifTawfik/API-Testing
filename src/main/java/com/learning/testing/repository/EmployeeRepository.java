package com.learning.testing.repository;

import com.learning.testing.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Employee getByEmail(String email);


    @Query("select emp from Employee emp where emp.firstName=:firstName")   // it may return list but just for testing
    Employee getByFirstName(@Param("firstName") String firstName);
}
