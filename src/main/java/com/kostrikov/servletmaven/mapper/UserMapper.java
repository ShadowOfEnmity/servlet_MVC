package com.kostrikov.servletmaven.mapper;

import com.kostrikov.servletmaven.dto.UserDto;
import com.kostrikov.servletmaven.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<User, UserDto> {
    private static final UserMapper USERMAPPER = new UserMapper();

    public static UserMapper getInstance() {
        return USERMAPPER;
    }

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(String.valueOf(user.getAge()))
                .email(user.getEmail())
                .build();

    }

    @Override
    public User mapTo(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(Integer.parseInt(userDto.getAge()))
                .email(userDto.getEmail())
                .build();
    }
}
