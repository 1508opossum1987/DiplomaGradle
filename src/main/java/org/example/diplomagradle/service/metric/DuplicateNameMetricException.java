package org.example.diplomagradle.service.metric;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//отлов дубликата имени
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateNameMetricException extends RuntimeException {
    public DuplicateNameMetricException(String name) {
        super("This name: "+name+" is already exists, please choice other name");
    }
}
