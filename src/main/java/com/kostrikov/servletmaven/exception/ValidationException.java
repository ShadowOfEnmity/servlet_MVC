package com.kostrikov.servletmaven.exception;

import com.kostrikov.servletmaven.validation.Error;
import lombok.Getter;

import java.util.List;

public class ValidationException extends Exception {

    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
