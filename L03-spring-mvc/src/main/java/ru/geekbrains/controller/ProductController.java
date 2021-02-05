package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.entity.Product;
import ru.geekbrains.persist.RepositoryInterface;

import java.sql.SQLException;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private RepositoryInterface<Product> productRepository;

    @Autowired
    public ProductController(RepositoryInterface<Product> productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping
    public String productsPage(Model model) throws SQLException {
        logger.info("product page requested");
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) throws SQLException {
        logger.info("product {id} Edit page requested");
        model.addAttribute("product", productRepository.findById(id));
        return "product-form";
    }

    @GetMapping("/add")
    public String add(Model model){
        logger.info("product Create page requested");
        model.addAttribute("product", new Product(null,0,"",""));
        return "product-form";
    }

    @PostMapping("/update")
    public String update(Product product) throws SQLException {
        if(product.getId()!= null){
            logger.info("product update page requested");
            productRepository.update(product);
        } else productRepository.insert(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) throws SQLException {
        logger.info("product delete page requested");
        productRepository.delete(id);
        return "redirect:/products";
    }
}
