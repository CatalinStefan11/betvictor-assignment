package com.betvictor.processor.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER})
@Constraint(validatedBy = ValidParagraphRequestValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidParagraphLength {
    String message() default "Valid values for paragraph length are: short, medium, long, verylong";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}