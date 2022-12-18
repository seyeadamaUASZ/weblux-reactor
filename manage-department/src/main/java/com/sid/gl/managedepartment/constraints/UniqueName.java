package com.sid.gl.managedepartment.constraints;

import com.sid.gl.managedepartment.validators.UniqueNameValidator;
import com.sid.gl.managedepartment.response.DepartmentMessage;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueName {
    String message() default DepartmentMessage.CODE_UNIQUE_CONSTRAINT_ERROR;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
