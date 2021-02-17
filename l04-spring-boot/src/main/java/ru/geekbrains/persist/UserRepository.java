package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.Product;
import ru.geekbrains.entity.User;
import ru.geekbrains.service.UserRepr;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserName(String userName);

    @Query("select u from User u where u.userName like concat('%',:userName,'%') ")
    List<User> findUserByUserNameLike(@Param("userName") String userName);

    void deleteByUserName(String userName);

    @Query("select u from User u inner join u.products p where p.id=:id")
    List<User> findAllProductsByUserId (@Param("id") long id);

}
