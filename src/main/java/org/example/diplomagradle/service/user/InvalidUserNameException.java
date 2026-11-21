package org.example.diplomagradle.service.user;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException(String username) {
        super("This username: "+username+" is invalid!");
    }
}
