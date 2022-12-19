package com.sid.gl.manageemployee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sid.gl.manageemployee.enums.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {
    private String lastName;
    private String firstName;
    private String username;
    private Date birthday;
    private Long departmentId;
    private EmployeeType employeeType;

    @JsonIgnore
    public String getEmployeeTypeString(){
        return getEmployeeType().name();
    }


}
