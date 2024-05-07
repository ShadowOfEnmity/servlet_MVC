package com.kostrikov.servletmaven.service;

import com.kostrikov.servletmaven.dao.UserDao;
import com.kostrikov.servletmaven.dto.NewUserDto;
import com.kostrikov.servletmaven.dto.UserDto;
import com.kostrikov.servletmaven.entity.User;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.exception.ValidationException;
import com.kostrikov.servletmaven.mapper.NewUserMapper;
import com.kostrikov.servletmaven.mapper.UserMapper;
import com.kostrikov.servletmaven.validation.CreateUserValidator;
import com.kostrikov.servletmaven.validation.Error;
import com.kostrikov.servletmaven.validation.UserValidator;
import com.kostrikov.servletmaven.validation.ValidationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final NewUserMapper newUserMapper = NewUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final UserValidator userValidator = UserValidator.getInstance();

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserDto> login(String login, String pwd) throws DaoException {
        UserMapper mapper = UserMapper.getInstance();
        return userDao.findByLoginAndPassword(login, pwd).map(mapper::mapFrom);
    }

    public Long create(NewUserDto userDto) throws ValidationException, DaoException {
        ValidationResult validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User user = newUserMapper.mapFrom(userDto);
        if (userDao.isUserFoundByLogin(user.getLogin())) {
            validationResult.add(Error.of("invalid.login", "A user with this login is already registered"));
            throw new ValidationException(validationResult.getErrors());
        }
        userDao.save(user);

        return user.getId();
    }

    public Optional<UserDto> findUserById(Long id) throws DaoException {
        UserMapper mapper = UserMapper.getInstance();
        return userDao.findById(id).map(mapper::mapFrom);
    }

    public void update(UserDto userDto) throws ValidationException, DaoException {
        ValidationResult validationResult = userValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        userDao.update(userMapper.mapTo(userDto));
    }

    public void delete(Long id) throws DaoException {
        userDao.delete(id);
    }
}
