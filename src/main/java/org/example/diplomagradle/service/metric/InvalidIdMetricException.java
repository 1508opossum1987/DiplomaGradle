package org.example.diplomagradle.service.metric;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//валидация id показателя
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidIdMetricException extends RuntimeException{
    public InvalidIdMetricException(Long id ) {
        super("This id: " + id + " isn't found");
    }
}
