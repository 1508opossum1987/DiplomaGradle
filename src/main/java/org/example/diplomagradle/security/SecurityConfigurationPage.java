package org.example.diplomagradle.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurationPage {
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChainPage(HttpSecurity httpSecurity){
        httpSecurity
                .authorizeHttpRequests(r -> r
                .requestMatchers("/css/**", "/webjars/**").permitAll()
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/user", "/user/**").authenticated()
                .requestMatchers("/user", "/user/**").hasRole("ADMIN")
                .requestMatchers("/role/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/block", "/block/**").authenticated()
                .requestMatchers("/block", "/block/**").hasRole("ADMIN")
                .requestMatchers("/metric/*/edit/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/metric", "/metric/**").authenticated()
                .requestMatchers("/metric", "/metric/**").hasRole("ADMIN")
                .requestMatchers("/metric-test-data/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)
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
