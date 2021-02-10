package ru.geekbrains.persist;

import ru.geekbrains.entity.User;
import ru.geekbrains.util.UpdateType;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryJdbc implements RepositoryInterface<User> {


    private final EntityManager em;

    public UserRepositoryJdbc(EntityManager em) {
        this.em = em;

    }

    @Override
    public List<User> findAll() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findById(String id) {
        long idd = Long.parseLong(id);
        return this.findById(idd);
    }

    public List<User> findByName(String userName) {
        return em.createQuery("from User u where u.userName=:userName", User.class)
                .setParameter("userName", userName).getResultList();
    }

    @Override
    public void insert(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void update(String userName, Object newParameter, UpdateType updateType) {
        List<User> user = findByName(userName);
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
    }

    @Override
    public void delete(long id) {
        em.getTransaction().begin();
        if (findById((id)) != null) {
            em.createQuery("delete from User u where u.id=:id")
                    .setParameter("id", id).executeUpdate();
        } else throw new RuntimeException("This user doesn't exist");
        em.getTransaction().commit();
    }

    @Override
    public void deleteByName(String userName) {
        em.getTransaction().begin();
        if (!findByName(userName).isEmpty()) {
            em.createQuery("delete from User u where u.userName=:userName")
                    .setParameter("userName", userName).executeUpdate();
        } else throw new RuntimeException("That user doesn't exist");
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
