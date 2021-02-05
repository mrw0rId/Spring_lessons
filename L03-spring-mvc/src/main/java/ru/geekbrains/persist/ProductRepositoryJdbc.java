package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.controller.UserController;
import ru.geekbrains.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryJdbc implements RepositoryInterface<Product> {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Connection connection;

    public ProductRepositoryJdbc(Connection connection) {
        this.connection = connection;
        try {
            createTableIfNotExists(connection);
        }catch (SQLException e){
            logger.info("Failed to create Products table: " + e.getMessage());
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> res = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from products");
            while (rs.next()) {
                res.add(new Product(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
            }
        }
        return res;
    }

    @Override
    public Product findById(Long id) throws SQLException {
        return getProduct(id);
    }

    @Override
    public Product findById(String id) throws SQLException {
        long idd = Long.parseLong(id);
        return getProduct(idd);
    }

    private Product getProduct(long id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select * from products where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getString(4));
            }
        }
        return null;
    }

    @Override
    public void insert(Product product) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "insert into products(price, product, description) values (?, ?, ?);")) {
            stmt.setInt(1, product.getPrice());
            stmt.setString(2, product.getProduct());
            stmt.setString(3, product.getDescription());
            stmt.execute();
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        if(findById(product.getId()).getId()!=-1L) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "update products set (price, product, description) values (?, ?, ?) where id = ?;")) {
                stmt.setInt(1, product.getPrice());
                stmt.setString(2, product.getProduct());
                stmt.setString(3, product.getDescription());
                stmt.setLong(4, product.getId());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        if(findById(id).getId()!=-1L) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "delete from products where id = ?;")) {
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
            stmt.execute("create table if not exists products (\n" +
                    "    id int auto_increment primary key, \n" +
                    "    price int, \n" +
                    "    product varchar(25), \n" +
                    "    description varchar(256) \n" +
                    ");");
        }
    }
}
