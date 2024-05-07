package com.kostrikov.servletmaven.dao;

import com.kostrikov.servletmaven.entity.User;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String GET_BY_LOGIN_AND_PASSWORD_SQL = "SELECT * FROM users WHERE login = ? AND password = ?";
    private static final String SAVE_NEW_USER_SQL = "INSERT  INTO users(name, age, email, login, password) VALUES (?, ?, ?, ?,?)";
    private static final String GET_BY_LOGIN_SQL = "SELECT * FROM users WHERE login = ?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE users SET name=?, email=?, age=? WHERE id=?";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(User user) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setLong(4, user.getId());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean isUserFoundByLogin(String login) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_BY_LOGIN_SQL)) {
            preparedStatement.setString(1, login);
            var result = preparedStatement.executeQuery();

            return result.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User save(User user) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_NEW_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, user.getName());
            preparedStatement.setObject(2, user.getAge());
            preparedStatement.setObject(3, user.getEmail());
            preparedStatement.setObject(4, user.getLogin());
            preparedStatement.setObject(5, user.getPwd());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            user.setId(generatedKeys.getLong("id"));
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findByLoginAndPassword(String login, String pwd) throws DaoException {
        User user = null;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_BY_LOGIN_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pwd);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getObject("name", String.class))
                .age(resultSet.getObject("age", Integer.class))
                .email(resultSet.getObject("email", String.class))
                .login(resultSet.getObject("login", String.class))
                .pwd(resultSet.getObject("password", String.class))
                .build();
    }
}
