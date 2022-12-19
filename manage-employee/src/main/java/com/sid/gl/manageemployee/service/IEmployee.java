package com.sid.gl.manageemployee.service;

import com.sid.gl.manageemployee.dto.EmployeeRequest;
import com.sid.gl.manageemployee.dto.EmployeeResponse;
import com.sid.gl.manageemployee.exceptions.ResourceNotFoundException;
import com.sid.gl.manageemployee.models.Employee;

import java.util.List;
import java.util.Map;

public interface IEmployee {
    Employee saveEmployee(EmployeeRequest employee) throws ResourceNotFoundException;
    List<EmployeeResponse> listEmployee();
    EmployeeResponse getEmployee(Long id) throws ResourceNotFoundException;

    Map<String,List<EmployeeResponse>> listEmployeeByType();

    List<EmployeeResponse> listEmployeeByType(String employeeType);


}
