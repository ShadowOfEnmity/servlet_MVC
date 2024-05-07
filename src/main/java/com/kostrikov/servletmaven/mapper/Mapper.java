package com.kostrikov.servletmaven.mapper;

public interface Mapper<F, T> {
    T mapFrom(F f);
    F mapTo(T t);
}
