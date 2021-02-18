package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.Product;
import ru.geekbrains.entity.User;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductName(String productName);

    void deleteByProductName(String productName);

    @Query("select p from Product p where p.productName like concat('%',:productName,'%') ")
    List<Product> findProductByProductNameLike(@Param("productName") String productName);

    @Query("select p from Product p where p.productName like concat('%',:productName,'%') order by p.price asc")
    List<Product> findProductByProductNameLikeOrderByPriceAsc(@Param("productName") String productName);

    @Query("select p from Product p where p.productName like concat('%',:productName,'%') order by p.price desc")
    List<Product> findProductByProductNameLikeOrderByPriceDesc(@Param("productName") String productName);

    @Query("select p from Product p inner join p.users u where u.id=:id")
    List <Product> findAllUsersByProductId (@Param("id") long id);
}
