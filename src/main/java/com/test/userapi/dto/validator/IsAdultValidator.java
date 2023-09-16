package com.test.userapi.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;

import static com.test.userapi.utils.StaticMessages.*;

public class IsAdultValidator implements ConstraintValidator<IsAdult, LocalDate> {

    @Override
    public void initialize(IsAdult isAdult) {
        //No initialisation is needed
    }

    @Override
    public boolean isValid(LocalDate birthDay, ConstraintValidatorContext context){
        if(birthDay == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    BIRTHDAY_MAY_NOT_BE_NULL
            );
        if(Period.between(birthDay, LocalDate.now()).getYears() < 18)
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    ONLY_ADULT_ARE_ALLOWED_TO_CREATE_AN_ACCOUNT
            );
        return true;
    }
}
