package com.test.userapi.facade;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFacade {

    private final UserService userService;

    @Autowired
    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creating user account
     * @param userDto representing User informations
     */
    public void createAccount(UserDto userDto){
        userService.createAccount(userDto);
    }

    /**
     * Searching Users by filter
     * @param userFilterDto representing search filter, it contain name and email
     * @return List<UserDto> of founded users
     */
    public List<UserDto> searchByFilter(UserFilterDto userFilterDto){
        return userService.searchByFilter(userFilterDto);
    }
}
