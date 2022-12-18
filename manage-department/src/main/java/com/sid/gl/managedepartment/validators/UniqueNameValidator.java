package com.sid.gl.managedepartment.validators;

import com.sid.gl.managedepartment.constraints.UniqueName;
import com.sid.gl.managedepartment.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {
    private final DepartmentRepository departmentRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !departmentRepository.existsByName(value);
    }
}
