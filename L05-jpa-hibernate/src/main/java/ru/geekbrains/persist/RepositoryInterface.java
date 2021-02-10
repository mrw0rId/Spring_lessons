package ru.geekbrains.persist;

import ru.geekbrains.util.UpdateType;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryInterface<T> {

    List<T> findAll();

    T findById (Long id);

    T findById (String id);

    void insert (T entity);

    void update (String userName, Object newParameter, UpdateType updateType);

    void delete(long id);

    void delete(String id);

    void deleteByName(String name);
}
