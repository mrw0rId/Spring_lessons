package ru.geekbrains.entity;

public class User {

    private Long id;
    private String userName;
    private String post;
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
