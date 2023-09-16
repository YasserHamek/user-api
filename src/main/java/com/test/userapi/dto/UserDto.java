package com.test.userapi.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String name;

    private LocalDate birthday;

    private String country;

    private String email;

    private String phoneNumber;

    private String gender;
}
