package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.entity.User;
import ru.geekbrains.persist.RepositoryInterface;

import java.sql.SQLException;

@Controller
@RequestMapping("/users")
public class UserController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private RepositoryInterface<User> userRepository;

    @Autowired
    public UserController(RepositoryInterface<User> userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public String usersPage(Model model) throws SQLException {
        logger.info("Users page requested");
        model.addAttribute("users", userRepository.findAll());
        return "user";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) throws SQLException {
        logger.info("User {id} Edit page requested");
        model.addAttribute("user", userRepository.findById(id));
        return "user-form";
    }

    @GetMapping("/add")
    public String add(Model model){
        logger.info("User Create page requested");
        model.addAttribute("user", new User(null,"","",0));
        return "user-form";
    }

    @PostMapping("/update")
    public String update(User user) throws SQLException {
        if(user.getId()!= null){
            logger.info("User update page requested");
            userRepository.update(user);
        } else userRepository.insert(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) throws SQLException {
        logger.info("Users delete page requested");
        userRepository.delete(id);
        return "redirect:/users";
    }
}
