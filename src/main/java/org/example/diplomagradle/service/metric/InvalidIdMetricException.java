package org.example.diplomagradle.service.metric;

//валидация id показателя
public class InvalidIdMetricException extends RuntimeException{
    public InvalidIdMetricException(Long id ) {
        super("This id: " + id + " isn't found");
    }
}
