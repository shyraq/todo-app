package com.example.org.config;

import com.example.org.security.AuthProviderImpl;
import com.example.org.services.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthProviderImpl authProvider;
    private final UsersDetailsService usersDetailsService;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider, UsersDetailsService usersDetailsService) {
        this.authProvider = authProvider;
        this.usersDetailsService = usersDetailsService;
    }

    //Настраиваем аутентификацию
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    //Настраиваем Spring Security
    //Настраиваем авторизацию
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/error", "/auth/registration").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/tasks", true)
                        .failureUrl("/auth/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/auth/login")
                        .logoutUrl("/logout").permitAll());


        return http.build();

    }
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new P;
//    }
}
