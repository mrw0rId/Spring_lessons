import persist.User;
import persist.UserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/http-servlet/*")
public class FirstHttpServlet extends HttpServlet {

    private UserRepositoryImpl repo;

    @Override
    public void init() throws ServletException {
        repo = (UserRepositoryImpl) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getPathInfo().startsWith("/users")){
            List<User> users = repo.findAll();
            resp.getWriter().println("<h1>Список сотрудников:</h1>");
            resp.getWriter().println("<table>");
            resp.getWriter().println("<tr>\n" +
                    "    <th>Имя</th>\n" +
                    "    <th>Должность</th>\n" +
                    "    <th> Возраст</th>\n" +
                    "  </tr>");
            users.forEach(u -> {
                try {
                    resp.getWriter().println("<tr>\n" +
                            "    <td>"+u.getName()+"</td>\n" +
                            "    <td> "+u.getPosition()+"</td>\n" +
                            "    <td> "+u.getAge()+"</td>\n" +
                            "  </tr>");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            resp.getWriter().println("</table>");
        } else if(req.getPathInfo().startsWith("/user")){
            User user = repo.findById(req.getParameter("id"));
            resp.getWriter().println("<h1>Сотрудник: "+user.getName()+"</h1>");
            resp.getWriter().println("<p>Должность: "+user.getPosition()+"</p>");
            resp.getWriter().println("<p>Возраст: "+user.getAge()+"</p>");
        }
    }
}
