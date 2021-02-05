package ru.geekbrains.entity;

public class Product {

    private Long id;
    private int price;
    private String product;
    private String description;

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

    public Product(Long id, int price, String product, String description) {
        this.id = id;
        this.price = price;
        this.product = product;
        this.description = description;
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
}
