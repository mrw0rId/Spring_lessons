package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.entity.User;
import ru.geekbrains.persist.RoleRepository;
import ru.geekbrains.service.UserRepr;
import ru.geekbrains.service.UserService;
import ru.geekbrains.util.NotFoundException;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController{

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository){
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String usersPage(Model model,
                            @RequestParam("usernameFilter") Optional<String> usernameFilter,
                            @RequestParam("ageMinFilter") Optional<Integer> ageMinFilter,
                            @RequestParam("ageMaxFilter") Optional<Integer> ageMaxFilter,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            @RequestParam("sort") Optional<String> sort) {

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
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("pathSegment", "users");
        model.addAttribute("user", userService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "user-form";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("user", new UserRepr());
        model.addAttribute("pathSegment", "users");
        return "user-form";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("user") UserRepr user, BindingResult result, Model model) {
        model.addAttribute("roles",roleRepository.findAll());


        if(result.hasErrors()){
            return "user-form";
        }

        if (!user.getPassword().equals(user.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            return "user-form";
        }

        userService.save(user);
        return "redirect:/users";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else if(principal instanceof UserRepr){
            username = ((UserRepr)principal).getUserName();
        }else if(principal instanceof User){
            username = ((User)principal).getUserName();
        }
        UserRepr userRepr = userService.findById(id).orElseThrow(NotFoundException::new);
        if (username.equals(userRepr.getUserName())){
            userService.delete(id);
            SecurityContextHolder.getContext().setAuthentication(null);
            return "redirect:/products";
        } else {
            userService.delete(id);
            return "redirect:/users";
        }
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex){
        ModelAndView mav = new ModelAndView("not-found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}
