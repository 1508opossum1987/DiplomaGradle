package org.example.diplomagradle.service.metric;

import org.example.diplomagradle.service.block.InvalidIdBlockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MetricExceptionHandler {
    @ExceptionHandler(InvalidIdMetricException.class)
    public ResponseEntity<String> handleInvalidIdMetric(InvalidIdMetricException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMetricNameException.class)
    public ResponseEntity<String> handleInvalidMetricName(InvalidMetricNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateNameMetricException.class)
    public ResponseEntity<String> handleDuplicateNameMetric(DuplicateNameMetricException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMetricValuesException.class)
    public ResponseEntity<String> handleInvalidMetricValues(InvalidMetricValuesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidUnitMetricException.class)
    public ResponseEntity<String> handleInvalidUnitMetric(InvalidUnitMetricException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyMetricsException.class)
    public ResponseEntity<String> handleEmptyMetrics(EmptyMetricsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MetricHasMetricValuesException.class)
    public ResponseEntity<String> handleMerticHasMetricValues(MetricHasMetricValuesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
