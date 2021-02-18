package ru.geekbrains.service;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductRepr> findAllUsersByProductId(long id);

    List<ProductRepr> findAll();

    Optional<ProductRepr> findById(Long id);

    Optional<ProductRepr> findById(String id);

    List<ProductRepr> findByName(String productName);

    List<ProductRepr> filterByName(String productName);

    List<ProductRepr> sortByPriceUp(String productName);

    List<ProductRepr> sortByPriceDown(String productName);

    void save(ProductRepr product);

    void delete(long id);

    void deleteByName(String productName);

    void delete(String id);
}
