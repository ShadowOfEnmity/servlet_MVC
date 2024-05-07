package com.kostrikov.servletmaven.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    @Getter
    private final List<Error> errors = new ArrayList<>();

    public void add(Error e) {
        this.errors.add(e);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
