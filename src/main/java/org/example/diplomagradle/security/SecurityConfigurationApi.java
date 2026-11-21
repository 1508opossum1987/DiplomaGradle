package org.example.diplomagradle.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurationApi {
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        //настройки конфигурации защиты api-методов
        httpSecurity.securityMatcher("/api/**")
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests(r->
                r.requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/user", "/api/user/**").authenticated()
                .requestMatchers("/api/user", "/api/user/**").hasRole("ADMIN")
                .requestMatchers(("/api/role/**")).hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/block", "/api/block/**").authenticated()
                .requestMatchers("/api/block","/api/block/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/metric", "/api/metric/**").authenticated()
                .requestMatchers("/api/metric","/api/metric/**").hasRole("ADMIN")
                .requestMatchers("/api/metric-value/**").authenticated()
                .requestMatchers("/api/metric-test-data/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );
        return httpSecurity.build();
    }
}
