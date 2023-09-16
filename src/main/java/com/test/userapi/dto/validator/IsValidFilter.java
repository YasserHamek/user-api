package com.test.userapi.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.test.userapi.utils.StaticMessages.INVALID_FILTER;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = IsValidFilterValidator.class)
@Documented
public @interface IsValidFilter {
    String message() default INVALID_FILTER;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


