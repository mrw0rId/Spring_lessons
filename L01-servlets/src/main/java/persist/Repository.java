package persist;

import java.util.ArrayList;
import java.util.List;

public interface Repository {

    List<User> findAll();

    User findById (Long id);

    void insert (User user);

    void update (User user);

    void delete(long id);
}
