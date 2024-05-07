package com.kostrikov.servletmaven.validation;

public interface Validator <T>{
    ValidationResult isValid(T obj);
}
