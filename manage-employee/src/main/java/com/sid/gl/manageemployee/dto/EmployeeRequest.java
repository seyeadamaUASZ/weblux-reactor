package com.sid.gl.manageemployee.dto;

import com.sid.gl.manageemployee.enums.EmployeeType;
import com.sid.gl.manageemployee.constraints.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private Long id;
    private String lastName;
    private String firstName;
    @UniqueEmail
    private String username;
    private Date birthday;
    private String password;
    private Long departmentId;
    private EmployeeType employeeType;
}
