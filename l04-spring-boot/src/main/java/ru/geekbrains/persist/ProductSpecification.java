package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entity.Product;


public final class ProductSpecification {

    public static Specification<Product> productNameLike(String productName) {
        return (root, query, cb) -> cb.like(root.get("productName"), "%" + productName + "%");
    }

    public static Specification<Product> minAge(Integer minAge) {
        return (root, query, cb) -> cb.ge(root.get("age"), minAge);
    }

    public static Specification<Product> maxAge(Integer maxAge) {
        return (root, query, cb) -> cb.le(root.get("age"), maxAge);
    }

}
