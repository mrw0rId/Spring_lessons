package ru.geekbrains.service;

import ru.geekbrains.entity.Cart;
import ru.geekbrains.entity.Product;

import java.util.List;

public interface CartService {

    List<Cart> getAllCarts();

    Cart getUserCart(Long userId);

    void addToCart(Long userId, Product product);

    Cart removeFromCart(Long userId, Product product);

    Cart clearCart(Long userId);

}
