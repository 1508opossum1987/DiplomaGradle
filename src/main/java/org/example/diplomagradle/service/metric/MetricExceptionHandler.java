package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.service.block.InvalidIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MetricExceptionHandler {
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<String> handleInvalidId(InvalidIdException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidIdMetricException.class)
    public ResponseEntity<String> handleInvalidIdMetric(InvalidIdMetricException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMetricNameException.class)
    public ResponseEntity<String> handleInvalidMetricName(InvalidMetricNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
