package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.Product;
import ru.geekbrains.entity.User;
import ru.geekbrains.persist.RepoFactory;
import ru.geekbrains.persist.RepositoryInterface;
import ru.geekbrains.util.RepoType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main06 {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        RepositoryInterface<User> userRepo = RepoFactory.createRepo(RepoType.USER_REMOTE, emFactory);
        RepositoryInterface<Product> prodRepo = RepoFactory.createRepo(RepoType.PRODUCT_REMOTE, emFactory);

//        //INSERT
//        Product p1 = new Product(123, "bred", "just bred");
//        Product p2 = new Product(234, "apple", "just apple");
//        Product p3 = new Product(345, "orange", "just orange");
//        Product p4 = new Product(765, "lemon", "just lemon");
//        Product p5 = new Product(900, "meat", "just meat");
//        prodRepo.insert(p1);
//        prodRepo.insert(p2);
//        prodRepo.insert(p3);
//        prodRepo.insert(p4);
//        prodRepo.insert(p5);
//
//        List<Product> pSet1 = Arrays.asList(p1,p2);
//        List<Product> pSet2 = Arrays.asList(p2,p3);
//        List<Product> pSet3 = Arrays.asList(p3,p4);
//
//
//        userRepo.insert(new User("Nick", "123", 43, pSet1));
//        userRepo.insert(new User("Jack", "123", 23, pSet2));
//        userRepo.insert(new User("Lorry", "123", 27, pSet3));

        //SELECT
        /*
        При попытке выполнить след. два селекта выдается Исключение:
            Exception in thread "main" java.lang.IllegalStateException: Session/EntityManager is closed

	    При этом если убрать закрытие EntityManager после каждой операции в блоке finally метода executeForEntityManager,
	    то селект работает, однако другие запросы выдают:
	        HibernateException: The internal connection pool has reached its maximum size.
         */
        System.out.println(userRepo.findAllAttributes(1));
//        System.out.println(prodRepo.findAllAttributes(2));

//        System.out.println(userRepo.findByName("Nick"));

//        System.out.println(userRepo.findAll());
    }
}
