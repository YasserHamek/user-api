package com.test.userapi.dto;

import com.test.userapi.dto.validator.IsValidFilter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@IsValidFilter()
public class UserFilterDto {
    private String name;

    private String email;
}
