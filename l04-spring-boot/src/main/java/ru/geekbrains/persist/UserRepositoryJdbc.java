package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJdbc implements RepositoryInterface<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryJdbc.class);

    private final Connection connection;

    public UserRepositoryJdbc(Connection connection){
        this.connection = connection;
        try {
            createTableIfNotExists(connection);
        }catch (SQLException e){
            logger.info("Failed to create Users table: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public List<User> findAll() throws SQLException {
        List<User> res = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                res.add(new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        }
        return res;
    }

    @Override
    public User findById(Long id) throws SQLException {
        return getUser(id);
    }

    @Override
    public User findById(String id) throws SQLException{
        long idd = Long.parseLong(id);
        return getUser(idd);
    }

    private User getUser(long id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select * from users where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        }
        return null;
    }

    @Override
    public void insert(User user) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "insert into users(userName, post, age) values (?, ?, ?);")) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPost());
            stmt.setInt(3, user.getAge());
            stmt.execute();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        if(findById(user.getId()).getId()!=-1L) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "update users set (userName, post, age) values (?, ?, ?) where id = ?;")) {
                stmt.setString(1, user.getUserName());
                stmt.setString(2, user.getPost());
                stmt.setInt(3, user.getAge());
                stmt.setLong(4, user.getId());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        if(findById(id).getId()!=-1L) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "delete from users where id = ?;")) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        long idd = Long.parseLong(id);
        this.delete(idd);
    }

    public void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists users (\n" +
                    "    id int auto_increment primary key, \n" +
                    "    userName varchar(25), \n" +
                    "    post varchar(25), \n" +
                    "    age int \n" +
                    ");");
        }
    }
}
