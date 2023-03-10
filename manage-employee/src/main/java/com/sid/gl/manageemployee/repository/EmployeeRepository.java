package com.sid.gl.manageemployee.repository;

import com.sid.gl.manageemployee.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {
    boolean existsByUsername(String username);
    Optional<Employee>findByUsername(String username);

    //TODO query for to filtre element by startDate and endDate
}
