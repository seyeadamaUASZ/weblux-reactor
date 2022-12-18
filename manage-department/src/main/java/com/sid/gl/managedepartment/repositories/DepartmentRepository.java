package com.sid.gl.managedepartment.repositories;

import com.sid.gl.managedepartment.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    boolean existsByName(String name);
}
