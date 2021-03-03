package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Cart;
import ru.geekbrains.entity.Product;
import ru.geekbrains.service.CartService;
import ru.geekbrains.util.BadRequestException;
import ru.geekbrains.util.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
public class CartResource {

    private final CartService cartService;

    @Autowired
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Cart> findAll(){
        return cartService.getAllCarts();
    }

    @GetMapping(path = "/{id}")
    public Cart findById(@PathVariable("id") Long userId){
        Cart cart = cartService.getUserCart(userId);
        if(cart==null) {
            throw new NotFoundException();
        } else return  cart;
    }

    @PostMapping(path = "/{id}", consumes = "application/json")
    public Cart add(@PathVariable("id") Long userId, @RequestBody Product product){
        cartService.addToCart(userId, product);
        return cartService.getUserCart(userId);
    }

    @PutMapping("/{id}")
    public void remove(@PathVariable("id") Long userId, @RequestBody Product product){
        if(cartService.removeFromCart(userId, product)==null) throw new NotFoundException();
    }

    @DeleteMapping("/{id}")
    public void clear(@PathVariable("id") Long userId){
        if(cartService.clearCart(userId)==null) throw new NotFoundException();
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NotFoundException ex){
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> badRequestException(BadRequestException ex){
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

}
