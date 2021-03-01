package ru.geekbrains.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductRepr> findAllUsersByProductId(long id);

    List<ProductRepr> findAll();

    Optional<ProductRepr> findById(Long id);

    Optional<ProductRepr> findById(String id);

    List<ProductRepr> findByName(String productName);

    Page<ProductRepr> findWithFilter(String productName, Integer minPrice, Integer maxPrice,
                                     String sort, Integer page, Integer size);

    void save(ProductRepr product);

    void delete(long id);

    void deleteByName(String productName);

    void delete(String id);
}
