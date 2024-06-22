package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserDetailServiceImp implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public UserDetailServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
       Optional<User> user = userRepository.findByUsername(name);

        if(user.isEmpty()){
            throw  new UsernameNotFoundException("User not found");
        }
        User user1 = user.get();
        user1.getAuthorities();
        return user1 ;
        }
    }

