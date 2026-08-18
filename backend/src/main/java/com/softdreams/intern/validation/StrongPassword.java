package com.softdreams.intern.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Mật khẩu cần ít nhất 8 ký tự, gồm chữ hoa, chữ thường và số";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
