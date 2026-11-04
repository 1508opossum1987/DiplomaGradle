package org.example.diplomagradle.service.block;

public class DuplicateTitleBlockException extends RuntimeException {
    public DuplicateTitleBlockException(String title) {
        super("Block with title: " + title + " already exists");
    }
}
