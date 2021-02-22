package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entity.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<ProductRepr> findAllUsersByProductId(long id) {
        return productRepo.findAllUsersByProductId(id)
                .stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRepr> findAll() {
        return productRepo.findAll()
                .stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<ProductRepr> findById(Long id) {
        return productRepo.findById(id)
                .map(ProductRepr::new);
    }

    @Transactional
    @Override
    public Optional<ProductRepr> findById(String id) {
        long idd = Long.parseLong(id);
        return productRepo.findById(idd)
                .map(ProductRepr::new);
    }

    @Transactional
    @Override
    public List<ProductRepr> findByName(String userName) {
        return productRepo.findByProductName(userName)
                .stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductRepr> findWithFilter(String productName, String sort,
                                            Integer page, Integer size) {
        Specification<Product> spec = Specification.where(null);
        Page<ProductRepr> products;

        if (productName != null && !productName.isBlank()) {
            spec = spec.and(ProductSpecification.productNameLike(productName));
        }
        products = productRepo.findAll(spec, PageRequest.of(page, size))
                .map(ProductRepr::new);

        if (sort != null && !sort.isBlank()) {
            products = ProductSpecification.sortByPrice(products, sort);
        }
        return products;
    }

    @Transactional
    public void save(ProductRepr product) {
        productRepo.save(new Product(product));
    }

    @Transactional
    @Override
    public void delete(long id) {
        productRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByName(String userName) {
        productRepo.deleteByProductName(userName);
    }

    @Transactional
    @Override
    public void delete(String id) {
        long idd = Long.parseLong(id);
        productRepo.deleteById(idd);
    }
}