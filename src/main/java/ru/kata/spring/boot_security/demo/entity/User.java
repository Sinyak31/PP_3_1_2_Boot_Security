package ru.kata.spring.boot_security.demo.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Поле обязательное к заполнению")
    @Size(min = 2, max = 30, message = "Имя должно состоять от 2 до 30 символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Имя не может содержать цифры или символы")
    private String username;
    @NotBlank(message = "Поле обязательное к заполнению")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+(?:-[a-zA-Zа-яА-Я]+)?$", message = "Фамилия не может содержать цифры или символы кроме -")
    private String surname;
    @Column(name = "age")
    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private int age;

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @Column(name = "password")
    @Size(min = 3, message = "Должно быть минимум 3 символа")
    private String password;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roleList;


    public User(String username, String surname, int age, String password, String email) {
        this.username = username;
        this.surname = surname;
        this.age = age;
        this.password = password;
        this.email = email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
