package com.sid.gl.manageemployee.validators;

import com.sid.gl.manageemployee.constraints.UniqueEmail;
import com.sid.gl.manageemployee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator  implements ConstraintValidator<UniqueEmail, String> {
   private final EmployeeRepository employeeRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !employeeRepository.existsByUsername(value);
    }
}
