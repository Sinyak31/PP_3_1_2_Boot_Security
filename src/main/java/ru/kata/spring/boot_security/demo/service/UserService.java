package ru.kata.spring.boot_security.demo.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RoleService roleService, ModelMapper modelMapper) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
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

    @Transactional
    public void deleteUser(long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void updateUser(UserDTO user) {
        User userUpdate = repository.findById(user.getId()).orElse(null);
        if (user.getRoleList() == null) {
            user.setRoleList(Collections.singleton(new Role(1l, "ROLE_USER")));
        }
        user.setRoleList(user.getRoleList().stream()
                .map(role -> roleService.getByRole(role.getRole()))
                .collect(Collectors.toSet()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(convertingFromUserDTOToUser(user));
    }

    @Transactional
    public void saveUserWithRoles(UserDTO user) {
        if (user.getRoleList() == null) {
            user.setRoleList(Collections.singleton(new Role(1l, "ROLE_USER")));
        }
        user.setRoleList(user.getRoleList().stream()
                .map(role -> roleService.getByRole(role.getRole()))
                .collect(Collectors.toSet()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(convertingFromUserDTOToUser(user));
    }

    @Transactional
    public void saveUserWithRoles(User user, Set<Role> roles) {
       user.setRoleList(roles);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       repository.save(user);
    }


    public UserDTO getUserById(Long id) {
        return convertingFromUserToUserDTO(repository.getById(id));
    }

    private User convertingFromUserDTOToUser(UserDTO userDTO) {
       return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertingFromUserToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
