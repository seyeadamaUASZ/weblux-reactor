package com.sid.gl.managedepartment.dto;

import com.sid.gl.managedepartment.constraints.UniqueName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;

    @UniqueName
    @NotEmpty(message = "le nom ne doit pas être nul")
    private String name;
    @NotEmpty(message = "le manager ne doit pas être vide")
    private String manager;
    private String description;
}
