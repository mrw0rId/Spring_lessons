package ru.geekbrains.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entity.Product;
import ru.geekbrains.service.ProductRepr;

import java.util.Comparator;
import java.util.stream.Collectors;


public final class ProductSpecification {

    public static Page<ProductRepr> sortByPrice(Page<ProductRepr> products, String sort){
        switch (sort){
            case "1":
                return new PageImpl<>(products.toList().stream()
                        .sorted(Comparator.comparing(ProductRepr::getPrice))
                        .collect(Collectors.toList())
                );
            case "2":
                return new PageImpl<>(products.toList().stream()
                        .sorted(Comparator.comparing(ProductRepr::getPrice).reversed())
                        .collect(Collectors.toList())
                );
        }
        return null;
    }

    public static Specification<Product> productNameLike(String productName){
        return (root, query, cb) -> cb.like(root.get("productName"), "%" + productName + "%");
    }

    public static Specification<Product> minAge(Integer minAge){
        return (root, query, cb) -> cb.ge(root.get("age"), minAge);
    }

    public static Specification<Product> maxAge(Integer maxAge){
        return (root, query, cb) -> cb.le(root.get("age"), maxAge);
    }

}
