package org.example.diplomagradle.service.user;

public class EmptyUsersException extends RuntimeException {
    public EmptyUsersException() {
        super("It is no found users!");
    }
}
