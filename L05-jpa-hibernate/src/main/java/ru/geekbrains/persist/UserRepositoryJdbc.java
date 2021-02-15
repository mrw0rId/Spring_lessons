package ru.geekbrains.persist;

import ru.geekbrains.entity.Product;
import ru.geekbrains.entity.User;
import ru.geekbrains.util.UpdateType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserRepositoryJdbc implements RepositoryInterface<User> {


    private EntityManagerFactory emFactory;

    public UserRepositoryJdbc(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    @Override
    public List<Product> findAllAttributes(long id) {
        return executeForEntityManager(
                em->em.createNamedQuery("User.findAllAttributes",Product.class))
                .setParameter("id",id)
                .getResultList();
    }

    @Override
    public List<User> findAll() {
        return executeForEntityManager(
                em -> em.createNamedQuery("User.findAll", User.class).getResultList()
        );
    }

    @Override
    public User findById(Long id) {
        return executeForEntityManager(
                em -> em.find(User.class, id)
        );
    }

    @Override
    public User findById(String id) {
        long idd = Long.parseLong(id);
        return this.findById(idd);
    }

    @Override
    public List<User> findByName(String userName) {
        return executeForEntityManager(
                em -> em.createNamedQuery("User.findByName", User.class)
                        .setParameter("userName", userName).getResultList()
        );
    }

    @Override
    public void insert(User user) {
        executeInTransaction(em -> em.persist(user));
    }

    @Override
    public void update(String userName, Object newParameter, UpdateType updateType) {
        EntityManager em = emFactory.createEntityManager();
        List<User> user = findByName(userName);
        try {
            em.getTransaction().begin();

            switch (updateType) {
                case USER_NAME:
                    user.forEach(u -> u.setUserName((String) newParameter));
                    break;
                case PASSWORD:
                    user.forEach(u -> u.setPassword((String) newParameter));
                    break;
                case AGE:
                    user.forEach(u -> u.setAge((Integer) newParameter));
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
                em.createNamedQuery("User.deleteById", User.class)
                        .setParameter("id", id).executeUpdate();
            } else throw new RuntimeException("This user doesn't exist");
        });
    }

    @Override
    public void deleteByName(String userName) {
        executeInTransaction(em -> {
            if (!findByName(userName).isEmpty()) {
                em.createNamedQuery("User.deleteByName", User.class)
                        .setParameter("userName", userName).executeUpdate();
            } else throw new RuntimeException("That user don't exist");
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
