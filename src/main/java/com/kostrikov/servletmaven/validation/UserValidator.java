package com.kostrikov.servletmaven.validation;

import com.kostrikov.servletmaven.dto.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.lang.Integer.parseInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidator implements Validator<UserDto> {
    private static final UserValidator INSTANCE= new UserValidator();

    public static UserValidator getInstance() {
        return INSTANCE;
    }
    @Override
    public ValidationResult isValid(UserDto user) {
        ValidationResult validationResult = new ValidationResult();
        if (user.getAge().isBlank() || (parseInt(user.getAge()) < 0 || parseInt(user.getAge()) >= 120)) {
            validationResult.add(Error.of("invalid.age", "Age is incorrect"));
        }

        if (user.getEmail().isBlank()) {
            validationResult.add(Error.of("invalid.email", "Invalid email"));
        }

        if (user.getName().isBlank()) {
            validationResult.add(Error.of("invalid.name", "Invalid name"));
        }

        return validationResult;
    }
}
