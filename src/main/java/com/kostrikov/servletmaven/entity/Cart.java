package com.kostrikov.servletmaven.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Cart {
    User user;
    Product product;
    String product_name;
    double price;
    int quantity;

}
