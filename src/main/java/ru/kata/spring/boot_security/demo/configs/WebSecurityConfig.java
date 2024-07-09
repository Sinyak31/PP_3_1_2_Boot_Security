package ru.kata.spring.boot_security.demo.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceImp;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //    private final SuccessUserHandler successUserHandler;
    private final UserDetailServiceImp userDetailServiceImp;
    private final SuccessUserHandler handler;

    @Autowired
    public WebSecurityConfig(UserDetailServiceImp userDetailServiceImp, SuccessUserHandler handler) {
        this.userDetailServiceImp = userDetailServiceImp;
        this.handler = handler;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceImp)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/login", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/process_login")
                .usernameParameter("email")
                .failureUrl("/auth/login?error")
                .successHandler(handler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();



    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}