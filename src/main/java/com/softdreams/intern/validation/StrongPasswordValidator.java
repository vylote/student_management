package com.softdreams.intern.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private static final Pattern PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && PATTERN.matcher(password).matches();
    }
}
