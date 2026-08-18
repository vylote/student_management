package com.softdreams.intern.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidSubjectWeightValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSubjectWeight {
    String message() default "Tổng trọng số điểm phải là 1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
