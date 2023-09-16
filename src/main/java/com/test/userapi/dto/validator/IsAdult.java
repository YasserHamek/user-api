package com.test.userapi.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.test.userapi.utils.StaticMessages.ONLY_ADULT_ARE_ALLOWED_TO_CREATE_AN_ACCOUNT;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = IsAdultValidator.class)
@Documented
public @interface IsAdult {
    String message() default ONLY_ADULT_ARE_ALLOWED_TO_CREATE_AN_ACCOUNT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


