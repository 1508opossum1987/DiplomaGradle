package org.example.diplomagradle.service.block;

//неверный id
public class InvalidIdException extends RuntimeException {
    public InvalidIdException(Long id ) {
        super("This id: "+id+" isn't fount");
    }
}
