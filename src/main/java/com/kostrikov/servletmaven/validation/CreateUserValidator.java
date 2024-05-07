package com.kostrikov.servletmaven.validation;

import com.kostrikov.servletmaven.dto.NewUserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.lang.Integer.parseInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<NewUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(NewUserDto user) {
        ValidationResult validationResult = new ValidationResult();

        if (user.getAge().isBlank() || (parseInt(user.getAge()) < 0 || parseInt(user.getAge()) >= 120)) {
            validationResult.add(Error.of("invalid.age", "Age is incorrect"));
        }

        if (user.getLogin().isBlank()) {
            validationResult.add(Error.of("invalid.login", "Invalid login"));
        }

        if (user.getPwd().isBlank()) {
            validationResult.add(Error.of("invalid.pwd", "Invalid password"));
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
