package ru.geekbrains.entity;

import ru.geekbrains.service.UserRepr;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 512, nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "products_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public User() {
    }

    public User(UserRepr user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.age = user.getAge();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.products = user.getProducts();
    }

    public User(String userName, Integer age, String password, String email) {
        this.userName = userName;
        this.age = age;
        this.password = password;
        this.email = email;
    }

    public User(String userName, Integer age, String password, String email, List<Product> products) {
        this.userName = userName;
        this.age = age;
        this.password = password;
        this.email = email;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String post) {
        this.password = post;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String age) {
        this.email = age;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        List<String> names = new ArrayList<>();
        getProducts().forEach(p -> names.add(p.getProductName()));
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", products=" + names +
                '}';
    }
}
