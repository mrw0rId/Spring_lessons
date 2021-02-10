package ru.geekbrains.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
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

    public User() {
    }

    public User(String userName, String password, int age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }

    public User(Long id, String userName, String password, int age) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.age = age;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
