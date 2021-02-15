package ru.geekbrains.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "Product.findAllAttributes", query = "select u from User u inner join u.products p where p.id=:id"),
        @NamedQuery(name = "Product.findAll", query = "select p from Product p"),
        @NamedQuery(name = "Product.findByName", query = "select p from Product p where p.product=:product"),
        @NamedQuery(name = "Product.deleteById", query = "delete from Product p where p.id=:id"),
        @NamedQuery(name = "Product.deleteByName", query = "delete from Product p where p.product=:product")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(length = 128, nullable = false)
    private String product;

    @Column(length = 1024)
    private String description;

    @ManyToMany(mappedBy = "products")
    private List<User> users;

    public Product() {
    }

    public Product(int price, String product) {
        this.price = price;
        this.product = product;
    }

    public Product(int price, String product, String description) {
        this.price = price;
        this.product = product;
        this.description = description;
    }

    public Product(int price, String product, String description, List<User> users) {
        this.price = price;
        this.product = product;
        this.description = description;
        this.users = users;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
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
        getUsers().forEach(u->names.add(u.getUserName()));
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", product='" + product + '\'' +
                ", description='" + description + '\'' +
                ", users=" + names +
                '}';
    }
}
