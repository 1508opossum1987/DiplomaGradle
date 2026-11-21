package org.example.diplomagradle.service.role;

public class EmptyRoleNameException extends RuntimeException {
    public EmptyRoleNameException() {
        super ("The Role name is empty!");
    }
}
