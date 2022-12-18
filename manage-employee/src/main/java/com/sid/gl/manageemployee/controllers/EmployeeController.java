package com.sid.gl.manageemployee.controllers;

import com.sid.gl.manageemployee.dto.EmployeeRequest;
import com.sid.gl.manageemployee.exceptions.ResourceNotFoundException;
import com.sid.gl.manageemployee.service.IEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployee iEmployee;

    @GetMapping
    public ResponseEntity<?> listEmployee(){
     return ResponseEntity.ok(iEmployee.listEmployee());
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(iEmployee.saveEmployee(employeeRequest));
    }
    @GetMapping("/by-employee-type")
    public ResponseEntity<?> listEmployeeByType(){
        return ResponseEntity.ok(iEmployee.listEmployeeByType());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable("id")Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(iEmployee.getEmployee(id));
    }
}
