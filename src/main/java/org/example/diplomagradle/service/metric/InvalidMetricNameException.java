package org.example.diplomagradle.service.metric;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//неправильное имя показателя
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMetricNameException extends RuntimeException {
    public InvalidMetricNameException(String name) {
        super("This name: "+name+" isn't valid");
    }
}
