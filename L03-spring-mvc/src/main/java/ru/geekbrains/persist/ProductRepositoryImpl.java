package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepositoryImpl implements RepositoryInterface<Product> {

    private Map<Long,Product> productMap = new ConcurrentHashMap<>();

    private AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init(){
        this.insert(new Product(1000, "headphones", "best price-capabilities balanced headphones"));
        this.insert(new Product(500, "charger", "15W charger"));
        this.insert(new Product(2500, "case", "best glass-back phone protection case"));
        this.insert(new Product(10000, "monitor", "cheapest but not worst"));
    }

    @Override
    public List<Product> findAll() {
        return  new ArrayList<>(productMap.values());
    }

    @Override
    public Product findById(Long id) {
        return productMap.get(id);
    }

    @Override
    public Product findById (String id){
        long idd = Long.parseLong(id);
        return productMap.get(idd);
    }

    @Override
    public void insert(Product product) {
        long id = identity.incrementAndGet();
        product.setId(id);
        productMap.put(id, product);
    }

    @Override
    public void update(Product product) {
        productMap.put(product.getId(), product);
    }

    @Override
    public void delete(long id) {
        productMap.remove(id);
    }

    @Override
    public void delete(String id){
        long idd = Long.parseLong(id);
        productMap.remove(idd);
    }
}
