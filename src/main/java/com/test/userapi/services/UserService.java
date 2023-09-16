package com.test.userapi.services;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.dto.mapper.UserMapper;
import com.test.userapi.model.User;
import com.test.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.test.userapi.utils.StaticMessages.ONLY_ONE_ACCOUNT_PER_EMAIL_IS_ALLOWED;

@Service
public class UserService
        extends GenericService<UserRepository, UserMapper, User, UserDto, UserFilterDto> {

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository, userMapper);
    }

    /**
     * Creating user account
     * @param userDto representing User informations
     * @throws ResponseStatusException with status 401 when email already exists
     */
    public void createAccount(UserDto userDto) {
        if(repository.existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    ONLY_ONE_ACCOUNT_PER_EMAIL_IS_ALLOWED
            );
        }
        saveEntity(userDto);
    }

    /**
     * Searching Users by filter
     * @param userFilterDto representing search filter, it contain name and email
     * @return List of founded users
     * @throws ResponseStatusException with status 404 when no user was found
     */
    public List<UserDto> searchByFilter(UserFilterDto userFilterDto){
        List<UserDto> users = findAllByExample(userFilterDto);
        if(users.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Users matching : " + userFilterDto.getName() + " "
                            + userFilterDto.getEmail() + ", was not found in DataBase"
            );
        }
        return users;
    }
}
