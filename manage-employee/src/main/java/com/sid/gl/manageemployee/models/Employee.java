package com.sid.gl.manageemployee.models;

import com.sid.gl.manageemployee.enums.EmployeeType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Employee extends Auditable<String> {
    @Column(name = "name_employee",length = 50)
    private String lastName;
    @Column(name = "firstname_employee",length = 100)
    private String firstName;
    @Email
    @Column(name = "username_employee",unique = true)
    private String username;
    private Date birthday;
    private String password;
    private Long departmentId;
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

}
