package org.example.diplomagradle.service.metric;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//проверка наличия показателей
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyMetricsException extends RuntimeException {
    public EmptyMetricsException() {
        super("The metrics is empty");
    }
}
