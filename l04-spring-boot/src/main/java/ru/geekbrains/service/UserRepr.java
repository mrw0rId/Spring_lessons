package ru.geekbrains.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.geekbrains.entity.Product;
import ru.geekbrains.entity.User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepr {

    private Long id;

    @NotEmpty
    private String userName;

    @Min(18L)
    @Max(120L)
    @NotNull
    private int age;

    @Email
    private String email;

    @NotEmpty
    private String password;

    @JsonIgnore
    @NotEmpty
    private String matchingPassword;

    @ManyToMany
    @JoinTable(
            name = "products_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public UserRepr() {
    }

    public UserRepr(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.age = user.getAge();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    public UserRepr(String userName, Integer age, String password, String matchingPassword, String email) {
        this.userName = userName;
        this.age = age;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }

    public UserRepr(String userName, Integer age, String password, String email, List<Product> products) {
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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
                ", matchingPassword='" + matchingPassword + '\'' +
                ", email='" + email + '\'' +
                ", products=" + names +
                '}';
    }
}
