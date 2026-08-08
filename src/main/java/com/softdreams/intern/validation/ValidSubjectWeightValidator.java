package com.softdreams.intern.validation;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidSubjectWeightValidator implements ConstraintValidator<ValidSubjectWeight, CreateSubjectRequest> {
    private static final double EPSILON = 0.00001;

    @Override
    public boolean isValid(CreateSubjectRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if (request == null)
            return true; //@NotNull xử lí riêng

        double total = request.getProcessWeight() + request.getComponentWeight();
        return Math.abs(total - 1.0) < EPSILON;
    }
}
