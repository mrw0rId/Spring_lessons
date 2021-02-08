package ru.geekbrains.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.geekbrains.controller.UserController;

import java.sql.Connection;
import java.sql.DriverManager;

//@Component
public class DBConnection {

    private final String url = "jdbc:mysql://localhost:3306/network_chat?createDatabaseIfNotExist=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String password = "Twodaysago1!";

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Connection connection;

    public DBConnection() {
        Connection connection1;
        try {
            connection1 = DriverManager.getConnection(url, dbUser, password);
        } catch (Exception e) {
            logger.info("Database connection error:" + e.getMessage());
            connection1 = null;
        }
        connection = connection1;
        if(connection==null) logger.info("CONNECTION IS NOT CREATED");
    }

    public Connection getConnection() {
        return connection;
    }
}
