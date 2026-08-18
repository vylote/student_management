package com.softdreams.intern.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {
    String message() default "Độ tuổi không hợp lệ";

    int min() default 18;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
