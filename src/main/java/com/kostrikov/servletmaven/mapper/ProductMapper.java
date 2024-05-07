package com.kostrikov.servletmaven.mapper;

import com.kostrikov.servletmaven.dto.ProductDto;
import com.kostrikov.servletmaven.entity.Product;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductMapper implements Mapper<Product, ProductDto> {

    private static final ProductMapper USERMAPPER = new ProductMapper();

    public static ProductMapper getInstance() {
        return USERMAPPER;
    }

    @Override
    public ProductDto mapFrom(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    @Override
    public Product mapTo(ProductDto productDto) {
        return null;
    }
}
