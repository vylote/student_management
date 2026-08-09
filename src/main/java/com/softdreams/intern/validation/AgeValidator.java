package com.softdreams.intern.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {
    private int minAge;

    @Override
    public void initialize(ValidAge constraintAnnotation) {
        minAge = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
        if (dateOfBirth == null)
            return true;

        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        return age >= minAge;
    }
}
