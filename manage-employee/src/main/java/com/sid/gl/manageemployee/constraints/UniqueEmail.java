package com.sid.gl.manageemployee.constraints;

import com.sid.gl.manageemployee.validators.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email is duplicate ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
