package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

    public void updateUser(User user,List<Long> roleIds) {
        User userUpdate = repository.findById(user.getId()).orElse(null);
        userUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userUpdate.setUsername(user.getUserName());
        userUpdate.setSurname(user.getSurname());
        userUpdate.setAge(user.getAge());
        userUpdate.setEmail(user.getEmail());
        List<Role> roles = roleRepository.findAllById(roleIds);
        userUpdate.setRoleList(new HashSet<>(roles));
        repository.save(userUpdate);



    }

    public void saveUserWithRoles(User user, List<Long> roleIds) {

        User savedUser = repository.save(user);
        savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roles = roleRepository.findAllById(roleIds);
        savedUser.setRoleList(new HashSet<>(roles));
        repository.save(savedUser);
    }


    public User getUserById(Long id) {
        return repository.getById(id);
    }
}
