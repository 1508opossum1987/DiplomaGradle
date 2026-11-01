package org.example.diplomagradle.service.block;

//пустой или состоящий из пробелов title
public class InvalidTitleException extends RuntimeException {
    public InvalidTitleException(String title) {
        super("This tittle: "+title+" must be valid");
    }
}
