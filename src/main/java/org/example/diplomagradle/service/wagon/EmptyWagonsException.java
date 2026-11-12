package org.example.diplomagradle.service.wagon;

public class EmptyWagonsException extends RuntimeException {
    public EmptyWagonsException() {
        super("Empty list of wagons");
    }
}
