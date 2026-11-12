package org.example.diplomagradle.service.wagon;

public class InvalidWagonIdException extends RuntimeException {
    public InvalidWagonIdException(Long wagonId) {
        super("This wagonId: "+wagonId+" isn't valid");
    }
}
