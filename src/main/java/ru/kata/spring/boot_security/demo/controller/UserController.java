package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceImp;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Optional;


@RequestMapping(value = "/users")
@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/show")
    public String showUser(Model model, Principal principal) {
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        Optional<User> user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("roles", roleService.findAll());
        return "user/showUser";
    }


}
