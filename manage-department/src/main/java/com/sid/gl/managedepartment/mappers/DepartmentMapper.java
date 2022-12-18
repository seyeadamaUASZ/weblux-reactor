package com.sid.gl.managedepartment.mappers;

import com.sid.gl.managedepartment.dto.DepartmentDTO;
import com.sid.gl.managedepartment.models.Department;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper {

    public Department convertToDepartment(DepartmentDTO request){
        Department department = new Department();
        BeanUtils.copyProperties(request,department);
        return department;
    }

    public DepartmentDTO convertToDTO(Department department){
        DepartmentDTO departmentDTO = new DepartmentDTO();
        BeanUtils.copyProperties(department,departmentDTO);
        return departmentDTO;
    }


}
