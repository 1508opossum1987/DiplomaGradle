package org.example.diplomagradle.service.block;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//неверный id
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidIdBlockException extends RuntimeException {
    public InvalidIdBlockException(Long id ) {
        super("This id: "+id+" isn't found");
    }
}
