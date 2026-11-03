package org.example.diplomagradle.service.block;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BlockExceptionHandler {
    @ExceptionHandler(EmptyBlocksException.class)
    public ResponseEntity<String> handleEmptyBlocks(EmptyBlocksException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidIdBlockException.class)
    public ResponseEntity<String> handleInvalidId(InvalidIdBlockException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidTitleBlockException.class)
    public ResponseEntity<String> handleInvalidTitle(InvalidTitleBlockException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BlockHasMetricsException.class)
    public ResponseEntity<String> handleBlockHasMetrics(BlockHasMetricsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
