package ru.geekbrains.server;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import ru.geekbrains.server.auth.AuthService;
import ru.geekbrains.server.auth.AuthServiceImpl;
import ru.geekbrains.server.auth.AuthServiceJdbcImpl;
import ru.geekbrains.server.persistance.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class AppConfig {


    @Bean(name = "connection")
    public Connection connection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/network_chat?" +
                            "createDatabaseIfNotExist=true&characterEncoding=UTF-8&serverTimezone=UTC",
                    "root", "Twodaysago1!");
        } catch (SQLException e) {
            System.out.println("Database connection error:" + e.getMessage());
            return null;
        }
        return conn;
    }

    @Bean(name = "userRepository")
    public UserRepository userRepository(Connection connection) {
        try {
            return new UserRepository(connection);
        } catch (SQLException e) {
            System.out.println("Remote repository SQL error: " + e.getMessage());
            return null;
        } catch (NullPointerException n) {
            System.out.println("No connection");
            return null;
        }
    }

    @Bean(name = "authService")
    public AuthService authService(UserRepository userRepository) {
        try {
            userRepository.getAllUsers();
        } catch (Exception e) {
            System.out.println("Initializing local repository");
            return new AuthServiceImpl();
        }
        System.out.println("Initializing remote repository");
        return new AuthServiceJdbcImpl(userRepository);
    }

    @Bean(name = "chatServer")
    public ChatServer chatServer(AuthService authService) {
        return new ChatServer(authService);
    }


}



