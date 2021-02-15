package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.util.RepoType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class RepoFactory {

    private static final Logger logger = LoggerFactory.getLogger(RepoFactory.class);

    public static RepositoryInterface createRepo(RepoType type, EntityManagerFactory emFactory) {
        RepositoryInterface repo = null;
        switch (type) {
            case USER_REMOTE:
                    repo = new UserRepositoryJdbc(emFactory);
                break;
            case PRODUCT_REMOTE:
                    repo = new ProductRepositoryJdbc(emFactory);
                break;
        }
        return repo;
    }
}
