package org.example.diplomagradle.service.block;

//пустой или состоящий из пробелов title
public class InvalidTitleBlockException extends RuntimeException {
    public InvalidTitleBlockException(String title) {
        super("This tittle: "+title+" must be valid");
    }
}
