package org.example.diplomagradle.service.role;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RoleExceptionHandler {
    @ExceptionHandler(EmptyRoleNameException.class)
    public ResponseEntity<String> handleEmptyRoleName(EmptyRoleNameException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidRoleNameException.class)
    public ResponseEntity<String> handleInvalidRoleName(InvalidRoleNameException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateRoleNameException.class)
    public ResponseEntity<String> DuplicateRoleName(DuplicateRoleNameException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyRolesException.class)
    public ResponseEntity<String> emptyRoles(EmptyRolesException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
