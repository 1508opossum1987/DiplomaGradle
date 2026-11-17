package org.example.diplomagradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DiplomaGradleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaGradleApplication.class, args);
    }

}