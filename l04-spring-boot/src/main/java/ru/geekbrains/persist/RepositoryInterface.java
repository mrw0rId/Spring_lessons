package ru.geekbrains.persist;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryInterface<T> {

    List<T> findAll() throws SQLException;

    T findById (Long id) throws SQLException;

    T findById (String id) throws SQLException;

    void insert (T entity) throws SQLException;

    void update (T entity) throws SQLException;

    void delete(long id) throws SQLException;

    void delete(String id) throws SQLException;
}
