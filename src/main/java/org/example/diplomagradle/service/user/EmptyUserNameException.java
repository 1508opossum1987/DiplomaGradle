package org.example.diplomagradle.service.user;

public class EmptyUserNameException extends RuntimeException {
    public EmptyUserNameException() {
        super("The username is empty!");
    }
}
