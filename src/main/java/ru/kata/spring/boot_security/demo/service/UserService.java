package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findByUsername(String name) {
        Optional<User> user = repository.findByUsername(name);
        return user;
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);
        return user;
    }


    public void deleteUser(long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void updateUser(User user) {
        User userUpdate = repository.findById(user.getId()).orElse(null);
        if(user.getRoleList() == null){
            user.setRoleList(Collections.singleton(new Role(1l, "ROLE_USER")));
        }
        user.setRoleList(user.getRoleList().stream()
                .map(role -> roleService.getByRole(role.getRole()))
                .collect(Collectors.toSet()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Transactional
    public void saveUserWithRoles(User user) {
        if (user.getRoleList() == null) {
            user.setRoleList(Collections.singleton(new Role(1l, "ROLE_USER")));
        }
        user.setRoleList(user.getRoleList().stream()
                .map(role -> roleService.getByRole(role.getRole()))
                .collect(Collectors.toSet()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

//        List<Role> roles = roleRepository.findAllById(roleIds);
//        savedUser.setRoleList(new HashSet<>(roles));
        repository.save(user);
    }


    public User getUserById(Long id) {
        return repository.getById(id);
    }
}
