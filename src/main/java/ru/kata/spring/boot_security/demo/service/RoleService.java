package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repository;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll(){
        return repository.findAll();
    }
public Role getByRole(String role){
        return roleRepository.getByRole(role);
}

public Role save(Role role){
        return repository.save(role);
}
}
