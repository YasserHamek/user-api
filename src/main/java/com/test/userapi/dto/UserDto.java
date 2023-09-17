package com.test.userapi.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.userapi.dto.validator.IsAdult;
import com.test.userapi.dto.validator.IsFrenchResident;
import com.test.userapi.dto.validator.IsNotEmpty;
import com.test.userapi.dto.validator.IsValidEmail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @IsNotEmpty(fieldName = "Name")
    private String name;

    @IsAdult
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    @IsFrenchResident
    private String country;

    @IsValidEmail
    private String email;

    private String phoneNumber;

    private String gender;
}
