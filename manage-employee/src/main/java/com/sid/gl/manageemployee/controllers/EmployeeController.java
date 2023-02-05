package com.sid.gl.manageemployee.controllers;

import com.sid.gl.manageemployee.dto.EmployeeRequest;
import com.sid.gl.manageemployee.dto.FilterDTO;
import com.sid.gl.manageemployee.exceptions.ResourceNotFoundException;
import com.sid.gl.manageemployee.service.IEmployee;
import com.sid.gl.manageemployee.tools.PasswordTool;
import com.sid.gl.manageemployee.tools.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployee iEmployee;

    private final BCryptPasswordEncoder encoder;

    @GetMapping
    public ResponseEntity<BasicResponse> listEmployee(){
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setData(iEmployee.listEmployee());
        basicResponse.setStatus(200);
     return ResponseEntity.ok(basicResponse);
    }

    @PostMapping
    @PreAuthorize("hasPermission('ADMIN')")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) throws ResourceNotFoundException {
        BasicResponse basicResponse = new BasicResponse(200);
        String password = PasswordTool.generateCommonLangPassword();
        System.out.println("password "+password);
        String passwordHash = encoder.encode(password);
        employeeRequest.setPassword(passwordHash);
        basicResponse.setData(iEmployee.saveEmployee(employeeRequest));
        return ResponseEntity.ok(basicResponse);
    }
    @GetMapping("/by-employee-type")
    public ResponseEntity<?> listEmployeeByType(){
        BasicResponse response = new BasicResponse(200);
        response.setData(iEmployee.listEmployeeByType());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable("id")Long id) throws ResourceNotFoundException {
        BasicResponse response = new BasicResponse(200);
        response.setData(iEmployee.getEmployee(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("employee-by-type/{employeeType}")
    public ResponseEntity<?> listEmployeeByType(@PathVariable String employeeType){
        BasicResponse response = new BasicResponse(200);
        response.setData(iEmployee.listEmployeeByType(employeeType));
        return ResponseEntity.ok(response);
    }
    //url link search?
    @GetMapping("/search")
    public ResponseEntity<?> searchByCriteria(@Valid FilterDTO filterDTO){
        BasicResponse response = new BasicResponse(200);
        response.setData(iEmployee.searchEmployeeByCriteria(filterDTO));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<BasicResponse> findEmployee(@PathVariable String username){
        BasicResponse response = new BasicResponse(200);
        response.setData(iEmployee.findEmployeeByUsername(username));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BasicResponse> updateEmployee(@PathVariable Long id,@Valid @RequestBody EmployeeRequest employeeRequest){
        BasicResponse response = new BasicResponse(200);
        response.setData(iEmployee.updateEmployee(id,employeeRequest));
        return ResponseEntity.ok(response);
    }
}
