package com.kostrikov.servletmaven.dao;

import com.kostrikov.servletmaven.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    boolean update(E e) throws DaoException;

    List<E> findAll() throws DaoException;

    Optional<E> findById(K id) throws DaoException;

    E save(E entity) throws DaoException;

    boolean delete(K id) throws DaoException;
}
