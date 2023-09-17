package com.test.userapi.dto;

import com.test.userapi.dto.validator.IsValidFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@IsValidFilter()
@Getter
@Setter
@AllArgsConstructor
public class UserFilterDto {
    @Schema(description = "User name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(
        description = "User email",
        requiredMode = Schema.RequiredMode.REQUIRED,
        pattern = "*@*.*"
    )
    private String email;
}
