package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.UserRepr;
import ru.geekbrains.service.UserService;
import ru.geekbrains.util.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String usersPage(Model model, @RequestParam("usernameFilter") Optional<String> usernameFilter) {
        logger.info("Users page requested");

        List<UserRepr> users;
        if(usernameFilter.isPresent() && !usernameFilter.get().isBlank()){
            users = userService.findWithFilter(usernameFilter.get());
        } else users = userService.findAll();

        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model){
        logger.info("User {id} Edit page requested");
        model.addAttribute("user", userService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "user-form";
    }

    @GetMapping("/add")
    public String add(Model model){
        logger.info("User Create page requested");
        model.addAttribute("user", new UserRepr());
        return "user-form";
    }

    @PostMapping("/update")
    public String update(@Valid UserRepr user, BindingResult result, Model model) {

        if(result.hasErrors()){
            return "user-form";
        }

        if (!user.getPassword().equals(user.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            return "user-form";
        }

        logger.info("User update page requested");
        userService.save(user);
        return "redirect:/users";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        logger.info("Users delete page requested");
        userService.delete(id);
        return "redirect:/users";
    }
}
