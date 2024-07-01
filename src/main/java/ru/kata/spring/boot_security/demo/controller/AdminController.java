package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           @RequestParam("selectedRoles") List<Long> selectedRoles) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/AddUser";
        }
        userService.saveUserWithRoles(user, selectedRoles);

        return "redirect:/admin";
    }




    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/AddUser";
    }

    @GetMapping()
    public String getUsers(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("usersList", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "admin/Admin";
    }

    @PostMapping(value = "/remove")
    public String removeUser(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String showEditUser(Model model, @RequestParam(value = "id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/EditUser";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("user") @Valid User user
            , BindingResult bindingResult, @RequestParam("selectedRoles") List<Long> selectedRoles) {
        if (bindingResult.hasErrors()) {
            return "admin/EditUser";
        }
        userService.updateUser(user, selectedRoles);
        return "redirect:/admin";
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }
}
