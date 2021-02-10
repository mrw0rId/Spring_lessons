package ru.geekbrains.entity;

import javax.persistence.*;

@Entity
@Table(name = "product")
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", product='" + product + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
