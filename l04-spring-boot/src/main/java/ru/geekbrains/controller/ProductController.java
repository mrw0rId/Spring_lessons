package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.ProductRepr;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.util.NotFoundException;

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
                               @RequestParam(value = "priceFilter", required = false) Optional<String> priceFilter,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size) {
        logger.info("product page requested");

        Page<ProductRepr> products = productService.findWithFilter(
                productFilter.filter(s -> !s.isBlank()).orElse(null),
                priceFilter.filter(s -> !s.isBlank()).orElse(null),
                page.orElse(1) - 1,
                size.orElse(5)
        );

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

}
