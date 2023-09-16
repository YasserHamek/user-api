package com.test.userapi.dto.validator;

import com.test.userapi.dto.UserFilterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.test.userapi.utils.StaticMessages.INVALID_FILTER;

public class IsValidFilterValidator implements ConstraintValidator<IsValidFilter, UserFilterDto> {

    @Override
    public void initialize(IsValidFilter isValidFilter) {
        //No initialisation is needed
    }

    @Override
    public boolean isValid(UserFilterDto userFilterDto, ConstraintValidatorContext context){
        if(userFilterDto.getEmail() == null && userFilterDto.getName() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    INVALID_FILTER
            );
        }
        return true;
    }
}
