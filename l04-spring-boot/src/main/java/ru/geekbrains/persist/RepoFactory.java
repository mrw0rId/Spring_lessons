package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.geekbrains.controller.UserController;
import ru.geekbrains.util.RepoType;

import java.sql.Connection;

//@Component
public class RepoFactory {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public RepositoryInterface createRepo(RepoType type, Connection connection) {
        RepositoryInterface repo = null;
        switch (type) {
            case USER_LOCAL:
                repo = new UserRepositoryImpl();
                break;
            case USER_REMOTE:
                    repo = new UserRepositoryJdbc(connection);
                break;
            case PRODUCT_LOCAL:
                repo = new ProductRepositoryImpl();
                break;
            case PRODUCT_REMOTE:
                    repo = new ProductRepositoryJdbc(connection);
                break;
        }
        return repo;
    }
}
