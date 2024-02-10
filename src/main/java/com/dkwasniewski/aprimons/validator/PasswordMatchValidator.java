package com.dkwasniewski.aprimons.validator;

import com.dkwasniewski.aprimons.dto.NewUserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext
            constraintValidatorContext) {
        if (value instanceof NewUserDTO dto) {
            return dto.getPassword().equals(dto.getConfirmPassword());
        }
        return false;
    }


}
