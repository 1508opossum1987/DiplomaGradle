package org.example.diplomagradle.service.metricValue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//некорректные значения показателей в метриках
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMetricValueValuesException extends RuntimeException {
    public InvalidMetricValueValuesException(MetricValueValues values, String reason) {
        super("Invalid metricValue values: " + values + ". Reason: " + reason);
    }

    public InvalidMetricValueValuesException(String reason) {
        super("Invalid metricValue values. Reason: " + reason);
    }
}
