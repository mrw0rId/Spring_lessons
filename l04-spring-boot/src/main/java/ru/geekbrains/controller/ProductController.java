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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productsPage(Model model,
                               @RequestParam("productFilter") Optional<String> productFilter,
                               @RequestParam(value = "priceFilter", required = false) Optional<String> priceFilter) {
        logger.info("product page requested");

        List<ProductRepr> products;
        if (productFilter.isPresent() && !productFilter.get().isBlank()) {
            if (priceFilter.isPresent() && !priceFilter.get().isBlank()) {
                products
                        = filterByPrice(productFilter.get(), priceFilter.get());
            } else
                products
                        = productService
                        .filterByName(productFilter.get());
        } else if (priceFilter.isPresent() && !priceFilter.get().isBlank()) {
            products
                    = filterByPrice(productFilter.get(), priceFilter.get());
        } else products
                = productService.findAll();

        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        logger.info("product {id} Edit page requested");
        model.addAttribute("product", productService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "product-form";
    }

    @GetMapping("/add")
    public String add(Model model) {
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

    public List<ProductRepr> filterByPrice(String productFilter, String priceFilter) {
        switch (priceFilter) {
            case "1":
                return productService
                        .sortByPriceUp(productFilter);
            case "2":
                return productService
                        .sortByPriceDown(productFilter);
        }
        return null;
    }
}
