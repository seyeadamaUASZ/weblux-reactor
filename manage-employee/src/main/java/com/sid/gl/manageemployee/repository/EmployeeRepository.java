package com.sid.gl.manageemployee.repository;

import com.sid.gl.manageemployee.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    boolean existsByUsername(String username);
}
