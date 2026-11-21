package org.example.diplomagradle.service.user;

public class DuplicateUserNameException extends RuntimeException {
    public DuplicateUserNameException(String username) {
        super("User with this username: "+username+" is already exists");
    }
}
