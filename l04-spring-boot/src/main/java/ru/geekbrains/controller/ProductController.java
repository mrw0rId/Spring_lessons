package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.ProductRepr;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.util.NotFoundException;

import java.sql.SQLException;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public String productsPage(Model model){
        logger.info("product page requested");
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model){
        logger.info("product {id} Edit page requested");
        model.addAttribute("product", productService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "product-form";
    }

    @GetMapping("/add")
    public String add(Model model){
        logger.info("product Create page requested");
        model.addAttribute("product", new ProductRepr());
        return "product-form";
    }

    @PostMapping("/update")
    public String update(ProductRepr product) {
        productService.save(product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        logger.info("product delete page requested");
        productService.delete(id);
        return "redirect:/products";
    }
}
