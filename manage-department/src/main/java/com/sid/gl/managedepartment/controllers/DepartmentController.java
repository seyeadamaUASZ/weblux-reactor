package com.sid.gl.managedepartment.controllers;

import com.sid.gl.managedepartment.dto.DepartmentDTO;
import com.sid.gl.managedepartment.response.ApiResponse;
import com.sid.gl.managedepartment.response.DepartmentMessage;
import com.sid.gl.managedepartment.services.IDepartment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDepartment iDepartment;

    @PostMapping("/department")
    public ResponseEntity<ApiResponse> addDep(@Valid @RequestBody DepartmentDTO departmentDTO){
        ApiResponse apiResponse=new ApiResponse(DepartmentMessage.WS_SUCCESS);
        apiResponse.setData(iDepartment.addDepartment(departmentDTO));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/department")
    public ResponseEntity<ApiResponse> listDep(){
        ApiResponse apiResponse=
                new ApiResponse(DepartmentMessage.WS_SUCCESS);
        apiResponse.setData(iDepartment.listDepartments());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<ApiResponse> getDepartment(@PathVariable("id") Long id){
        ApiResponse apiResponse=
                new ApiResponse(DepartmentMessage.WS_SUCCESS);
        apiResponse.setData(iDepartment.getDepartment(id));
        return ResponseEntity.ok(apiResponse);
    }

}
