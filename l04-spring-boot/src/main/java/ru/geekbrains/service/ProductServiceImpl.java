package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entity.Product;
import ru.geekbrains.persist.ProductRepository;

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
    public List<ProductRepr> filterByName(String productName) {
        return productRepo.findProductByProductNameLike(productName)
                .stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRepr> sortByPriceUp(String productName) {
        return productRepo.findProductByProductNameLikeOrderByPriceAsc(productName)
                .stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRepr> sortByPriceDown(String productName) {
        return productRepo.findProductByProductNameLikeOrderByPriceDesc(productName)
                .stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
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
