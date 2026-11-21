package org.example.diplomagradle.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurationPage {
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChainPage(HttpSecurity httpSecurity){
        httpSecurity
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/css/**", "/webjars/**").permitAll()
                        .requestMatchers("/").authenticated()
                        .requestMatchers("/block/**").authenticated()
                        .requestMatchers("/metric/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // сервис для работы с пользователями
    // реализованный в виде in-memory заглушки
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // подготовим тестовых пользователей через встроенные типы
//        UserDetails user = User.builder()
//            .username("user")
//            .password("qwerty")
//            .passwordEncoder(passwordEncoder()::encode)
//            .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("qwerty")
//                .passwordEncoder(passwordEncoder()::encode)
//                .roles("ADMIN")
//                .build();
//
//        // подготовка тестовой имплементации UserDetailsService
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
