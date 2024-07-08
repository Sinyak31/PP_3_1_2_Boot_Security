package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.exception_handling.UserIncorrectData;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;

    }


    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody @Valid UserDTO user) {
        userService.saveUserWithRoles(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/userAuth")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
        System.out.println(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> removeUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/users")
    public ResponseEntity<UserDTO> editUser(@RequestBody @Valid UserDTO user) {
        userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        if (userService.getUserById(id) == null) {
            throw new EntityNotFoundException("There is no user with id=" + id);
        }
       UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(EntityNotFoundException ex){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(ex.getMessage());
return new ResponseEntity<>(data,HttpStatus.NOT_FOUND);
    }
}
