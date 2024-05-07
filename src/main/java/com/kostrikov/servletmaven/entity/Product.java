package com.kostrikov.servletmaven.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Product {
    long id;
    String name;
    double price;
    int quantity;
}
