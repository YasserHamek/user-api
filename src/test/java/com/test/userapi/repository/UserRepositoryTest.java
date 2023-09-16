package com.test.userapi.repository;

import com.test.userapi.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private static User user;

    @BeforeAll
    static void setUp() {
        user = new User();
        user.setEmail("user@gmail.com");
        user.setName("user");
        user.setBirthday(LocalDate.of(1970, 01, 01));
        user.setCountry("France");
    }

    @Test
    @DisplayName("Testing if user exists by email")
    void existsByEmailTest() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        userRepository.existsByEmail(user.getEmail());
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }


    @Test
    @DisplayName("Testing find all method")
    void findAllTest() {
        Example example = Example.of(user);
        when(userRepository.findAll(example)).thenReturn(new ArrayList<>());
        userRepository.findAll(example);
        verify(userRepository, times(1)).findAll(example);
    }

    @Test
    @DisplayName("Testing saving user")
    void saveTest() {
        when(userRepository.save(user)).thenReturn(user);
        userRepository.save(user);
        verify(userRepository, times(1)).save(user);
    }
}
