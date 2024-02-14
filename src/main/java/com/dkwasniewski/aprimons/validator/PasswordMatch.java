package com.dkwasniewski.aprimons.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
    String message() default "Hasła się nie zgadzają";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
