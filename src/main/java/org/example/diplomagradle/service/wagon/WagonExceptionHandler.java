package org.example.diplomagradle.service.wagon;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WagonExceptionHandler {
    @ExceptionHandler(InvalidWagonValuesException.class)
    public ResponseEntity<String> handleInvalidWagonValues(InvalidWagonValuesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateWagonNumberException.class)
    public ResponseEntity<String> handleDuplicateWagonNumber(DuplicateWagonNumberException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidWagonIdException.class)
    public ResponseEntity<String> handleInvalidWagonId(InvalidWagonIdException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidWagonNumberException.class)
    public ResponseEntity<String> handleInvalidWagonNumber(InvalidWagonNumberException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyWagonsException.class)
    public ResponseEntity<String> handleEmptyWagons(EmptyWagonsException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidWagonPeriodException.class)
    public ResponseEntity<String> handleInvalidWagonPeriod(InvalidWagonPeriodException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
