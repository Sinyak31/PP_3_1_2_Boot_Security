package ru.kata.spring.boot_security.demo.DTO;

import ru.kata.spring.boot_security.demo.entity.Role;

import javax.validation.constraints.*;
import java.util.Set;

public class UserDTO {

    private Long id;

    @NotBlank(message = "Поле обязательное к заполнению")
    @Size(min = 2, max = 30, message = "Имя должно состоять от 2 до 30 символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Имя не может содержать цифры или символы")
    private String username;

    @NotBlank(message = "Поле обязательное к заполнению")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+(?:-[a-zA-Zа-яА-Я]+)?$", message = "Фамилия не может содержать цифры или символы кроме -")
    private String surname;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private int age;

    @Email
    private String email;

    @Size(min = 3, message = "Должно быть минимум 3 символа")
    private String password;

    private Set<Role> roleList;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
