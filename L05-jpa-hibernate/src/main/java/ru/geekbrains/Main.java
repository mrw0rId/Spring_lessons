package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.User;
import ru.geekbrains.persist.RepoFactory;
import ru.geekbrains.persist.RepositoryInterface;
import ru.geekbrains.util.RepoType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        EntityManager em = emFactory.createEntityManager();

        RepositoryInterface<User> userRepo = RepoFactory.createRepo(RepoType.USER_REMOTE, em);
        //INSERT
//        userRepo.insert(new User("Nick", "123", 43));
//        userRepo.insert(new User("Jack", "123", 23));
//        userRepo.insert(new User("Lorry", "123", 27));
        userRepo.insert(new User("Barbara", "123", 34));
        //UPDATE
//        userRepo.update("Nick", "Flash", UpdateType.USER_NAME);
//        userRepo.update("Jack", "321", UpdateType.PASSWORD);
//        userRepo.update("Lorry", 100, UpdateType.AGE);
        //DELETE
//        userRepo.deleteByName("Barbara");

        //SELECT
        userRepo.findAll().forEach(System.out::println);
    }
}
