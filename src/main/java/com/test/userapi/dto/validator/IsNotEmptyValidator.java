package com.test.userapi.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.test.userapi.utils.StaticMessages.MAY_NOT_BE_NULL_OR_EMPTY;

public class IsNotEmptyValidator implements ConstraintValidator<IsNotEmpty, String> {
    private String fieldName;

    @Override
    public void initialize(IsNotEmpty isNotEmpty) {
        fieldName = isNotEmpty.fieldName();
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context){
        if(field == null || field.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    fieldName + MAY_NOT_BE_NULL_OR_EMPTY
            );
        return true;
    }
}
