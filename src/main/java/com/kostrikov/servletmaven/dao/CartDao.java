package com.kostrikov.servletmaven.dao;

import com.kostrikov.servletmaven.entity.Cart;
import com.kostrikov.servletmaven.entity.Product;
import com.kostrikov.servletmaven.entity.User;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDao implements Dao<Long, Cart> {
    private final UserDao userDao = UserDao.getInstance();
    private final ProductDao productDao = ProductDao.getInstance();
    private static final CartDao INSTANCE = new CartDao();
    private static final String FIND_BY_USER_AND_PRODUCTS_IDS_SQL = "SELECT * FROM carts WHERE user_id = ? AND product_id = ?";
    private static final String SAVE_NEW_CART_SQL = "INSERT  INTO carts (user_id, product_name, price, quantity, product_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE carts SET product_name=?, price=?, quantity=? WHERE user_id=? AND product_id=?";
    public static final String FIND_BY_USER_SQL = "SELECT * FROM carts WHERE user_id = ?";

    public static CartDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Cart cart) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, cart.getProduct_name());
            preparedStatement.setBigDecimal(2, BigDecimal.valueOf(cart.getPrice()));
            preparedStatement.setInt(3, cart.getQuantity());
            preparedStatement.setLong(4, cart.getUser().getId());
            preparedStatement.setLong(5, cart.getProduct().getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Cart> findAll() throws DaoException {
        return null;
    }


    public List<Cart> findAll(long userId) throws DaoException {

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_USER_SQL)) {
            List<Cart> carts = new ArrayList<>();
            statement.setLong(1, userId);
            var result = statement.executeQuery();
            while (result.next()) {
                carts.add(buildCart(result));
            }
            return carts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Cart> findById(Long id) throws DaoException {
        return Optional.empty();

    }

    public Optional<Cart> findByIds(long userId, long productId) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_USER_AND_PRODUCTS_IDS_SQL)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Cart cart = null;
            if (resultSet.next()) {
                cart = buildCart(resultSet);
            }

            return Optional.ofNullable(cart);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Cart save(Cart cart) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_NEW_CART_SQL)) {
            preparedStatement.setLong(1, cart.getUser().getId());
            preparedStatement.setObject(2, cart.getProduct_name());
            preparedStatement.setBigDecimal(3, BigDecimal.valueOf(cart.getPrice()));
            preparedStatement.setInt(4, cart.getQuantity());
            preparedStatement.setLong(5, cart.getProduct().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            return cart;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }


    private Cart buildCart(ResultSet resultSet) throws SQLException, DaoException {

        Optional<User> user = userDao.findById(resultSet.getLong("user_id"));
        Optional<Product> product = productDao.findById(resultSet.getLong("product_id"));
        return Cart.builder().user(user.get())
                .product(product.get())
                .product_name(resultSet.getString("product_name"))
                .quantity(resultSet.getInt("quantity"))
                .price(resultSet.getBigDecimal("price").doubleValue())
                .build();
    }

    public Cart buildCartByProduct(long id, List<Product> goodsInBatch, Long userId) throws DaoException {
        List<Cart> list = goodsInBatch.stream().filter(product -> product.getId() == id)
                .map(product -> {
                    try {
                        return Cart.builder()
                                .user(userDao.findById(userId).get())
                                .product(product)
                                .product_name(product.getName())
                                .quantity(0)
                                .price(product.getPrice())
                                .build();
                    } catch (DaoException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        Cart cart = list.get(0);
        return save(cart);
    }
}

