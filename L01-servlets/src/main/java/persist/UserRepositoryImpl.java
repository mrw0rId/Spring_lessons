package persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepositoryImpl implements Repository {

    private Map<Long,User> userMap = new ConcurrentHashMap<>();

    private AtomicLong identity = new AtomicLong(0);

    public List<User> findAll(){
        return  new ArrayList<>(userMap.values());
    }

    public User findById (Long id){
        return userMap.get(id);
    }
    public User findById (String id){
        long idd = Long.parseLong(id);
        return userMap.get(idd);
    }

    public void insert (User user){
        long id = identity.incrementAndGet();
        user.setId(id);
        userMap.put(id, user);
    }

    public void update (User user){

    }

    public void delete(long id){
        userMap.remove(id);
    }

    public Map<Long, User> getUserMap() {
        return userMap;
    }
}
