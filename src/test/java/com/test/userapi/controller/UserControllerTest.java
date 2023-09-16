package com.test.userapi.controller;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.facade.UserFacade;
import com.test.userapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private UserController spyUserController;

    User user;
    UserDto userDto;
    UserFilterDto userFilterDto;

    @BeforeEach
    void setUp() {
        spyUserController = spy(new UserController(userFacade));

        user = new User();
        user.setName("user");
        user.setEmail("user@gmail.com");

        userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        userFilterDto = new UserFilterDto(user.getName(), user.getEmail());
    }

    @Test
    @DisplayName("Testing create account")
    void createAccountTest() {
        doNothing().when(userFacade).createAccount(userDto);

        spyUserController.createAccount(userDto);

        verify(spyUserController, times(1)).createAccount(userDto);
        verify(userFacade, times(1)).createAccount(userDto);
    }

    @Test
    @DisplayName("Testing searching by filter")
    void searchByFilterTest() {
        List<UserDto> userDtoList = List.of(userDto);
        when(userFacade.searchByFilter(userFilterDto)).thenReturn(userDtoList);


        assertEquals(userDtoList, spyUserController.searchByFilter(userFilterDto));

        verify(spyUserController, times(1)).searchByFilter(userFilterDto);
        verify(userFacade, times(1)).searchByFilter(userFilterDto);
    }

}
