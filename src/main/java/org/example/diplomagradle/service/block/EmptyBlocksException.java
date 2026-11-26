package org.example.diplomagradle.service.block;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//пустая коллекция блоков
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyBlocksException extends RuntimeException {
    public EmptyBlocksException() {
        super("The blocks is empty");
    }
}
