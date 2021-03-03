package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entity.Product;


public final class ProductSpecification {

    public static Specification<Product> productNameLike(String productName) {
        return (root, query, cb) -> cb.like(root.get("productName"), "%" + productName + "%");
    }

    public static Specification<Product> minPrice(Integer minPrice) {
        return (root, query, cb) -> cb.ge(root.get("price"), minPrice);
    }

    public static Specification<Product> maxPrice(Integer maxPrice) {
        return (root, query, cb) -> cb.le(root.get("price"), maxPrice);
    }

}
