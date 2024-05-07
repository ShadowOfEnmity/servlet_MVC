package com.kostrikov.servletmaven.dao;

import com.kostrikov.servletmaven.entity.Product;
import com.kostrikov.servletmaven.entity.User;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.xml.stream.events.Characters;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao implements Dao<Long, Product> {


    private static final ProductDao INSTANCE = new ProductDao();
    private static final String FIND_ALL_SQL = "SELECT * FROM goods";
    private static final String FIND_BY_ID_BATCH_SQL = "SELECT * FROM goods WHERE id IN (%s)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM goods WHERE id = ?";

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Product product) throws DaoException {
        return false;
    }

    @Override
    public List<Product> findAll() throws DaoException {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Product> goods = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                goods.add(buildProduct(result));
            }
            return goods;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Product product = null;
            if (resultSet.next()) {
                product = buildProduct(resultSet);
            }

            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Product save(Product entity) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    private Product buildProduct(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getObject("name", String.class))
//                .price(resultSet.getObject("price", Double.class))
                .price(resultSet.getBigDecimal("price").doubleValue())
                .quantity(resultSet.getObject("quantity", Integer.class))
                .build();
    }

    public List<Product> findByIdBatch(List<Long> idList) throws DaoException {
        StringBuilder chunk = new StringBuilder();
        for (int i = 0; i < idList.size(); i++) {
            if (i != 0) chunk.append(',');
            chunk.append(idList.get(i));
        }

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_BATCH_SQL.formatted(chunk.toString()))) {

            List<Product> goods = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                goods.add(buildProduct(result));
            }
            return goods;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
