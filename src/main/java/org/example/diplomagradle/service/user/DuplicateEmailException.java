package org.example.diplomagradle.service.user;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("User with email '" + email + "' already exists");
    }
}
