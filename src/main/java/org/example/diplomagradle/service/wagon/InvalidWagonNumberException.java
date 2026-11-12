package org.example.diplomagradle.service.wagon;

public class InvalidWagonNumberException extends RuntimeException  {
    public InvalidWagonNumberException(Long wagonNumber) {
        super("Bad number: "+wagonNumber);
    }
}
