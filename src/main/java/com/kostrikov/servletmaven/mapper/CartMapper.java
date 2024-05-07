package com.kostrikov.servletmaven.mapper;

import com.kostrikov.servletmaven.dto.CartDto;
import com.kostrikov.servletmaven.entity.Cart;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartMapper implements Mapper<Cart, CartDto> {

    private static final CartMapper CARTMAPPER = new CartMapper();

    public static CartMapper getInstance() {
        return CARTMAPPER;
    }

    @Override
    public CartDto mapFrom(Cart cart) {
        return CartDto.builder()
                .user(cart.getUser().getId())
                .product(cart.getProduct().getId())
                .name(cart.getProduct_name())
                .price(cart.getPrice())
                .quantity(cart.getQuantity())
                .build();
    }

    @Override
    public Cart mapTo(CartDto cartDto) {
        return null;
    }
}
