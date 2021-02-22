package ru.geekbrains.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserRepr> findAllProductsByUserId(long id);

    List<UserRepr> findAll();

    Optional<UserRepr> findById(Long id);

    Optional<UserRepr> findById(String id);

    List<UserRepr> findByName(String userName);

    Page<UserRepr> findWithFilter(String userName, Integer minAge, Integer maxAge,
                                  Integer page, Integer size);

    void save(UserRepr user);

    void delete(long id);

    void deleteByName(String userName);

    void delete(String id);
}
