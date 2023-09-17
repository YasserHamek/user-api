package com.test.userapi.controller;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.facade.UserFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @Operation(summary = "Create user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User account has been created successfully"),
        @ApiResponse(responseCode = "401", description = "Email is already used or user is underage or user is not a french resident."),
        @ApiResponse(responseCode = "400", description = "Name or Country or Birthday may be null or empty or Email is invalid, null or empty.")
    })
    @PostMapping(value = "/singup", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createAccount(@RequestBody @Valid UserDto userDto) {
        this.userFacade.createAccount(userDto);
    }

    @Operation(summary = "Search users by name, email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "302", description = "Users corresponding to filter has been found."),
        @ApiResponse(responseCode = "400", description = "Provided filter is invalid, both name and email are null."),
    })
    @PostMapping(value = "/search-by-filter", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<UserDto> searchByFilter(@RequestBody @Valid UserFilterDto userFilterDto) {
        return this.userFacade.searchByFilter(userFilterDto);
    }
}
