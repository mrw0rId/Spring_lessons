package ru.geekbrains.persist;

import ru.geekbrains.entity.Product;
import ru.geekbrains.util.UpdateType;

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

    Object findAllAttributes(long id);

    List<T> findByName(String product);
}
