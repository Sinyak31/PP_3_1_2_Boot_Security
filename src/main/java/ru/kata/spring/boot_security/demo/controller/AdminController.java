package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;

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
            return "admin/AddUser";
        }
        userService.saveUserWithRoles(user, selectedRoles);
        return "redirect:/admin/index";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/AddUser";
    }

    @GetMapping("/index")
    public String getUsers(Model model) {
        model.addAttribute("usersList", userService.findAll());
        return "admin/Users";
    }

    @GetMapping(value = "/remove")
    public String removeUser(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return "redirect:index";
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
        return "redirect:index";
    }
}
