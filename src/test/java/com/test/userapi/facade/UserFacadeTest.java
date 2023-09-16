package com.test.userapi.facade;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.dto.mapper.UserMapper;
import com.test.userapi.model.User;
import com.test.userapi.repository.UserRepository;
import com.test.userapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserFacade spyUserFacade;

    User user;
    User user2;
    UserDto userDto;
    UserDto userDto2;
    UserFilterDto userFilterDto;

    @BeforeEach
    void setUp() {
        spyUserFacade = spy(new UserFacade(userService));

        user = new User();
        user.setName("user");
        user.setEmail("user@gmail.com");
        user2 = new User();
        user2.setName("user2");
        user2.setEmail("user2@gmail.com");

        userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto2 = new UserDto();
        userDto2.setName(user2.getName());
        userDto2.setEmail(user2.getEmail());

        userFilterDto = new UserFilterDto(user.getName(), user.getEmail());
    }

    @Test
    @DisplayName("Testing create account")
    void createAccountTest() {
        doNothing().when(userService).createAccount(userDto);

        spyUserFacade.createAccount(userDto);

        verify(spyUserFacade, times(1)).createAccount(userDto);
        verify(userService, times(1)).createAccount(userDto);
    }

    @Test
    @DisplayName("Testing searching users by filter")
    void searchByFilterTest() {
        List<UserDto> userDtoList = List.of(userDto, userDto2);

        when(userService.searchByFilter(userFilterDto)).thenReturn(userDtoList);

        assertEquals(userDtoList, spyUserFacade.searchByFilter(userFilterDto));
        verify(spyUserFacade, times(1)).searchByFilter(userFilterDto);
        verify(userService, times(1)).searchByFilter(userFilterDto);
    }
}
