package com.test.userapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(
        description = "User birthday",
        requiredMode = Schema.RequiredMode.REQUIRED,
        pattern = "dd/MM/yyyy"
    )
    private String name;

    @Schema(
        description = "User birthday",
        requiredMode = Schema.RequiredMode.REQUIRED,
        pattern = "dd/MM/yyyy"
    )
    private LocalDate birthday;

    @Schema(
        description = "User country",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String country;

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
