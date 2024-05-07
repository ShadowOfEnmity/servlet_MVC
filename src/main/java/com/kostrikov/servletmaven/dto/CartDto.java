package com.kostrikov.servletmaven.dto;

import com.kostrikov.servletmaven.entity.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CartDto {
    long user;
    long product;
    String name;
    double price;
    int quantity;
}
