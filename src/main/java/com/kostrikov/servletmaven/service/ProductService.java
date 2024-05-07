package com.kostrikov.servletmaven.service;

import com.kostrikov.servletmaven.dao.ProductDao;
import com.kostrikov.servletmaven.dto.ProductDto;
import com.kostrikov.servletmaven.entity.Product;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.mapper.ProductMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final ProductDao productDao = ProductDao.getInstance();
    private final ProductMapper prodMapper = ProductMapper.getInstance();

    public static ProductService getInstance() {
        return INSTANCE;
    }

    public List<ProductDto> getAllGoods() throws DaoException {
        List<Product> goods = productDao.findAll();
        return goods.stream().map(prodMapper::mapFrom).toList();
    }
}
