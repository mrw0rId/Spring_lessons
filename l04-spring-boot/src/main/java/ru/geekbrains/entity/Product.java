package ru.geekbrains.entity;

import ru.geekbrains.service.ProductRepr;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 128, nullable = false)
    private String productName;

    @Column(length = 1024)
    private String description;

    @ManyToMany(mappedBy = "products")
    private List<User> users;

    public Product() {
    }

    public Product(ProductRepr product) {
        this.id = product.getId();
        this.price = product.getPrice();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.users = product.getUsers();
    }

    public Product(BigDecimal price, String productName) {
        this.price = price;
        this.productName = productName;
    }

    public Product(BigDecimal price, String productName, String description) {
        this.price = price;
        this.productName = productName;
        this.description = description;
    }

    public Product(BigDecimal price, String productName, String description, List<User> users) {
        this.price = price;
        this.productName = productName;
        this.description = description;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String product) {
        this.productName = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        List<String> names = new ArrayList<>();
        getUsers().forEach(u -> names.add(u.getUserName()));
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", product='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", users=" + names +
                '}';
    }
}
