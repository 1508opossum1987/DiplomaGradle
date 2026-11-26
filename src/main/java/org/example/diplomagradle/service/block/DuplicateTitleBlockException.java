package org.example.diplomagradle.service.block;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//исключение при попытке добавить уже используемый блок
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateTitleBlockException extends RuntimeException {
    public DuplicateTitleBlockException(String title) {
        super("Block with title: " + title + " already exists");
    }
}
