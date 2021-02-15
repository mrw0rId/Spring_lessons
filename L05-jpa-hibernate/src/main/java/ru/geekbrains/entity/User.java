package ru.geekbrains.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findAllAttributes", query = "select p from Product p inner join p.users u where u.id=:id"),
        @NamedQuery(name = "User.findAll", query = "select u from User u"),
        @NamedQuery(name = "User.findByName", query = "select u from User u where u.userName=:userName"),
        @NamedQuery(name = "User.deleteById", query = "delete from User u where u.id=:id"),
        @NamedQuery(name = "User.deleteByName", query = "delete from User u where u.userName=:userName")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, unique = true, nullable = false)
    private String userName;

    @Column(length = 512, nullable = false)
    private String password;

    @Column(nullable = false)
    private int age;

    @ManyToMany
    @JoinTable(
            name = "products_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )

    private List<Product> products;

    public User() {
    }

    public User(String userName, String password, int age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }

    public User(String userName, String password, int age, List<Product> products) {
        this.userName = userName;
        this.password = password;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        List<String> names = new ArrayList<>();
        getProducts().forEach(p->names.add(p.getProduct()));
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", products=" + names +
                '}';
    }
}
