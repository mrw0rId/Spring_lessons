package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entity.User;


public final class UserSpecification {

    public static Specification<User> userNameLike(String userName){
        return (root, query, cb) -> cb.like(root.get("userName"), "%" + userName + "%");
    }

    public static Specification<User> minAge(Integer minAge){
        return (root, query, cb) -> cb.ge(root.get("age"), minAge);
    }

    public static Specification<User> maxAge(Integer maxAge){
        return (root, query, cb) -> cb.le(root.get("age"), maxAge);
    }

}
