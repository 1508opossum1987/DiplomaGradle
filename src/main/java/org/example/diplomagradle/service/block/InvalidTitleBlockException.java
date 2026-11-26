package org.example.diplomagradle.service.block;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//пустой или состоящий из пробелов title
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTitleBlockException extends RuntimeException {
    public InvalidTitleBlockException(String title) {
        super("This tittle: "+title+" must be valid");
    }
}
