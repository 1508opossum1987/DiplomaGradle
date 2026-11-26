package org.example.diplomagradle.service.metricValue;

import org.example.diplomagradle.service.metric.InvalidIdMetricException;
import org.example.diplomagradle.service.metric.InvalidMetricNameException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "org.example.diplomagradle.api.metricValueApi")
public class MetricValueExceptionHandler {
    @ExceptionHandler(InvalidIdMetricException.class)
    public ResponseEntity<String> handleInvalidIdMetric(InvalidIdMetricException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMetricValueValuesException.class)
    public ResponseEntity<String> handleMetricValueValues(InvalidMetricValueValuesException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateMetricValueException.class)
    public ResponseEntity<String> handleDuplicateMetricValue(DuplicateMetricValueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMetricValueIdException.class)
    public ResponseEntity<String> handleInvalidMetricValueId(InvalidMetricValueIdException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyMetricValuesException.class)
    public ResponseEntity<String> handleEmptyMetricValues(EmptyMetricValuesException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMetricNameException.class)
    public ResponseEntity<String> handleInvalidMetricName(InvalidMetricNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidIdMetricValueException.class)
    public ResponseEntity<String> handleInvalidIdMetricValue(InvalidIdMetricValueException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Data integrity violation: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }
}
