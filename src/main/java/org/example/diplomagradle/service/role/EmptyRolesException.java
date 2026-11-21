package org.example.diplomagradle.service.role;

public class EmptyRolesException extends RuntimeException {
    public EmptyRolesException() {
      super("It is no found roles!");
    }
}
