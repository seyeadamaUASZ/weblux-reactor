package com.sid.gl.managedepartment.services;

import com.sid.gl.managedepartment.dto.DepartmentDTO;
import com.sid.gl.managedepartment.models.Department;

import java.util.List;

public interface IDepartment {
    Department addDepartment(DepartmentDTO request);
    List<DepartmentDTO> listDepartments();
    DepartmentDTO getDepartment(Long id);
}
