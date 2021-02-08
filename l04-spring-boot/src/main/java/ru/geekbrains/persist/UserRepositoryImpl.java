package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements RepositoryInterface<User> {

    private Map<Long,User> userMap = new ConcurrentHashMap<>();

    private AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init(){
        this.insert(new User("Nick", "manager", 35));
        this.insert(new User("Lucy", "team-lead", 33));
        this.insert(new User("John", "senior-developer", 32));
        this.insert(new User("Mike", "senior-pomidor", 24));
    }

    @Override
    public List<User> findAll(){
        return  new ArrayList<>(userMap.values());
    }

    @Override
    public User findById (Long id){
        return userMap.get(id);
    }

    @Override
    public User findById (String id){
        long idd = Long.parseLong(id);
        return userMap.get(idd);
    }

    @Override
    public void insert (User user){
        long id = identity.incrementAndGet();
        user.setId(id);
        userMap.put(id, user);
    }

    @Override
    public void update (User user){
        userMap.put(user.getId(), user);
    }

    @Override
    public void delete(long id){
        userMap.remove(id);
    }

    @Override
    public void delete(String id){
        long idd = Long.parseLong(id);
        userMap.remove(idd);
    }

}
