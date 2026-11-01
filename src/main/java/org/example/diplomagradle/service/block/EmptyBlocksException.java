package org.example.diplomagradle.service.block;

//пустая колелкция блоков
public class EmptyBlocksException extends RuntimeException {
    public EmptyBlocksException() {
        super("The blocks is empty");
    }
}
