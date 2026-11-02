package org.example.diplomagradle.service.metric;

public class InvalidIdMetricException extends RuntimeException{
    public InvalidIdMetricException(Long id ) {
        super("This id: " + id + " isn't found");
    }
}
