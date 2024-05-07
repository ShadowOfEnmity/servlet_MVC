package com.kostrikov.servletmaven.validation;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String code;
    String message;
}
