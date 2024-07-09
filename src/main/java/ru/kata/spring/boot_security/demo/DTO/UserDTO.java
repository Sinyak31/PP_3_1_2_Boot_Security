package ru.kata.spring.boot_security.demo.DTO;

import lombok.*;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.validation.constraints.*;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@ToString
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

}
