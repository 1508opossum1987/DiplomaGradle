package org.example.diplomagradle.service.block;

//неверный id
public class InvalidIdBlockException extends RuntimeException {
    public InvalidIdBlockException(Long id ) {
        super("This id: "+id+" isn't found");
    }
}
