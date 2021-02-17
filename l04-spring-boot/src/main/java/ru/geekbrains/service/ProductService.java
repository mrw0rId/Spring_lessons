package ru.geekbrains.service;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductRepr> findAllUsersByProductId(long id);

    List<ProductRepr> findAll();

    Optional<ProductRepr> findById(Long id);

    Optional<ProductRepr> findById(String id);

    List<ProductRepr> findByName(String userName);

    void save(ProductRepr user);

    void delete(long id);

    void deleteByName(String userName);

    void delete(String id);
}
