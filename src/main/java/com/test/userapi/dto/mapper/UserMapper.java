package com.test.userapi.dto.mapper;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDto, UserFilterDto> {
    @Override
    default User filterToUser(UserFilterDto userFilterDto) {
        User user = new User();
        user.setName(userFilterDto.getName());
        user.setEmail(userFilterDto.getEmail());
        return user;
    }
}
