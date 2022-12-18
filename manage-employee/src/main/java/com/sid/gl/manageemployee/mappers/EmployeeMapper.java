package com.sid.gl.manageemployee.mappers;

import com.sid.gl.manageemployee.dto.EmployeeRequest;
import com.sid.gl.manageemployee.dto.EmployeeResponse;
import com.sid.gl.manageemployee.models.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {

    public Employee convertToEmployee(EmployeeRequest employeeRequest){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest,employee);
        return employee;
    }

    public EmployeeResponse convertEmployeeResponse(Employee employee){
        EmployeeResponse response = new EmployeeResponse();
        BeanUtils.copyProperties(employee,response);
        return response;
    }
}
