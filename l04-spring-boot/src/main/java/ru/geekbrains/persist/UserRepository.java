package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findByUserName(String userName);

    Optional<User> findUserByUserName(String userName);

    void deleteByUserName(String userName);

    @Query("select u from User u inner join u.products p where p.id=:id")
    List<User> findAllProductsByUserId (@Param("id") long id);

}
