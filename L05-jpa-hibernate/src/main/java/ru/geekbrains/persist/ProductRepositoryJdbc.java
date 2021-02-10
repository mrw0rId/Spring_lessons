package ru.geekbrains.persist;

import ru.geekbrains.entity.Product;
import ru.geekbrains.util.UpdateType;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductRepositoryJdbc implements RepositoryInterface<Product> {

    private final EntityManager em;

    public ProductRepositoryJdbc(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    @Override
    public Product findById(String id) {
        long idd = Long.parseLong(id);
        return this.findById(idd);
    }

    public List<Product> findByName(String product) {
        return em.createQuery("from Product p where p.prodcut=:product", Product.class)
                .setParameter("product", product).getResultList();
    }

    @Override
    public void insert(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    @Override
    public void update(String userName, Object newParameter, UpdateType updateType) {

    }

    @Override
    public void delete(long id) {
        em.getTransaction().begin();
        if (findById((id)) != null) {
            em.createQuery("delete from Product p where p.id=:id")
                    .setParameter("id", id).executeUpdate();
        } else throw new RuntimeException("This product doesn't exist");
        em.getTransaction().commit();
    }

    @Override
    public void deleteByName(String product) {
        em.getTransaction().begin();
        if (!findByName(product).isEmpty()) {
            em.createQuery("delete from Product p where p.product=:product")
                    .setParameter("product", product).executeUpdate();
        } else throw new RuntimeException("That user don't exist");
        em.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        em.getTransaction().begin();
        long idd = Long.parseLong(id);
        this.delete(idd);
        em.getTransaction().commit();
    }
}
