package org.example.diplomagradle.service.role;

public class InvalidRoleNameException extends RuntimeException {
    public InvalidRoleNameException(String name) {
        super ("This name: "+name+" isn't correct!");
    }
}
