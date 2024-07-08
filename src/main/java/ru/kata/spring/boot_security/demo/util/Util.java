package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Util {

    private final UserService userService;
private final RoleService roleService;

    public Util(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PostConstruct
    public void init() {
        // создаю роли
        Role roleUser = new Role(1L, "ROLE_USER");
        Role roleAdmin = new Role(2L, "ROLE_ADMIN");
        roleService.save(roleUser);
        roleService.save(roleAdmin);

        // для входа
        userService.saveUserWithRoles(new User("admin", "admin", 30, "234065", "admin@mail.com"),
                new HashSet<Role>(Set.of(roleAdmin)));
        System.out.println("добавилась роль " + roleAdmin);
        userService.saveUserWithRoles(new User("user", "user", 31, "234065", "user@mail.com"),
                new HashSet<Role>(Set.of(roleUser)));
    }
}