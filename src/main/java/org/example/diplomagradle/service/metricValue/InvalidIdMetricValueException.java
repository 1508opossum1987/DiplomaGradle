package org.example.diplomagradle.service.metricValue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//проверка метрики по id
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidIdMetricValueException extends RuntimeException {
    public InvalidIdMetricValueException(Long id) {
        super("MetricValue with id: " + id + " not found");
    }
}
