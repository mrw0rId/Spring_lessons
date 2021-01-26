package listeners;

import persist.User;
import persist.UserRepositoryImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private UserRepositoryImpl repository;

    @Override
    public void contextInitialized(ServletContextEvent sce) {


        ServletContext sc = sce.getServletContext();
        repository = new UserRepositoryImpl();
        sc.setAttribute("userRepository", repository);

        repository.insert(new User("Nick", "manager", 35));
        repository.insert(new User("Lucy", "team-lead", 33));
        repository.insert(new User("John", "senior-developer", 32));
        repository.insert(new User("Mike", "senior-pomidor", 24));

    }
}
