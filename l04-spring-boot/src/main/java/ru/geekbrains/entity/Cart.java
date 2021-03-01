package ru.geekbrains.entity;

import java.util.List;

public class Cart {

    private Long id;

    private List<Product> products;

    public Cart() {
    }

    public Cart(Long id) {
        this.id = id;
    }

    public Cart(Long id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }
}
