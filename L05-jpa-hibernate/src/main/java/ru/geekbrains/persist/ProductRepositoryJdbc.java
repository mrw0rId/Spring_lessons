package ru.geekbrains.persist;

import ru.geekbrains.entity.Product;
import ru.geekbrains.entity.User;
import ru.geekbrains.util.UpdateType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductRepositoryJdbc implements RepositoryInterface<Product> {

    private EntityManagerFactory emFactory;

    public ProductRepositoryJdbc(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    @Override
    public List<User> findAllAttributes(long id) {
        return executeForEntityManager(
                em->em.createNamedQuery("Product.findAllAttributes",User.class))
                .setParameter("id",id)
                .getResultList();
    }

    @Override
    public List<Product> findAll() {
        return executeForEntityManager(
                em -> em.createNamedQuery("Product.findAll", Product.class).getResultList()
        );
    }

    @Override
    public Product findById(Long id) {
        return executeForEntityManager(
                em -> em.find(Product.class, id)
        );
    }

    @Override
    public Product findById(String id) {
        long idd = Long.parseLong(id);
        return this.findById(idd);
    }

    @Override
    public List<Product> findByName(String product) {
        return executeForEntityManager(
                em -> em.createNamedQuery("Product.findByName", Product.class)
                        .setParameter("product", product).getResultList()
        );
    }

    @Override
    public void insert(Product product) {
        executeInTransaction(em -> em.persist(product));
    }

    @Override
    public void update(String product, Object newParameter, UpdateType updateType) {
        EntityManager em = emFactory.createEntityManager();
        List<Product> products = findByName(product);
        try {
            em.getTransaction().begin();

            switch (updateType) {
                case PRICE:
                    products.forEach(p -> p.setPrice((Integer) newParameter));
                    break;
                case PRODUCT:
                    products.forEach(p -> p.setProduct((String) newParameter));
                    break;
                case DESCRIPTION:
                    products.forEach(p -> p.setDescription((String) newParameter));
                    break;
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void delete(long id) {
        executeInTransaction(em -> {
            if (findById((id)) != null) {
                em.createNamedQuery("Product.deleteById", Product.class)
                        .setParameter("id", id).executeUpdate();
            } else throw new RuntimeException("This product doesn't exist");
        });
    }

    @Override
    public void deleteByName(String product) {
        executeInTransaction(em -> {
            if (!findByName(product).isEmpty()) {
                em.createNamedQuery("Product.deleteByName", Product.class)
                        .setParameter("product", product).executeUpdate();
            } else throw new RuntimeException("That product don't exist");
        });
    }

    @Override
    public void delete(String id) {
        long idd = Long.parseLong(id);
        this.delete(idd);
    }

    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
