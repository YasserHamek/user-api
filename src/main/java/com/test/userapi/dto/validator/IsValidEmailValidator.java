package com.test.userapi.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

import static com.test.userapi.utils.StaticMessages.*;

public class IsValidEmailValidator implements ConstraintValidator<IsValidEmail, String> {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    @Override
    public void initialize(IsValidEmail isValidEmail) {
        //No initialisation is needed
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        if(email == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    EMAIL_MAY_NOT_BE_NULL_OR_EMPTY
            );
        if(!Pattern.compile(EMAIL_PATTERN).matcher(email).matches())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    INVALID_EMAIL
            );
        return true;
    }
}
