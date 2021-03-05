package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.RoleRepository;
import ru.geekbrains.service.UserRepr;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService, RoleRepository roleRepository){
        this.userService = userService;
    }

    @GetMapping
    public String loginPage(Model model,
                            @RequestParam(value = "error" ,required = false) Optional<String> error,
                            @RequestParam(value = "logout", required = false) Optional<String> logout){

        String message = null;
        if(error.isPresent()) message = "Incorrect username or password";
        if(logout.isPresent()) message = "You've been successfully logged out";
        model.addAttribute("message", message);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new UserRepr());
        model.addAttribute("pathSegment", "login");
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

        userService.save(user);
        return "redirect:/login";
    }

}
