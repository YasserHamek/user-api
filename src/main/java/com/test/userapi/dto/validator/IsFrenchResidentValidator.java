package com.test.userapi.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.test.userapi.utils.StaticMessages.*;

public class IsFrenchResidentValidator implements ConstraintValidator<IsFrenchResident, String> {

    @Override
    public void initialize(IsFrenchResident isFrenchResident) {
        //No initialisation is needed
    }

    @Override
    public boolean isValid(String country, ConstraintValidatorContext context){
        if(country == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    COUNTRY_MAY_NOT_BE_NULL_OR_EMPTY
            );
        if(!"France".equals(country))
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    ONLY_FRENCH_RESIDENTS_ARE_ALLOWED_TO_CREATE_AN_ACCOUNT
            );
        return true;
    }
}
