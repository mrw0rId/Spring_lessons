package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.entity.Cart;
import ru.geekbrains.entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private Map<Long, Cart> usersCarts = new HashMap<>();

    @Override
    public List<Cart> getAllCarts() {
        return new ArrayList<>(usersCarts.values());
    }

    @Override
    public Cart getUserCart(Long userId) {
        return usersCarts.get(userId);
    }

    @Override
    public void addToCart(Long userId, Product product) {
        if(usersCarts.containsKey(userId)){
            usersCarts.get(userId).getProducts().add(product);
        } else {
            Cart cart = new Cart(userId, new ArrayList<>());
            cart.getProducts().add(product);
            usersCarts.put(userId,cart);
        }
    }

    @Override
    public Cart removeFromCart(Long userId, Product product) {
        if(usersCarts.containsKey(userId)){
            usersCarts.get(userId).getProducts().remove(product);
            return usersCarts.get(userId);
        } else return null;
    }

    @Override
    public Cart clearCart(Long userId) {
        if(usersCarts.containsKey(userId)){
            usersCarts.get(userId).getProducts().clear();
            return usersCarts.get(userId);
        } else return null;
    }


    public Map<Long, Cart> getUsersCarts() {
        return usersCarts;
    }
    public void setUsersCarts(Map<Long, Cart> usersCarts) {
        this.usersCarts = usersCarts;
    }
}
