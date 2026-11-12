package org.example.diplomagradle.service.wagon;

public class DuplicateWagonNumberException extends RuntimeException {
    public DuplicateWagonNumberException(Long wagonNumber) {
        super("This wagonNumber: "+wagonNumber+" is already exists");
    }
}
