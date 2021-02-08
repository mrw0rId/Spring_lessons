package ru.geekbrains.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {

    private Long id;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String post;
    @NotNull
    @Min(18L)
    private int age;

    public User(){

    }
    public User(String userName, String post, int age) {
        this.userName = userName;
        this.post = post;
        this.age = age;
    }

    public User(Long id, String userName, String post, int age) {
        this.id = id;
        this.userName = userName;
        this.post = post;
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
