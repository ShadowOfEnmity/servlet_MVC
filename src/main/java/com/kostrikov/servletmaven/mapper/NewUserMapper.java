package com.kostrikov.servletmaven.mapper;

import com.kostrikov.servletmaven.dto.NewUserDto;
import com.kostrikov.servletmaven.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewUserMapper implements Mapper<NewUserDto, User> {

    private static final NewUserMapper INSTANCE = new NewUserMapper();

    public static NewUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User mapFrom(NewUserDto newUserDto) {
        return User.builder()
                .name(newUserDto.getName())
                .login(newUserDto.getLogin())
                .email(newUserDto.getEmail())
                .age(Integer.parseInt(newUserDto.getAge()))
                .pwd(newUserDto.getPwd())
                .build();
    }

    @Override
    public NewUserDto mapTo(User user) {
        return null;
    }

}
