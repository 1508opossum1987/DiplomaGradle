package org.example.diplomagradle.service.role;

public class DuplicateRoleNameException extends RuntimeException {
    public DuplicateRoleNameException(String name) {
        super("This name: "+name+" is already exists!");
    }
}
