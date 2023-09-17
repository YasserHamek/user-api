package com.test.userapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.userapi.dto.validator.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    @IsNotEmpty(fieldName = "Name")
    @Schema(description = "User name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @IsAdult
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Schema(
        description = "User birthday",
        requiredMode = Schema.RequiredMode.REQUIRED,
        pattern = "dd/MM/yyyy"
    )
    private LocalDate birthday;

    @IsFrenchResident
    @Schema(
        description = "User country",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String country;

    @IsValidEmail
    @Schema(
        description = "User email",
        requiredMode = Schema.RequiredMode.REQUIRED,
        pattern = "*@*.*"
    )
    private String email;

    @Schema(
        description = "User phone number",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String phoneNumber;

    @Schema(
        description = "User gender",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String gender;
}
