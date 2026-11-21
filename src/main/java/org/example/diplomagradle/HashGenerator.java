package org.example.diplomagradle;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//временный класс для хеширования паролей
public class HashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "norm_eng"; // пароль для хеширования
        String hash = encoder.encode(rawPassword);
        System.out.println(hash);
    }
}