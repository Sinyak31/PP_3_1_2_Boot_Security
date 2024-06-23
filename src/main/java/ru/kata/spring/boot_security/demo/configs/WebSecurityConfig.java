package ru.kata.spring.boot_security.demo.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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

//    private final UserDetailServiceImp userDetailServiceImp;
//private final AuthProvider authProvider;
//
//    public WebSecurityConfig(AuthProvider authProvider) {
//        this.authProvider = authProvider;
//    }


//    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
//        this.successUserHandler = successUserHandler;
//    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceImp)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/auth/**", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(handler)
                .loginProcessingUrl("/process_login")
                .failureUrl("/auth/login?error");

    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}