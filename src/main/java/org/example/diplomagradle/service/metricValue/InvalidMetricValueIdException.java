package org.example.diplomagradle.service.metricValue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//проверка по id метрики
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidMetricValueIdException extends RuntimeException {
    public InvalidMetricValueIdException(Long metricValueId) {
        super("This id: "+metricValueId+ " is invalid");
    }
}
