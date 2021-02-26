package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.service.UserRepr;
import ru.geekbrains.service.UserService;
import ru.geekbrains.util.NotFoundException;

import javax.validation.Valid;
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
    public String usersPage(Model model,
                            @RequestParam("usernameFilter") Optional<String> usernameFilter,
                            @RequestParam("ageMinFilter") Optional<Integer> ageMinFilter,
                            @RequestParam("ageMaxFilter") Optional<Integer> ageMaxFilter,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            @RequestParam("sort") Optional<String> sort) {

        logger.info("Users page requested");

        Page<UserRepr> users = userService.findWithFilter(
                usernameFilter.orElse(null),
                ageMinFilter.orElse(null),
                ageMaxFilter.orElse(null),
                page.orElse(1) - 1,
                size.orElse(5),
                sort.orElse(null)
        );

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
    public String update(@Valid @ModelAttribute("user") UserRepr user, BindingResult result, Model model) {

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

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex){
        ModelAndView mav = new ModelAndView("not-found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}
