package com.sid.gl.manageemployee.service;

import com.sid.gl.manageemployee.dto.EmployeeRequest;
import com.sid.gl.manageemployee.dto.EmployeeResponse;
import com.sid.gl.manageemployee.dto.FilterDTO;
import com.sid.gl.manageemployee.exceptions.ResourceNotFoundException;
import com.sid.gl.manageemployee.models.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IEmployee {
    Employee saveEmployee(EmployeeRequest employee) throws ResourceNotFoundException;
    List<EmployeeResponse> listEmployee();
    EmployeeResponse getEmployee(Long id) throws ResourceNotFoundException;

    Map<String,List<EmployeeResponse>> listEmployeeByType();

    List<EmployeeResponse> listEmployeeByType(String employeeType);

    UserDetails loadUserByUsername(String username);

     Set<GrantedAuthority> getAllPermissionsAsGrantedAuthorities(Long userId);

     List<EmployeeResponse> searchEmployeeByCriteria(FilterDTO filterDTO);

     EmployeeResponse findEmployeeByUsername(String username);

     Employee updateEmployee(Long id,EmployeeRequest employeeRequest);

}
