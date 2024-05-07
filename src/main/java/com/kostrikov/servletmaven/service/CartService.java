package com.kostrikov.servletmaven.service;

import com.kostrikov.servletmaven.dao.CartDao;
import com.kostrikov.servletmaven.dao.ProductDao;
import com.kostrikov.servletmaven.dto.CartDto;
import com.kostrikov.servletmaven.dto.UserDto;
import com.kostrikov.servletmaven.entity.Cart;
import com.kostrikov.servletmaven.entity.Product;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.mapper.CartMapper;
import com.kostrikov.servletmaven.servlet.GoodsServlet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartService {
    private static final CartService CARTSERVICE = new CartService();
    private final CartDao cartDao = CartDao.getInstance();
    private final ProductDao productDao = ProductDao.getInstance();
    private final CartMapper cartMapper = CartMapper.getInstance();

    public static CartService getInstance() {
        return CARTSERVICE;
    }

    public List<CartDto> getProductsInCart(UserDto user, Map<Long, Integer> goodsQuantity) throws DaoException {
        List<CartDto> listDto = new ArrayList<>();
        List<Product> goodsInBatch = productDao.findByIdBatch(new ArrayList<>(goodsQuantity.keySet()));

        List<Cart> list = goodsQuantity.keySet().stream().map(key -> {
            try {
                return cartDao.findByIds(user.getId(), key).orElseGet(() -> {
                    try {
                        return cartDao.buildCartByProduct(key, goodsInBatch, user.getId());
                    } catch (DaoException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        for (int i = 0; i < list.size(); i++) {
            var cart = list.get(i);
            var quantity = goodsQuantity.get(cart.getProduct().getId());
            cart.setQuantity(quantity + cart.getQuantity());
            cartDao.update(cart);
            listDto.add(cartMapper.mapFrom(cart));
        }

        return listDto;
    }

    public List<CartDto> getCartInfo(Long id) throws DaoException {
        return cartDao.findAll(id).stream().map(cartMapper::mapFrom).toList();
    }
}
