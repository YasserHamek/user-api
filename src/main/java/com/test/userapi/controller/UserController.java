package com.test.userapi.controller;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.facade.UserFacade;
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

    @PostMapping(value = "/singup", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createAccount(@RequestBody @Valid UserDto userDto) {
        this.userFacade.createAccount(userDto);
    }

    @PostMapping(value = "/search-by-filter", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<UserDto> searchByFilter(@RequestBody @Valid UserFilterDto userFilterDto) {
        return this.userFacade.searchByFilter(userFilterDto);
    }
}
