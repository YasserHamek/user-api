package com.test.userapi.services;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.dto.mapper.UserMapper;
import com.test.userapi.model.User;
import com.test.userapi.repository.UserRepository;
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
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService spyUserService;

    User user;
    User user2;
    UserDto userDto;
    UserDto userDto2;
    UserFilterDto userFilterDto;

    @BeforeEach
    void setUp() {
        spyUserService = spy(new UserService(userRepository, userMapper));

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
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        spyUserService.createAccount(userDto);

        verify(spyUserService, times(1)).createAccount(userDto);
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }


    @Test
    @DisplayName("Testing exception handling when saving user")
    void createAccountTest_WhenEmailExists() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> spyUserService.createAccount(userDto));
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Testing searching users by filter")
    void searchByFilterTest() {
        List<UserDto> userDtoList = List.of(userDto, userDto2);

        when(userMapper.filterToUser(any())).thenReturn(new User());
        when(userRepository.findAll(any(Example.class))).thenReturn(new ArrayList());
        when(userMapper.entitiesToDtos(any())).thenReturn(userDtoList);

        assertEquals(userDtoList, spyUserService.searchByFilter(any()));
        verify(spyUserService, times(1)).findAllByExample(any());
        verify(spyUserService, times(1)).searchByFilter(any());
        verify(userMapper, times(1)).filterToUser(any());
        verify(userRepository, times(1)).findAll(any(Example.class));
        verify(userMapper, times(1)).entitiesToDtos(any());
    }

    @Test
    @DisplayName("Testing searching users by filter when no users was found")
    void searchByFilterTest_WhenThereIsNoUser() {
        when(userMapper.filterToUser(any())).thenReturn(new User());
        when(userRepository.findAll(any(Example.class))).thenReturn(new ArrayList());
        when(userMapper.entitiesToDtos(any())).thenReturn(new ArrayList<>());

        assertThrows(ResponseStatusException.class, () -> spyUserService.searchByFilter(userFilterDto));

        verify(userMapper, times(1)).filterToUser(any());
        verify(userRepository, times(1)).findAll(any(Example.class));
        verify(userMapper, times(1)).entitiesToDtos(any());
    }

}
