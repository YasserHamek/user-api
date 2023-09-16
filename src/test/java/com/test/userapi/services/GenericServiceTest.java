package com.test.userapi.services;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.dto.mapper.UserMapper;
import com.test.userapi.model.User;
import com.test.userapi.repository.UserRepository;
import com.test.userapi.services.GenericService;
import com.test.userapi.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenericServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService spyGenericService;

    User user;
    User user2;
    UserDto userDto;
    UserDto userDto2;
    UserFilterDto userFilterDto;

    @BeforeEach
    void setUp() {
        spyGenericService = spy(new UserService(userRepository, userMapper));

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
    @DisplayName("Testing saving user")
    void saveEntityTest() {
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.dtoToEntity(userDto)).thenReturn(user);

        spyGenericService.saveEntity(userDto);

        verify(spyGenericService, times(1)).saveEntity(userDto);
        verify(userMapper, times(1)).dtoToEntity(userDto);
        verify(userRepository, times(1)).save(user);
    }


    @Test
    @DisplayName("Testing exception handling when saving user")
    void saveEntityTest_WhenExceptionIsThrown() {
        when(userRepository.save(user)).thenThrow(new IllegalArgumentException());
        when(userMapper.dtoToEntity(userDto)).thenReturn(user);

        assertThrows(ResponseStatusException.class, () -> spyGenericService.saveEntity(userDto));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Testing searching users by filter with only one result")
    void findAllByExampleTest() {
        List<UserDto> userDtoList = List.of(userDto, userDto2);

        when(userMapper.filterToUser(any())).thenReturn(new User());
        when(userRepository.findAll(any(Example.class))).thenReturn(new ArrayList());
        when(userMapper.entitiesToDtos(any())).thenReturn(userDtoList);

        assertEquals(userDtoList, spyGenericService.findAllByExample(any()));
        verify(spyGenericService, times(1)).findAllByExample(any());
        verify(userMapper, times(1)).filterToUser(any());
        verify(userRepository, times(1)).findAll(any(Example.class));
        verify(userMapper, times(1)).entitiesToDtos(any());
    }

    @Test
    @DisplayName("Testing searching users by filter when exception is thrown")
    void findAllByExampleTest_WhenExceptionIsThrown() {
        assertThrows(ResponseStatusException.class, () -> spyGenericService.findAllByExample(userFilterDto));
    }

}
