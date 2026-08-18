package com.softdreams.intern.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Slf4j
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

        int age = Period.between(dateOfBirth, LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"))).getYears();
        log.error("{} years old", age);

        return age >= minAge;
    }
}
